import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * The type Population.
 */
public class Population {
    private Individual[] individuals;
    private final int POPULATION_SIZE;
    private final int CHROMOSOME_LENGTH;

    /**
     * Instantiates a new Population.
     *
     * @param populationSize   the population size
     * @param chromosomeLength the chromosome length
     */
    public Population(int populationSize, int chromosomeLength) {
        CHROMOSOME_LENGTH = chromosomeLength;
        POPULATION_SIZE = populationSize;
        individuals = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual(chromosomeLength);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Individual individual : individuals) {
            if (individual != null) {
                result.append(individual.getFitnessScore()).append("\n");
            } else {
                result.append("Null\n");
            }
        }
        return result.toString();
    }

    /**
     * Evaluate fitness.
     *
     * @param solution the solution
     */
    public void evaluateFitness(String solution) {
        FitnessCalculator fc = new FitnessCalculator(new Chromosome(solution));
        for (Individual individual : individuals) {
            int score = fc.evaluateFitness(individual.getChromosome());
            individual.setFitnessScore(score);
        }
    }

    private void killTheWeak(double averageFitness) {
        int numOfIndividualsKilled = 0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (individuals[i].getFitnessScore() < averageFitness) {
                individuals[i] = null;
                numOfIndividualsKilled++;
            }
        }

//        System.out.println("Alive: " + (POPULATION_SIZE - numOfIndividualsKilled));
//        System.out.println("Dead: " + numOfIndividualsKilled);
//        System.out.println("Children needed: " + numOfIndividualsKilled);
    }

    private double getAverageFitness() {
        //get the average fitness
        double averageFitness = 0;
        for (Individual individual : individuals) {
            averageFitness += individual.getFitnessScore();
        }
        averageFitness = Math.round(averageFitness / POPULATION_SIZE);
//        System.out.println("Average fitness: " + averageFitness);
        return averageFitness;
    }

    /**
     * Select fittest individuals.
     */
    public void selectFittestIndividuals() {
        //Using basic average selection

        double averageFitness = getAverageFitness();

        //sort the arrays from largest to smallest
        Arrays.sort(individuals);

        //remove any individual whose fitness is less than the average
        killTheWeak(averageFitness);
    }

    /**
     * Reproduce.
     * Uses one-point crossover
     */
    public void reproduce() {
        Random rand = new Random();
        char[][] offspringsGenes = new char[getDeadPopulation()][CHROMOSOME_LENGTH];

        for (int i = 0; i < offspringsGenes.length; i++) {
            int crossoverPoint = rand.nextInt(CHROMOSOME_LENGTH - 1);

            int livingPopulation = getLivingPopulation();
            Individual parent1 = individuals[rand.nextInt(livingPopulation)];
            Individual parent2;
            do {
                parent2 = individuals[rand.nextInt(livingPopulation)];
            } while (parent1.equals(parent2));

            for (int j = 0; j < crossoverPoint + 1; j++) {
                offspringsGenes[i][j] = parent1.getChromosome().getGenes()[j];
            }
            for (int j = crossoverPoint + 1; j < CHROMOSOME_LENGTH; j++) {
                offspringsGenes[i][j] = parent2.getChromosome().getGenes()[j];
            }

            //25% chance to mutate
            if (rand.nextInt(99) + 1 < 60) {
                //Phase 5
                mutate(offspringsGenes[i], rand.nextInt(CHROMOSOME_LENGTH));
            }
        }

        int offspringIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i] == null) {
                individuals[i] = new Individual(new Chromosome(offspringsGenes[offspringIndex]));
                offspringIndex++;
            }
        }
    }

    private void mutate(char[] offspringsGene, int mutationIndex) {
        if (offspringsGene[mutationIndex] == '1') {
            offspringsGene[mutationIndex] = '0';
        } else {
            offspringsGene[mutationIndex] = '1';
        }
    }

    private int getLivingPopulation() {
        int alive = 0;
        for (Individual individual : individuals) {
            if (individual != null) {
                alive++;
            }
        }
        return alive;
    }

    private int getDeadPopulation() {
        int dead = 0;
        for (Individual individual : individuals) {
            if (individual == null) {
                dead++;
            }
        }
        return dead;
    }

    public Individual getFittestIndividual() {
        return individuals[0];
    }
}
