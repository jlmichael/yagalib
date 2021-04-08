package yagalib.blackjack_example;

import yagalib.EvolutionManager;
import yagalib.Genome;

import java.util.*;

public class Strategy implements Genome<Rule> {

    private Map<String, Rule> rules = new HashMap<String, Rule>();

    public Strategy() {
    }

    public Strategy(List<Rule> rules) {
        for(Rule rule : rules) {
            String key = rule.makeKey();
            this.rules.put(key, rule);
        }
    }

    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<Rule>();
        rules.addAll(this.rules.values());
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules.clear();
        for(Rule rule : rules) {
            String key = rule.makeKey();
            this.rules.put(key, rule);
        }
    }

    public List<Rule> getGenes() {
        return getRules();
    }

    public Rule getRuleThatAppliesToTableState(Card dealerCard, Hand hand, Agent agent) {
        Rule fakeRule = new Rule();
        fakeRule.setDealerValue(dealerCard.getValue());
        fakeRule.setHandValue(hand.getValue());
        fakeRule.setHandIsSoft(hand.isSoft());
        fakeRule.setHandIsSplittable(CasinoRules.agentCanSplitHand(agent, hand));
        String key = fakeRule.makeKey();
        return rules.get(key);
    }

    public void pointMutateOneGene() {
        if(rules.isEmpty()) {
            return;
        }
        List<Rule> ruleList = new ArrayList<Rule>();
        ruleList.addAll(rules.values());
        Rule mutant = ruleList.get(EvolutionManager.random.nextInt(ruleList.size()));
        mutant.mutate();
    }

    public Genome crossoverWith(Genome otherGenome) {
        // Create a new Genome, with half the rules from one Genome, and half from the other.
        Strategy offspringGenome = new Strategy();
        List<Rule> newRules = new ArrayList<Rule>();
        List<Rule> ourRules = new ArrayList<Rule>(rules.values());
        Collections.sort(ourRules);
        if(ourRules.size() > 0) {
            for(int i = 0; i < ourRules.size(); i += 2) {
                Rule newRule = new Rule();
                Rule parentRule = ourRules.get(i);
                newRule.setDealerValue(parentRule.getDealerValue());
                newRule.setHandValue(parentRule.getHandValue());
                newRule.setHandIsSplittable(parentRule.getHandIsSplittable());
                newRule.setHandIsSoft(parentRule.getHandIsSoft());
                newRule.setCommand(parentRule.getCommand());
                newRules.add(newRule);
            }
        }
        List<Rule> otherGenes = otherGenome.getGenes();
        Collections.sort(otherGenes);
        if(otherGenes.size() > 1) {
            for(int i = 1; i < otherGenes.size(); i += 2) {
                Rule newRule = new Rule();
                Rule parentRule = otherGenes.get(i);
                newRule.setDealerValue(parentRule.getDealerValue());
                newRule.setHandValue(parentRule.getHandValue());
                newRule.setHandIsSplittable(parentRule.getHandIsSplittable());
                newRule.setHandIsSoft(parentRule.getHandIsSoft());
                newRule.setCommand(parentRule.getCommand());
                newRules.add(newRule);
            }
        }
        offspringGenome.setRules(newRules);

        return offspringGenome;
    }

    public void complexify() {
        Rule newRule = Rule.generateRandomRule();
        rules.put(newRule.makeKey(), newRule);
    }

    public void simplify() {
        if(!rules.isEmpty()) {
            List<Rule> ruleList = new ArrayList<Rule>();
            ruleList.addAll(rules.values());
            Rule mutant = ruleList.get(EvolutionManager.random.nextInt(ruleList.size()));
            String mutantKey = mutant.makeKey();
            rules.remove(mutantKey);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        int i = 0;
        for(Map.Entry<String, Rule> entry : rules.entrySet()) {
            sb.append("Rule ");
            sb.append(i++);
            sb.append(": ");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public Strategy copy() {
        Strategy copy = new Strategy();
        List<Rule> rules = new ArrayList<Rule>();
        for(Rule rule : this.getRules()) {
            rules.add(rule.copy());
        }
        copy.setRules(rules);
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) { return true; }
        if(!(obj instanceof Strategy)) { return false; }
        Strategy o = (Strategy) obj;
        List<Rule> left = getRules();
        List<Rule> right = o.getRules();
        Collections.sort(left);
        Collections.sort(right);
        return left.equals(right);
    }
}
