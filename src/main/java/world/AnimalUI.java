package world;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class AnimalUI implements ILivingElementUI {

  private Ellipse ellipse;
  private UIController uiController;

  public AnimalUI(Ellipse ellipse, UIController uiController){
    this.ellipse = ellipse;
    this.uiController = uiController;
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
    this.uiController.removeAnimal(ellipse);
  }

  @Override
  public void updateData(double energyPercent) {
    this.ellipse.setFill(Color.web(GameColor.ANIMAL.color,energyPercent));
  }
}
