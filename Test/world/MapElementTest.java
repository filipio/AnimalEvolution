package world;

import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class MapElementTest {


  MapElement mapElement = new MapElement();
  private static int [] genes = new int[32];
  private static Animal [] animals;
  private UIMap uiMap = new UIMap();

  @BeforeAll
  public static void setGenes(){
    for(int i=0; i<genes.length; i++){
      genes[i] = ThreadLocalRandom.current().nextInt(0, 8);
    }
    animals = new Animal[]{
            new Animal(new Coordinate(0,0),10,new Genotype(genes),Direction.NORTH),
            new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.NORTH),
            new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.NORTH),
            new Animal(new Coordinate(0,0),39,new Genotype(genes),Direction.NORTH),
            new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.NORTH),
            new Animal(new Coordinate(0,0),20,new Genotype(genes),Direction.NORTH)};
    }


  @Test
  void testFeedAnimals() {

    for(int i=0; i<animals.length; i++){
      mapElement.addAnimal(animals[i]);
    }
    Animal.feedEnergy = 40;
    mapElement.setPlant(new GrassUI(new Rectangle(),uiMap));
    for(int i=0; i<animals.length; i++){
      mapElement.feedAnimal(animals[i]);
    }

    assertEquals(53, animals[1].getEnergy());
    assertEquals(53, animals[2].getEnergy());
    assertEquals(53, animals[4].getEnergy());

    Animal animal = new Animal(new Coordinate(0,0),100,new Genotype(genes),Direction.NORTH);
    mapElement.setPlant(new GrassUI(new Rectangle(),uiMap));
    mapElement.addAnimal(animal);
    for(int i=0; i<animals.length; i++){
      assertFalse(mapElement.feedAnimal(animals[i]));
    }
    assertTrue(mapElement.feedAnimal(animal));
    assertEquals(10,animals[0].getEnergy());
    assertEquals(53,animals[1].getEnergy());
    assertEquals(53,animals[2].getEnergy());
    assertEquals(39,animals[3].getEnergy());
    assertEquals(53,animals[4].getEnergy());
    assertEquals(20,animals[5].getEnergy());
    assertEquals(140,animal.getEnergy());
  }

  @Test
  void testGetParents(){
    for(int i=0; i<animals.length; i++){
      mapElement.removeAnimal(animals[i]); // cleaning
    }
    Animal powerfulParentOne = new Animal(new Coordinate(10,10),1000,new Genotype(genes),Direction.SOUTH);
    Animal powerfulParentTwo = new Animal(new Coordinate(10,10),900,new Genotype(genes),Direction.SOUTH);
    Animal someAnimal = new Animal(new Coordinate(10,10),50,new Genotype(genes),Direction.SOUTH);

    mapElement.addAnimal(powerfulParentOne);
    mapElement.addAnimal(powerfulParentTwo);
    mapElement.addAnimal(someAnimal);

    AnimalParents parents = mapElement.getParents();

    assertNotNull(parents);
    assertEquals(parents.parentOne.getEnergy(),powerfulParentOne.getEnergy());
    assertEquals(parents.parentTwo.getEnergy(),powerfulParentTwo.getEnergy());

    mapElement.removeAnimal(powerfulParentOne);
    mapElement.removeAnimal(powerfulParentTwo);
    //only some animal in list
    assertNull(mapElement.getParents());
    // no animals in list
    mapElement.removeAnimal(someAnimal);
    assertNull(mapElement.getParents());

    //two animals with not enough energy
    someAnimal.modifyEnergy(-40);
    mapElement.addAnimal(someAnimal);
    mapElement.addAnimal(someAnimal);
    assertNull(mapElement.getParents());
  }

}