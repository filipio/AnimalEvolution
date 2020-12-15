package world;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Genotype {

  private final int[] genes;
  private final int dominantGene;

  public Genotype(int[] genes){

    this.genes = genes;
    this.dominantGene = findDominantGene();
  }

  public int getDominantGene(){
    return this.dominantGene;
  }

  private int findDominantGene() {
    int [] dominantGenes = new int[Arrays.stream(genes).distinct().toArray().length];
    for(int i=0; i<genes.length; i++){
      dominantGenes[genes[i]]++;
    }
    int dominantGene = 0;
    for(int i=0; i<dominantGenes.length; i++){
      if(dominantGenes[i] > dominantGenes[dominantGene]){
        dominantGene = i;
      }
    }
    return dominantGene;
  }

  public int[] getGenes() {
    return genes;
  }

  public int tossAGene(){
    int randomGene = ThreadLocalRandom.current().nextInt(0, genes.length);
    return genes[randomGene];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Genotype genotype = (Genotype) o;
    return Arrays.equals(genes, genotype.genes);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(genes);
  }

  public String toString(){
    return Arrays.toString(genes);
  }
}
