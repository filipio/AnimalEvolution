package world;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class StartDataProvider implements IStartDataProvider {

  @Override
  public StartData getStartData() {
    JSONParser jsonParser = new JSONParser();
    try{
      FileReader fileReader = new FileReader("parameters.json");
      Object obj = jsonParser.parse(fileReader);
      JSONObject jsonObject = (JSONObject) obj;

      long width = (long) jsonObject.get("width");
      long height = (long) jsonObject.get("height");
      long startEnergy = (long) jsonObject.get("startEnergy");
      long moveEnergy = (long) jsonObject.get("moveEnergy");
      long plantEnergy = (long) jsonObject.get("plantEnergy");
      double jungleRatio = (double) jsonObject.get("jungleRatio");

      StartData startData = new StartData((int)height,(int)width,(int)startEnergy,(int)moveEnergy,(int)plantEnergy,jungleRatio);
      return startData;
    }
    catch (IOException | ParseException e){
      System.out.println("problems with file parameters.json:\n" + e.getMessage());
      System.out.println("setting up default data");
      DefaultStartData defaultStartData = new DefaultStartData();
      return defaultStartData.getStartData();
    }
  }
}
