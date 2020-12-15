package world;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LeftPanel {

  private Text[] data;
  private BottomPanel bottomPanel;
  private UIData uiData;

  public LeftPanel(BottomPanel bottomPanel, UIData uiData){
    this.bottomPanel = bottomPanel;
    this.uiData = uiData;
  }

  public VBox createPanel(int prefWidth, int maxWidth){
    VBox vbox = new VBox();

    vbox.setPadding(new Insets(10));
    vbox.setSpacing(8);
    vbox.setMaxWidth(maxWidth);
    vbox.setPrefWidth(prefWidth);
    Text title = new Text("Data");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vbox.getChildren().add(title);

    Button buttons[] = new Button[] {
            new Button("Pause"),
            new Button("Resume"),};

    data = new Text[]{
            new Text("animals :"),
            new Text(""),
            new Text("grass :"),
            new Text(""),
            new Text("avg energy :"),
            new Text(""),
            new Text("avg\nchildren : "),
            new Text(""),
            new Text("avg length\nfor dead : "),
            new Text("")
    };
    buttons[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        uiData.isStopped = true;
      }
    });
    buttons[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        uiData.isStopped = false;
        bottomPanel.hideData();
      }
    });

    for (int i=0; i< buttons.length; i++) {
      VBox.setMargin(buttons[i], new Insets(0, 0, 0, 8));
      vbox.getChildren().add(buttons[i]);
    }

    for (int i=0; i< data.length; i++) {
      data[i].maxWidth(100);
      data[i].prefWidth(100);
      VBox.setMargin(data[i], new Insets(0, 0, 0, 8));
      vbox.getChildren().add(data[i]);
    }
    vbox.setStyle("-fx-background-color: " + GameColor.PANEL.color + ";");
    return vbox;
  }

  public void setAnimalsCount(int count){
    this.data[1].setText(Integer.toString(count));
  }

  public void setGrassCount(int count){
    this.data[3].setText(Integer.toString(count));
  }

  public void setAvgEnergy(int avgEnergy){
    this.data[5].setText(Integer.toString(avgEnergy));
  }

  public void setAvgChildrenCount(int count){
    this.data[7].setText(Integer.toString(count));
  }

  public void setAvgLengthForDead(int avgLength){
    this.data[9].setText(Integer.toString(avgLength));
  }



}
