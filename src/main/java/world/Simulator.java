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
    IStartDataProvider startDataProvider = new StartDataProvider();
    StartData startData = startDataProvider.getStartData();
    Animal.startEnergy=startData.getStartEnergy();
    Animal.feedEnergy = startData.getPlantEnergy();
    Animal.moveEnergy = -startData.getMoveEnergy();
    Coordinate.MAX_Y = startData.getWidth();
    Coordinate.MAX_X = startData.getHeight();

    int jungleHeight =(int) (Coordinate.MAX_X * startData.getJungleRatio());
    int jungleWidth= (int) (Coordinate.MAX_Y * startData.getJungleRatio());

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    Coordinate [] coordinates = new Coordinate[Coordinate.MAX_Y * Coordinate.MAX_X];
    Coordinate [] jungleCoordinates = new Coordinate[jungleHeight * jungleWidth];
    MapElement [] mapElements = new MapElement[Coordinate.MAX_X * Coordinate.MAX_Y];

    int jungleStartX = Coordinate.MAX_X/2 -1 - (jungleHeight/2); // zero based
    int jungleStartY = Coordinate.MAX_Y/2 -1 - (jungleWidth/2); // zero based
    int jungleEndX = jungleStartX + jungleHeight;
    int jungleEndY = jungleStartY + jungleWidth;
    //
    for(int i=0; i<Coordinate.MAX_X; i++) {
      for (int j = 0; j < Coordinate.MAX_Y; j++) {
        coordinates[i * Coordinate.MAX_Y + j] = new Coordinate(i, j);
        mapElements[i * Coordinate.MAX_Y + j] = new MapElement();
        if ( jungleStartX <= i &&  i < jungleEndX && jungleStartY <= j && j < jungleEndY) {
          jungleCoordinates[(i-jungleStartX) * jungleWidth + j-jungleStartY] = new Coordinate(i, j);
        }
      }
    }
    Coordinate [] desert =  Arrays.stream(coordinates).filter
            (t -> !Arrays.stream(jungleCoordinates)
                    .anyMatch(g -> g.equals(t))).toArray(Coordinate[]::new);

    Territory territoryDesert = new Territory(desert);
    Territory territoryJungle = new Territory(jungleCoordinates);

    //genes
    int animalRows = 5;
    int animalColumns = 5;
    int numberOfAnimals = animalRows * animalColumns;
    int [][] genes = new int[numberOfAnimals][32];
    int [] uniqueGenes = new int[]{0,1,2,3,4,5,6,7};
    for(int i=0; i<numberOfAnimals; i++){
      for(int j=0; j<genes[i].length; j++ ){
        genes[i][j] = ThreadLocalRandom.current().nextInt(0,8);
      }
      final int index = i;
      while(uniqueGenes.length != Arrays.stream(genes[i]).distinct().toArray().length) {
        int [] lackingGenes = Arrays.stream(uniqueGenes).filter(t -> !Arrays.stream(genes[index]).anyMatch(g -> g==t)).toArray();
        for(int k=0; k<lackingGenes.length; k++) {
          genes[i][ThreadLocalRandom.current().nextInt(0, genes[i].length)] = lackingGenes[k];
        }
      }
      Arrays.sort(genes[i]);
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

    Animal [] animals = new Animal[numberOfAnimals];

    for(int i=0; i<animalRows; i++) {
      for(int j=0; j<animalColumns; j++){
        int x = jungleStartX  + (jungleEndX-jungleStartX)/2 - (animalRows/2) + i;
        int y = jungleStartY + (jungleEndY - jungleStartY)/2 - (animalColumns/2) + j;
        animals[i* animalColumns + j] = new Animal(new Coordinate( x,y ),Animal.startEnergy,new Genotype(genes[i * animalColumns + j]),Direction.SOUTH);
        actionsExecutor.onAnimalBorn(animals[i * animalColumns + j]);
        Ellipse animalShape = uiMap.animalShape();
        AnimalUI animalUI = new AnimalUI(animalShape,uiMap);
        animalUI.setPosition(new Coordinate(i,j));
        shapeAnimalConnector.addElement(animals[i*animalColumns + j],animalShape);
        animals[i*animalColumns+j].addObservator(animalUI);
      }
    }

    int plantsCount = 100;
    for(int i=0; i<plantsCount/2; i++) {
      gameMap.plantPlants();
    }


    SimulationTask simulationTask = new SimulationTask(daySimulator,actionsExecutor,uiMap);
    executorService.scheduleAtFixedRate(simulationTask,100,100,TimeUnit.MILLISECONDS);
  }
}
