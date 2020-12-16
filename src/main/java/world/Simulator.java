package world;

import javafx.application.Platform;
import javafx.scene.shape.Ellipse;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.*;

public class Simulator {

  private class SimulationTask extends TimerTask{

    private DaySimulator daySimulator;
    private ActionsExecutor actionsExecutor;
    private UIMap uiMap;

    public SimulationTask(DaySimulator daySimulator, ActionsExecutor actionsExecutor, UIMap uiMap) {
      this.daySimulator = daySimulator;
      this.actionsExecutor = actionsExecutor;
      this.uiMap = uiMap;
    }
    public void run(){
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
//          System.out.println("running in Simulation task thread");
          if(uiMap.isRunning()){
            daySimulator.simulateADay();
            AnimalsData animalsData = actionsExecutor.animalsData();
            uiMap.updateData(animalsData);
          }
        }
      });
    }
  }

  private UIMap uiMap;


  public Simulator(UIMap uiMap){
    this.uiMap = uiMap;
  }

  public void initialze() {
    System.out.println("running in a initialize method");
    Animal.startEnergy=50;
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    Coordinate.MAX_Y = 30;
    Coordinate.MAX_X = 30;
    Coordinate [] coordinates = new Coordinate[Coordinate.MAX_Y * Coordinate.MAX_X];
    Coordinate [] jungleCoordinates = new Coordinate[100];
    Coordinate [] desert = new Coordinate[800];
    MapElement [] mapElements = new MapElement[Coordinate.MAX_X * Coordinate.MAX_Y];
    for(int i=0; i<Coordinate.MAX_X; i++) {
      for (int j = 0; j < Coordinate.MAX_Y; j++) {
        coordinates[i * Coordinate.MAX_X + j] = new Coordinate(i, j);
        mapElements[i * Coordinate.MAX_X + j] = new MapElement();
        if (i < 10 && j < 10) {
          jungleCoordinates[i * 10 + j] = new Coordinate(i, j);
        } else if(i < 10) {
          desert[i * 20 + j-10] = new Coordinate(i, j);
        }else{
          desert[i * Coordinate.MAX_X +j - 100] = new Coordinate(i,j);
        }
      }
    }
    Territory territoryDesert = new Territory(desert);
    Territory territoryJungle = new Territory(jungleCoordinates);

    //genes
    int [] genes = new int[32];
    for(int i=0; i<genes.length; i++){
      genes[i] = ThreadLocalRandom.current().nextInt(0,8);
    }
    Arrays.sort(genes);
    for(int i=0; i<genes.length; i++){
      System.out.print(genes[i] + " ");
    }



    ShapeAnimalConnector shapeAnimalConnector = uiMap.getAnimalConnector();
    GameElementsCreator gameElementsCreator = new GameElementsCreator(uiMap, shapeAnimalConnector); // to change
    Territory [] territories = new Territory[2];
    territories[0] = territoryDesert;
    territories[1] = territoryJungle;
    GameMap gameMap = new GameMap(coordinates,mapElements,territories,gameElementsCreator);
    ActionsExecutor actionsExecutor = new ActionsExecutor(gameMap, shapeAnimalConnector);
    gameElementsCreator.addObserver(actionsExecutor);
    DaySimulator daySimulator = new DaySimulator(actionsExecutor);

    //animals
    int numberOfAnimals = 25;
    Animal [] animals = new Animal[numberOfAnimals];
    for(int i=0; i<5; i++) {
      for(int j=0; j<5; j++){
        animals[i* 5 + j] = new Animal(new Coordinate( i,j ),Animal.startEnergy,new Genotype(genes),Direction.SOUTH);
        actionsExecutor.onAnimalBorn(animals[i * 5 + j]);
        Ellipse animalShape = uiMap.animalShape();
        AnimalUI animalUI = new AnimalUI(animalShape,uiMap);
        animalUI.setPosition(new Coordinate(i,j));
        shapeAnimalConnector.addElement(animals[i*5 + j],animalShape);
        animals[i*5+j].addObservator(animalUI);
      }
    }

    int plantsCount = 100;
    for(int i=0; i<plantsCount/2; i++) {
      gameMap.plantPlants();
    }

    System.out.println("after initialization of objects");

    SimulationTask simulationTask = new SimulationTask(daySimulator,actionsExecutor,uiMap);
    executorService.scheduleAtFixedRate(simulationTask,100,100,TimeUnit.MILLISECONDS);


  }
}
