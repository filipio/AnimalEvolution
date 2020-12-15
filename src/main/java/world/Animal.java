package world;

import java.util.Arrays;
import java.util.LinkedList;

public class Animal implements IAnimalObservable {

  public static int feedEnergy=20;
  public static int startEnergy=80;

//needs to be changed later on
  private int moveEnergy = -5;
  private int initialEnergy;
  private Coordinate coordinate;
  private final Genotype genotype;
  private int energy;
  private LinkedList<Animal> children = new LinkedList<>();
  private LinkedList<ILivingElementUI> observators = new LinkedList<>();
  private boolean visited = false;
  private Integer deadTime = null;

  private Direction forwardDirection;

  public Animal(Coordinate coordinate, int energy, Genotype genotype, Direction forwardDirection){
    this.coordinate = coordinate;
    this.energy = energy;
    this.initialEnergy = energy;
    this.genotype = genotype;
    this.forwardDirection = forwardDirection;
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  public int getEnergy() {
    return energy;
  }

  public void modifyEnergy(int amount){
    this.energy += amount;
  }

  public void addChild(Animal animal){
    children.add(animal);
  }

  public Genotype getGenotype() {
    return genotype;
  }

  public boolean canBornAChild(){
    return this.energy > startEnergy/2;
  }

  public int children(){
    return this.children.size();
  }

  private double energyPercent(){
    double percent = energy/(double)initialEnergy;
    if(percent <= 0)
      return 0;
    if(percent >= 1)
      return 1;
    return percent;
  }

  public int descendants(){
    int childrenDescendants = 0;
    int notVisitedChildren = 0;
    for(Animal child : children){
      if(!child.visited) {
        notVisitedChildren++;
        childrenDescendants += child.descendants();
      }
    }
    visited = true;
    return childrenDescendants + notVisitedChildren;
  }

  public void resetDescendants(){
    for(Animal child : children){
      if(child.visited){
        child.resetDescendants();
      }
    }
    visited = false;
  }


  public void move(){
    int directionIndex = (genotype.tossAGene() + forwardDirection.ordinal()) % Direction.values().length;
    Direction newDirection = Direction.values()[directionIndex];
    Coordinate moveCoordinate = newDirection.getCoordinate();
    this.coordinate = coordinate.add(moveCoordinate);
    this.forwardDirection = newDirection;
    this.modifyEnergy(moveEnergy);
    notifyObservators();
  }

  public boolean checkDeath() {
    if(this.energy<=0) {
      onDeath();
      return true;
    }
    return false;
  }

  private void onDeath(){
    for(ILivingElementUI observator : observators){
      observator.disappear();
    }
  }

  @Override
  public void notifyObservators() {
    for(ILivingElementUI observator : observators){
      observator.setPosition(this.coordinate);
      observator.updateData(energyPercent());
    }
  }

  public String toString(){
    return genotype.toString();
  }

  @Override
  public void addObservator(ILivingElementUI observator) {
    observators.add(observator);
  }

  @Override
  public void removeObservator(ILivingElementUI observator) {
    observators.remove(observator);
  }

  public void setDeadTime(int deadTime) {
    this.deadTime = deadTime;
  }

  public Integer getDeadTime() {
    return deadTime;
  }
}
