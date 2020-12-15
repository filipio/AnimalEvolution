package world;


import javafx.scene.shape.Rectangle;

public class GrassUI implements IElementUI {

  private Rectangle rectangle;
  private UIMap uiMap;

  public GrassUI(Rectangle rectangle, UIMap uiMap){
    this.rectangle = rectangle;
    this.uiMap = uiMap;
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
    uiMap.removeGrass(this.rectangle);
  }
}
