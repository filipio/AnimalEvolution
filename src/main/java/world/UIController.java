package world;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public class UIController extends Application{

  private List<UIMap> maps = new ArrayList<UIMap>();
  private int mapsCount=1;

  public UIController(){
  }

  public void setMapsCount(int mapsCount){
    this.mapsCount = mapsCount;
  }

  @Override
  public void start(Stage primaryStage) {
    System.out.println("in main thread : " + this);
    System.out.println("calling start method!");
    System.out.println(mapsCount);
      for(int i=0; i<mapsCount; i++){
        Stage stage = new Stage();
        UIMap uiMap = new UIMap();
        uiMap.setStage(stage);
        uiMap.initializeMap();
        maps.add(uiMap);
      }
      for(UIMap map : maps){
        map.getStage().show();
      }
    }


    public void launcher(){
    Application.launch();
    }

  public List<UIMap> getMaps() {
    return this.maps;
  }
}
