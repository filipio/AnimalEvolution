package world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenotypeTest {

  @Test
  void testDominantGenotype(){
    int [] genes = new int[]{0,0,0,1,1,1,1,2,2,3,3,4,4,4,4,4,4,4,5,5,6,6,6,6,6,7,7};
    Genotype genotype = new Genotype(genes);
    assertEquals(4,genotype.getDominantGene());

    int [] otherGenes = new int[]{0,0,1,1,2,2,3,3,4,4,5,5};
    Genotype anotherGenotype = new Genotype(otherGenes);
    assertEquals(0,anotherGenotype.getDominantGene());
  }



}