package world;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShapeAnimalConnector {
  private BiMap<Animal, Ellipse> shapeAnimalMap = HashBiMap.create();

  public Animal getAnimal(Ellipse shape){
    return shapeAnimalMap.inverse().get(shape);
  }
  public void removeElement(Animal animal){
    shapeAnimalMap.remove(animal);
  }

  public void addElement(Animal key, Ellipse value){
    shapeAnimalMap.put(key,value);
  }

  public List<Shape> getDominantShapes(Genotype dominantGenotype){
    List<Shape> dominantShapes = new LinkedList<>();
    for(Animal animal : shapeAnimalMap.keySet()){
      if(animal.getGenotype().equals(dominantGenotype)){
        dominantShapes.add(shapeAnimalMap.get(animal));
      }
    }
    return dominantShapes;
  }

}
