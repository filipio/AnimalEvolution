package world;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.function.UnaryOperator;

public class BottomPanel {

  private enum index{
    GENOTYPE,
    CHILDREN,
    DESCENDANTS,
    DEAD_TIME,
    DOMINANT_GENOTYPE
  }

  private Text [] texts = new Text[5];
  private TextField input;
  private Button chaseHistoryButton;
  private UIData uiData;
  private Genotype dominantGenotype;

  public Genotype getDominantGenotype() {
    return dominantGenotype;
  }


  public BottomPanel(UIData uiData){
    this.uiData = uiData;
  }

  public HBox createPanel(){

    HBox hBox = new HBox();
    hBox.setPadding(new Insets(10));
    hBox.setSpacing(8);
    hBox.setPrefHeight(50);


    for(int i=0; i<texts.length; i++){
      texts[i] = new Text();
      hBox.getChildren().add(texts[i]);
      texts[i].setVisible(false);
    }

    texts[index.DOMINANT_GENOTYPE.ordinal()].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        System.out.println("show animals with dominant genotype");
        uiData.shouldShowDominantAnimals = true;
      }
    });

    int numbersInInput = 9;
    UnaryOperator<TextFormatter.Change> filter = change -> {

      if (change.isContentChange()) {
        int newLength = change.getControlNewText().length();
        if (newLength > numbersInInput) {
          String tail = change.getControlNewText().substring(newLength - numbersInInput, newLength);
          change.setText(tail);
          int oldLength = change.getControlText().length();
          change.setRange(0, oldLength);
        }
      }

      String text = change.getText();
      if (text.matches("[0-9]*")) {
        return change;
      }

      return null;
    };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);




    input = new TextField();




    input.setTextFormatter(textFormatter);
    input.setMaxWidth(90);
    input.setVisible(false);
    hBox.getChildren().add(input);

    chaseHistoryButton = new Button();
    chaseHistoryButton.setText("chase history");

    chaseHistoryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        uiData.daysToChase = Integer.parseInt(input.getText());
        uiData.isChasingHistory = true;
        uiData.resetCounter();
        uiData.isStopped = false;
        hideData();
      }
    });
    hBox.getChildren().add(chaseHistoryButton);
    chaseHistoryButton.setVisible(false);

    return hBox;
  }

  public void hideData(){
    input.setVisible(false);
    chaseHistoryButton.setVisible(false);
    input.setText("");
    for(int i=0; i< texts.length; i++){
      texts[i].setText("");
    }
  }

  public void showAnimalData(String animalGenotype) {
    hideData();
    texts[index.GENOTYPE.ordinal()].setText(animalGenotype);
    texts[index.GENOTYPE.ordinal()].setVisible(true);
    input.setVisible(true);
    chaseHistoryButton.setVisible(true);
  }

  public void showAnimalData(Animal animal){
    hideData();
    int children = animal.children();
    int descendants = animal.descendants();
    Integer deadTime = animal.getDeadTime();

    texts[index.CHILDREN.ordinal()].setText("Your animals has now : " + Integer.toString(children) + " childrens,");
    texts[index.DESCENDANTS.ordinal()].setText(Integer.toString(descendants) + " descendants");
    String deadMessage = deadTime != null ? "and died after " +  Integer.toString(deadTime) + " days" :  " and is still alive!";
    texts[index.DEAD_TIME.ordinal()].setText(deadMessage);
    for(int i=1; i<=3; i++){
      texts[i].setVisible(true);
    }
    texts[index.DOMINANT_GENOTYPE.ordinal()].setText("");
  }

  public void setDominantGenotype(Genotype genotype) {
    if(genotype != null){
      this.dominantGenotype = genotype;
      texts[index.DOMINANT_GENOTYPE.ordinal()].setVisible(true);
      texts[index.DOMINANT_GENOTYPE.ordinal()].setText(genotype.toString());
    }

  }
}




