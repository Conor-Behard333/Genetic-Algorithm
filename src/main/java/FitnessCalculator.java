public class FitnessCalculator {
    Chromosome solution;
    char[] solutionsGenes;

    public FitnessCalculator(Chromosome solution) {
        this.solution = solution;
        solutionsGenes = solution.getGenes();
    }

    public int evaluateFitness(Chromosome chromosome) {
        int score = 0;
        char[] individualsGenes = chromosome.getGenes();
        for (int i = 0; i < solutionsGenes.length; i++) {
            if (solutionsGenes[i] == individualsGenes[i]) {
                score++;
            }
        }
        return score;
    }
}
