package world;

public class DaySimulator extends AbstractDaySimulator {

  public DaySimulator(ActionsExecutor actionsExecutor){
    this.actionsExecutor = actionsExecutor;
  }

  @Override
  public void simulateADay() {
    actionsExecutor.removeDeadAnimals();
    actionsExecutor.moveAnimals();
    actionsExecutor.feedAnimals();
    actionsExecutor.bornAnimals();
    actionsExecutor.plantPlants();
    actionsExecutor.nextDay();
  }
}
