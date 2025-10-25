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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.EventObject;
import java.util.ArrayList;
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
    GameBoard gameBoard;
    ComboBox<Integer> spotsComboBox;
    ComboBox<Integer> drawingsComboBox;
    Button randomPickButton;
    Button startDrawingButton;
    Label statusLabel;
    Button confirmSelectionButton;

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

        MenuItem mG3 = new MenuItem("Change Theme");
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
        // pane.setRight(sceneChangeToMenu);

        gameBoard = new GameBoard();
        GridPane grid = gameBoard.createGameBoard(e -> {
            Button btn = (Button) e.getSource();
            gameLogic.handleButtonPress(btn);
            checkIfReadyToStart(); // Check if player has selected enough numbers
        }, themeManager);
        pane.setLeft(grid);

        // Create right side control panel
        VBox rightPanel = createRightPanel();

        VBox rightSide = new VBox(20);
        rightSide.setPadding(new Insets(10));
        rightSide.getChildren().addAll(sceneChangeToMenu, rightPanel);
        pane.setRight(rightSide);
        
        Scene scene = new Scene(pane, 1250, 700);
        themeManager.applyToScene(scene);
        return scene;
    }

    private VBox createRightPanel() {
        VBox rightPanel = new VBox(20);
        rightPanel.setPadding(new Insets(75, 50, 50, 50));
        rightPanel.setAlignment(Pos.TOP_CENTER);

        // Status label to guide the user
        statusLabel = new Label("Select number of spots and drawings");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(200);

        // Spots selection
        Label spotsLabel = new Label("Number of Spots:");
        spotsLabel.setStyle("-fx-font-size: 14px;");
        spotsComboBox = new ComboBox<>();
        spotsComboBox.getItems().addAll(1, 4, 8, 10);
        spotsComboBox.setPromptText("Choose spots...");
        spotsComboBox.setPrefWidth(150);

        // Drawings selection
        Label drawingsLabel = new Label("Number of Drawings:");
        drawingsLabel.setStyle("-fx-font-size: 14px;");
        drawingsComboBox = new ComboBox<>();
        drawingsComboBox.getItems().addAll(1, 2, 3, 4);
        drawingsComboBox.setPromptText("Choose drawings...");
        drawingsComboBox.setPrefWidth(150);

        // Random pick button
        randomPickButton = new Button("Random Pick");
        randomPickButton.setPrefWidth(150);
        randomPickButton.setPrefHeight(40);
        randomPickButton.setStyle("-fx-font-size: 14px;");
        randomPickButton.setDisable(true);

        // Confirm selection button
        confirmSelectionButton = new Button("Confirm Selection");
        confirmSelectionButton.setPrefWidth(150);
        confirmSelectionButton.setPrefHeight(40);
        confirmSelectionButton.setStyle("-fx-font-size: 14px;");
        confirmSelectionButton.setDisable(true);

        // Start drawing button
        startDrawingButton = new Button("Start Drawing");
        startDrawingButton.setPrefWidth(150);
        startDrawingButton.setPrefHeight(40);
        startDrawingButton.setStyle("-fx-font-size: 14px;");
        startDrawingButton.setDisable(true);

        // Add event handlers
        setupControlHandlers();

        rightPanel.getChildren().addAll(
            statusLabel,
            new Separator(),
            spotsLabel,
            spotsComboBox,
            drawingsLabel,
            drawingsComboBox,
            new Separator(),
            randomPickButton,
            confirmSelectionButton,
            startDrawingButton
        );

        return rightPanel;
    }

    private void setupControlHandlers() {
        // When spots are selected
        spotsComboBox.setOnAction(e -> {
            checkIfSettingsComplete();
        });

        // When drawings are selected
        drawingsComboBox.setOnAction(e -> {
            checkIfSettingsComplete();
        });

        // Random pick button
        randomPickButton.setOnAction(e -> {
            // First, clear any existing selections
            gameLogic.getPlayerNumbers().clear();
            
            // Reset all button visuals
            for (Button btn : gameBoard.getGridButtons()) {
                StackPane graphic = (StackPane) btn.getGraphic();
                graphic.setOpacity(1.0);
            }
            
            int spots = spotsComboBox.getValue();
            ArrayList<Integer> randomNumbers = gameLogic.generateRandomPicks(spots);
            
            // Visually select the random numbers on the grid
            for (int number : randomNumbers) {
                for (Button btn : gameBoard.getGridButtons()) {
                    if ((int) btn.getUserData() == number) {
                        StackPane graphic = (StackPane) btn.getGraphic();
                        graphic.setOpacity(0.4);
                        break;
                    }
                }
            }
            
            // Set the numbers in game logic
            gameLogic.setPlayerNumbers(randomNumbers);
            
            // Update status and enable confirm button (same as manual selection)
            statusLabel.setText("Ready! Click Confirm Selection.");
            confirmSelectionButton.setDisable(false);
            // Do NOT enable Start Drawing yet - must confirm first
        });

        // Confirm selection button
        confirmSelectionButton.setOnAction(e -> {
            gameBoard.disableAllButtons();
            statusLabel.setText("Numbers locked! Click Start Drawing.");
            startDrawingButton.setDisable(false);
            confirmSelectionButton.setDisable(true);
        });

        // Start drawing button - we'll implement this later
        startDrawingButton.setOnAction(e -> {
            statusLabel.setText("Drawing in progress...");
            // TODO: Implement drawing logic
        });
    }

    private void checkIfSettingsComplete() {
        if (spotsComboBox.getValue() != null && drawingsComboBox.getValue() != null) {
            int spots = spotsComboBox.getValue();
            int drawings = drawingsComboBox.getValue();
            
            // Set game settings in logic
            gameLogic.setGameSettings(spots, drawings);
            
            // Enable the grid buttons
            gameBoard.enableAllButtons();
            
            // Enable random pick button
            randomPickButton.setDisable(false);
            
            // Update status
            statusLabel.setText("Select " + spots + " numbers on the bet card");
            
            // Disable the comboboxes so they can't be changed
            spotsComboBox.setDisable(true);
            drawingsComboBox.setDisable(true);
        }
    }

    private void checkIfReadyToStart() {
        if (spotsComboBox.getValue() == null) {
            return;
        }
        
        int requiredSpots = spotsComboBox.getValue();
        int selectedSpots = gameLogic.getPlayerNumbers().size();
        
        if (selectedSpots == requiredSpots) {
            statusLabel.setText("Ready! Click Confirm Selection.");
            confirmSelectionButton.setDisable(false);  // Enable confirm button instead
            randomPickButton.setDisable(false);
            // DON'T disable buttons yet - let them change their mind
            // DON'T enable start drawing yet
        } else if (selectedSpots < requiredSpots) {
            statusLabel.setText("Selected " + selectedSpots + "/" + requiredSpots + " numbers");
            confirmSelectionButton.setDisable(true);
            startDrawingButton.setDisable(true);
        } else {
            statusLabel.setText("Too many numbers selected!");
            confirmSelectionButton.setDisable(true);
            startDrawingButton.setDisable(true);
        }
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
}