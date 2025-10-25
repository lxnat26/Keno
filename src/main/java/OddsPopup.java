import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OddsPopup implements Popup {

    @Override
    public String getTitle() {
        return "Odds & Payouts";
    }

    @Override
    public String getContent() {
        return "NC LOTTERY KENO PAYOUTS:\n\n" +
                "1 SPOT:\n" +
                "  Match 1 = $2\n\n" +
                "4 SPOTS:\n" +
                "  Match 2 = $1\n" +
                "  Match 3 = $5\n" +
                "  Match 4 = $75\n\n" +
                "8 SPOTS:\n" +
                "  Match 4 = $2\n" +
                "  Match 5 = $12\n" +
                "  Match 6 = $50\n" +
                "  Match 7 = $750\n" +
                "  Match 8 = $10,000\n\n" +
                "10 SPOTS:\n" +
                "  Match 0 = $5\n" +
                "  Match 5 = $2\n" +
                "  Match 6 = $15\n" +
                "  Match 7 = $40\n" +
                "  Match 8 = $450\n" +
                "  Match 9 = $4,250\n" +
                "  Match 10 = $100,000";
    }

    @Override
    public void show(ThemeManager themeManager) {
        Alert alert = new Alert(AlertType.INFORMATION);
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

        alert.showAndWait();
    }
}
