package world;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class MapElement {

  private LinkedList<Animal> animals = new LinkedList<Animal>();
  private Set<Animal> animalsToBeFed;
  private int feedFactor = 1;
  private AnimalComparator animalComparator = new AnimalComparator();
  private GrassUI grass;

  private boolean animalsWereModified = true;

  public void addAnimal(Animal animal){
    this.animals.add(animal);
    animalsWereModified = true;
  }
  public void removeAnimal(Animal animal){
    this.animals.remove(animal);
    animalsWereModified = true;
  }
  public boolean isOccupied(){ return animals.size() != 0;}
  public boolean isPlanted(){return grass!= null;}

  private void findAnimalsToBeFed(){
    if(animals.size() > 0){
      animals.sort(animalComparator);
      int minEnergyToBeFed = animals.getFirst().getEnergy();
     animalsToBeFed = animals.stream().filter(t -> t.getEnergy() == minEnergyToBeFed).collect(Collectors.toSet());
     feedFactor = animalsToBeFed.size();
    }
  }

  public boolean feedAnimal(Animal animal){
    if(isPlanted()){
      if(animalsWereModified){
        findAnimalsToBeFed();
        animalsWereModified = false;
      }
      if(animalsToBeFed.contains(animal)){
        animal.modifyEnergy(Animal.feedEnergy/feedFactor);
        animalsToBeFed.remove(animal);
        if(animalsToBeFed.size() == 0){
          removePlant();
          return true;
        }
      }
    }
    return false;
  }


  public AnimalParents getParents(){
    if(animals.size() >= 2){
      Animal parentOne = animals.getFirst();
      Animal parentTwo = animals.get(1);
      if(parentOne.canBornAChild() && parentTwo.canBornAChild()){
        return new AnimalParents(parentOne,parentTwo);
      }
    }
    return null;
  }


  public String toString(){
    int i=0;
    String resultString = "";
    for(Animal animal : animals){
      resultString += "Animal " + i + " enegy : " + animal.getEnergy() + "\n";
      i++;
    }
    return resultString;
  }


  public void setPlant(GrassUI grassUI) {
    this.grass = grassUI;
  }
  private void removePlant(){
    grass.disappear();
    grass = null;
  }
}
