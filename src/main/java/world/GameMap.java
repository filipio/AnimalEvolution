package world;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameMap {

  private HashMap<Coordinate, MapElement> map = new HashMap<Coordinate, MapElement>();
  private Territory[] territories;
  private GameElementsCreator gameElementsCreator;

  public GameMap(Coordinate[] keys, MapElement[] values, Territory[] territories, GameElementsCreator creator) {
    if (keys.length != values.length)
      throw new IllegalArgumentException("wrong sizes - key size : " + keys.length + " values size : " + values.length);
    for (int i = 0; i < keys.length; i++) {
      map.put(keys[i], values[i]);
    }
    this.territories = territories;
    this.gameElementsCreator = creator;
  }

  public void addAnimal(Animal animal) {
    MapElement modifiedMapElement = map.get(animal.getCoordinate());
    modifiedMapElement.addAnimal(animal);
  }

  public void removeAnimal(Animal animal) {
    MapElement modifiedMapElement = map.get(animal.getCoordinate());
    modifiedMapElement.removeAnimal(animal);
  }

  public int plantPlants() {
    int plantedCounter = 0;
    for (int i = 0; i < territories.length; i++) {
      List<Coordinate> checkedCoordinates = new LinkedList<>();
      while (true) {
        Coordinate randomCoordinate = territories[i].tossACoordinate();
        if (checkedCoordinates.contains(randomCoordinate)) {
          continue;
        }
        MapElement elementToBePlanted = map.get(randomCoordinate);
        if (canSetPlant(elementToBePlanted)) {
          elementToBePlanted.setPlant(gameElementsCreator.getGrass(randomCoordinate));
          plantedCounter++;
          break;
        } else {
          checkedCoordinates.add(randomCoordinate);
          if (checkedCoordinates.size() == territories[i].size()) {
            break;
          }
        }
      }
    }
    return plantedCounter;
  }

  private boolean canSetPlant(MapElement elementToBePlanted) {
    return !elementToBePlanted.isOccupied() && !elementToBePlanted.isPlanted();
  }

  public int tryBornAnimals(List<Coordinate> coordinates) {
    HashSet<Coordinate> checkedCoordinates = new HashSet<>();
    int bornedAnimals = 0;
    for(Coordinate coor :  coordinates){
      if(!checkedCoordinates.contains(coor)){
        if(tryBornAnimal(coor)){
          bornedAnimals += 1;
        }
        checkedCoordinates.add(coor);
      }
    }
    return bornedAnimals;
  }

  private boolean tryBornAnimal(Coordinate coordinate){
    MapElement element = map.get(coordinate);
    AnimalParents parents = element.getParents();
    if (parents != null) {
      Coordinate childCoordinate = tossChildCoordinate(coordinate);
      Animal animal = gameElementsCreator.bornAnAnimal(parents, childCoordinate);
      addAnimal(animal);
      return true;
    }
    return false;
  }

  public int tryFeedAnimal(Animal animal){
    return map.get(animal.getCoordinate()).feedAnimal(animal) ? 1 : 0;
  }

  private Coordinate tossChildCoordinate(Coordinate coordinate) {
    Direction[] directions = Direction.values();
    Coordinate childCoordinate;
    List<Direction> possibleDirections = new LinkedList<>();
    for(int i=0; i<directions.length; i++){
      if(!map.get(coordinate.add(directions[i].getCoordinate())).isOccupied()){
        possibleDirections.add(directions[i]);
      }
    }

    if (possibleDirections.size() > 0) {
      int possibleSize = possibleDirections.size();
      childCoordinate = coordinate.add(possibleDirections.get(randomIndex(possibleSize)).getCoordinate());
    } else {
      childCoordinate = coordinate.add(directions[randomIndex(directions.length)].getCoordinate());
    }
    return childCoordinate;
  }

  private int randomIndex(int size) {
    return ThreadLocalRandom.current().nextInt(0, size);
  }

}