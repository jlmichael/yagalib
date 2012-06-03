package yagalib.util;

public class SampledFileStatWriter implements StatWriter {

    private FileStatWriter writer;
    private int sampleRateModulus;
    private long lineCount = 0;

    public SampledFileStatWriter(String outputFilename, int sampleRateModulus) {
        this.writer = new FileStatWriter(outputFilename);
        this.sampleRateModulus = sampleRateModulus;
    }


    /**
     * Initialize the writer.
     */
    public void init() {
        writer.init();
        lineCount = 0;
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
        if(lineCount++ % sampleRateModulus == 0) {
            writer.logGeneration(generation, maxFitness, minFitness, medianFitness);
        }
    }
}
