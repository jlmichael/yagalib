package blackjack_example;

import org.apache.log4j.Logger;
import yagalib.Organism;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Agent implements Organism<Strategy> {

    // An agent's score is sort of like a player's bankroll.  Each hand of blackjack, it gets decreased by 2.  If the
    // agent wins the hand, it gets increased by 4.  If they push, it gets increased by 2.  If they get a blackjack, it
    // gets increased by 5 (except on dealer BJ, where it increased 4).  If they surrender, it gets increased by 1.
    // We start at 0, and negative values are allowed, obviously.
    private int score = 0;
    private Strategy strategy = new Strategy();

    // An agent may split his cards, hence we need to provide for a list of Hands rather than just one.
    private List<Hand> hands = new ArrayList<Hand>();

    private static Logger logger = Logger.getLogger(Agent.class);

    public Integer getHandCount() {
        return hands.size();
    }

    public List<Hand> getHands() {
        return hands;
    }

    public void addHand(Hand hand) {
        hands.add(hand);
    }

    public void clearHands() {
        hands.clear();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public String promptForCommand(Card dealerCard, Hand hand) {
        Rule rule = strategy.getRuleThatAppliesToTableState(dealerCard, hand, this);
        if(rule != null) {
            logger.debug("Got a hit!  Command is " + rule.getCommand());
            return rule.getCommand();
        }

//        Random r = new Random(new Date().getTime());
//        switch(r.nextInt(5)) {
//            case 0:
//                return Rule.HIT;
//            case 1:
//                return Rule.STAND;
//            case 2:
//                return Rule.DOUBLE_OR_HIT;
//            case 3:
//                return Rule.DOUBLE_OR_STAND;
//            case 4:
//                return Rule.SURRENDER_OR_HIT;
//        }
        return Rule.HIT;
    }

    // Genetic operations follow

    public Integer getFitness() {
        return score;
    }

    public void resetFitness() {
        score = 0;
    }

    public Strategy getGenome() {
        return strategy;
    }

    public void pointMutate() {
        strategy.pointMutateOneGene();
    }

    public Organism reproduceWith(Organism otherOrganism) {
        Agent offspring = new Agent();
        Strategy newStrategy = (Strategy)strategy.crossoverWith(otherOrganism.getGenome());
        offspring.setStrategy(newStrategy);
        return offspring;
    }

    public void complexifyGenome() {
        strategy.complexify();
    }

    public void simplifyGenome() {
        strategy.simplify();
    }

    public int compareTo(Object o) {
        Integer otherScore = ((Agent)o).getScore();
        if(score > otherScore) {
            return -1;
        }
        if(score == otherScore) {
            return 0;
        }
        return 1;
    }
}
