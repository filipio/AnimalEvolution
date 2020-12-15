package world;


import javafx.application.Application;
import org.checkerframework.checker.guieffect.qual.UI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args){
    (new Thread(new UIController())).start();
  }

}
