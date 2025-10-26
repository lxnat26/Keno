import javafx.stage.Stage;

public interface Popup {
    String getTitle();
    String getContent();
    void show(ThemeManager themeManager, Stage primaryStage);

}