package yagalib;

import java.util.List;

/**
 * Environments are the worlds in which Organisms live.  By doing work on their population, Environments cause
 * each Organism to exercise its Genes.<P>
 * <P>
 * An example Environment might be a Casino in a Blackjack simulation.  The Organisms in the sim would be blackjack
 * players, and the Casino, acting as the Environment, would make each player play a number of hands of blackjack
 * using rules/information encoded in the player's Gene objects.
 *
 * @param <T> The type of Organisms populating this Environment
 * @see Organism, Gene
 * @author Jason Michael
 * @version 1.0
 */
public interface Environment<T extends Organism> {

    /**
     * Generate the given number of Organisms.
     * @param organismCount  the number of organisms to create
     */
    public void populateWithOrganisms(Integer organismCount);

    /**
     * Reset any Environment-specific state between generations.  An example would be to reset all the dealers, tables,
     * and card decks in a Casino.  Note: it is important that implementing classes do *not* alter any Organism state
     * in this method.
     */
    public void reset();

    /**
     * Get the list of Organisms populating the Environment
     * @return A List of the Organisms populating the Environment
     */
    public List<T> getOrganisms();

    /**
     * Remove all Organisms from the Environment
     */
    public void clearOrganisms();

    /**
     * Cause the Organisms in the Environment to exercise their Genes.  Implementing classes should execute or cause
     * to be executed the logic of the problem space in this function.  In the Casino example, the Casino class might
     * cause each dealer at each table to deal a number of hands of blackjack to the Organisms in the Casino, causing
     * each to exercise the blackjack logic/rules encoded in their Genes.
     */
    public void doWorkOnOrganisms();
}