package world;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;

public class NumberInput {

  public TextField getInputField(int numbersInInput,boolean isVisible){
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

    TextField inputField = new TextField();
    inputField.setTextFormatter(textFormatter);
    inputField.setMaxWidth(90);
    inputField.setVisible(isVisible);
    return inputField;
  }

}
