package world;

import java.util.Objects;

public class StartData {

  private final int height;
  private final int width;
  private final int startEnergy;
  private final int moveEnergy;
  private final int plantEnergy;
  private final double jungleRatio;

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public int getStartEnergy() {
    return startEnergy;
  }

  public int getMoveEnergy() {
    return moveEnergy;
  }

  public int getPlantEnergy() {
    return plantEnergy;
  }

  public double getJungleRatio() {
    return jungleRatio;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StartData startData = (StartData) o;
    return height == startData.height &&
            width == startData.width &&
            startEnergy == startData.startEnergy &&
            moveEnergy == startData.moveEnergy &&
            plantEnergy == startData.plantEnergy &&
            Double.compare(startData.jungleRatio, jungleRatio) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(height, width, startEnergy, moveEnergy, plantEnergy, jungleRatio);
  }

  public StartData(int height, int width, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio) {
    this.height = height;
    this.width = width;
    this.startEnergy = startEnergy;
    this.moveEnergy = moveEnergy;
    this.plantEnergy = plantEnergy;
    this.jungleRatio = jungleRatio;
  }

}
