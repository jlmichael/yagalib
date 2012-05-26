import blackjack_example.Casino;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;
import yagalib.EvolutionManager;
import yagalib.Organism;
import yagalib.util.FileStatWriter;

import java.util.List;

public class GABlackJackTest extends TestCase {

    private Logger logger = Logger.getLogger(GABlackJackTest.class);

    @Test
    public void testDoOneGeneration() {
        EvolutionManager em = new EvolutionManager();
        em.setPopulation(1000);
        em.setEnvironment(new Casino());
        em.setupEnvironment();
        em.doOneGeneration();

        List<Organism> organisms = em.getEnvironment().getOrganisms();

        int i = 0;
        for(Organism o : organisms) {
            logger.info("Agent " + i++ + " fitness: " + o.getFitness());
        }
    }

    @Test
    public void testEvolve() {
        EvolutionManager em = new EvolutionManager();
        em.setPopulation(100);
        em.setBirthRatePerThousand(500);
        em.setDeathRatePerThousand(500);
        em.setMutationChancePerThousand(500);
        em.setComplexifyChancePerThousand(500);
        em.setSimplifyChancePerThousand(0);
        em.setEnvironment(new Casino());
        em.setupEnvironment();
        FileStatWriter writer = new FileStatWriter("/tmp/genstats");
        writer.init();
        em.setWriter(writer);
        em.evolve(100);

        List<Organism> organisms = em.getEnvironment().getOrganisms();
        Organism mostFit = organisms.get(0);
        logger.info("The most fit organism (fitness of " + mostFit.getFitness() + ") has a Genome like: " + mostFit.getGenome());
    }
}
