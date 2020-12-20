package world;



import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.IOException;


public class UIMap{

  public static int widthInPixels = 700;
  public static int heightInPixels = 700;

  private GridPane animalPane = new GridPane();
  private GridPane grassPane = new GridPane();
  private BorderPane mainPane = new BorderPane();
  private Pane centerPane = new Pane();
  private Stage stage;
  private DataSave dataSave = new DataSave();

  private int vBoxWidth = 100;
  private UIData uiData;
  private BottomPanel bottomPanel;
  private LeftPanel leftPanel;

  private int animalsCounter = 0;
  private int grassCounter = 0;
  private Animal lastClickedAnimal;

  private ShapeAnimalConnector shapeAnimalConnector = new ShapeAnimalConnector();

  public boolean isRunning(){
    return !uiData.isStopped;
  }

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

  private EventHandler<MouseEvent> dominantGenotypeClicked = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent mouseEvent) {
      if(uiData.isStopped){
        Genotype dominantGenotype = bottomPanel.getDominantGenotype();
        for(Shape shape : shapeAnimalConnector.getDominantShapes(dominantGenotype)){
          shape.setFill(Color.RED);
        }
      }

    }
  };

  private EventHandler<MouseEvent> saveClicked = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent mouseEvent) {
      int enteredInput = leftPanel.getInputValue();
      try{
        dataSave.saveDataToFile(enteredInput,"save.txt");
      }
      catch (IllegalArgumentException | IOException e){
        bottomPanel.errorMessage(e.getMessage());
      }
    }
  };

  public void initializeMap(){
    if(stage != null){
      stage.setTitle("Animal world");
      mainPane.setCenter(centerPane);
      centerPane.getChildren().addAll(animalPane,grassPane);
      centerPane.setStyle("-fx-background-color: " + GameColor.GRASS.color);
      animalPane.toFront();
      uiData = new UIData();
      bottomPanel = new BottomPanel(uiData,dominantGenotypeClicked);
      leftPanel = new LeftPanel(bottomPanel,uiData,saveClicked);
      mainPane.setBottom(bottomPanel.createPanel());
      mainPane.setLeft(leftPanel.createPanel(100,100));
      Scene scene = new Scene(this.mainPane,widthInPixels,heightInPixels,Color.GREEN);
      stage.setScene(scene);
    }
  }

  public Stage getStage(){
    return this.stage;
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


  public ShapeAnimalConnector getAnimalConnector() {
    return this.shapeAnimalConnector;
  }

  public void updateData(AnimalsData animalsData) {
    if(!uiData.isStopped){
      if(uiData.isChasingHistory){
        if(uiData.historyCounter == uiData.daysToChase){
          uiData.isStopped = true;
          uiData.isChasingHistory = false;
          bottomPanel.showAnimalData(lastClickedAnimal);
          return;
        }
        uiData.historyCounter++;
      }
      DataContainer data  = new DataContainer(animalsCounter,grassCounter,
              animalsData.averageEnergy,animalsData.averageLifeLengthForDead,animalsData.averageChildrenCount);
      dataSave.addNewData(data);
      leftPanel.setData(data);
      bottomPanel.setDominantGenotype(animalsData.dominantGenotype);
    }
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }
}