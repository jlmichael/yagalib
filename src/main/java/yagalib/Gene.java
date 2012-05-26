package yagalib;

/**
 * Genes represent the encoding of an Organism's behavior within an Environment.  They are the rules that control
 * how an Organism acts when confronted with a decision.<P>
 * <P>
 * An example of a specific Gene would be a blackjack player's rule, "When I have a soft 15 and the dealer is showing
 * a 5, I should hit."  This might be represented by a few base pairs, such as "15-soft-5-hit", and each base pair
 * would be open to mutation.
 * @see Organism
 * @see Environment
 * @author Jason Michael
 * @version 1.0
 */
public interface Gene {

    /**
     * Cause a point mutation in this Gene.
     */
    public void mutate();

    /**
     * Create a Gene with a random internal structure.
     * @return A randomized Gene.
     */
    public Gene generateRandomGene();
}