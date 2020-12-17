package world;

import javax.management.openmbean.InvalidOpenTypeException;
import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class DataSave {

  private LinkedList<DataContainer> historyData = new LinkedList<DataContainer>();
  public void addNewData(DataContainer dataContainer){
    this.historyData.add(dataContainer);
  }

  public void saveDataToFile(int daysCount, String fileName) throws IllegalArgumentException, IOException {
    if(daysCount < 0){
      throw new IllegalArgumentException("passed daysCount : " + daysCount + " cant be negative!");
    }
    int currDay = 0;
    DataContainer data = new DataContainer(0,0,0,0,0);
    Iterator iter = historyData.iterator();
    while(currDay < daysCount){
      if(!iter.hasNext()){
        throw new IllegalArgumentException("Not enough days have passed. " + daysCount + " is too big.");
      }
      currDay++;
      DataContainer nextData = (DataContainer) iter.next();
      data.add(nextData);
    }
    data.averageData(daysCount);
    try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
      writer.write(data.toString());
      writer.close();
    }
    catch (IOException e){
      throw new IOException("problems with file with name : " + fileName + "\n" + e.getMessage());
    }
  }
}
