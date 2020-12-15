package world;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class GameElementsCreatorTest {

  private ShapeAnimalConnector shapeAnimalConnector = new ShapeAnimalConnector();

  private GameElementsCreator gameElementsCreator = new GameElementsCreator(new UIMap(), shapeAnimalConnector);
  private int uniqueGenes [] = new int[]{0,1,2,3,4,5,6,7};

  //not passing because of lacking gene
  @Test
  void testBornAnAnimal() {
    for(int i=0; i<2; i++){
      int [] genes = new int[32];
      int [] otherGenes = new int[32];
      for(int j=0; j<genes.length; j++) {
        int randomInt = ThreadLocalRandom.current().nextInt(0, 8);
        genes[j] = randomInt;
        otherGenes[j] = randomInt;
      }
      Arrays.sort(genes);
      Arrays.sort(otherGenes);
      System.out.println(Arrays.toString(genes));
      System.out.println(Arrays.toString(otherGenes));
      Animal parentOne = new Animal(new Coordinate(15,20),25,new Genotype(genes), Direction.SOUTH);
      Animal parentTwo = new Animal(new Coordinate(15,20),40,new Genotype(otherGenes), Direction.EAST);

      Animal child = gameElementsCreator.bornAnAnimal(new AnimalParents(parentOne,parentTwo),new Coordinate(12,21));
      assertNotNull(child);
      assertEquals(16,child.getEnergy());

      int [] childUniqueGenes = Arrays.stream(child.getGenotype().getGenes()).distinct().toArray();
      for(int k=0; i<uniqueGenes.length; i++){
        assertEquals(uniqueGenes[k],childUniqueGenes[k]);
      }
    }
  }
}