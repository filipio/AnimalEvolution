package world;

public interface IBornObservable {
  void addObserver(IBornsObservator observator);
  void removeObserver(IBornsObservator observator);
  void notifyObservers(Animal newAnimal);
}
