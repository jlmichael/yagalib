package yagalib.util;

/**
 * A class for storing the fitness data for a given generation of Organisms.  The EvolutionManager will call this
 * interface's logGeneration() method after every generation.  The implementing class is free to serialize and store
 * this data however it sees fit.  If no data storage is needed, a {@link NullWriter} is provided.
 */
public interface StatWriter {

    /**
     * Initialize the writer.
     */
    public void init();

    /**
     * Log one generation's fitness data to whatever store the StatWriter employs.
     * @param generation the generation number
     * @param maxFitness the fitness of the most fit member of the generation
     * @param minFitness the fitness of the least fir member of the generation
     * @param medianFitness the fitness of the median member of the generation
     */
    public void logGeneration(Integer generation, Integer maxFitness, Integer minFitness, Integer medianFitness);

}
