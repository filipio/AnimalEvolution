package world;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;



public class BottomPanel {

  public void errorMessage(String message) {
    hideData();
    errorText.setText(message);
  }

  private enum index{
    GENOTYPE,
    CHILDREN,
    DESCENDANTS,
    DEAD_TIME,
    DOMINANT_GENOTYPE
  }

  private Text [] texts = new Text[5];
  private TextField input;
  private Text errorText = new Text();
  private Button chaseHistoryButton;
  private UIData uiData;
  private Genotype dominantGenotype;
  private EventHandler<MouseEvent> dominantGenotypeClicked;
  private NumberInput numberInput = new NumberInput();

  public Genotype getDominantGenotype() {
    return dominantGenotype;
  }


  public BottomPanel(UIData uiData, EventHandler<MouseEvent> dominantGenotypeClicked){
    this.uiData = uiData;
    this.dominantGenotypeClicked = dominantGenotypeClicked;
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
    texts[index.DOMINANT_GENOTYPE.ordinal()].addEventHandler(MouseEvent.MOUSE_CLICKED,dominantGenotypeClicked);
    input = numberInput.getInputField(9,false);
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
    hBox.getChildren().add(errorText);
    return hBox;
  }

  public void hideData(){
    input.setVisible(false);
    chaseHistoryButton.setVisible(false);
    input.setText("");
    errorText.setText("");
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
    animal.resetDescendants();
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
    else{
      texts[index.DOMINANT_GENOTYPE.ordinal()].setVisible(true);
      texts[index.DOMINANT_GENOTYPE.ordinal()].setText("");
    }
  }
}




