package world;

import java.util.concurrent.ThreadLocalRandom;

public class Territory {
  private final Coordinate[] coordinates;

  public Territory(Coordinate[] coordinates){
    this.coordinates = coordinates;
  }
  public Coordinate tossACoordinate(){
    return coordinates[ThreadLocalRandom.current().nextInt(0, coordinates.length)];
  }
  public int size(){
    return coordinates.length;
  }
}
