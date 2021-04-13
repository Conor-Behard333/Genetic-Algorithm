import java.util.Arrays;

public class Population {
    private Individual[] individuals;
    private final int POPULATION_SIZE;

    public Population(int populationSize, int chromosomeLength) {
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
            result.append(individual.toString()).append("\n");
        }
        return result.toString();
    }

    public void evaluateFitness(String solution) {
        FitnessCalculator fc = new FitnessCalculator(new Chromosome(solution));
        for (Individual individual : individuals) {
            int score = fc.evaluateFitness(individual.getChromosome());
            individual.setFitnessScore(score);
        }
    }

    public void selectFittestIndividuals() {
        //Using basic average selection

        //get the average fitness
        double averageFitness = 0;
        for (Individual individual : individuals) {
            averageFitness += individual.getFitnessScore();
        }
        averageFitness = Math.round(averageFitness / POPULATION_SIZE);
        System.out.println("Average fitness: " + averageFitness);

        //sort the arrays from largest to smallest
        Arrays.sort(individuals);

        //remove any individual whose fitness is less than the average
        int numOfIndividualsKilled = 0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (individuals[i].getFitnessScore() < averageFitness) {
                individuals[i] = null;
                numOfIndividualsKilled++;
            }
        }


        System.out.println("Alive: " + (POPULATION_SIZE - numOfIndividualsKilled));
        System.out.println("Dead: " + numOfIndividualsKilled);
        System.out.println("Children needed: " + numOfIndividualsKilled);

    }

    public void reproduce() {
    }
}
