package world;

import javafx.application.Application;
import javafx.stage.Stage;


public class UIController extends Application{

  private int mapsCount=2;
  private UIMap[] maps = new UIMap[mapsCount];

  @Override
  public void start(Stage primaryStage) {
      for(int i=0; i<maps.length; i++){
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
