import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.util.Duration;
import java.util.HashMap;
import java.util.ArrayList;


public class JavaFXTemplate extends Application {

    // buttons to switch between screens
    Button sceneChangeToGame, sceneChangeToMenu;
    
    // store menu and game scenes here
    HashMap<String, Scene> sceneMap;
    
    // menu bars for both scenes
    MenuBar menuBarWelcome, menuBarGame;
    Menu menuWelcome, menuGame;
    
    // main app window
    Stage primaryStage;
    
    // managers for theme, game logic, and board
    ThemeManager themeManager;
    GameLogic gameLogic;
    GameBoard gameBoard;
    
    // right side controls (user selects stuff here)
    ComboBox<Integer> spotsComboBox;
    ComboBox<Integer> drawingsComboBox;
    Button randomPickButton;
    Button startDrawingButton;
    Label statusLabel;
    Button confirmSelectionButton;
    Button nextDrawingButton;
    
    // group of settings that we hide when game starts
    VBox settingsContainer;
    
    // stuff that shows during drawing animation
    VBox drawingInfoPanel;
    Label drawingNumberLabel;
    Label currentDrawnNumberLabel;
    Label matchesLabel;
    Label winningsLabel;
    Label totalWinningsLabel;
    FlowPane drawnNumbersDisplay;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Keno");

        // lock window size
        primaryStage.setResizable(false);

        // init managers
        themeManager = new ThemeManager();
        gameLogic = new GameLogic();

        // menus for both scenes
        menuWelcome = new Menu("✪ Menu");
        menuGame = new Menu("✪ Menu");
        menuBarWelcome = new MenuBar();
        menuBarGame = new MenuBar();

        menuWelcome.setStyle("-fx-font-size: 14px;");
        menuGame.setStyle("-fx-font-size: 14px;");

        // welcome menu options
        MenuItem mW1 = new MenuItem("Rules");
        mW1.setOnAction(e -> showRules());
        MenuItem mW2 = new MenuItem("Odds");
        mW2.setOnAction(e -> showOdds());
        MenuItem mW3 = new MenuItem("Exit");
        mW3.setOnAction(e -> exitGame());

        // game menu options
        MenuItem mG1 = new MenuItem("Rules");
        mG1.setOnAction(e -> showRules());
        MenuItem mG2 = new MenuItem("Odds");
        mG2.setOnAction(e -> showOdds());
        MenuItem mG3 = new MenuItem("Change Theme");
        mG3.setOnAction(e -> toggleTheme());
        MenuItem mG4 = new MenuItem("Exit");
        mG4.setOnAction(e -> exitGame());

        // add items to menu bars
        menuWelcome.getItems().addAll(mW1, mW2, mW3);
        menuBarWelcome.getMenus().addAll(menuWelcome);
        menuGame.getItems().addAll(mG1, mG2, mG3, mG4);
        menuBarGame.getMenus().addAll(menuGame);

        // buttons to switch scenes
        sceneChangeToGame = new Button();
        sceneChangeToMenu = new Button("Back To Menu");
        sceneChangeToMenu.setPrefWidth(120);
        sceneChangeToMenu.setPrefHeight(40);
        sceneChangeToMenu.setStyle("-fx-background-radius: 50px;" +
                "-fx-border-radius: 50px;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #6750A4;" +
                "-fx-font-weight: bold;");

        // map to store all scenes
        sceneMap = new HashMap<>();

        // go to game
        sceneChangeToGame.setOnAction(e -> {
            Scene gameScene = createGameScene();
            sceneMap.put("game", gameScene);
            primaryStage.setScene(sceneMap.get("game"));
        });

        // go back to menu
        sceneChangeToMenu.setOnAction(e -> {
            resetGame();
            sceneMap.put("menu", createMenuScene());
            primaryStage.setScene(sceneMap.get("menu"));
        });

        // build both scenes at startup
        sceneMap.put("menu", createMenuScene());
        sceneMap.put("game", createGameScene());

        // show menu first
        primaryStage.setScene(sceneMap.get("menu"));
        primaryStage.show();
    }

    // creates welcome screen with play button and bg image
    public Scene createMenuScene() {
        // Gets the background image (Keno Logo)
        ImageView bgImage = themeManager.getBackgroundImageView();

        // Creates and sets up BorderPane
        BorderPane pane = new BorderPane();
        pane.setPrefSize(1250, 700);

        // Creates and sets up centerBox
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(10));
        centerBox.setStyle("-fx-alignment:center;");

        // Gets Image for Play Button
        String buttonImagePath = "/images/" + themeManager.getButtonImage();
        Image buttonImage = new Image(getClass().getResource(buttonImagePath).toExternalForm());
        ImageView buttonImageView = new ImageView(buttonImage);

        // Sets Sizing of Play Button Image
        buttonImageView.setFitWidth(180);
        buttonImageView.setFitHeight(60);

        // Sets Image to Button
        sceneChangeToGame.setGraphic(buttonImageView);
        sceneChangeToGame.setStyle("-fx-background-color: transparent;");

        // Sets up layout of play Button
        VBox.setMargin(sceneChangeToGame, new Insets(250, 0, 0, 0));
        centerBox.getChildren().addAll(sceneChangeToGame);

        // Sets Up Menu Bar and centerBox on BorderPane
        pane.setTop(menuBarWelcome);
        pane.setCenter(centerBox);

        // Stacks background Image (Keno Logo) to Borderpane
        StackPane root = new StackPane(bgImage, pane);

        // Creates Scene and applys the theme
        Scene scene = new Scene(root, 1250, 700);
        themeManager.applyToScene(scene);

        return scene;
    }

    // creates main game screen layout
    public Scene createGameScene() {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(1250, 700);
        pane.setTop(menuBarGame);

        // left side: keno number grid
        gameBoard = new GameBoard();
        GridPane grid = gameBoard.createGameBoard(e -> {
            Button btn = (Button) e.getSource();
            gameLogic.handleButtonPress(btn);
            checkIfReadyToStart();
        }, themeManager);
        pane.setLeft(grid);

        // right side: control panel
        VBox rightPanel = createRightPanel();

        VBox rightSide = new VBox(10);
        rightSide.setPadding(new Insets(10));
        rightSide.getChildren().addAll(sceneChangeToMenu, rightPanel);
        pane.setRight(rightSide);

        Scene scene = new Scene(pane, 1250, 700);
        scene.getRoot().setStyle("-fx-opacity: 1.0;");
        themeManager.applyToScene(scene);
        return scene;
    }

    // builds the right-side controls for spots, drawings, etc.
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(20);
        rightPanel.setPadding(new Insets(20, 175, 0, 50));
        rightPanel.setAlignment(Pos.TOP_CENTER);

        statusLabel = new Label("Select number of spots and drawings");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(200);

        Label spotsLabel = new Label("Number of Spots:");
        spotsComboBox = new ComboBox<>();
        spotsComboBox.getItems().addAll(1, 4, 8, 10);
        spotsComboBox.setPromptText("Choose spots...");
        spotsComboBox.setPrefWidth(150);

        Label drawingsLabel = new Label("Number of Drawings:");
        drawingsComboBox = new ComboBox<>();
        drawingsComboBox.getItems().addAll(1, 2, 3, 4);
        drawingsComboBox.setPromptText("Choose drawings...");
        drawingsComboBox.setPrefWidth(150);

        randomPickButton = new Button("Random Pick");
        randomPickButton.setPrefWidth(150);
        randomPickButton.setPrefHeight(40);
        randomPickButton.setDisable(true);

        confirmSelectionButton = new Button("Confirm Selection");
        confirmSelectionButton.setPrefWidth(150);
        confirmSelectionButton.setPrefHeight(40);
        confirmSelectionButton.setDisable(true);

        startDrawingButton = new Button("Start Drawing");
        startDrawingButton.setPrefWidth(150);
        startDrawingButton.setPrefHeight(40);
        startDrawingButton.setDisable(true);

        settingsContainer = new VBox(10);
        settingsContainer.setAlignment(Pos.CENTER);
        settingsContainer.getChildren().addAll(
            spotsLabel, spotsComboBox,
            drawingsLabel, drawingsComboBox,
            new Separator(),
            randomPickButton,
            confirmSelectionButton,
            startDrawingButton
        );

        // info shown when drawing starts
        drawingInfoPanel = new VBox(10);
        drawingInfoPanel.setAlignment(Pos.CENTER);
        drawingInfoPanel.setPadding(new Insets(10));
        drawingInfoPanel.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 10;");
        drawingInfoPanel.setVisible(false);
        drawingInfoPanel.setMaxWidth(250);

        drawingNumberLabel = new Label();
        currentDrawnNumberLabel = new Label();
        currentDrawnNumberLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #FF4444;");

        Label drawnNumbersHeader = new Label("Drawn Numbers:");
        drawnNumbersDisplay = new FlowPane();
        drawnNumbersDisplay.setHgap(4);
        drawnNumbersDisplay.setVgap(4);
        drawnNumbersDisplay.setAlignment(Pos.CENTER);
        drawnNumbersDisplay.setPrefWrapLength(240);

        matchesLabel = new Label();
        winningsLabel = new Label();
        winningsLabel.setTextFill(Color.GREEN);
        totalWinningsLabel = new Label();
        totalWinningsLabel.setTextFill(Color.web("#0066CC"));

        nextDrawingButton = new Button("Next Drawing");
        nextDrawingButton.setPrefWidth(150);
        nextDrawingButton.setPrefHeight(40);
        nextDrawingButton.setVisible(false);
        nextDrawingButton.setManaged(false);

        drawingInfoPanel.getChildren().addAll(
            drawingNumberLabel,
            new Separator(),
            currentDrawnNumberLabel,
            drawnNumbersHeader,
            drawnNumbersDisplay,
            new Separator(),
            matchesLabel,
            winningsLabel,
            totalWinningsLabel,
            nextDrawingButton
        );

        setupControlHandlers();

        rightPanel.getChildren().addAll(
            statusLabel,
            new Separator(),
            settingsContainer,
            drawingInfoPanel
        );

        return rightPanel;
    }

    // connects all the button actions
    private void setupControlHandlers() {
        spotsComboBox.setOnAction(e -> checkIfSettingsComplete());
        drawingsComboBox.setOnAction(e -> checkIfSettingsComplete());

        // random pick just fills random numbers for player
        randomPickButton.setOnAction(e -> {
            gameLogic.getPlayerNumbers().clear();
            for (Button btn : gameBoard.getGridButtons()) {
                StackPane graphic = (StackPane) btn.getGraphic();
                graphic.getChildren().removeIf(node -> node instanceof Rectangle);
                graphic.setOpacity(1.0);
                btn.setStyle("-fx-background-color: white;");
            }

            int spots = spotsComboBox.getValue();
            ArrayList<Integer> randomNumbers = gameLogic.generateRandomPicks(spots);

            for (int number : randomNumbers) {
                for (Button btn : gameBoard.getGridButtons()) {
                    if ((int) btn.getUserData() == number) {
                        ((StackPane) btn.getGraphic()).setOpacity(0.4);
                        break;
                    }
                }
            }

            gameLogic.setPlayerNumbers(randomNumbers);
            statusLabel.setText("Ready! Click Confirm Selection.");
            confirmSelectionButton.setDisable(false);
        });

        // confirm button has two roles:
        // 1. first confirms settings
        // 2. then locks number selection
        confirmSelectionButton.setOnAction(e -> {
            if (!spotsComboBox.isDisabled()) {
                int spots = spotsComboBox.getValue();
                int drawings = drawingsComboBox.getValue();
                gameLogic.setGameSettings(spots, drawings);
                spotsComboBox.setDisable(true);
                drawingsComboBox.setDisable(true);
                gameBoard.enableAllButtons();
                randomPickButton.setDisable(false);
                confirmSelectionButton.setDisable(true);
                statusLabel.setText("Select " + spots + " numbers on the board");
            } else {
                gameBoard.disableAllButtons();
                randomPickButton.setDisable(true);
                confirmSelectionButton.setDisable(true);
                startDrawingButton.setDisable(false);
                statusLabel.setText("Numbers locked! Click Start Drawing.");
            }
        });

        // start drawing
        startDrawingButton.setOnAction(e -> {
            settingsContainer.setVisible(false);
            settingsContainer.setManaged(false);
            drawingInfoPanel.setVisible(true);
            drawingInfoPanel.setManaged(true);
            startDrawingButton.setDisable(true);
            performDrawing();
        });

        // move to next drawing if more rounds remain
        nextDrawingButton.setOnAction(e -> {
            gameLogic.incrementDrawing();
            resetBoardForNextDrawing();
            currentDrawnNumberLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #FF4444;");
            nextDrawingButton.setVisible(false);
            nextDrawingButton.setManaged(false);
            performDrawing();
        });
    }

    // main function that runs one drawing
    private void performDrawing() {
        statusLabel.setText("Drawing in progress...");
        drawnNumbersDisplay.getChildren().clear();

        ArrayList<Integer> drawnNumbers = gameLogic.performDrawing();
        ArrayList<Integer> matches = gameLogic.getMatchedNumbers();

        drawingNumberLabel.setText("Drawing " + gameLogic.getCurrentDrawing() +
                " of " + gameLogic.getNumDrawings());

        animateDrawing(drawnNumbers, matches);
    }

    // shows the drawn numbers one at a time with short delay
    private void animateDrawing(ArrayList<Integer> drawnNumbers, ArrayList<Integer> matches) {
        SequentialTransition sequence = new SequentialTransition();

        for (int i = 0; i < drawnNumbers.size(); i++) {
            final int index = i;
            PauseTransition pause = new PauseTransition(Duration.millis(300));

            pause.setOnFinished(event -> {
                int currentNumber = drawnNumbers.get(index);
                boolean isMatch = matches.contains(currentNumber);
                currentDrawnNumberLabel.setText(String.valueOf(currentNumber));
                addNumberToDrawnDisplay(currentNumber, isMatch);

                // highlight the drawn number on board
                for (Button btn : gameBoard.getGridButtons()) {
                    if ((int) btn.getUserData() == currentNumber) {
                        StackPane graphic = (StackPane) btn.getGraphic();
                        boolean playerPicked = gameLogic.getPlayerNumbers().contains(currentNumber);
                        if (isMatch) {
                            Rectangle green = new Rectangle(60, 60, Color.rgb(76, 175, 80, 0.7));
                            graphic.getChildren().add(0, green);
                        } else if (playerPicked) {
                            graphic.setOpacity(0.4);
                        } else {
                            Rectangle blue = new Rectangle(60, 60, Color.rgb(176, 196, 222, 0.6));
                            graphic.getChildren().add(0, blue);
                        }
                        break;
                    }
                }

                int count = 0;
                for (int j = 0; j <= index; j++)
                    if (matches.contains(drawnNumbers.get(j))) count++;
                matchesLabel.setText("Matches: " + count);
            });
            sequence.getChildren().add(pause);
        }

        PauseTransition finalPause = new PauseTransition(Duration.millis(1500));
        finalPause.setOnFinished(event -> showDrawingResults(matches));
        sequence.getChildren().add(finalPause);
        sequence.play();
    }

    // adds small circle visuals for each drawn number
    private void addNumberToDrawnDisplay(int number, boolean isMatch) {
        StackPane numberCircle = new StackPane();
        Circle circle = new Circle(16);

        if (isMatch) {
            circle.setFill(Color.rgb(76, 175, 80));
            circle.setStroke(Color.rgb(56, 142, 60));
        } else {
            circle.setFill(Color.rgb(176, 196, 222));
            circle.setStroke(Color.DARKGRAY);
        }

        Label label = new Label(String.valueOf(number));
        label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        numberCircle.getChildren().addAll(circle, label);
        drawnNumbersDisplay.getChildren().add(numberCircle);
    }

    // shows final results after a drawing
    private void showDrawingResults(ArrayList<Integer> matches) {
        int matchCount = matches.size();
        int winnings = gameLogic.getCurrentDrawingWinnings();

        currentDrawnNumberLabel.setText("Complete!");
        currentDrawnNumberLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0066CC;");

        matchesLabel.setText("Matches: " + matchCount + " / " + gameLogic.getPlayerNumbers().size());
        winningsLabel.setText("This Drawing: $" + winnings);
        totalWinningsLabel.setText("Total: $" + gameLogic.getTotalWinnings());
        statusLabel.setText("Drawing complete!");

        boolean last = (gameLogic.getCurrentDrawing() >= gameLogic.getNumDrawings());
        if (last) showGameOver();
        else {
            nextDrawingButton.setVisible(true);
            nextDrawingButton.setManaged(true);
        }
    }
    // resets visuals before next drawing
    private void resetBoardForNextDrawing() {
        drawnNumbersDisplay.getChildren().clear();
        currentDrawnNumberLabel.setText("");
        matchesLabel.setText("");
        winningsLabel.setText("");
        for (Button btn : gameBoard.getGridButtons()) {
            int number = (int) btn.getUserData();
            StackPane graphic = (StackPane) btn.getGraphic();

            // remove color overlays from previous drawing
            graphic.getChildren().removeIf(node -> node instanceof Rectangle);

            // make player selections look faded, others normal
            if (gameLogic.getPlayerNumbers().contains(number)) {
                btn.setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
                graphic.setOpacity(0.4);
            } else {
                btn.setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
                graphic.setOpacity(1.0);
            }
        }
    }

    // shows final popup after last drawing is done
    private void showGameOver() {
        javafx.application.Platform.runLater(() -> {
            try {
                GameOverPopup popup = new GameOverPopup(gameLogic.getTotalWinnings(), this);
                popup.show(themeManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // lets player start a brand new game without going back to menu
    public void startNewGame() {
        resetGame();
    }

    // resets everything to original state (used for replay or back to menu)
    private void resetGame() {
        gameLogic.reset();

        // show settings again, hide drawing info
        settingsContainer.setVisible(true);
        settingsContainer.setManaged(true);
        drawingInfoPanel.setVisible(false);
        drawingInfoPanel.setManaged(false);

        // reset dropdowns and buttons
        spotsComboBox.setValue(null);
        spotsComboBox.setDisable(false);
        drawingsComboBox.setValue(null);
        drawingsComboBox.setDisable(false);

        randomPickButton.setDisable(true);
        confirmSelectionButton.setDisable(true);
        startDrawingButton.setDisable(true);
        nextDrawingButton.setVisible(false);
        nextDrawingButton.setManaged(false);

        statusLabel.setText("Select number of spots and drawings");
        drawnNumbersDisplay.getChildren().clear();
        currentDrawnNumberLabel.setText("");
        currentDrawnNumberLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #FF4444;");

        // reset the board visuals
        for (Button btn : gameBoard.getGridButtons()) {
            btn.setStyle("-fx-background-color: white;");
            StackPane graphic = (StackPane) btn.getGraphic();
            graphic.getChildren().removeIf(node -> node instanceof Rectangle);
            graphic.setOpacity(1.0);
            btn.setDisable(true);
        }
    }

    // checks if both dropdowns (spots + drawings) are selected
    private void checkIfSettingsComplete() {
        if (spotsComboBox.getValue() != null && drawingsComboBox.getValue() != null) {
            confirmSelectionButton.setDisable(false);
            statusLabel.setText("click 'confirm selection' to lock your settings");

            // user can’t touch board yet until confirmed
            gameBoard.disableAllButtons();
            randomPickButton.setDisable(true);
        }
    }

    // checks if player picked enough numbers before confirming
    private void checkIfReadyToStart() {
        if (spotsComboBox.getValue() == null) return;

        int required = spotsComboBox.getValue();
        int picked = gameLogic.getPlayerNumbers().size();

        if (picked == required) {
            statusLabel.setText("ready! click confirm selection.");
            confirmSelectionButton.setDisable(false);
            randomPickButton.setDisable(false);
        } else if (picked < required) {
            statusLabel.setText("selected " + picked + "/" + required + " numbers");
            confirmSelectionButton.setDisable(true);
            startDrawingButton.setDisable(true);
        } else {
            statusLabel.setText("too many numbers selected!");
            confirmSelectionButton.setDisable(true);
            startDrawingButton.setDisable(true);
        }
    }

    // opens popup showing keno rules
    public void showRules() {
        RulesPopup popup = new RulesPopup();
        popup.show(themeManager);
    }

    // opens popup showing keno odds table
    public void showOdds() {
        OddsPopup popup = new OddsPopup();
        popup.show(themeManager);
    }

    // switches between light/dark or fish theme without losing state
    public void toggleTheme() {
        themeManager.toggleTheme();
        Scene current = primaryStage.getScene();

        if (current == sceneMap.get("menu")) {
            // rebuild menu to show new background
            sceneMap.put("menu", createMenuScene());
            primaryStage.setScene(sceneMap.get("menu"));
        } else {
            // update all game buttons with new theme images
            String newImagePath = "/images/" + themeManager.getBoardImage();
            Image newImage = new Image(getClass().getResource(newImagePath).toExternalForm());

            for (Button btn : gameBoard.getGridButtons()) {
                StackPane graphic = (StackPane) btn.getGraphic();
                ImageView imageView = null;

                for (javafx.scene.Node node : graphic.getChildren()) {
                    if (node instanceof ImageView) {
                        imageView = (ImageView) node;
                        break;
                    }
                }

                if (imageView != null) {
                    imageView.setImage(newImage);
                }

                // tweak label position for certain themes
                for (javafx.scene.Node node : graphic.getChildren()) {
                    if (node instanceof Label) {
                        Label numText = (Label) node;
                        if (themeManager.getBoardImage().equals("Fish.PNG")) {
                            StackPane.setMargin(numText, new Insets(0, 0, 10, 10));
                        } else {
                            StackPane.setMargin(numText, new Insets(0, 0, 0, 10));
                        }
                        break;
                    }
                }
            }

            themeManager.applyToScene(current);
        }
    }

    // just closes the game window
    public void exitGame() {
        primaryStage.close();
    }

    // gives access to theme manager (for other classes)
    public ThemeManager getThemeManager() {
        return themeManager;
    }
}