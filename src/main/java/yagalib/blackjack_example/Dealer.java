package yagalib.blackjack_example;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Dealer {

    // The list of Agents this dealer is dealing to.
    private List<Agent> agents;

    // This dealer's shoe of cards
    private Shoe shoe;

    private Logger logger = Logger.getLogger(Dealer.class);

    private static final Integer BET = 2;

    public Dealer() {
    }

    public Dealer(List<Agent> agents, Shoe shoe) {
        this.agents = agents;
        this.shoe = shoe;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public void dealSomeHands(int numberOfHandsToDeal) {
        while(numberOfHandsToDeal-- > 0) {
            deal();
        }
    }

    public void deal() {
        // Check the shoe for reshuffle
        if(shoe.needsReshuffle()) {
            try {
                shoe.reshuffleShoe();
            } catch (Exception e) {
                logger.error("Caught exception while shuffling shoe: ", e);
                return;
            }
        }
        // Clear each agent's hands and deal their first 2 cards
        // No, this is not how cards are normally dealt at a table.
        // No, it does not affect the outcome.
        int i = 1;
        for(Agent agent : agents) {
            agent.clearHands();
            agent.setScore(agent.getScore() - BET);

            Hand hand = new Hand();
            hand.setBetOnHand(BET);
            hand.addCard(shoe.getNextCard());
            hand.addCard(shoe.getNextCard());
            agent.addHand(hand);
            logger.debug("Agent " + i++ + " got dealt: " + hand);
        }

        // Dealer gets a 2 cards, the first of which is the "up" card
        Hand dealerHand = new Hand();
        Card upCard = shoe.getNextCard();
        Card downCard = shoe.getNextCard();
        dealerHand.addCard(upCard);
        dealerHand.addCard(downCard);
        logger.debug("Dealer showing: " + upCard + " and holding " + downCard);

        // Check for dealer blackjack
        if(dealerHand.getValue() == 21) {
            logger.debug("Dealer got a blackjack.");
        } else {
            // For each hand for each agent, keep prompting until they stand or bust
            i = 0;
            for(Agent agent : agents) {
                i++;
                int j = 1;
                List<Hand> hands = agent.getHands();
                // Since we can't remove hands from the agent during this loop, let's keep track of losing hands
                List<Hand> removeList = new ArrayList<Hand>();
                for(int handCounter = 0; handCounter < hands.size(); handCounter++) {
                    Hand hand = hands.get(handCounter);
                    logger.debug("Working through Agent " + i + " Hand " + j++);

                    while(true) {

                        // If this hand is the result of split aces, and the casino forbids hitting split aces, just cont.
                        if(hands.size() > 1 && hand.getCards().get(0).getPip() == Card.Pip.ACE) {
                            logger.debug("This hand resulted from splitting Aces, so no prompting");
                            break;
                        }

                        logger.debug("Hand is now: " + hand);
                        if(hand.getValue() > 21) {
                            // Bust!
                            logger.debug("Agent busted");
                            removeList.add(hand);
                            break;
                        } else if(hand.getValue() == 21) {
                            logger.debug("Agent got 21!");
                            break;
                        }
                        String command = agent.promptForCommand(upCard, hand);
                        if(command.equals(Rule.STAND)) {
                            logger.debug("Agent stands");
                            // Done with this hand
                            break;
                        }
                        else if(command.equals(Rule.HIT)) {
                            logger.debug("Agent hits");
                            // Add a card
                            hand.addCard(shoe.getNextCard());
                        } else if(command.equals(Rule.DOUBLE_OR_HIT)) {
                            // Check if double is allowed
                            if(CasinoRules.agentCanDoubleOnHand(agent, hand)) {
                                logger.debug("Agent doubles down (hit)");
                                // It is.  Subtract another bet from the score.
                                agent.setScore(agent.getScore() - BET);
                                hand.setBetOnHand(BET * 2);
                                // Deal one more card
                                hand.addCard(shoe.getNextCard());
                                if(hand.getValue() > 21) {
                                    // Bust!
                                    logger.debug("Agent busted");
                                    removeList.add(hand);
                                }
                                break;
                            } else {
                                // Not allowed, so just hit
                                logger.debug("Agent wanted to double, but must hit");
                                hand.addCard(shoe.getNextCard());
                            }
                        } else if(command.equals(Rule.DOUBLE_OR_STAND)) {
                            // Check if double is allowed
                            if(CasinoRules.agentCanDoubleOnHand(agent, hand)) {
                                logger.debug("Agent doubles down (stand)");
                                // It is.  Subtract another bet from the score.
                                agent.setScore(agent.getScore() - BET);
                                hand.setBetOnHand(BET * 2);
                                // Deal one more card
                                hand.addCard(shoe.getNextCard());
                                if(hand.getValue() > 21) {
                                    // Bust!
                                    logger.debug("Agent busted");
                                    removeList.add(hand);;
                                }
                                break;
                            } else {
                                // Not allowed, so just stand
                                logger.debug("Agent wanted to double, but must stand");
                                break;
                            }

                        } else if(command.equals(Rule.SPLIT)) {
                            // Verify that we can split this hand
                            if(CasinoRules.agentCanSplitHand(agent, hand)) {
                                logger.debug("Agent splits");
                                // They can.  Move the second card into a new hand and deal one card to each hand.
                                agent.setScore(agent.getScore() - BET);
                                List<Card> cards = hand.getCards();
                                Card card = cards.get(1);
                                cards.remove(1);
                                Hand newHand = new Hand();
                                newHand.addCard(card);
                                newHand.setBetOnHand(BET);
                                hand.addCard(shoe.getNextCard());
                                newHand.addCard(shoe.getNextCard());
                                agent.addHand(newHand);
                            } else {
                                // This shouldn't happen!  Log error and treat as a stand I guess.
                                logger.error("Requested a split on a non-splittable hand! Standing instead.");
                                break;
                            }
                        } else if(command.equals(Rule.SURRENDER_OR_HIT)) {
                            if(hand.getCards().size() == 2) {
                                logger.debug("Agent surrenders");
                                // Refund half the bet and remove the hand from the agent
                                agent.setScore(agent.getScore() + (BET / 2));
                                removeList.add(hand);
                                break;
                            } else {
                                logger.debug("Agent hits (couldn't surrender)");
                                hand.addCard(shoe.getNextCard());
                            }
                        } else {
                            logger.error("Uh, agent returned an invalid command: " + command);
                            break;
                        }
                    }
                    logger.debug("Final hand was: " + hand);
                }
                agent.getHands().removeAll(removeList);
            }

            // Dealer's turn.  Execute the dealer logic
            logger.debug("Dealer's turn");
            while(true) {
                logger.debug("Dealer's hand: " + dealerHand);
                if(dealerHand.getValue() > 21) {
                    // Dealer busts!
                    logger.debug("Dealer busts!");
                    break;
                } else if(dealerHand.getValue() <= 16) {
                    // Dealer must hit
                    logger.debug("Dealer hits");
                    dealerHand.addCard(shoe.getNextCard());
                } else if(dealerHand.getValue() == 17 && dealerHand.isSoft()) {
                    // Check for dealer must hit soft 17
                    if(CasinoRules.getDealerMustHitSoft17()) {
                        logger.debug("Dealer hits (soft 17)");
                        dealerHand.addCard(shoe.getNextCard());
                    } else {
                        logger.debug("Dealer stays (didn't have to hit soft 17");
                        break;
                    }
                } else {
                    logger.debug("Dealer stays");
                    break;
                }
            }
        }

        // Payouts
        i = 1;
        for(Agent agent : agents) {
            List<Hand> hands = agent.getHands();
            int j = 1;
            for(Hand hand : hands) {
                logger.debug("Agent " + i++ + " Hand " + j++ + ": " + hand);
                // Check for blackjack first
                if(hand.getValue() == 21 && hand.getCards().size() == 2 && hands.size() == 1) {
                    // If dealer got BJ as well, it's a push
                    if(dealerHand.getValue() == 21 && dealerHand.getCards().size() == 2) {
                        logger.debug("\tAgent and Dealer both got BJ: push");
                        agent.setScore(agent.getScore() + BET);
                    } else {
                        // Otherwise, it's a BJ
                        logger.debug("\tAgent got a blackjack!");
                        agent.setScore(agent.getScore() + BET * 3 + BET/2);
                    }
                }
                // Check for greater than dealer's hand or dealer busts
                else if(hand.getValue() > dealerHand.getValue() || dealerHand.getValue() > 21) {
                    logger.debug("\tWins!");
                    agent.setScore(agent.getScore() + hand.getBetOnHand() * 2);
                }
                // Check for push
                else if(hand.getValue() == dealerHand.getValue() && dealerHand.getValue() <= 21) {
                    logger.debug("\tPushes");
                    agent.setScore(agent.getScore() + hand.getBetOnHand());
                } else {
                    logger.debug("\tLoses");
                }
            }
            logger.debug("Agent's score is: " + agent.getScore());
        }
    }
}

