import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GameBoard {
    private ArrayList<Button> gridButtons = new ArrayList<>();

    public GridPane createGameBoard(EventHandler<ActionEvent> buttonHandler, ThemeManager themeManager) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(75));
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(2); 
        grid.setVgap(2); 


        String imagePath = "/images/" + themeManager.getBoardImage();
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());

        int num = 1;
        Font numFont = themeManager.getFont(14);

        // add rows and columns of buttons
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 10; c++){
                ImageView v = new ImageView(image);
                v.setFitHeight(60);
                v.setFitWidth(60);

                Label numText = new Label(String.valueOf(num));
                numText.setStyle("-fx-font-family: '" + numFont.getFamily() + "'; -fx-font-size: 20px; -fx-font-weight: bold; -fx-opacity: 1.0;");
                StackPane p = new StackPane(v, numText);

                // centers the number depending on which image button it is
                if(themeManager.getBoardImage().equals("Fish.PNG")){
                    StackPane.setMargin(numText, new Insets(0, 0, 10, 10));
                }
                else{
                    StackPane.setMargin(numText, new Insets(0, 0, 0, 10));
                }

                Button space = new Button();
                space.setGraphic(p);
                space.setPrefSize(60, 60);

                space.setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
                
                space.setDisable(true); // disable initially
                space.setUserData(num); // store the number as user data
                gridButtons.add(space); // add to our list
                space.setOnAction(buttonHandler); // set the event handler

                // makes sure there is no space between the buttons
                space.setPadding(Insets.EMPTY);
                p.setPadding(Insets.EMPTY);

                grid.add(space,c,r);
                num++;
            }
        }
        return grid;
    }

    // enables all buttons on bet card
    public void enableAllButtons() {
        for (Button btn : gridButtons) {
            btn.setDisable(false);
            if (!btn.getStyle().contains("-fx-opacity")) {
                btn.setStyle(btn.getStyle() + " -fx-opacity: 1.0;");
            }
        }
    }

    // disables all buttons on bet card
    public void disableAllButtons() {
        for (Button btn : gridButtons) {
            btn.setDisable(true);
            if (!btn.getStyle().contains("-fx-opacity")) {
                btn.setStyle(btn.getStyle() + " -fx-opacity: 1.0;");
            }
        }
    }

    public ArrayList<Button> getGridButtons() {
        return gridButtons;
    }
}