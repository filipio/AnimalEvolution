package world;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class AnimalUI implements ILivingElementUI {

  private Ellipse ellipse;
  private UIMap uiMap;

  public AnimalUI(Ellipse ellipse, UIMap uiMap){
    this.ellipse = ellipse;
    this.uiMap = uiMap;
  }

  @Override
  public void setPosition(Coordinate newCoordinates) {
    double x = ellipse.getRadiusX() * 2 * newCoordinates.x;
    double y = ellipse.getRadiusY() * 2 * newCoordinates.y;
    this.ellipse.setTranslateX(x);
    this.ellipse.setTranslateY(y);
  }

  @Override
  public void disappear() {
    this.uiMap.removeAnimal(ellipse);
  }

  @Override
  public void updateData(double energyPercent) {
    this.ellipse.setFill(Color.web(GameColor.ANIMAL.color,energyPercent));
  }
}