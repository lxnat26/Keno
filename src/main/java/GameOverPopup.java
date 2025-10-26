import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class GameOverPopup implements Popup {
    private int totalWinnings;
    private JavaFXTemplate app;

    public GameOverPopup(int winnings, JavaFXTemplate app) {
        this.totalWinnings = winnings;
        this.app = app;
    }

    @Override
    public String getTitle() {
        return "Game Over";
    }

    @Override
    public String getContent() {
        return "Total Winnings: $" + totalWinnings;
    }

    @Override
    public void show(ThemeManager themeManager) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(getTitle());
        alert.setHeaderText(null);
        alert.setContentText(getContent());

        // Gets Icon Image for PopUp
        String iconImagePath = "/images/" + themeManager.getPopUpImage();
        Image iconImage = new Image(getClass().getResource(iconImagePath).toExternalForm());
        ImageView iconImageView = new ImageView(iconImage);

        // Sets up Icon and Styles Pop Up
        iconImageView.setFitHeight(50);
        iconImageView.setFitWidth(50);
        alert.setGraphic(iconImageView);

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType backToMenu = new ButtonType("Back to Menu");

        alert.getButtonTypes().setAll(playAgain, backToMenu);

        Optional<ButtonType> result = alert.showAndWait();
        
        // Handle button clicks
        if (result.isPresent()) {
            if (result.get() == playAgain) {
                // Reset game and stay on game screen
                app.startNewGame();
            } else if (result.get() == backToMenu) {
                // Use the existing back to menu button - it has all the logic we need!
                app.sceneChangeToMenu.fire();
            }
        }
    }
}