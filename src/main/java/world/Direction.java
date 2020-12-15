package world;

public enum Direction {
  NORTH(0,-1),
  NORTH_EAST(1,-1),
  EAST(1,0),
  SOUTH_EAST(1,1),
  SOUTH(0,1),
  SOUTH_WEST(-1,1),
  WEST(-1,0),
  NORTH_WEST(-1,-1);


  private Coordinate coordinate;

  private Direction(int x, int y){
    this.coordinate =  new Coordinate(x,y);
  }

  public Coordinate getCoordinate(){
    return coordinate;
  }

}
