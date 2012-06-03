package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;
import yagalib.blackjack_example.Agent;
import yagalib.blackjack_example.Casino;

import java.util.List;

public class CasinoTest extends TestCase {

    @Test
    public void testPopulateAndGet() {
        Casino casino = new Casino();
        casino.populateWithOrganisms(10);
        List<Agent> orgs = casino.getOrganisms();
        assertNotNull(orgs);
        assertEquals(10, orgs.size());
    }
}
