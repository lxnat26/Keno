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
import java.util.ArrayList;

/**
 * GameBoard class responsible for creating and managing the Keno number grid.
 * Creates an 8x10 grid of buttons numbered 1-80 for players to select their numbers.
 * Handles the visual representation of the game board with themed button images.
 */
public class GameBoard {
    // List storing all 80 button references for easy access and manipulation
    private ArrayList<Button> gridButtons = new ArrayList<>();

    /**
     * Creates the main Keno game board as a GridPane with 80 numbered buttons (1-80).
     * Each button displays a themed image with its number overlaid.
     * The grid is arranged in 8 rows and 10 columns.
     * 
     * @param buttonHandler The event handler to be called when any button is clicked
     * @param themeManager The theme manager providing themed images for the buttons
     * @return GridPane containing all 80 numbered buttons in an 8x10 layout
     */
    public GridPane createGameBoard(EventHandler<ActionEvent> buttonHandler, ThemeManager themeManager) {
        // Sets up Grid for buttons
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(75)); // Original padding preserved
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(2); // Add horizontal gap between buttons
        grid.setVgap(2); // Add vertical gap between buttons

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
                
                // CRITICAL: Add inline style to prevent JavaFX from graying out disabled buttons
                space.setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
                
                space.setDisable(true); // Disable initially
                space.setUserData(num); // Store the number as user data
                gridButtons.add(space); // Add to our list
                space.setOnAction(buttonHandler); // Set the event handler

                // Makes sure there is no space between the buttons
                space.setPadding(Insets.EMPTY);
                p.setPadding(Insets.EMPTY);

                grid.add(space,c,r);
                num++;
            }
        }
        return grid;
    }

    /**
     * Enables all buttons on the game board.
     * Called when the player has selected their game settings and is ready to pick numbers.
     */
    public void enableAllButtons() {
        for (Button btn : gridButtons) {
            btn.setDisable(false);
            // Maintain the style to prevent any visual changes
            if (!btn.getStyle().contains("-fx-opacity")) {
                btn.setStyle(btn.getStyle() + " -fx-opacity: 1.0;");
            }
        }
    }

    /**
     * Disables all buttons on the game board WITHOUT changing their appearance.
     * Called when the player has confirmed their selection or when numbers should not be changed.
     */
    public void disableAllButtons() {
        for (Button btn : gridButtons) {
            btn.setDisable(true);
            // CRITICAL: Maintain opacity even when disabled
            if (!btn.getStyle().contains("-fx-opacity")) {
                btn.setStyle(btn.getStyle() + " -fx-opacity: 1.0;");
            }
        }
    }

    /**
     * Resets all buttons to their initial state.
     * Sets background color to white and disables all buttons.
     * Used when starting a new game or resetting the board.
     */
    public void resetAllButtons() {
        for (Button btn : gridButtons) {
            btn.setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
            btn.setDisable(true);
        }
    }

    /**
     * Getter method for accessing all grid buttons.
     * Useful for operations that need to interact with specific buttons by their number.
     * 
     * @return ArrayList containing all 80 button references
     */
    public ArrayList<Button> getGridButtons() {
        return gridButtons;
    }
}