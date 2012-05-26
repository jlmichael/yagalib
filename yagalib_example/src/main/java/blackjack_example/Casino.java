package blackjack_example;

import org.apache.log4j.Logger;
import yagalib.Environment;

import java.util.ArrayList;
import java.util.List;

public class Casino implements Environment<Agent> {

    private List<Agent> agents = new ArrayList<Agent>();
    private List<Dealer> dealers = new ArrayList<Dealer>();

    private Logger logger = Logger.getLogger(Casino.class);

    public void populateWithOrganisms(Integer organismCount) {
        // Generate empty Agents
        while(organismCount-- > 0) {
            agents.add(new Agent());
        }
    }

    public List<Agent> getOrganisms() {
        return agents;
    }

    public void clearOrganisms() {
        agents.clear();
    }

    public void reset() {
        dealers.clear();
    }

    public void doWorkOnOrganisms() {
        // Create enough Dealers to accomodate our Agents
        int dealerCount = (int)Math.ceil(agents.size() / 5.0f);
        while(dealerCount-- > 0) {
            try {
                dealers.add(new Dealer(new ArrayList<Agent>(), new Shoe(8, 20)));
            } catch (Exception e) {
                logger.error("Caught exception while trying to create dealers: ", e);
                return;
            }
        }

        // Place the agents with their dealers
        for(int i = 0; i < agents.size(); i++) {
            Dealer myDealer = dealers.get((int)(i / 5));
            myDealer.getAgents().add(agents.get(i));
        }

        // Tell each dealer to deal 1000 hands of BJ
        for(Dealer dealer : dealers) {
            dealer.dealSomeHands(1000);
        }
    }
}
