import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    public void show(ThemeManager themeManager, Stage primaryStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(getTitle());
        alert.setHeaderText(null);
        alert.setContentText(getContent());

        // gets icon image for popup
        String iconImagePath = "/images/" + themeManager.getPopUpImage();
        Image iconImage = new Image(getClass().getResource(iconImagePath).toExternalForm());
        ImageView iconImageView = new ImageView(iconImage);

        // sets up icon
        iconImageView.setFitHeight(50);
        iconImageView.setFitWidth(50);
        alert.setGraphic(iconImageView);

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType backToMenu = new ButtonType("Back to Menu");
        ButtonType exit = new ButtonType("Exit Game");

        alert.getButtonTypes().setAll(playAgain, backToMenu, exit);

        Optional<ButtonType> result = alert.showAndWait();
        
        // handle button clicks
        if (result.isPresent()) {
            if (result.get() == playAgain) {
                // reset game and stay on game screen
                app.sceneChangeToGame.fire();
            } else if (result.get() == backToMenu) {
                // use the existing back to menu button
                app.sceneChangeToMenu.fire();
            } else if(result.get() == exit){
                primaryStage.close();
            }
        }
    }
}