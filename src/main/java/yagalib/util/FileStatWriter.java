package yagalib.util;

import java.io.*;

public class FileStatWriter implements StatWriter {

    private Writer writer;
    private String outputFilename;

    /**
     * A StatWriter that writes its output to a file on local disk.
     * @param outputFilename the name of the output file, including path
     */
    public FileStatWriter(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    /**
     * Initialize the FileStatWriter.  Creates a BufferedWriter pointed at the writer's outputFilename.
     */
    public void init() {
        try {
            writer = new BufferedWriter(new FileWriter(outputFilename));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Writes and flushes one generation of fitness data to the output file.
     * @param generation the generation number
     * @param maxFitness the fitness of the most fit member of the generation
     * @param minFitness the fitness of the least fir member of the generation
     * @param medianFitness the fitness of the median member of the generation
     */
    public void logGeneration(Integer generation, Integer maxFitness, Integer minFitness, Integer medianFitness) {
        StringBuilder line = new StringBuilder();
        line.append(generation);
        line.append(",");
        line.append(maxFitness);
        line.append(",");
        line.append(minFitness);
        line.append(",");
        line.append(medianFitness);
        line.append("\n");
        try {
            writer.write(line.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
