package world;

public class DefaultStartData implements IStartDataProvider {

  private int width = 200;
  private int height = 200;
  private int startEnergy = 80;
  private int moveEnergy = 10;
  private int plantEnergy = 20;
  private double jungleRatio = 0.2;

  @Override
  public StartData getStartData() {
    return new StartData(height,width,startEnergy,moveEnergy,plantEnergy,jungleRatio);
  }
}
