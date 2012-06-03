import yagalib.blackjack_example.Casino;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;
import yagalib.EvolutionManager;
import yagalib.Organism;
import yagalib.util.NullWriter;

import java.util.List;

public class GABlackJackTest extends TestCase {

    private Logger logger = Logger.getLogger(GABlackJackTest.class);

    @Test
    public void testEvolve() {
        // Remove the next line to actually run the test
        if(true == true) return;
        EvolutionManager em = new EvolutionManager();
        em.setPopulation(100);
        em.setBirthRatePerThousand(500);
        em.setDeathRatePerThousand(500);
        em.setMutationChancePerThousand(500);
        em.setComplexifyChancePerThousand(500);
        em.setSimplifyChancePerThousand(0);
        em.setEnvironment(new Casino());
        em.setupEnvironment();
        NullWriter writer = new NullWriter();
        writer.init();
        em.setWriter(writer);
        em.evolve(100000);

        List<Organism> organisms = em.getEnvironment().getOrganisms();
        Organism mostFit = organisms.get(0);
        logger.info("The most fit organism (fitness of " + mostFit.getFitness() + ") has a Genome like: " + mostFit.getGenome());
    }
}
