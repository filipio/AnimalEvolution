package world;

public class AnimalsData {
  public int averageEnergy;
  public int averageChildrenCount;
  public int averageLifeLengthForDead;
  public Genotype dominantGenotype;

  public AnimalsData(int averageEnergy, int averageChildrenCount, int averageLifeLengthForDead, Genotype dominantGenotype) {
    this.averageEnergy = averageEnergy;
    this.averageChildrenCount = averageChildrenCount;
    this.averageLifeLengthForDead = averageLifeLengthForDead;
    this.dominantGenotype = dominantGenotype;
  }
}
