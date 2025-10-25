import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType backToMenu = new ButtonType("Back to Menu");

        alert.getButtonTypes().setAll(playAgain, backToMenu);

        Optional<ButtonType> result = alert.showAndWait();
        // Handle button clicks
    }
}