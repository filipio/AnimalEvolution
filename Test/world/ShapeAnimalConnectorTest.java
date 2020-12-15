package world;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeAnimalConnectorTest {

  @Test
  void testRemoveElement(){
    BiMap<Animal,Shape> map = HashBiMap.create();
    Shape someShape = new Circle();
    int genes [] = new int[]{0,1,2};
    Animal animal = new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.EAST);
    map.put(animal,someShape);
    assertTrue(map.containsKey(animal));
    assertTrue(map.containsValue(someShape));
   Animal returnedAnimal =  map.inverse().remove(someShape);
   assertTrue(returnedAnimal.equals(animal));
    assertFalse(map.containsKey(animal));
    assertFalse(map.containsValue(someShape));
  }

  @Test
  void testGetAnimalFromShape(){
    Ellipse someShape = new Ellipse();
    int genes [] = new int[]{0,1,2};
    Animal animal = new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.EAST);
    ShapeAnimalConnector shapeAnimalConnector = new ShapeAnimalConnector();
    shapeAnimalConnector.addElement(animal,someShape);
    someShape.setTranslateX(100);
    someShape.setTranslateY(100);
    Animal retrievedAnimal = shapeAnimalConnector.getAnimal(someShape);
    assertEquals(retrievedAnimal,animal);

    assertNull(shapeAnimalConnector.getAnimal(new Ellipse()));
  }
}