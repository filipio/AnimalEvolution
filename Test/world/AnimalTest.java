package world;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

  Animal animal;
  @Test
  void testMove() {
    int[] genes = new int[32];
    for(int i=0; i<32; i++){
      genes[i] = ThreadLocalRandom.current().nextInt(0, 8);
    }
    Arrays.sort(genes);
    animal = new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.EAST);
    for(int i=0; i<2000; i++) {
      Coordinate oldCoord = animal.getCoordinate();
      animal.move();
      assertNotEquals(oldCoord,animal.getCoordinate());
      assertFalse(animal.getCoordinate().x<0);
      assertFalse(animal.getCoordinate().y<0);
      assertFalse(animal.getCoordinate().y>=Coordinate.MAX_Y);
      assertFalse(animal.getCoordinate().x>=Coordinate.MAX_X);
    }
  }

  @Test
  void testDescendants_ChildAndParentNotHaveChild(){
    int[] genes = new int[32];
    Animal [] animals = new Animal[10];
    for(int i=0; i<animals.length; i++){
      animals[i] = new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.EAST);
    }
    animals[0].addChild(animals[1]);
    animals[0].addChild(animals[2]);
    animals[0].addChild(animals[3]);
    animals[1].addChild(animals[4]);
    animals[1].addChild(animals[5]);
    animals[1].addChild(animals[6]);
    animals[1].addChild(animals[7]);
    animals[2].addChild(animals[6]);
    animals[2].addChild(animals[7]);
    animals[2].addChild(animals[8]);
    animals[3].addChild(animals[8]);
    animals[3].addChild(animals[9]);
    assertEquals(9,animals[0].descendants());
    animals[0].resetDescendants();
    assertEquals(4,animals[1].descendants());
    animals[1].resetDescendants();
    assertEquals(3,animals[2].descendants());
    animals[2].resetDescendants();
    assertEquals(2,animals[3].descendants());
    animals[3].resetDescendants();
  }

  @Test
  void testDescendants_ChildAndParentHaveChild(){
    int[] genes = new int[32];
    Animal [] animals = new Animal[6];
    for(int i=0; i<animals.length; i++){
      animals[i] = new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.EAST);
    }
    animals[0].addChild(animals[1]);
    animals[0].addChild(animals[2]);
    animals[1].addChild(animals[3]);
    animals[1].addChild(animals[4]);
    animals[2].addChild(animals[1]); // here is the condition
    animals[2].addChild(animals[4]); // and here
    animals[2].addChild(animals[5]);

    assertEquals(5,animals[0].descendants());
    animals[0].resetDescendants();
    assertEquals(2,animals[1].descendants());
    animals[1].resetDescendants();
    assertEquals(4,animals[2].descendants());
    animals[2].resetDescendants();
  }

}