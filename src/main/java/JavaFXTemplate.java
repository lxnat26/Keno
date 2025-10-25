import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.EventObject;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

public class JavaFXTemplate extends Application {
    Button sceneChangeToGame, sceneChangeToMenu;
    HashMap<String, Scene> sceneMap;
    MenuBar menuBarWelcome, menuBarGame;
    Menu menuWelcome, menuGame;
    Stage primaryStage;
    ThemeManager themeManager;
    GameLogic gameLogic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Keno");

        // Initialize the size of the entire game window
        primaryStage.setResizable(false);
        primaryStage.setWidth(1250);
        primaryStage.setHeight(700);

        // Initialize theme manager and game logic
        themeManager = new ThemeManager();
        gameLogic = new GameLogic();

        //Creating Menu Bars for Welcome & Game Scene
        menuWelcome = new Menu("✪ Menu");
        menuGame = new Menu("✪ Menu");
        menuBarWelcome = new MenuBar();
        menuBarGame = new MenuBar();

        // Setting Style for Welcome & Game Menu
        menuWelcome.setStyle("-fx-font-size: 14px;");
        menuGame.setStyle("-fx-font-size: 14px;");


        //Menu Items for Welcome Scene
        MenuItem mW1 = new MenuItem("Rules");
        mW1.setOnAction(e -> showRules());

        MenuItem mW2 = new MenuItem("Odds");
        mW2.setOnAction(e -> showOdds());

        MenuItem mW3 = new MenuItem("Exit");
        mW3.setOnAction(e -> exitGame());

        //Menu Items for Game Scene
        MenuItem mG1 = new MenuItem("Rules");
        mG1.setOnAction(e -> showRules());

        MenuItem mG2 = new MenuItem("Odds");
        mG2.setOnAction(e -> showOdds());

        MenuItem mG3 = new MenuItem("Themes");
        mG3.setOnAction(e -> toggleTheme());

        MenuItem mG4 = new MenuItem("Exit");
        mG4.setOnAction(e -> exitGame());

        //Setting Up Menu Bars for Welcome & Game Scene
        menuWelcome.getItems().addAll(mW1, mW2, mW3);
        menuBarWelcome.getMenus().addAll(menuWelcome);
        menuGame.getItems().addAll(mG1, mG2, mG3, mG4);
        menuBarGame.getMenus().addAll(menuGame);

        // Play Button
        sceneChangeToGame = new Button();

        // Back To Menu Button
        sceneChangeToMenu = new Button("Back To Menu");
        sceneChangeToMenu.setPrefWidth(180);
        sceneChangeToMenu.setPrefHeight(60);
        sceneChangeToMenu.setStyle("-fx-background-radius: 50px;" + "-fx-border-radius: 50px;" + "-fx-font-size: 20px;"
                + "-fx-text-fill: #6750A4;" + "-fx-font-weight: bold;");

        sceneMap = new HashMap<String,Scene>();

        sceneChangeToGame.setOnAction(e -> {
            Scene gameScene = createGameScene();
            sceneMap.put("game", gameScene);
            primaryStage.setScene(sceneMap.get("game"));
        });
        sceneChangeToMenu.setOnAction(e -> primaryStage.setScene(sceneMap.get("menu")));

        sceneMap.put("menu", createMenuScene());
        sceneMap.put("game", createGameScene());

        primaryStage.setScene(sceneMap.get("menu"));
        primaryStage.show();
    }

    public Scene createMenuScene(){
        //Sets up Title
        ImageView bgImage = themeManager.getBackgroundImageView();

        //Sets up BorderPane
        BorderPane pane = new BorderPane();
        pane.setPrefSize(1250, 700);

        //Adds padding and centerBox
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(10));
        centerBox.setStyle("-fx-alignment:center;");

        String buttonImagePath = "/images/" + themeManager.getButtonImage();
        Image buttonImage = new Image(getClass().getResource(buttonImagePath).toExternalForm());
        ImageView buttonImageView = new ImageView(buttonImage);

        buttonImageView.setFitWidth(180);
        buttonImageView.setFitHeight(60);

        sceneChangeToGame.setGraphic(buttonImageView);
        sceneChangeToGame.setStyle("-fx-background-color: transparent;");
        VBox.setMargin(sceneChangeToGame, new Insets(250, 0, 0, 0));

        centerBox.getChildren().addAll(sceneChangeToGame);

        pane.setTop(menuBarWelcome);
        pane.setCenter(centerBox);

        StackPane root = new StackPane(bgImage, pane);
        Scene scene = new Scene(root, 1250, 700);
        themeManager.applyToScene(scene);
        return scene;
    }

    public Scene createGameScene(){
        BorderPane pane = new BorderPane();
        pane.setPrefSize(1250, 700);

        pane.setTop(menuBarGame);
        pane.setRight(sceneChangeToMenu);

        GameBoard betCard = new GameBoard();
        GridPane grid = betCard.createGameBoard(e -> gameLogic.handleButtonPress((Button) e.getSource()), themeManager);
        pane.setLeft(grid);

        Scene scene = new Scene(pane, 1250, 700);
        themeManager.applyToScene(scene);
        return scene;
    }

    public void showRules() {
        RulesPopup popup = new RulesPopup();
        popup.show();
    }

    public void showOdds() {
        OddsPopup popup = new OddsPopup();
        popup.show();
    }

    public void toggleTheme() {
        themeManager.toggleTheme();

        // Rebuilding menu and game scene with new image
        sceneMap.put("menu", createMenuScene());
        sceneMap.put("game", createGameScene());

        Scene current = primaryStage.getScene();

        if(current == sceneMap.get("menu")){
            primaryStage.setScene(sceneMap.get("menu"));
        }
        else{
            primaryStage.setScene(sceneMap.get("game"));
        }
        // Apply theme to BOTH scenes
        // I WILL UNCOMMENT FOR NOW BUT TALK WITH LENA LATER BOUT THIS
        //themeManager.applyToScene(sceneMap.get("menu"));
        //themeManager.applyToScene(sceneMap.get("game"));

    }

    public void exitGame() {
        primaryStage.close();
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }
}