package yagalib.blackjack_example;

import org.apache.log4j.Logger;
import yagalib.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Casino implements Environment<Agent> {

    private List<Agent> agents = new ArrayList<Agent>();
    private List<Dealer> dealers = new ArrayList<Dealer>();
    private ExecutorService executor;
    private List<Callable<Boolean>> dealerTasks = new ArrayList<Callable<Boolean>>();

    private Logger logger = Logger.getLogger(Casino.class);

    public void populateWithOrganisms(Integer organismCount) {
        // Generate empty Agents
        while(organismCount-- > 0) {
            Agent agent = new Agent();
            agent.setStrategy(new Strategy(Rule.generateFullRandomStrategy()));
            agents.add(agent);
        }

        int dealerCount = (int)Math.ceil(agents.size() / 5.0f);
        executor = Executors.newFixedThreadPool(4);
        while(dealerCount-- > 0) {
            try {
                Dealer dealer = new Dealer(new ArrayList<Agent>(), new Shoe(8, 20));
                dealers.add(dealer);
                dealerTasks.add(dealer.getCallable());
            } catch (Exception e) {
                logger.error("Caught exception while trying to create dealers: ", e);
                return;
            }
        }
    }

    public List<Agent> getOrganisms() {
        return agents;
    }

    public void clearOrganisms() {
        agents.clear();
        dealers.clear();
    }

    public void reset() {}

    public void doWorkOnOrganisms() {
        for(Dealer dealer : dealers) {
            dealer.clearAgents();
        }
        // Place the agents with their dealers
        for(int i = 0; i < agents.size(); i++) {
            Dealer myDealer = dealers.get((int)(i / 5));
            myDealer.getAgents().add(agents.get(i));
        }

        try {
            executor.invokeAll(dealerTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
