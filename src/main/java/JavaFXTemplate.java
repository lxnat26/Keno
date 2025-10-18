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
import javafx.geometry.Insets;
import javafx.util.Duration;
import java.util.HashMap;


public class JavaFXTemplate extends Application {
    Button sceneChange;
    Button changeSceneBack;
    HashMap<String, Scene> sceneMap;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Keno");

        sceneChange = new Button("Change Scene");
        changeSceneBack = new Button("Change Scene Back");
        sceneMap = new HashMap<String,Scene>();

        sceneChange.setOnAction(e -> primaryStage.setScene(sceneMap.get("second")));
        changeSceneBack.setOnAction(e -> primaryStage.setScene(sceneMap.get("first")));

        sceneMap.put("first", createTestSceneOne());
		sceneMap.put("second", createTestSceneTwo());
        
			primaryStage.setScene(sceneMap.get("first"));
			primaryStage.show();
		
				
		
	}

    public Scene createTestSceneOne(){
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));

        VBox paneCenter = new VBox(10);

        pane.setLeft(sceneChange);
        pane.setStyle("-fx-background-color: lightPink;");

        return new Scene(pane, 700,700, Color.BLUE);
    }

    public Scene createTestSceneTwo(){
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));

        VBox paneCenter = new VBox(10);

        pane.setLeft(changeSceneBack);
        pane.setStyle("-fx-background-color: lightBlue;");
        return new Scene(pane, 700,700, Color.VIOLET);
    }

}
