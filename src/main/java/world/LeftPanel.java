package world;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LeftPanel {

  private enum TextIndex{
    ANIMALS(1),
    GRASS(3),
    AVG_ENERGY(5),
    AVG_CHILDREN(7),
    AVG_LIFE_LENGTH(9);

    public int index;

    private TextIndex(int index){
      this.index = index;
    }
  }

  private Text[] data;
  private BottomPanel bottomPanel;
  private UIData uiData;
  private EventHandler<MouseEvent> saveClicked;
  private TextField input;
  private NumberInput numberInput  = new NumberInput();


  public LeftPanel(BottomPanel bottomPanel, UIData uiData, EventHandler<MouseEvent> saveClicked){
    this.bottomPanel = bottomPanel;
    this.uiData = uiData;
    this.saveClicked = saveClicked;
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
            new Button("Resume"),
            new Button("Save")};

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
        buttons[2].setVisible(true);
        input.setVisible(true);
      }
    });
    buttons[1].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        uiData.isStopped = false;
        buttons[2].setVisible(false);
        input.setVisible(false);
        input.setText("");
        bottomPanel.hideData();
      }
    });

    buttons[2].addEventHandler(MouseEvent.MOUSE_CLICKED,saveClicked);
    buttons[2].setVisible(false);

    for (int i=0; i< buttons.length; i++) {
      VBox.setMargin(buttons[i], new Insets(0, 0, 0, 8));
      vbox.getChildren().add(buttons[i]);
    }
    input = numberInput.getInputField(9,false);
    vbox.getChildren().add(input);

    for (int i=0; i< data.length; i++) {
      data[i].maxWidth(100);
      data[i].prefWidth(100);
      VBox.setMargin(data[i], new Insets(0, 0, 0, 8));
      vbox.getChildren().add(data[i]);
    }
    vbox.setStyle("-fx-background-color: " + GameColor.PANEL.color + ";");
    return vbox;
  }


  public void setData(DataContainer data) {
    int [] dataAsArray = data.asArray();
    int index = 0;
    for(TextIndex textIndex : TextIndex.values()){
      this.data[textIndex.index].setText(Integer.toString(dataAsArray[index]));
      index++;
    }
  }

  public int getInputValue(){
    return Integer.parseInt(input.getText());
  }

}
