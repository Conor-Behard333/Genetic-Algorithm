import java.util.Arrays;

public class Chromosome {
    char[] genes;

    public Chromosome(int chromosomeLength) {
        genes = new char[chromosomeLength];
        for (int i = 0; i < chromosomeLength; i++) {
            genes[i] = String.valueOf(Math.round(Math.random())).charAt(0);
        }
    }

    public Chromosome(String chromosome) {
        genes = new char[chromosome.length()];
        for (int i = 0; i < chromosome.length(); i++) {
            genes[i] = chromosome.charAt(i);
        }
    }

    public Chromosome(char[] genes) {
        this.genes = genes;
    }

    public static int getIndexOfNumber(char[] genes, int numLookingFor) {
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == numLookingFor) {
                return i;
            }
        }
        return -1;
    }

    public char[] getGenes() {
        return genes;
    }

    @Override
    public String toString() {
        return "Chromosome:" + Arrays.toString(genes);
    }
}
