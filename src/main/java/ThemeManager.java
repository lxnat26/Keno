import javafx.scene.Scene;
import java.util.HashMap;

public class ThemeManager {
    private String currentTheme;
    private HashMap<String, String[]> themes;

    public ThemeManager() {
        currentTheme = "bunny";
        initializeThemes();
    }

    private void initializeThemes() {
        themes = new HashMap<>();

        // In ThemeManager.java
        themes.put("bunny", new String[]{
                "lightPink",     // Changed to match your original
                "#FF69B4",
                "bunny.png"
        });

        themes.put("cat", new String[]{
                "lightBlue",     // Changed to match your original
                "#00ACC1",
                "cat.png"
        });

    }

    public void toggleTheme() {
        if (currentTheme.equals("bunny")) {
            currentTheme = "cat";
        } else {
            currentTheme = "bunny";
        }
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public String getBackgroundColor() {
        return themes.get(currentTheme)[0];
    }

    public String getAccentColor() {
        return themes.get(currentTheme)[1];
    }

    public String getBackgroundImage() {
        return themes.get(currentTheme)[2];
    }

    public void applyToScene(Scene scene) {
        String bgColor = getBackgroundColor();
        String accentColor = getAccentColor();

        scene.getRoot().setStyle(
                "-fx-background-color: " + bgColor + ";"
        );
    }
}