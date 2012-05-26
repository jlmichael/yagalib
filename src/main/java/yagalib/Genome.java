package yagalib;

import yagalib.Gene;

import java.util.List;

/**
 * The Genome stores an Organism's Genes in some manner.  When an Organism is chosen for mutation, crossover, etc., it
 * is the Genome that handles choosing which Gene to mutate or how to populate the Genome of its offspring.<P>
 * <P>
 * The structure used to store the Genes can be fitted to the specific needs of one's Organism implementor.  If
 * having repeated Genes is important or logical, a simple List would suffice.  If duplicate Genes are counter to the
 * problem space, a Map could be used, and then each Gene would need to be identifiable by some kind of key.<P>
 * <P>
 * An example of a Genome might be a blackjack player's strategy, itself composed of a list of rules the player uses
 * to decide how to play a given hand versus the dealer's up card.
 * @param <T> the type of Genes the Genome is composed of.
 * @see Organism
 * @see Gene
 * @author Jason Michael
 * @version 1.0
 */
public interface Genome<T extends Gene> {

    /**
     * Retrieve the Genes in this Genome
     * @return a List of the Genes in this Genome
     */
    public List<T> getGenes();

    /**
     * Choose a random Gene in the Genome and then mutate it in some way.  Implementing classes should delegate the
     * actual mutation logic to the Gene's mutate() method.
     */
    public void pointMutateOneGene();

    /**
     * Produce an offspring Genome comprised of the crossover of this Genome and the other parent Organism's Genome.
     * @param otherGenome The other parent Organism's Genome
     * @return a new Genome composed of the crossover of the parents' Genomes
     */
    public Genome crossoverWith(Genome otherGenome);

    /**
     * Complexify this Genome in some way, usually by the addition of a new Gene.
     */
    public void complexify();

    /**
     * Simpleify this Genome in some way, usually by the deletion of a random Gene.
     */
    public void simplify();

}
