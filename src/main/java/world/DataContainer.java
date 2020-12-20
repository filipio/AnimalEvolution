package world;

public class DataContainer {
  private int animalsCount;
  private int grassCount;
  private int avgAnimalsEnergy;
  private int avgLifeLengthForDead;
  private int avgChildrenCount;

  public DataContainer(int animalsCount, int grassCount, int avgAnimalsEnergy, int avgLifeLengthForDead, int avgChildrenCount) {
    this.animalsCount = animalsCount;
    this.grassCount = grassCount;
    this.avgAnimalsEnergy = avgAnimalsEnergy;
    this.avgLifeLengthForDead = avgLifeLengthForDead;
    this.avgChildrenCount = avgChildrenCount;
  }

  public void add(DataContainer dataContainer) {
    this.animalsCount += dataContainer.animalsCount;
    this.grassCount += dataContainer.grassCount;
    this.avgAnimalsEnergy += dataContainer.avgAnimalsEnergy;
    this.avgLifeLengthForDead += dataContainer.avgLifeLengthForDead;
    this.avgChildrenCount += dataContainer.avgChildrenCount;
  }

  public void averageData(int n) {
    this.avgChildrenCount /= n;
    this.avgLifeLengthForDead /= n;
    this.animalsCount /= n;
    this.grassCount /= n;
    this.avgAnimalsEnergy /= n;
  }

  public String toString() {
    return String.format("animals : %d\n" +
            "grass : %d\n" +
            "animals avg energy : %d\n" +
            " life length for dead : %d\n" +
            "avg childrenCount : %d ", animalsCount, grassCount, avgAnimalsEnergy, avgLifeLengthForDead, avgChildrenCount);
  }

  public int[] asArray() {
    return new int[]{animalsCount, grassCount, avgAnimalsEnergy, avgChildrenCount, avgLifeLengthForDead};
    }
}