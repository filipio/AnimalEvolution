package world;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShapeAnimalConnectorTest {

  @Test
  void testRemoveElement(){
    ShapeAnimalConnector shapeAnimalConnector = new ShapeAnimalConnector();
    Ellipse someShape = new Ellipse();
    int genes [] = new int[]{0,1,2};
    Animal animal = new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.EAST);
    shapeAnimalConnector.addElement(animal,someShape);
    assertNotNull(shapeAnimalConnector.getAnimal(someShape));
    assertEquals(animal,shapeAnimalConnector.getAnimal(someShape));
    shapeAnimalConnector.removeElement(animal);
    assertNull(shapeAnimalConnector.getAnimal(someShape));
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

  @Test
  void testGetDominantShapes(){
    int genes [] = new int[]{0,1,2};
    List<Ellipse> shapes = new ArrayList<Ellipse>();
    shapes.add(new Ellipse());
    shapes.add(new Ellipse());
    shapes.add(new Ellipse());
    Animal[] animals = new Animal[3];
    ShapeAnimalConnector shapeAnimalConnector = new ShapeAnimalConnector();
    for(int i=0; i<animals.length; i++){
      animals[i] = new Animal(new Coordinate(0,0),40,new Genotype(genes),Direction.SOUTH);
      shapeAnimalConnector.addElement(animals[i], shapes.get(i));
    }
    assertEquals(shapes,shapeAnimalConnector.getDominantShapes(new Genotype(genes)));
  }
}