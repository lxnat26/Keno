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
        // bunny theme: [backgroundColor, accentColor, backgroundImage]
        themes.put("bunny", new String[]{"#FFE4E1", "#FF69B4", "bunny.png"});
        themes.put("cat", new String[]{"#E0F7FA", "#00ACC1", "cat.png"});
    }
}
