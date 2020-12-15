package world;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GenotypesControllerTest {

  @Test
  void testGetDominantGenotype(){
    GenotypesController genotypesController = new GenotypesController();
    genotypesController.addGenotype(new Genotype(new int[]{0,1,2,3}));
    genotypesController.addGenotype(new Genotype(new int[]{0,1,2,3}));
    genotypesController.addGenotype(new Genotype(new int[]{0,1,2,3}));
    genotypesController.addGenotype(new Genotype(new int[]{0,1,2,2,3}));
    genotypesController.addGenotype(new Genotype(new int[]{0,1,2,2,3}));
    genotypesController.addGenotype(new Genotype(new int[]{0,1,2,3,3,3}));
    assertEquals(new Genotype(new int[]{0,1,2,3}),genotypesController.getDominantGenotype());
  }

  @Test
  void testNoDominantGenotypeExist(){
    GenotypesController genotypesController = new GenotypesController();
    assertThrows(NoSuchElementException.class, () -> {genotypesController.getDominantGenotype();});
  }
}