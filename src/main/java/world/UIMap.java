package world;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.checkerframework.checker.guieffect.qual.UI;

import java.util.Arrays;
import java.util.concurrent.*;


public class UIMap{

  public static int widthInPixels = 700;
  public static int heightInPixels = 700;

  private GridPane animalPane = new GridPane();
  private GridPane grassPane = new GridPane();
  private BorderPane mainPane = new BorderPane();
  private Pane centerPane = new Pane();
  private Stage stage = new Stage();

  private int vBoxWidth = 100;

  private UIData uiData;
  private BottomPanel bottomPanel;
  private LeftPanel leftPanel;

  // not really sure about this
  private int animalsCounter = 0;
  private int grassCounter = 0;
  private Animal lastClickedAnimal;

  private ShapeAnimalConnector shapeAnimalConnector = new ShapeAnimalConnector();

  private EventHandler<MouseEvent> animalClicked = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent mouseEvent) {
      if(uiData.isStopped){
        Ellipse clickedShape = (Ellipse)mouseEvent.getSource();
        lastClickedAnimal = shapeAnimalConnector.getAnimal(clickedShape);
        bottomPanel.showAnimalData(lastClickedAnimal.toString());
      }

    }
  };

  public UIMap(){
    stage.setTitle("Animal world");
    mainPane.setCenter(centerPane);
    centerPane.getChildren().addAll(animalPane,grassPane);
    centerPane.setStyle("-fx-background-color: " + GameColor.GRASS.color);
    animalPane.toFront();
    uiData = new UIData();
    bottomPanel = new BottomPanel(uiData);
    leftPanel = new LeftPanel(bottomPanel,uiData);
    mainPane.setBottom(bottomPanel.createPanel());
    mainPane.setLeft(leftPanel.createPanel(100,100));
    Scene scene = new Scene(this.mainPane,widthInPixels,heightInPixels,Color.GREEN);
  }

  public void start(Stage primaryStage) throws Exception {



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
    System.out.println();

    //animalUI

    //managers
    GameElementsCreator gameElementsCreator = new GameElementsCreator(this, shapeAnimalConnector);
    Territory [] territories = new Territory[2];
    territories[0] = territoryDesert;
    territories[1] = territoryJungle;
    GameMap gameMap = new GameMap(coordinates,mapElements,territories,gameElementsCreator);
    ActionsExecutor actionsExecutor = new ActionsExecutor(gameMap, shapeAnimalConnector);
    gameElementsCreator.addObserver(actionsExecutor);
    IDaySimulator daySimulator = new DaySimulator(actionsExecutor);


    //animals
    int numberOfAnimals = 25;
    Animal [] animals = new Animal[numberOfAnimals];
    for(int i=0; i<5; i++) {
      for(int j=0; j<5; j++){
        animals[i* 5 + j] = new Animal(new Coordinate( i,j ),Animal.startEnergy,new Genotype(genes),Direction.SOUTH);
        actionsExecutor.onAnimalBorn(animals[i * 5 + j]);
      }
    }


    for(Animal animal : animals){
      Ellipse animalShape = this.animalShape();
      shapeAnimalConnector.addElement(animal,animalShape);
      AnimalUI animalUI  = new AnimalUI(animalShape,this);
      animalUI.setPosition(animal.getCoordinate());
      animal.addObservator(animalUI);
    }
//
    int plantsCount = 100;
    for(int i=0; i<plantsCount/2; i++) {
      gameMap.plantPlants();
    }

    Scene scene = new Scene(this.mainPane,widthInPixels,heightInPixels,Color.GREEN);
    primaryStage.setScene(scene);
    primaryStage.show();

    try{
      executorService.scheduleAtFixedRate(new Runnable() {
        public void run() {
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              if(!uiData.isStopped){
                if(uiData.isChasingHistory){
                  if(uiData.historyCounter == uiData.daysToChase){
                    uiData.isStopped = true;
                    uiData.isChasingHistory = false;
                    bottomPanel.showAnimalData(lastClickedAnimal);
                    return; // display data
                    // get the
                  }
                  uiData.historyCounter++;
                }
                daySimulator.simulateADay();
                leftPanel.setAnimalsCount(animalsCounter);
                leftPanel.setGrassCount(grassCounter);

                AnimalsData animalsData = actionsExecutor.animalsData();
                leftPanel.setAvgEnergy(animalsData.averageEnergy);
                leftPanel.setAvgChildrenCount(animalsData.averageChildrenCount);
                leftPanel.setAvgLengthForDead(animalsData.averageLifeLengthForDead);
                bottomPanel.setDominantGenotype(animalsData.dominantGenotype);
              }
              else if(uiData.shouldShowDominantAnimals){
                showDominantAnimals();
                uiData.shouldShowDominantAnimals = false;
              }
            }
          });

        }
      },0,3, TimeUnit.SECONDS);
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }



  private void showDominantAnimals(){
    Genotype dominantGenotype = bottomPanel.getDominantGenotype();
    for(Shape shape : shapeAnimalConnector.getDominantShapes(dominantGenotype)){
      shape.setFill(Color.RED);
    }
  }


  public Rectangle grassShape(){
    Rectangle rectangle = new Rectangle();
    double width =  (widthInPixels-vBoxWidth)/(double)Coordinate.MAX_X;
    double height = (heightInPixels-50)/(double)Coordinate.MAX_Y;
    rectangle.setWidth(width);
    rectangle.setHeight(height);
    rectangle.setFill(Color.LIMEGREEN);
    grassPane.getChildren().add(rectangle);
    grassCounter++;
    return rectangle;
  }

  public Ellipse animalShape(){
    Ellipse ellipse = new Ellipse();
    ellipse.setRadiusX((widthInPixels-vBoxWidth)/(double)(Coordinate.MAX_X*2));
    ellipse.setRadiusY((heightInPixels-50)/(double)(Coordinate.MAX_Y*2));
    ellipse.setFill(Color.valueOf(GameColor.ANIMAL.color));
    animalPane.getChildren().add(ellipse);
    animalsCounter++;
    ellipse.addEventHandler(MouseEvent.MOUSE_CLICKED,animalClicked);
    return ellipse;
  }

  public void removeGrass(Rectangle grass){
    this.grassPane.getChildren().remove(grass);
    grassCounter--;
  }
  public void removeAnimal(Ellipse animal){
    this.animalPane.getChildren().remove(animal);
    animalsCounter--;
  }

  private boolean wasLaunched = false;
  @Override
  public void run() {
    if(!wasLaunched){
      wasLaunched = true;
      Application.launch();
    }
    else{
      try {
        start(new Stage());
      }
      catch (Exception e){
        System.out.println("error!");
      }
    }
  }

}
