package world;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameElementsCreatorTest {

  private ShapeAnimalConnector shapeAnimalConnector = new ShapeAnimalConnector();

  private GameElementsCreator gameElementsCreator = new GameElementsCreator(new UIController(), shapeAnimalConnector);
  private int uniqueGenes [] = new int[]{0,1,2,3,4,5,6,7};

  @Test
  void testBornAnAnimal() {
    for(int i=0; i<1000; i++){
      int [] genes = new int[32];
      int [] otherGenes = new int[32];
      Random random = new Random();
      for(int j=0; i<genes.length; i++){
        genes[j] = random.nextInt(8);
        otherGenes[j] = random.nextInt(8);
      }
      Arrays.sort(genes);
      Arrays.sort(otherGenes);
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