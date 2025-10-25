import javafx.scene.Scene;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
                "#9BCCB6",     // Changed to match your original
                "#FF69B4",
                "bunnyKeno.PNG",
                "PinkButton.PNG",
                "Carrot.PNG"
        });

        themes.put("cat", new String[]{
                "#2A638D",     // Changed to match your original
                "#00ACC1",
                "catKeno.PNG",
                "OrangeButton.PNG",
                "Fish.PNG"
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

    public String getBackgroundImage() {return themes.get(currentTheme)[2];}

    public String getButtonImage(){return themes.get(currentTheme)[3];}

    public String getBoardImage(){return themes.get(currentTheme)[4];}

    public ImageView getBackgroundImageView(){
        String imageFile = getBackgroundImage();
        String imagePath ="/images/" + imageFile;

        Image pic = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView v = new ImageView(pic);

        v.setFitHeight(540);
        v.setFitWidth(740);

        return v;
    }

    public void applyToScene(Scene scene) {
        String bgColor = getBackgroundColor();
        String accentColor = getAccentColor();

        scene.getRoot().setStyle(
                "-fx-background-color: " + bgColor + ";"
        );
    }
}