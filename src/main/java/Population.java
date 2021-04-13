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
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (individuals[i].getFitnessScore() < averageFitness) {
                individuals[i] = null;
            }
        }
    }

    private double getAverageFitness() {
        //get the average fitness
        double averageFitness = 0;
        for (Individual individual : individuals) {
            averageFitness += individual.getFitnessScore();
        }
        averageFitness = Math.round(averageFitness / POPULATION_SIZE);
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

            Individual[] parents = getParents(rand);

//            onePointCrossover(offspringsGenes, i, rand, parents[0], parents[1]);

//            twoPointCrossover(offspringsGenes, i, rand, parents[0], parents[1]);

            uniformCrossover(offspringsGenes, i, rand, parents[0], parents[1]);

            //TODO: Maybe add it so that parents make two children instead of one

            //60% chance to mutate
            if (rand.nextInt(99) + 1 < 70) {
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

    private void uniformCrossover(char[][] offspringsGenes, int offspringIndex, Random rand, Individual parent1, Individual parent2) {
        for (int i = 0; i < offspringsGenes[offspringIndex].length; i++) {
            int parentIndex = rand.nextInt(2);
            if(parentIndex == 0){
                offspringsGenes[offspringIndex][i] = parent1.getChromosome().getGenes()[i];
            }else{
                offspringsGenes[offspringIndex][i] = parent2.getChromosome().getGenes()[i];
            }
        }
    }

    private Individual[] getParents(Random rand) {
        int livingPopulation = getLivingPopulation();
        Individual parent1 = individuals[rand.nextInt(livingPopulation)];
        Individual parent2;
        do {
            parent2 = individuals[rand.nextInt(livingPopulation)];
        } while (parent1.equals(parent2));

        return new Individual[]{parent1, parent2};
    }

    private void onePointCrossover(char[][] offspringsGenes, int offspringIndex, Random rand, Individual parent1, Individual parent2) {
        int crossoverPoint = rand.nextInt(CHROMOSOME_LENGTH - 1);
        //go from start to crossover Point 1
        for (int i = 0; i < crossoverPoint + 1; i++) {
            offspringsGenes[offspringIndex][i] = parent1.getChromosome().getGenes()[i];
        }
        //go from crossover Point 1 to end
        for (int i = crossoverPoint + 1; i < CHROMOSOME_LENGTH; i++) {
            offspringsGenes[offspringIndex][i] = parent2.getChromosome().getGenes()[i];
        }
    }

    private void twoPointCrossover(char[][] offspringsGenes, int offspringIndex, Random rand, Individual parent1, Individual parent2) {
        int crossoverPoint1;
        int crossoverPoint2;
        do {
            crossoverPoint1 = rand.nextInt(CHROMOSOME_LENGTH / 2);
            crossoverPoint2 = (rand.nextInt(CHROMOSOME_LENGTH / 2) + CHROMOSOME_LENGTH / 2) - 1;
        } while (crossoverPoint2 < crossoverPoint1);

        //go from start to crossover Point 1
        for (int i = 0; i < crossoverPoint1 + 1; i++) {
            offspringsGenes[offspringIndex][i] = parent1.getChromosome().getGenes()[i];
        }

        //go from crossover Point 1 to crossover Point 2
        for (int i = crossoverPoint1 + 1; i <= crossoverPoint2; i++) {
            offspringsGenes[offspringIndex][i] = parent2.getChromosome().getGenes()[i];
        }

        //go from crossover Point 2 to end
        for (int i = crossoverPoint2 + 1; i < CHROMOSOME_LENGTH; i++) {
            offspringsGenes[offspringIndex][i] = parent1.getChromosome().getGenes()[i];
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
