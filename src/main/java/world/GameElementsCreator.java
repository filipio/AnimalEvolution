package world;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.*;

public class GameElementsCreator implements IBornObservable {

  private int energyFactor = 4;
  private List<IBornsObservator> observators = new LinkedList<IBornsObservator>();
  private UIController uiController;
  private ShapeAnimalConnector shapeAnimalConnector;

  public GameElementsCreator(UIController uiController, ShapeAnimalConnector shapeAnimalConnector){
    this.uiController = uiController;
    this.shapeAnimalConnector = shapeAnimalConnector;
  }

  public GrassUI getGrass(Coordinate coordinate){
    Rectangle grassShape = uiController.grassShape();
    GrassUI grass = new GrassUI(grassShape,uiController);
    grass.setPosition(coordinate);
    return grass;
  }

  public Animal bornAnAnimal(AnimalParents parents, Coordinate childCoordinate){
    int childHealth = getEnergyForChild(parents);
    int [] genes  = createChildGenes(parents);
    Genotype childGenotype = new Genotype(genes);
    Animal child = new Animal(childCoordinate,childHealth,childGenotype, randomDirection()); // direction musi byc randomowe
    addChildUI(child);
    parents.parentOne.addChild(child);
    parents.parentTwo.addChild(child);
    notifyObservers(child);
    return child;
  }

  private void addChildUI(Animal child) {
    Ellipse animalShape = uiController.animalShape();
    shapeAnimalConnector.addElement(child,animalShape);
    Coordinate coordinate = child.getCoordinate();
    AnimalUI animalUI = new AnimalUI(animalShape,uiController);
    animalUI.setPosition(coordinate);
    child.addObservator(animalUI);
  }

  private Direction randomDirection() {
   return Direction.values()[ThreadLocalRandom.current().nextInt(0,Direction.values().length)];
  }

  private int getEnergyForChild(AnimalParents parents) {
    Animal parentOne = parents.parentOne;
    Animal parentTwo = parents.parentTwo;
    int childEnergy = parentOne.getEnergy()/energyFactor + parentTwo.getEnergy()/energyFactor;
    parentOne.modifyEnergy(parentOne.getEnergy()/energyFactor);
    parentTwo.modifyEnergy(parentTwo.getEnergy()/energyFactor);
    return childEnergy;
  }


  private int[] createChildGenes(AnimalParents parents) {
    Animal p1 = parents.parentOne;
    Animal p2 = parents.parentTwo;
    int [] genesOne = p1.getGenotype().getGenes();
    int [] genesTwo = p2.getGenotype().getGenes();

    int [] childGenes = new int[genesOne.length];

    int breakPointOne = ThreadLocalRandom.current().nextInt(1, 31);
    int breakPointTwo = ThreadLocalRandom.current().nextInt(breakPointOne, 31);


    int randInt = ThreadLocalRandom.current().nextInt(0,2);
    if(randInt == 0){
      copyGenesToChild(genesOne, genesTwo, childGenes, breakPointOne, breakPointTwo);
    }
    else{
      copyGenesToChild(genesTwo, genesOne, childGenes, breakPointOne, breakPointTwo);
    }

    int[] uniqueGenes = Arrays.stream(genesOne).distinct().toArray();

    while(uniqueGenes.length != Arrays.stream(childGenes).distinct().toArray().length) {
      int [] lackingGenes = Arrays.stream(uniqueGenes).filter(t -> !Arrays.stream(childGenes).anyMatch(g -> g==t)).toArray();
      for(int i=0; i<lackingGenes.length; i++) {
        childGenes[ThreadLocalRandom.current().nextInt(0, childGenes.length)] = lackingGenes[i];
        }
    }
    Arrays.sort(childGenes);
    return childGenes;
  }

  private void copyGenesToChild(int[] genesOne, int[] genesTwo, int[] childGenes, int breakPointOne, int breakPointTwo) {
    System.arraycopy(genesOne, 0, childGenes, 0, breakPointOne - 1);
    System.arraycopy(genesTwo, breakPointOne, childGenes, breakPointOne, breakPointTwo - breakPointOne + 1);
    System.arraycopy(genesOne, breakPointTwo + 1, childGenes, breakPointTwo + 1, genesOne.length - breakPointTwo - 1);
  }

  @Override
  public void addObserver(IBornsObservator observator) {
    this.observators.add(observator);
  }

  @Override
  public void removeObserver(IBornsObservator observator) {
    this.observators.remove(observator);
  }

  @Override
  public void notifyObservers(Animal newAnimal) {
    for(IBornsObservator observator : observators){
      observator.onAnimalBorn(newAnimal);
    }
  }


}
