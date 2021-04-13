public class Individual {
    private Chromosome chromosome;
    private int fitnessScore;

    public Individual(int chromosomeLength) {
        chromosome = new Chromosome(chromosomeLength);
    }

    public Individual(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public void setFitnessScore(int fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    public int getFitnessScore() {
        return fitnessScore;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    @Override
    public String toString() {
        return chromosome.toString() + " fitness: " + fitnessScore;
    }
}
