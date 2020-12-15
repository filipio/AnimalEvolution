package world;

import java.util.*;
import java.util.stream.Collectors;

public class ActionsExecutor implements IBornsObservator {

  private LinkedList<Animal> livingAnimals = new LinkedList<Animal>();
  private GameMap gameMap;
  //maybe needs refactoring later on
  private int currDay = 0;
  private int totalLifeLengthForDead = 0;
  private int deadAnimals = 0;
  private ShapeAnimalConnector shapeAnimalConnector;
  private GenotypesController genotypesController = new GenotypesController();

  public ActionsExecutor(GameMap gameMap, ShapeAnimalConnector shapeAnimalConnector) {
    this.gameMap = gameMap;
    this.shapeAnimalConnector = shapeAnimalConnector;
  }

  public void nextDay(){
    this.currDay++;
  }

  public AnimalsData animalsData(){
    Genotype dominantGenotype;
    try{
      dominantGenotype =  genotypesController.getDominantGenotype();
    }
    catch (NoSuchElementException e){
      dominantGenotype = null;
    }
    int totalEnergy = 0;
    int totalChildren = 0;
    int avgEnergy = 0;
    int avgChildren = 0;
    int avgLifeLengthForDead =  deadAnimals > 0 ? totalLifeLengthForDead / deadAnimals : 0;

    if(livingAnimals.size() > 0){
      for(Animal animal : livingAnimals){
        totalEnergy += animal.getEnergy();
        totalChildren += animal.children();
      }
      avgEnergy = totalEnergy/livingAnimals.size();
      avgChildren = totalChildren/livingAnimals.size();
    }
    return new AnimalsData(avgEnergy,avgChildren,avgLifeLengthForDead,dominantGenotype);
  }

  public void plantPlants(){
    gameMap.plantPlants();
  }

  public void bornAnimals() {
    gameMap.tryBornAnimals(livingAnimals.stream().map(animal -> animal.getCoordinate()).collect(Collectors.toList()));
  }

  public void feedAnimals() {
    for (Animal animal : livingAnimals) {
      gameMap.tryFeedAnimal(animal);
    }
  }

  public void moveAnimals() {
    for (Animal animal : livingAnimals) {
      gameMap.removeAnimal(animal);
      animal.move();
      gameMap.addAnimal(animal);
    }
  }

  public void removeDeadAnimals() {
    for(Iterator<Animal> it = livingAnimals.iterator(); it.hasNext();){
      Animal animal = it.next();
      if(animal.checkDeath()){
        animal.setDeadTime(currDay);
        gameMap.removeAnimal(animal);
        shapeAnimalConnector.removeElement(animal);
        genotypesController.removeGenotype(animal.getGenotype());
        it.remove();
        deadAnimals += 1;

        totalLifeLengthForDead += currDay;
      }
    }
  }

  @Override
  public void onAnimalBorn(Animal animal) {
    genotypesController.addGenotype(animal.getGenotype());
    livingAnimals.add(animal);
  }
}
