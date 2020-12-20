package world;

import java.util.Objects;

public class Coordinate {

  public static int MAX_X = 20;
  public static int MAX_Y = 30;

  public int x;
  public int y;

  public Coordinate(int x, int y){
    this.x = x;
    this.y = y;
  }
  public Coordinate add(Coordinate coordinate){
    return new Coordinate((this.x + coordinate.x + MAX_X) % MAX_X, (this.y + coordinate.y + MAX_Y) % MAX_Y);
  }

  public String toString(){
    return String.format("(%d,%d)",this.x,this.y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinate that = (Coordinate) o;
    return x == that.x &&
            y == that.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
