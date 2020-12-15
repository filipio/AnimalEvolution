package world;

public class UIData {
  public int daysToChase;
  public int historyCounter = 0;
  public boolean isChasingHistory = false;
  public boolean isStopped = false;
  public boolean shouldShowDominantAnimals;

  public void resetCounter(){
    this.historyCounter = 0;
  }
}
