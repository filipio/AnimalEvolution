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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


public class UIController extends Application{

  private List<UIMap> maps = new ArrayList<UIMap>();
  private int mapsCount;

  public UIController(int mapsCount){
    this.mapsCount = mapsCount;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
      for(int i=0; i<mapsCount; i++){
        maps.add(new UIMap());
      }
      for(UIMap map : maps){
        map.getStage().show();
      }
    }


}
