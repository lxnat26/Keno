import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
        sceneChangeToMenu.setPrefWidth(100);
        sceneChangeToMenu.setPrefHeight(20);
        sceneChangeToMenu.setStyle("-fx-background-radius: 50px;" + "-fx-border-radius: 50px;" + "-fx-font-size: 12;"
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
        // Sets up Title
        ImageView bgImage = themeManager.getBackgroundImageView();

        // Sets up BorderPane
        BorderPane pane = new BorderPane();
        pane.setPrefSize(1250, 700);

        // Adds padding and centerBox
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(10));
        centerBox.setStyle("-fx-alignment:center;");

        // Sets up Play Button Image
        String buttonImagePath = "/images/" + themeManager.getButtonImage();
        Image buttonImage = new Image(getClass().getResource(buttonImagePath).toExternalForm());
        ImageView buttonImageView = new ImageView(buttonImage);

        // Sizing of Play Button
        buttonImageView.setFitWidth(180);
        buttonImageView.setFitHeight(60);

        // Style of Play Button
        sceneChangeToGame.setGraphic(buttonImageView);
        sceneChangeToGame.setStyle("-fx-background-color: transparent;");

        // Layout of button
        VBox.setMargin(sceneChangeToGame, new Insets(250, 0, 0, 0));
        centerBox.getChildren().addAll(sceneChangeToGame);

        // Sets placement of menu and centerbox to BorderPane
        pane.setTop(menuBarWelcome);
        pane.setCenter(centerBox);

        // Stacks the Welcome(BackgroundImage) to the BorderPane
        StackPane root = new StackPane(bgImage, pane);

        // Creates the Scene
        Scene scene = new Scene(root, 1250, 700);
        themeManager.applyToScene(scene);

        return scene;
    }

    public Scene createGameScene(){
        BorderPane pane = new BorderPane();
        pane.setPrefSize(1250, 700);

        pane.setTop(menuBarGame);

        // Creates the Keno Board
        gameBoard = new GameBoard();
        GridPane grid = gameBoard.createGameBoard(e -> {
            Button btn = (Button) e.getSource();
            gameLogic.handleButtonPress(btn);
            checkIfReadyToStart(); // Check if player has selected enough numbers
        }, themeManager);
        pane.setLeft(grid);

        // Create right side control panel
        VBox rightPanel = createRightPanel();

        // Container for back button and control panel
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
        rightPanel.setPadding(new Insets(50, 175, 0, 50));
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

        // Confirm selection button - does double duty
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
        // When spots combo changes - enable confirm if both selected
        spotsComboBox.setOnAction(e -> {
            checkIfSettingsComplete();
        });

        // When drawings combo changes - enable confirm if both selected
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
            
            // Update status and enable confirm button
            statusLabel.setText("Ready! Click Confirm Selection.");
            confirmSelectionButton.setDisable(false);
        });

        // Confirm selection button - handles BOTH confirmations
        confirmSelectionButton.setOnAction(e -> {
            // FIRST STAGE: If combo boxes are enabled, this confirms settings
            if (!spotsComboBox.isDisabled()) {
                int spots = spotsComboBox.getValue();
                int drawings = drawingsComboBox.getValue();
                
                // Lock the settings in game logic
                gameLogic.setGameSettings(spots, drawings);
                
                // Disable the combo boxes - user can't change these now
                spotsComboBox.setDisable(true);
                drawingsComboBox.setDisable(true);
                
                // Enable the game board for number selection
                gameBoard.enableAllButtons();
                
                // Enable random pick button
                randomPickButton.setDisable(false);
                
                // Disable confirm button until numbers are selected
                confirmSelectionButton.setDisable(true);
                
                // Update status
                statusLabel.setText("Select " + spots + " numbers on the bet card");
            }
            // SECOND STAGE: If combo boxes are disabled, this confirms number selection
            else {
                // Lock the number selections - disable board
                gameBoard.disableAllButtons();
                
                // Disable random pick and confirm selection
                randomPickButton.setDisable(true);
                confirmSelectionButton.setDisable(true);
                
                // Enable start drawing
                statusLabel.setText("Numbers locked! Click Start Drawing.");
                startDrawingButton.setDisable(false);
            }
        });

        // Start drawing button
        startDrawingButton.setOnAction(e -> {
            statusLabel.setText("Drawing in progress...");
            // TODO: Implement drawing logic
        });
    }

    private void checkIfSettingsComplete() {
        if (spotsComboBox.getValue() != null && drawingsComboBox.getValue() != null) {
            // Enable confirm button to lock settings
            confirmSelectionButton.setDisable(false);
            statusLabel.setText("Click 'Confirm Selection' to lock your settings");
            
            // Don't enable board or random pick yet - must confirm first
            gameBoard.disableAllButtons();
            randomPickButton.setDisable(true);
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
            confirmSelectionButton.setDisable(false);
            randomPickButton.setDisable(false);
        } 
        else if (selectedSpots < requiredSpots) {
            statusLabel.setText("Selected " + selectedSpots + "/" + requiredSpots + " numbers");
            confirmSelectionButton.setDisable(true);
            startDrawingButton.setDisable(true);
        } 
        else {
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
        // Save current game state before rebuilding
        Integer savedSpots = spotsComboBox.getValue();
        Integer savedDrawings = drawingsComboBox.getValue();
        boolean spotsWasDisabled = spotsComboBox.isDisabled();
        boolean drawingsWasDisabled = drawingsComboBox.isDisabled();
        boolean buttonsWereEnabled = !gameBoard.getGridButtons().get(0).isDisabled(); // Check if board was enabled
        
        themeManager.toggleTheme();

        // Rebuilding menu and game scene with new image
        sceneMap.put("menu", createMenuScene());
        sceneMap.put("game", createGameScene());

        // Restore the ComboBox values
        if (savedSpots != null) {
            spotsComboBox.setValue(savedSpots);
            spotsComboBox.setDisable(spotsWasDisabled);
        }
        if (savedDrawings != null) {
            drawingsComboBox.setValue(savedDrawings);
            drawingsComboBox.setDisable(drawingsWasDisabled);
        }

        // Re-enable the board if it was enabled before
        if (buttonsWereEnabled) {
            gameBoard.enableAllButtons();
        }

        // Reapply the opacity to selected numbers on the new buttons
        if (!gameLogic.getPlayerNumbers().isEmpty()) {
            for (int selectedNum : gameLogic.getPlayerNumbers()) {
                for (Button btn : gameBoard.getGridButtons()) {
                    if ((int) btn.getUserData() == selectedNum) {
                        StackPane graphic = (StackPane) btn.getGraphic();
                        graphic.setOpacity(0.4); // Reapply the dimmed effect
                        break;
                    }
                }
            }
            
            // Update the status label appropriately
            int requiredSpots = savedSpots != null ? savedSpots : 0;
            int selectedSpots = gameLogic.getPlayerNumbers().size();
            
            if (selectedSpots == requiredSpots && requiredSpots > 0) {
                statusLabel.setText("Ready! Click Confirm Selection.");
                confirmSelectionButton.setDisable(false);
            } else if (selectedSpots < requiredSpots) {
                statusLabel.setText("Selected " + selectedSpots + "/" + requiredSpots + " numbers");
            }
        } else {
            // No selections yet, but settings might be chosen
            if (savedSpots != null && savedDrawings != null) {
                statusLabel.setText("Select " + savedSpots + " numbers on the bet card");
            }
        }
        
        // Re-enable random pick button if settings were complete
        if (savedSpots != null && savedDrawings != null) {
            randomPickButton.setDisable(false);
        }

        Scene current = primaryStage.getScene();

        if(current == sceneMap.get("menu")){
            primaryStage.setScene(sceneMap.get("menu"));
        }
        else{
            primaryStage.setScene(sceneMap.get("game"));
        }
    }

    public void exitGame() {
        primaryStage.close();
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }
}