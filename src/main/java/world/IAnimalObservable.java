package world;

public interface IAnimalObservable {
  void notifyObservators();
  void addObservator(ILivingElementUI observator);
  void removeObservator(ILivingElementUI observator);
}
