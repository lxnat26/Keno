import javafx.scene.Scene;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class ThemeManager { // anything theme related is found here!
    private String currentTheme;
    private HashMap<String, String[]> themes;

    public ThemeManager() {
        currentTheme = "bunny";
        initializeThemes();
    }

    private void initializeThemes() { // defines resources needed for each theme
        themes = new HashMap<>();

        themes.put("bunny", new String[]{
                "#9BCCB6",
                "bunnyKeno.PNG",
                "PinkButton.PNG",
                "Carrot.PNG",
                "CarrotPng.PNG",
                "BunnyConveyor.PNG",
                "CarrotPng.PNG",
                "GamjaFlower-Regular.ttf"
        });

        themes.put("cat", new String[]{
                "#2A638D",
                "catKeno.PNG",
                "OrangeButton.PNG",
                "Fish.PNG",
                "FishPng.PNG",
                "CatConveyor.PNG",
                "FishPng.PNG",
                "GamjaFlower-Regular.ttf"
        });
    }

    public void toggleTheme() {
        if (currentTheme.equals("bunny")) {
            currentTheme = "cat";
        } else {
            currentTheme = "bunny";
        }
    }

    // getters and setters
    public String getCurrentTheme() {
        return currentTheme;
    }

    public String getBackgroundColor() {return themes.get(currentTheme)[0];}

    public String getBackgroundImage() {return themes.get(currentTheme)[1];}

    public String getButtonImage(){return themes.get(currentTheme)[2];}

    public String getBoardImage(){return themes.get(currentTheme)[3];}

    public String getPopUpImage(){return themes.get(currentTheme)[4];}

    public String getAnimationImage(){return themes.get(currentTheme)[5];}

    public String getAnimationObjectImage(){return themes.get(currentTheme)[6];}

    public Font getFont(double size){
        String fontFile = themes.get(currentTheme)[7];
        String fontPath = "/fonts/" + fontFile;

        return Font.loadFont(getClass().getResourceAsStream(fontPath), size);
    }

    public ImageView getBackgroundImageView(){
        String imageFile = getBackgroundImage();
        String imagePath ="/images/" + imageFile;

        Image pic = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView v = new ImageView(pic);

        v.setFitHeight(540);
        v.setFitWidth(740);

        return v;
    }

    public ImageView getConveyorImage(){
        String animationImagePath = "/images/" + getAnimationImage();
        Image animationImage = new Image(getClass().getResource(animationImagePath).toExternalForm());
        ImageView conveyor = new ImageView(animationImage);

        conveyor.setFitHeight(200);
        conveyor.setFitWidth(340);

        return conveyor;
    }

    public ImageView getObjectImageView(){
        String imagePath = "/images/" + getAnimationObjectImage();
        Image i = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView objectImageView = new ImageView(i);

        objectImageView.setFitHeight(70);
        objectImageView.setFitWidth(70);

        return objectImageView;
    }

    public void applyToScene(Scene scene) {
        String bgColor = getBackgroundColor();
        scene.getRoot().setStyle(
                "-fx-background-color: " + bgColor + ";"
        );
    }

}