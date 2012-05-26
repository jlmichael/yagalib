package yagalib.util;

public class NullWriter implements StatWriter {
    /**
     * Initialize the writer.
     */
    public void init() {
        // Noop
    }

    /**
     * Log one generation's fitness data to whatever store the StatWriter employs.
     *
     * @param generation    the generation number
     * @param maxFitness    the fitness of the most fit member of the generation
     * @param minFitness    the fitness of the least fir member of the generation
     * @param medianFitness the fitness of the median member of the generation
     */
    public void logGeneration(Integer generation, Integer maxFitness, Integer minFitness, Integer medianFitness) {
        // Noop
    }
}
