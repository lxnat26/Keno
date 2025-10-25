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

public class GameBoard {

    public GridPane createGameBoard(EventHandler<ActionEvent> buttonHandler, ThemeManager themeManager) {
       // Sets up Grid for buttons
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(75));
        grid.setAlignment(Pos.CENTER_LEFT);

        // Gets image for buttons
        String imagePath = "/images/" + themeManager.getBoardImage();
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());

        int num = 1;

        // Nested For Loop to add rows and columns of buttons
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 10; c++){
                ImageView v = new ImageView(image);
                v.setFitHeight(60);
                v.setFitWidth(60);

                Label numText = new Label(String.valueOf(num));

                StackPane p = new StackPane(v, numText);

                // Centers the number depending on which image button it is
                if(themeManager.getBoardImage().equals("Fish.PNG")){
                    StackPane.setMargin(numText, new Insets(0, 0, 10, 10));
                }
                else{
                    StackPane.setMargin(numText, new Insets(0, 0, 0, 10));
                }

                Button space = new Button();
                space.setGraphic(p);
                space.setPrefSize(60, 60);
                space.setStyle("-fx-background-color: white;");

                // Makes sure there is no space between the buttons
                space.setPadding(Insets.EMPTY);
                p.setPadding(Insets.EMPTY);

                grid.add(space,c,r);
                num++;
            }
        }
        return grid;
    }

}
