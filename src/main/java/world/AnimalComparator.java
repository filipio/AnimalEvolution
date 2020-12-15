package world;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
  @Override
  public int compare(Animal o1, Animal o2) {
    return o2.getEnergy() - o1.getEnergy();
  }
}
