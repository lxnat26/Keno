import javafx.stage.Stage;

// base for odds and rules popups
public interface Popup {
    String getTitle();
    String getContent();
    void show(ThemeManager themeManager, Stage primaryStage);

}