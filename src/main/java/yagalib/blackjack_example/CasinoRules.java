package yagalib.blackjack_example;

import java.util.List;

public class CasinoRules {

    public static Boolean dealerMustHitSoft17 = true;
    public static Integer numberOfDecksInShoe = 8;
    public static String surrenderAllowed = "late"; // Can be no, early, and late
    public static Integer resplitsAllowed = -1; // -1 means unlimited
    public static Boolean canHitSplitAces = false;
    public static Boolean canResplitAces = true;
    public static Boolean canDoubleAfterSplit = true;
    public static Boolean doubleOnNineTenElevenOnly = false;

    public static Boolean getDealerMustHitSoft17() {
        return dealerMustHitSoft17;
    }

    public static void setDealerMustHitSoft17(Boolean dealerMustHitSoft17) {
        CasinoRules.dealerMustHitSoft17 = dealerMustHitSoft17;
    }

    public static Integer getNumberOfDecksInShoe() {
        return numberOfDecksInShoe;
    }

    public static void setNumberOfDecksInShoe(Integer numberOfDecksInShoe) {
        CasinoRules.numberOfDecksInShoe = numberOfDecksInShoe;
    }

    public static String getSurrenderAllowed() {
        return surrenderAllowed;
    }

    public static void setSurrenderAllowed(String surrenderAllowed) {
        CasinoRules.surrenderAllowed = surrenderAllowed;
    }

    public static Integer getResplitsAllowed() {
        return resplitsAllowed;
    }

    public static void setResplitsAllowed(Integer resplitsAllowed) {
        CasinoRules.resplitsAllowed = resplitsAllowed;
    }

    public static Boolean getCanHitSplitAces() {
        return canHitSplitAces;
    }

    public static void setCanHitSplitAces(Boolean canHitSplitAces) {
        CasinoRules.canHitSplitAces = canHitSplitAces;
    }

    public static Boolean getCanResplitAces() {
        return canResplitAces;
    }

    public static void setCanResplitAces(Boolean canResplitAces) {
        CasinoRules.canResplitAces = canResplitAces;
    }

    public static Boolean getCanDoubleAfterSplit() {
        return canDoubleAfterSplit;
    }

    public static void setCanDoubleAfterSplit(Boolean canDoubleAfterSplit) {
        CasinoRules.canDoubleAfterSplit = canDoubleAfterSplit;
    }

    public static Boolean getDoubleOnNineTenElevenOnly() {
        return doubleOnNineTenElevenOnly;
    }

    public static void setDoubleOnNineTenElevenOnly(Boolean doubleOnNineTenElevenOnly) {
        CasinoRules.doubleOnNineTenElevenOnly = doubleOnNineTenElevenOnly;
    }

    public static Boolean agentCanSplitHand(Agent agent, Hand hand) {
        List<Card> cards = hand.getCards();

        // 2 cards check
        if(cards.size() != 2) {
            return false;
        }

        // Pair check
        if(cards.get(0).getPip() != cards.get(1).getPip()) {
            return false;
        }

        // Resplit check
        if(resplitsAllowed > -1 && agent.getHandCount() >= resplitsAllowed) {
            return false;
        }

        // resplit aces check
        if(cards.get(0).getPip() == Card.Pip.ACE && agent.getHandCount() > 1 && !canResplitAces) {
            return false;
        }

        return true;
    }

    public static Boolean agentCanDoubleOnHand(Agent agent, Hand hand) {
        // Can only double on initial hand.  Should this be configurable?
        if(hand.getCards().size() != 2) {
            return false;
        }

        // Check if we can only double on 9, 10, 11
        if(doubleOnNineTenElevenOnly && (
                hand.getValue() < 9 ||
                hand.getValue() > 11)) {
            return false;
        }

        // check if doubleAfterSplit is allowed, and if already split
        if(!canDoubleAfterSplit && agent.getHandCount() > 1) {
            return false;
        }

        return true;
    }
}
