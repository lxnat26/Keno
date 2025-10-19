import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
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


public class JavaFXTemplate extends Application {
    Button sceneChangeToGame, sceneChangeToMenu;
    HashMap<String, Scene> sceneMap;
    MenuBar menuBarWelcome, menuBarGame;
    Menu menuWelcome, menuGame;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Keno");

        //Creating Menu Bars for Welcome & Game Scene
        menuWelcome = new Menu("Menu");
        menuGame = new Menu("Menu");
        menuBarWelcome = new MenuBar();
        menuBarGame = new MenuBar();

        //Menu Items for Welcome Scene
        MenuItem mW1 = new MenuItem("Rules");
        MenuItem mW2 = new MenuItem("Odds");
        MenuItem mW3 = new MenuItem("Exit");

        //Menu Items for Game Scene
        MenuItem mG1 = new MenuItem("Rules");
        MenuItem mG2 = new MenuItem("Odds");
        MenuItem mG3 = new MenuItem("Themes");
        MenuItem mG4 = new MenuItem("Exit");

        //Setting Up Menu Bars for Welcome & Game Scene
        menuWelcome.getItems().addAll(mW1, mW2, mW3);
        menuBarWelcome.getMenus().addAll(menuWelcome);
        menuGame.getItems().addAll(mG1, mG2, mG3, mG4);
        menuBarGame.getMenus().addAll(menuGame);

        sceneChangeToGame = new Button("Play");
        sceneChangeToMenu = new Button("Back To Menu");
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
        pane.setPadding(new Insets(70));

        VBox paneCenter = new VBox(10);

        pane.setLeft(sceneChangeToGame);
        pane.setTop(menuBarWelcome);
        pane.setStyle("-fx-background-color: lightPink;");

        return new Scene(pane, 700,700);
    }

    public Scene createGameScene(){
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));

        VBox paneCenter = new VBox(10);

        pane.setLeft(sceneChangeToMenu);
        pane.setTop(menuBarGame);

        pane.setStyle("-fx-background-color: lightBlue;");
        return new Scene(pane, 700,700);
    }

}
