package yagalib.blackjack_example;

import yagalib.EvolutionManager;
import yagalib.Gene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Rule implements Gene, Comparable<Rule> {

    private Integer dealerValue;
    private Integer handValue;
    private Boolean handIsSoft;
    private Boolean handIsSplittable;
    private String command;

    public static final String HIT = "H";
    public static final String STAND = "S";
    public static final String DOUBLE_OR_HIT = "Dh";
    public static final String DOUBLE_OR_STAND = "Ds";
    public static final String SPLIT = "P";
    public static final String SURRENDER_OR_HIT = "U";

    public Rule() {
    }

    public Rule(Integer dealerValue, Integer handValue, Boolean handIsSoft, Boolean handIsSplittable, String command) {
        this.dealerValue = dealerValue;
        this.handValue = handValue;
        this.handIsSoft = handIsSoft;
        this.handIsSplittable = handIsSplittable;
        this.command = command;
    }

    public Integer getDealerValue() {
        return dealerValue;
    }

    public void setDealerValue(Integer dealerValue) {
        this.dealerValue = dealerValue;
    }

    public Integer getHandValue() {
        return handValue;
    }

    public void setHandValue(Integer handValue) {
        this.handValue = handValue;
    }

    public Boolean getHandIsSoft() {
        return handIsSoft;
    }

    public void setHandIsSoft(Boolean handIsSoft) {
        this.handIsSoft = handIsSoft;
    }

    public Boolean getHandIsSplittable() {
        return handIsSplittable;
    }

    public void setHandIsSplittable(Boolean handIsSplittable) {
        this.handIsSplittable = handIsSplittable;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String makeKey() {
        return this.dealerValue + "-" + this.handValue + "-" + this.handIsSoft + "-" + this.handIsSplittable;
    }

    public String toString() {
        return makeKey() + "-" + this.command;
    }

    public Boolean isValid() {
        // Check for split on non-splittable hand
        if(handIsSplittable && handValue % 2 == 1) {
            return false;
        }

        if(command.equals(SPLIT) && !handIsSplittable) {
            return false;
        }

        // Check for isSoft when hand value is less than 12 (A,A)
        if(handIsSoft && handValue < 12) {
            return false;
        }

        return true;
    }

    public Boolean appliesToTableState(Card dealerCard, Hand hand, Agent agent) {
        if(dealerCard.getValue() != dealerValue) {
            return false;
        }

        if(hand.getValue() != handValue) {
            return false;
        }

        if(handIsSoft && !hand.isSoft()) {
            return false;
        }

        if(!handIsSoft && hand.isSoft()) {
            return false;
        }

        if(handIsSplittable && !CasinoRules.agentCanSplitHand(agent, hand)) {
            return false;
        }

        if(!handIsSplittable && CasinoRules.agentCanSplitHand(agent, hand)) {
            return false;
        }

        return true;
    }

    // A library function to generate a random, valid rule
    public static Rule generateRandomRule() {
        Rule rule;
        do {
            int dealerValue = EvolutionManager.random.nextInt(10) + 1;
            boolean isSplittable = EvolutionManager.random.nextInt(2) == 0;
            int handValue = EvolutionManager.random.nextInt(17) + 4;
            if(handValue % 2 == 1) {
                isSplittable = false;
            } else {
                isSplittable = EvolutionManager.random.nextInt(2) == 0;
            }
            boolean isSoft = EvolutionManager.random.nextInt(2) == 0;
            String command = "";
            switch(EvolutionManager.random.nextInt(6)) {
                case 0:
                    command = Rule.HIT;
                    break;
                case 1:
                    command = Rule.STAND;
                    break;
                case 2:
                    command = Rule.DOUBLE_OR_HIT;
                    break;
                case 3:
                    command = Rule.DOUBLE_OR_STAND;
                    break;
                case 4:
                    command = Rule.SPLIT;
                    break;
                case 5:
                    command = Rule.SURRENDER_OR_HIT;
                    break;
            }
            rule = new Rule(dealerValue, handValue, isSoft, isSplittable, command);
        } while(!rule.isValid());

        return rule;
    }

    //  A library function to return a list of Rules representing Basic Strategy
    public static List<Rule> generateBasicStrategy() {
        List<Rule> strategy = new ArrayList<Rule>();

        // Hard hands
        for(int i = 1; i <= 10; i++) {
            for(int j = 5; j <= 21; j++) {
                String command = "";
                if(i == 1) {
                    if (j >= 17) {
                        command = STAND;
                    } else if(j == 16) {
                        command = SURRENDER_OR_HIT;
                    } else {
                        command = HIT;
                    }
                } else if(i == 2) {
                    if(j >= 13) {
                        command = STAND;
                    } else if(j == 12 || j <= 9) {
                        command = HIT;
                    } else {
                        command = DOUBLE_OR_HIT;
                    }
                } else if(i == 3) {
                    if(j >= 13) {
                        command = STAND;
                    } else if(j == 12 || j <= 8) {
                        command = HIT;
                    } else {
                        command = DOUBLE_OR_HIT;
                    }
                } else if(i >= 4 && i <= 6) {
                    if(j >= 12) {
                        command = STAND;
                    } else if(j <= 8) {
                        command = HIT;
                    } else {
                        command = DOUBLE_OR_HIT;
                    }
                } else if(i == 7 || i == 8) {
                    if(j >= 17) {
                        command = STAND;
                    } else if(j >= 12 || j <= 9) {
                        command = HIT;
                    } else {
                        command = DOUBLE_OR_HIT;
                    }
                } else if(i == 9) {
                    if(j >= 17) {
                        command = STAND;
                    } else if(j == 16) {
                        command = SURRENDER_OR_HIT;
                    } else if(j >= 12 || j <= 9) {
                        command = HIT;
                    } else {
                        command = DOUBLE_OR_HIT;
                    }
                } else if(i == 10) {
                    if(j >= 17) {
                        command = STAND;
                    } else if(j == 16 || j == 15) {
                        command = SURRENDER_OR_HIT;
                    } else if(j >= 12 || j <= 10) {
                        command = HIT;
                    } else {
                        command = DOUBLE_OR_HIT;
                    }
                }
                strategy.add(new Rule(i, j, false, false, command));
            }
        }

        // Soft totals
        for(int i = 1; i <= 10; i++) {
            for(int j = 13; j <= 21; j++) {
                String command = "";
                if(i == 1 || i >= 9) {
                    if(j >= 19) {
                        command = STAND;
                    } else {
                        command = HIT;
                    }
                } else if(i == 2 || i >= 7) {
                    if(j >= 18) {
                        command = STAND;
                    } else {
                        command = HIT;
                    }
                } else if(i == 3) {
                    if(j >= 19) {
                        command = STAND;
                    } else if(j == 18) {
                        command = DOUBLE_OR_STAND;
                    } else if(j == 17) {
                        command = DOUBLE_OR_HIT;
                    } else {
                        command = HIT;
                    }
                } else if(i == 4) {
                    if(j >= 19) {
                        command = STAND;
                    } else if(j == 18) {
                        command = DOUBLE_OR_STAND;
                    } else if(j >= 15) {
                        command = DOUBLE_OR_HIT;
                    } else {
                        command = HIT;
                    }
                } else {
                    if(j >= 19) {
                        command = STAND;
                    } else if(j == 18) {
                        command = DOUBLE_OR_STAND;
                    } else {
                        command = DOUBLE_OR_HIT;
                    }
                }
                strategy.add(new Rule(i, j, true, false, command));
            }
        }

        // Splits
        for(int i = 1; i <= 10; i++) {
            for(int j = 2; j <= 20; j += 2) {
                String command = "";
                if(i == 1) {
                    if(j == 2 || j == 16) {
                        command = SPLIT;
                    } else if(j == 20 || j == 18) {
                        command = STAND;
                    } else {
                        command = HIT;
                    }
                } else if(i <= 4) {
                    if(j == 10) {
                        command = DOUBLE_OR_HIT;
                    } else if(j == 20) {
                        command = STAND;
                    } else if(j == 8) {
                        command = HIT;
                    } else {
                        command = SPLIT;
                    }
                } else if(i <= 6) {
                    if(j == 10) {
                        command = DOUBLE_OR_HIT;
                    } else if(j == 20) {
                        command = STAND;
                    } else {
                        command = SPLIT;
                    }
                } else if(i == 7) {
                    if(j == 20 || j == 18) {
                        command = STAND;
                    } else if(j == 10) {
                        command = DOUBLE_OR_HIT;
                    } else if(j == 12 || j == 8) {
                        command = HIT;
                    } else {
                        command = SPLIT;
                    }
                } else if(i <= 9) {
                    if(j == 20) {
                        command = STAND;
                    } else if(j == 10) {
                        command = DOUBLE_OR_HIT;
                    } else if(j == 2 || j == 18 || j == 16) {
                        command = SPLIT;
                    } else {
                        command = HIT;
                    }
                } else {
                    if(j == 2 || j == 16) {
                        command = SPLIT;
                    } else if(j == 20 || j == 18) {
                        command = STAND;
                    } else {
                        command = HIT;
                    }
                }
                strategy.add(new Rule(i, j, false, true, command));
            }
        }

        // Ten more for A,A, since in the above Pairs section, everything was hard
        strategy.add(new Rule(1, 12, true, true, SPLIT));
        strategy.add(new Rule(2, 12, true, true, SPLIT));
        strategy.add(new Rule(3, 12, true, true, SPLIT));
        strategy.add(new Rule(4, 12, true, true, SPLIT));
        strategy.add(new Rule(5, 12, true, true, SPLIT));
        strategy.add(new Rule(6, 12, true, true, SPLIT));
        strategy.add(new Rule(7, 12, true, true, SPLIT));
        strategy.add(new Rule(8, 12, true, true, SPLIT));
        strategy.add(new Rule(9, 12, true, true, SPLIT));
        strategy.add(new Rule(10, 12, true, true, SPLIT));

        return strategy;
    }

    //  A library function to return a list of Rules representing random responses
    //  every possible input.
    public static List<Rule> generateFullRandomStrategy() {
        List<Rule> strategy = new ArrayList<Rule>();
        List<String> commands = Arrays.asList(STAND, SURRENDER_OR_HIT, HIT, SPLIT, DOUBLE_OR_HIT, DOUBLE_OR_STAND);
        Random rand = EvolutionManager.random;
        Rule rule;
        String command;

        // Hard hands
        for(int i = 1; i <= 10; i++) {
            for(int j = 5; j <= 21; j++) {
                do {
                    command = commands.get(rand.nextInt(commands.size()));
                    rule = new Rule(i, j, false, false, command);
                } while(!rule.isValid());
                strategy.add(rule);
            }
        }

        // Soft totals
        for(int i = 1; i <= 10; i++) {
            for(int j = 13; j <= 21; j++) {
                do {
                    command = commands.get(rand.nextInt(commands.size()));
                    rule = new Rule(i, j, true, false, command);
                } while(!rule.isValid());
                strategy.add(rule);
            }
        }

        // Splits
        for(int i = 1; i <= 10; i++) {
            for(int j = 2; j <= 20; j += 2) {
                do {
                    command = commands.get(rand.nextInt(commands.size()));
                    rule = new Rule(i, j, false, true, command);
                } while(!rule.isValid());
                strategy.add(rule);
            }
        }

        // Ten more for A,A, since in the above Pairs section, everything was hard
        for(int i = 1; i <= 10; i++) {
            do {
                command = commands.get(rand.nextInt(commands.size()));
                rule = new Rule(i, 12, true, true, command);
            } while(!rule.isValid());
            strategy.add(rule);
        }

        return strategy;
    }

    // Genetic operations below

    public void mutate() {
        do {
            int commandIndex = EvolutionManager.random.nextInt(6);
            String command = "";
            switch(commandIndex) {
                case 0:
                    command = HIT;
                    break;
                case 1:
                    command = STAND;
                    break;
                case 2:
                    command = DOUBLE_OR_HIT;
                    break;
                case 3:
                    command = DOUBLE_OR_STAND;
                    break;
                case 4:
                    command = SPLIT;
                    break;
                case 5:
                    command = SURRENDER_OR_HIT;
                    break;
            }
            setCommand(command);
        } while(!isValid());
    }

    public Gene generateRandomGene() {
        return generateRandomRule();
    }

    public Rule copy() {
        return new Rule(dealerValue, handValue, handIsSoft, handIsSplittable, command);
    }

    public int compareTo(Rule o) {
        int dvdiff = dealerValue.compareTo(o.getDealerValue());
        if(dvdiff == 0) {
            int hvdiff = handValue.compareTo(o.getHandValue());
            if(hvdiff == 0) {
                int softdiff = handIsSoft.compareTo(o.getHandIsSoft());
                if(softdiff == 0) {
                    int splitdiff = handIsSplittable.compareTo(o.getHandIsSplittable());
                    if(splitdiff == 0) {
                        return command.compareTo(o.getCommand());
                    } else {
                        return splitdiff;
                    }
                } else {
                    return softdiff;
                }
            } else {
                return hvdiff;
            }
        } else {
            return dvdiff;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (!dealerValue.equals(rule.dealerValue)) return false;
        if (!handValue.equals(rule.handValue)) return false;
        if (!handIsSoft.equals(rule.handIsSoft)) return false;
        if (!handIsSplittable.equals(rule.handIsSplittable)) return false;
        return command.equals(rule.command);

    }
}

