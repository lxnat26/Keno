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
    MenuBar mb;
    Menu m;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Keno");

        m = new Menu("Menu");

        MenuItem m1 = new MenuItem("Rules");
        MenuItem m2 = new MenuItem("Odds");
        MenuItem m3 = new MenuItem("Exit");

        m.getItems().addAll(m1, m2, m3);
        mb = new MenuBar();
        mb.getMenus().addAll(m);

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
        pane.setTop(mb);
        pane.setStyle("-fx-background-color: lightPink;");

        return new Scene(pane, 700,700);
    }

    public Scene createGameScene(){
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));

        VBox paneCenter = new VBox(10);

        pane.setLeft(sceneChangeToMenu);
        pane.setTop(mb);

        pane.setStyle("-fx-background-color: lightBlue;");
        return new Scene(pane, 700,700);
    }

}
