package world;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class GenotypesController {

  private HashMap<Genotype,Integer> genotypeMap = new HashMap<>();

  public void addGenotype(Genotype genotype){
    Integer value = genotypeMap.remove(genotype);
    if(value != null){
      value++;
    }
    else{
      value = 1;
    }
    genotypeMap.put(genotype,value);
  }
  public Genotype getDominantGenotype() throws NoSuchElementException{
    if(genotypeMap.isEmpty()){
      throw new NoSuchElementException("There aren't any genotypes registred");
    }
      Integer dominantCount = genotypeMap.values().stream().max(Integer::compareTo).get();
      Set<Genotype> keys = genotypeMap.keySet();
      for(Genotype genotype : keys){
        if(genotypeMap.get(genotype) == dominantCount){
          return genotype;
        }
      }
      return null;
    }

    public void removeGenotype(Genotype genotype){
    Integer value = genotypeMap.remove(genotype);
     if(value != null){
        value--;
        if(value != 0){
        genotypeMap.put(genotype,value) ;
        }
      }
    }
}
