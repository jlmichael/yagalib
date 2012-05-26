package yagalib;

import java.util.Comparator;
import java.util.List;

/**
 * Organisms are the agents that evolution works upon.  They populate an {@link Environment}, which causes them to
 * express their Genes.  Their Genes determine how they act within the Environment, and hence determine
 * each Organism's fitness.  The genetic functions of pointMutate(), reproduceWith(), complexifyGenome() and
 * simplifyGenome() allow fit Organisms to proliferate and modify their Genomes.
 * @param <T> The type of {@link Genome} each Organism stores its Genes in
 * @see Gene, Environment, Genome
 * @author Jason Michael
 * @version 1.0
 */
public interface Organism<T extends Genome> {

    /**
     * A simple class for comparing two Organisms based on their fitness function
     */
    public class OrganismComparator implements Comparator<Organism> {

        public int compare(Organism first, Organism second) {
            Integer firstFitness = first.getFitness();
            Integer secondFitness = second.getFitness();
            if(firstFitness > secondFitness) {
                return -1;
            }
            if(firstFitness.equals(secondFitness)) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * The fitness function for the Organism.
     * Retrieve the fitness measure for the Organism.  Higher values indicate a more fit Organism.
     * @return The Organism's fitness as an Integer
     */
    public Integer getFitness();

    /**
     * Reset the Organism's fitness to zero.  This normally happens after each generation.
     */
    public void resetFitness();

    /**
     * Retrieve the Organism's Genome
     * @return the Organism's Genome.
     */
    public T getGenome();

    /**
     * Instruct the Organism to change a single element in one of its Genes.  Implementing classes should delegate
     * to their respective Genome.  This satisfies the Mutate genetic operation.
     */
    public void pointMutate();

    /**
     * Instruct the Organism to breed with the given Organism.  Implementing classes should create a new Organism
     * whose Genes are created by calling crossoverWith() on each pair of parent Genes.  This satisfies the Crossover
     * genetic operation.
     * @param otherOrganism The other Organism to breed with
     * @return The offspring of the two Organisms
     */
    public Organism reproduceWith(Organism otherOrganism);

    /**
     * Instruct the Organism to spontaneously add to or otherwise complexify its genome.  Implementing classes should
     * delegate to their respective Genome.
     */
    public void complexifyGenome();

    /**
     * Instruct the Organism to spontaneously delete from or otherwise simplify its genome.  Implementing classes
     * should deletegate to their respective Genome.
     */
    public void simplifyGenome();

}