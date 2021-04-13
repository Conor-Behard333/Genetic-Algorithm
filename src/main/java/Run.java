import java.util.Random;

public class Run {
    public static void main(String[] args) {
        /* Phases of a genetic algorithm
         * Phase 1: Initial population
         * Individual contains a chromosome which is a string of genes
         * Population contains a collection of individuals
         * Population is of fixed size

         * Phase 2: Fitness function
         * Determines the fitness of an individual. Gives the individual a score

         * Phase 3: Selection
         * Two individuals are selected based on their fitness scores.
         * The individuals with high fitness have more chance to be selected for reproduction

         * Phase 4: Crossover (reproduction)
         * For each pair of parents to be mated, a crossover point is chosen at random
         * Offsprings are created by exchanging the genes of parents among themselves until the crossover piece is reached

         * Phase 5: Mutation
         * Some of the genes can be subjected to a mutation with a low random probability.
         * Results in some of the genes being flipped
         */

        //Create solution to try and get to
        StringBuilder solution = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            Random r = new Random();
            if (r.nextInt(2) == 0) {
                solution.append("0");
            } else {
                solution.append("1");

            }
        }
        System.out.println("Solution: " + solution);

        for (int iter = 0; iter < 1000; iter++) {
            //Phase 1:
            int populationSize = 300;
            int chromosomeLength = 1000;
            //Possible selections methods: one-point, two-point, uniform (default is uniform)
            Population population = new Population(populationSize, chromosomeLength, "uniform", 60);

            for (int generation = 0; generation < 400; generation++) {
                //Phase 2
                population.evaluateFitness(solution.toString());

                //Phase 3:
                population.selectFittestIndividuals();

                //Phase 4 & 5:
                population.reproduce();

                if (population.getFittestIndividual().getFitnessScore() == chromosomeLength) {
                    System.out.println("Solution Found on:");
                    System.out.println("Iteration: " + (iter + 1));
                    System.out.println("Generation: " + generation);
                    System.out.println(population.getFittestIndividual());
                    break;
                }
            }
            if (population.getFittestIndividual().getFitnessScore() == chromosomeLength) {
                break;
            }
            System.out.println("Best fitness score for iteration " + (iter + 1) + ": " + population.getFittestIndividual().getFitnessScore());
        }
    }

}
