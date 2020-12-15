package world;

import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

  public static Coordinate[] keys = new Coordinate[9];
  public static MapElement [] values = new MapElement[9];
 public static Territory [] territories = new Territory[2];
  public static int[] genes = new int[32];
  private UIController uiController = new UIController();
  private ShapeAnimalConnector  shapeAnimalConnector = new ShapeAnimalConnector();

  GameElementsCreator gameElementsCreator = new GameElementsCreator(new UIController(), shapeAnimalConnector);
  GameMap gameMap;
  @BeforeAll
  public static void createGenes(){
    for(int i=0; i<genes.length; i++){
      genes[i] = ThreadLocalRandom.current().nextInt(0,8);
    }
    Arrays.sort(genes);
  }

  //creating a mini-map
  @BeforeEach
  public void createCoordinatesKeysTerritories(){
    for(int i=0; i<3; i++){
      for(int j=0; j<3; j++){
        keys[i*3+j] = new Coordinate(i,j);
        values[i*3+j] = new MapElement();
      }
    }
    territories[0] = new Territory(new Coordinate[]{new Coordinate(0,0),
            new Coordinate(0,1),
            new Coordinate(0,2),
            new Coordinate(1,0),});
    territories[1] = new Territory(new Coordinate[]{new Coordinate(1,1),
            new Coordinate(1,2),
            new Coordinate(2,0),
            new Coordinate(2,1),
            new Coordinate(2,2)});
    Coordinate.MAX_X = 3;
    Coordinate.MAX_Y = 3;
    gameMap = new GameMap(keys,values,territories,gameElementsCreator);
  }
  @Test
  void testAddAndRemoveAnimal() {
    Animal animal = new Animal(keys[0],40, new Genotype(genes),Direction.SOUTH);
    assertFalse(values[0].isOccupied());
    gameMap.addAnimal(animal);
    assertTrue(values[0].isOccupied());
    gameMap.removeAnimal(animal);
    assertFalse(values[0].isOccupied());
  }


  @Test
  void testPlantPlants() {
    assertEquals(territories.length,gameMap.plantPlants());
    assertEquals(territories.length,gameMap.plantPlants());
    assertEquals(territories.length,gameMap.plantPlants());
    assertEquals(territories.length,gameMap.plantPlants());
    assertEquals(1,gameMap.plantPlants());
    assertEquals(0,gameMap.plantPlants());
  }

  @Test
  void testTryBornAnimals() {
    Animal.startEnergy = 40;
    Animal animalOne = new Animal(keys[0],40, new Genotype(genes),Direction.SOUTH);
    Animal animalTwo = new Animal(keys[0],50, new Genotype(genes),Direction.SOUTH);
    Animal animalThree = new Animal(keys[0],40, new Genotype(genes),Direction.SOUTH);
    Animal animalFour = new Animal(keys[1],60, new Genotype(genes),Direction.SOUTH);
    Animal animalFive = new Animal(keys[1],60, new Genotype(genes),Direction.SOUTH);
    Animal animalSix = new Animal(keys[2],100, new Genotype(genes),Direction.SOUTH);
    Animal animalSeven = new Animal(keys[2],20, new Genotype(genes),Direction.SOUTH);
    Animal animalEight = new Animal(keys[2],70, new Genotype(genes),Direction.SOUTH);

    gameMap.addAnimal(animalOne);
    gameMap.addAnimal(animalTwo);
    gameMap.addAnimal(animalThree);
    gameMap.addAnimal(animalFour);
    gameMap.addAnimal(animalFive);
    gameMap.addAnimal(animalSix);
    gameMap.addAnimal(animalSeven);
    gameMap.addAnimal(animalEight);


    List<Coordinate> coordinateList = new LinkedList<Coordinate>();
    coordinateList.add(keys[0]);
    coordinateList.add(keys[1]);
    coordinateList.add(keys[2]);
    coordinateList.add(keys[0]);
    coordinateList.add(keys[1]);
    assertEquals(2,gameMap.tryBornAnimals(coordinateList));

  }

  @Test
  void tryFeedAnimal() {

    Animal animalOne = new Animal(keys[0],40, new Genotype(genes),Direction.SOUTH);
    Animal animalTwo = new Animal(keys[0],50, new Genotype(genes),Direction.SOUTH);
    Animal animalThree = new Animal(keys[0],40, new Genotype(genes),Direction.SOUTH);
    Animal animalFour = new Animal(keys[1],60, new Genotype(genes),Direction.SOUTH);
    Animal animalFive = new Animal(keys[1],60, new Genotype(genes),Direction.SOUTH);

    gameMap.addAnimal(animalOne);
    gameMap.addAnimal(animalTwo);
    gameMap.addAnimal(animalThree);

    values[0].setPlant(new GrassUI(new Rectangle(), uiController));
    values[1].setPlant(new GrassUI(new Rectangle(),uiController));

    assertEquals(0,gameMap.tryFeedAnimal(animalOne));
    assertEquals(1,gameMap.tryFeedAnimal(animalTwo));
    assertEquals(0,gameMap.tryFeedAnimal(animalThree));

    gameMap.addAnimal(animalFour);
    gameMap.addAnimal(animalFive);

    assertEquals(0,gameMap.tryFeedAnimal(animalFour));
    assertEquals(1,gameMap.tryFeedAnimal(animalFive));
  }
}