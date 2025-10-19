import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
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
        sceneChangeToGame = new Button("₍^. .^₎Ⳋ Play");
        sceneChangeToGame.setPrefWidth(180);
        sceneChangeToGame.setPrefHeight(60);
        sceneChangeToGame.setStyle("-fx-background-radius: 50px; -fx-border-radius: 50px; -fx-font-size: 20px; " +
                "-fx-text-fill: #6750A4; -fx-font-weight: bold;");

        // Back To Menu Button
        sceneChangeToMenu = new Button("Back To Menu");
        sceneChangeToMenu.setPrefWidth(180);
        sceneChangeToMenu.setPrefHeight(60);
        sceneChangeToGame.getStyleClass().add("play-button");
        sceneChangeToMenu.setStyle("-fx-background-radius: 50px;" + "-fx-border-radius: 50px;" + "-fx-font-size: 20px;"
                + "-fx-text-fill: #6750A4;" + "-fx-font-weight: bold;");

        sceneMap = new HashMap<String,Scene>();

        sceneChangeToGame.setOnAction(e -> primaryStage.setScene(sceneMap.get("game")));
        sceneChangeToMenu.setOnAction(e -> primaryStage.setScene(sceneMap.get("menu")));

        sceneMap.put("menu", createMenuScene());
        sceneMap.put("game", createGameScene());

        primaryStage.setScene(sceneMap.get("menu"));
        primaryStage.show();
    }

    public Scene createMenuScene(){
        BorderPane pane = new BorderPane();
        pane.setPrefSize(700, 700);

        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(10));
        centerBox.setStyle("-fx-alignment:center;");

        Label titleKeno = new Label("Keno");
        titleKeno.setStyle("-fx-font-size: 80px; -fx-text-fill: #6750A4; -fx-font-weight:bold;");

        centerBox.getChildren().addAll(titleKeno, sceneChangeToGame);

        pane.setTop(menuBarWelcome);
        pane.setCenter(centerBox);

        Scene scene = new Scene(pane, 700, 700);
        themeManager.applyToScene(scene);
        return scene;
    }

    public Scene createGameScene(){
        BorderPane pane = new BorderPane();
        pane.setPrefSize(700, 700);

        pane.setTop(menuBarGame);
        pane.setCenter(sceneChangeToMenu);

        Scene scene = new Scene(pane, 700, 700);
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

        // Apply theme to BOTH scenes
        themeManager.applyToScene(sceneMap.get("menu"));
        themeManager.applyToScene(sceneMap.get("game"));
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