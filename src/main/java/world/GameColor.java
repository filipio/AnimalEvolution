package world;

public enum GameColor {
  ANIMAL("#ffffcc"),
  PANEL("#ffffff"),
  GRASS("#00994d");



  public String color;
  private GameColor(String color){
    this.color = color;
  }
}
