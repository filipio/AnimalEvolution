package world;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class GrassUI implements IElementUI {

  private Rectangle rectangle;
  private UIController uiController;

  public GrassUI(Rectangle rectangle, UIController uiController){
    this.rectangle = rectangle;
    this.uiController = uiController;
  }

  @Override
  public void setPosition(Coordinate newCoordinates) {
    double x = rectangle.getWidth() * newCoordinates.x;
    double y = rectangle.getHeight() * newCoordinates.y;
    this.rectangle.setTranslateX(x);
    this.rectangle.setTranslateY(y);
  }

  @Override
  public void disappear() {
    uiController.removeGrass(this.rectangle);
  }
}
