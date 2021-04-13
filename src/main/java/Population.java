public class Population {
    Individual[] individuals;

    public Population(int populationSize, int chromosomeLength) {
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
        int averageFitness = 0;
        for (Individual individual : individuals) {
            averageFitness += individual.getFitnessScore();
        }
        averageFitness /= individuals.length;
        System.out.println(averageFitness);
    }
}
