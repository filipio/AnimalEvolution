package world;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public class UIController extends Application{

  private int mapsCount=2;
  private UIMap[] maps = new UIMap[mapsCount];

  @Override
  public void start(Stage primaryStage) {
    System.out.println("calling start method!");
    System.out.println(mapsCount);
      for(int i=0; i<maps.length; i++){
        System.out.println("iteration nr " + i);
        Stage stage = new Stage();
        maps[i] = new UIMap();
        maps[i].setStage(stage);
        maps[i].initializeMap();
        Simulator simulator = new Simulator(maps[i]);
        simulator.initialze();
        stage.show();
      }
    }
}
