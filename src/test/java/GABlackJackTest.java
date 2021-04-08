import yagalib.blackjack_example.Agent;
import yagalib.blackjack_example.Casino;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;
import yagalib.EvolutionManager;
import yagalib.Organism;
import yagalib.blackjack_example.Rule;
import yagalib.blackjack_example.Strategy;
import yagalib.util.SampledFileStatWriter;

import java.util.List;
import java.util.function.Function;

public class GABlackJackTest extends TestCase {

    private Logger logger = Logger.getLogger(GABlackJackTest.class);

    private Function<List<Organism>, Boolean> stopWhenBasicStrategyEvolves = new Function<List<Organism>, Boolean>() {
        private Strategy basicStrategy = new Strategy(Rule.generateBasicStrategy());
        public Boolean apply(List<Organism> organisms) {
            for(Organism o : organisms) {
                Agent agent = (Agent) o;
                if(agent.getStrategy().equals(basicStrategy)) { return true; }
            }
            return false;
        }
    };

    @Test
    public void testEvolve() {
        // Remove the next line to actually run the test
//        if(true == true) return;
        EvolutionManager em = new EvolutionManager();
        em.setPopulation(200);
        em.setBirthRatePerThousand(400);
        em.setChampionCloneRatePerThousand(100);
        em.setDeathRatePerThousand(500);
        em.setMutationChancePerThousand(100);
        em.setComplexifyChancePerThousand(0);
        em.setSimplifyChancePerThousand(0);
        em.setEnvironment(new Casino());
        em.setupEnvironment();
        SampledFileStatWriter writer = new SampledFileStatWriter("/tmp/bj1.csv", 10);
        writer.init();
        em.setWriter(writer);
        em.evolve(100000, stopWhenBasicStrategyEvolves);

        List<Organism> organisms = em.getEnvironment().getOrganisms();
        Organism mostFit = organisms.get(0);
        logger.info("The most fit organism (fitness of " + mostFit.getFitness() + ") has a Genome like: " + mostFit.getGenome());
    }
}
