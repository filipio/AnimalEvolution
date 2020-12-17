package world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartDataProviderTest {
  @Test
  void testGetStartData(){
    IStartDataProvider startDataProvider = new StartDataProvider();
    StartData expectedData = new StartData(200,200,80,10,20,0.2);
    assertEquals(expectedData,startDataProvider.getStartData());
  }

}