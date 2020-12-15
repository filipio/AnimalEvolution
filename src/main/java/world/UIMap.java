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

  private ShapeAnimalConnector shapeAnimalConnector;

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
    stage.setScene(scene);
  }

  public Stage getStage(){
    return this.stage;
  }


  public void showDominantAnimals(){
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


}
