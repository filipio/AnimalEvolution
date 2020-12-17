package world;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class Main {

  public static void main(String[] args){
    JSONParser jsonParser = new JSONParser();
    try{
      FileReader fileReader = new FileReader("parameters.json");
      Object obj = jsonParser.parse(fileReader);
      JSONObject jsonObject = (JSONObject) obj;
      parseItemObject(jsonObject);
    }
    catch (IOException | ParseException e){
      e.printStackTrace();
    }
  }

  private static void parseItemObject(JSONObject item) {
    Collection<String> values  = item.values();
    for(String value : values){
      System.out.println("value is : " + value);
    }
    String name = (String) item.get("name");
    System.out.println("JSONobject as String : " + item.toString());
    System.out.println("name is " + name);
    Long age = (Long) item.get("age");
    System.out.println("age is : " + age);
    Long height = (Long) item.get("height");
    System.out.println("height is : " + height);
    Double ratio = (Double) item.get("ratio");
    System.out.println("ratio is : " + ratio);
  }

}
