import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RulesPopup implements Popup {

    @Override
    public String getTitle() {
        return "Rules";
    }

    @Override
    public String getContent() {
        return "How to play Keno:";
    }

    @Override
    public void show() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(getTitle());
        alert.setHeaderText(null);
        alert.setContentText(getContent());
        alert.showAndWait();
    }
}