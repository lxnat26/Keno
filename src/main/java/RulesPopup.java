import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// all of the code for the content in the rules popup
public class RulesPopup implements Popup {

    @Override
    public String getTitle() {
        return "Keno Rules";
    }

    @Override
    public String getContent() {
        return "HOW TO PLAY KENO:\n\n" +
                "1. Select number of spots (1, 4, 8, or 10)\n" +
                "2. Pick your numbers from 1-80\n" +
                "   - OR use Random Pick button\n" +
                "3. Choose number of drawings (1-4)\n" +
                "4. Watch 20 numbers get drawn\n" +
                "5. Match your numbers to win!\n\n" +
                "The more numbers you match, the more you win!\n" +
                "Check 'Odds' for payout table.";
    }

    @Override
    public void show(ThemeManager themeManager, Stage primaryStage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(getTitle());
        alert.setHeaderText(null);
        alert.setContentText(getContent());

        String iconImagePath = "/images/" + themeManager.getPopUpImage();
        Image iconImage = new Image(getClass().getResource(iconImagePath).toExternalForm());
        ImageView iconImageView = new ImageView(iconImage);

        iconImageView.setFitHeight(50);
        iconImageView.setFitWidth(50);
        alert.setGraphic(iconImageView);

        alert.showAndWait();
    }
}