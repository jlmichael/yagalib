package yagalib;

import org.apache.log4j.Logger;
import yagalib.util.StatWriter;

import java.util.*;

/**
 * EvolutionManager executes the process of evolution for the Environment it is given.  It manages the various rates,
 * such as death and birth rates, mutation rates, etc., that drive the evolution of Organisms in the Environment.
 * At the beginning of each generation, it instructs the Environment to reset its state and begin working on
 * the population of Organisms via its doWorkOnOrganisms() method.<P>
 * <P>
 * At the end of each generation, it sorts the Organisms in the Environment by fitness.  It then culls the least fit
 * based on the death rate, and repopulates by breeding surviving Organisms based on the birth rate.  Surviving
 * Organisms are then mutated based on the mutation rate, complexify rate, and simplify rates.  The EvolutionManager
 * will then instruct its StatWriter to log the generation's fitness data, and then it will reset all surviving
 * Organisms' fitness to some initial value (determined by the Organism class).  It repeats this entire process
 * the given number of times.
 * @see Environment
 * @see Organism
 */
public class EvolutionManager<T extends Organism> {

    /**
     * A Random that can be used by any of the various entities to save on instantiation costs
     */
    public static Random random = new Random(new Date().getTime());

    /**
     * The Environment this EvolutionManager is in charge of evolving.
     */
    private Environment environment;

    /**
     * The number of Organisms to populate the Environment with initially.
     */
    private Integer population = 10;

    /**
     * The rate at which mutations occur, as the number of point mutations per thousand Organisms.
     */
    private Integer mutationChancePerThousand = 10;

    /**
     * The rate of spontaneous Genome complexifying as the number of complexify calls per thousand Organisms.
     */
    private Integer complexifyChancePerThousand = 10;

    /**
     * The rate of spontaneous Genome simplifying as the number of simplify calls per thousand Organisms.
     */
    private Integer simplifyChancePerThousand = 10;

    /**
     * The death rate, expressed as the number of Organisms to cull per thousand Organisms.  Less fit Organisms will be
     * culled before more fit Organisms.  It is important to keep this rate equal to the birth rate if you desire a
     * fixed population size.
     */
    private Integer deathRatePerThousand = 10;

    /**
     * The birth rate, expressed as the number of offspring Organisms created per thousand Organisms.  It is important
     * to keep this rate equal to the death rate if you desire a fixed population size.
     */
    private Integer birthRatePerThousand = 10;

    /**
     * The current generation count
     */
    private Integer generationCount = 0;

    /**
     * An ArrayList for storing the offspring of one generation.
     */
    private ArrayList<T> tmpOffspringArray = new ArrayList<T>();

    /**
     * The number of deaths per generation
     */
    private int deaths;

    /**
     * The number of births per generation
     */
    private int births;

    /**
     * The number of complexifies per generation
     */
    private int complexifies;

    /**
     * The number of mutations per generation
     */
    private int mutations;

    /**
     * The number of simplifies per generation
     */
    private int simplifies;

    /**
     * The StatWriter instance used to log generation fitness data
     */
    private StatWriter writer;

    private Logger logger = Logger.getLogger(EvolutionManager.class);

    /**
     * Sets the Environment instance this EvolutionManager is in charge of
     * @param environment the Environment to evolve
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Retrieve the Environment assigned to this EvolutionManager
     * @return the Environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Sets the rate at which mutations occur, as the number of point mutations per thousand Organisms.
     * @param mutationChancePerThousand the number of point mutations per thousand Organisms
     */
    public void setMutationChancePerThousand(Integer mutationChancePerThousand) {
        this.mutationChancePerThousand = mutationChancePerThousand;
    }

    /**
     * Sets the rate of spontaneous Genome complexifying as the number of complexify calls per thousand Organisms.
     * @param complexifyChancePerThousand the number of complexify operations to call per thousand Organisms
     */
    public void setComplexifyChancePerThousand(Integer complexifyChancePerThousand) {
        this.complexifyChancePerThousand = complexifyChancePerThousand;
    }

    /**
     * Set the rate of spontaneous Genome simplifying as the number of simplify calls per thousand Organisms.
     * @param simplifyChancePerThousand the number of simplify operations to call per thousand Organisms
     */
    public void setSimplifyChancePerThousand(Integer simplifyChancePerThousand) {
        this.simplifyChancePerThousand = simplifyChancePerThousand;
    }

    /**
     * Set the death rate, expressed as the number of Organisms to cull per thousand Organisms.  Less fit Organisms
     * will be culled before more fit Organisms.  It is important to keep this rate equal to the birth rate if you
     * desire a fixed population size.
     * @param deathRatePerThousand the number of deaths per thousand Organisms
     */
    public void setDeathRatePerThousand(Integer deathRatePerThousand) {
        this.deathRatePerThousand = deathRatePerThousand;
    }

    /**
     * Set the birth rate, expressed as the number of offspring Organisms created per thousand Organisms.  It is
     * important to keep this rate equal to the death rate if you desire a fixed population size.
     * @param birthRatePerThousand the number of births per thousand Organisms
     */
    public void setBirthRatePerThousand(Integer birthRatePerThousand) {
        this.birthRatePerThousand = birthRatePerThousand;
    }

    /**
     * Sets the number of Organisms to populate the Environment with initially.
     * @param population The number of Organisms
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * Set the StatWriter instance used to log generation fitness data
     * @param writer The StatWriter instance
     */
    public void setWriter(StatWriter writer) {
        this.writer = writer;
    }

    /**
     * Initialize the Environment by calling its clearOrganism() and populatWithOrganisms() methods.
     */
    public void setupEnvironment() {
        environment.clearOrganisms();
        environment.populateWithOrganisms(population);
        deaths = (int)Math.ceil(population / 1000.0f * deathRatePerThousand);
        births = (int)Math.ceil(population / 1000.0f * birthRatePerThousand);
        complexifies = (int)Math.ceil((population - deaths) / 1000.0f * complexifyChancePerThousand);
        simplifies = (int)Math.ceil((population - deaths) / 1000.0f * simplifyChancePerThousand);
        mutations = (int)Math.ceil((population - deaths) / 1000.0f * mutationChancePerThousand);
    }

    /**
     * Execute a single generation of evolution against the Environment.
     */
    public void doOneGeneration() {
        // Reset the environment and fitness of the Organisms
        environment.reset();
        List<T> organisms = environment.getOrganisms();
        for(Organism organism : organisms) {
            organism.resetFitness();
        }

        // execute the environment logic
        environment.doWorkOnOrganisms();

        // After each run, sort the organisms by fitness
        Collections.sort(organisms, new Organism.OrganismComparator());

        // Emit some stats about the generation
        int generationSize = organisms.size();
        int maxFitness = organisms.get(0).getFitness();
        int minFitness = organisms.get(generationSize - 1).getFitness();
        int medianFitness = organisms.get((int)Math.ceil(generationSize / 2)).getFitness();

        logger.info("Greatest fitness is " + maxFitness);
        logger.info("Worst fitness is " + minFitness);
        logger.info("Median fitness is " + medianFitness);
        writer.logGeneration(generationCount, maxFitness, minFitness, medianFitness);

        // Step 1: kill off the bottom of the list based on death rate
        int deaths = (int)Math.ceil(generationSize / 1000.0f * deathRatePerThousand);
        organisms.subList(organisms.size() - deaths - 1, organisms.size() - 1).clear();

        int i;
        // Step 2: breed at random based on the birth rate
        for (i = 0; i < births; i++) {
            // Get two random organisms and breed them
            T parent1 = organisms.get(random.nextInt(organisms.size()));
            T parent2 = organisms.get(random.nextInt(organisms.size()));
            tmpOffspringArray.add((T) parent1.reproduceWith(parent2));
        }

        // Step 3: Complexify based on complexify rate
        // Note that the numerator here is the size of the living list, not the generationsize
        for (i = 0; i < complexifies; i++) {
            // Complexify the genome of a random organism
            organisms.get(random.nextInt(organisms.size())).complexifyGenome();
        }

        // Step 4: Simplify based on simplify rate
        // Note that the numerator here is the size of the living list, not the generationsize
        for (i = 0; i < simplifies; i++) {
            // Complexify the genome of a random organism
            organisms.get(random.nextInt(organisms.size())).simplifyGenome();
        }

        // Step 5: Point mutate based on mutation rate
        // Note that the numerator here is the size of the living list, not the generationsize
        for (i = 0; i < mutations; i++) {
            // Mutate the genome of a random organism
            organisms.get(random.nextInt(organisms.size())).pointMutate();
        }

        // Step 6: Add in the next generation
        organisms.addAll(tmpOffspringArray);
        tmpOffspringArray.clear();

    }

    /**
     * Evolve the Organisms in the Environment a set number of times.
     * @param numGenerations
     */
    public void evolve(int numGenerations) {
        setupEnvironment();
        generationCount = 0;
        while(generationCount < numGenerations) {
            logger.info("Beginning generation " + generationCount);
            doOneGeneration();
            logger.info("Generation " + generationCount + " finished.");
            generationCount++;
        }
    }
}
