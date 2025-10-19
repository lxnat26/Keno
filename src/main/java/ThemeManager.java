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

        // Each theme: [backgroundColor, accentColor, backgroundImage]
        themes.put("bunny", new String[]{
                "#FFE4E1",  // Light pink background
                "#FF69B4",  // Hot pink accent
                "bunny.png" // Background image (optional)
        });

        themes.put("cat", new String[]{
                "#E0F7FA",  // Light blue background
                "#00ACC1",  // Cyan accent
                "cat.png"   // Background image (optional)
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