// import javafx.animation.FadeTransition;
// import javafx.animation.PauseTransition;
// import javafx.animation.RotateTransition;
// import javafx.animation.SequentialTransition;
// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.layout.GridPane;
// import javafx.scene.text.Font;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.FlowPane;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Rectangle;
// import javafx.scene.shape.Circle;
// import javafx.stage.Stage;
// import javafx.scene.control.Button;
// import javafx.scene.control.*;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.Separator;
// import javafx.event.EventHandler;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.util.Duration;
// import java.util.HashMap;
// import java.util.Objects;
// import java.util.EventObject;
// import java.util.ArrayList;
// import javafx.scene.layout.StackPane;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.event.ActionEvent;

// /**
//  * Main application class for a Keno game built with JavaFX.
//  * This class manages the game interface, scene navigation, and user interactions.
//  * The game allows players to select numbers, choose betting options, and play multiple drawings.
//  */
// public class JavaFXTemplate extends Application {
//     // Navigation buttons for switching between scenes
//     Button sceneChangeToGame, sceneChangeToMenu;
    
//     // HashMap to store and manage different scenes (menu and game)
//     HashMap<String, Scene> sceneMap;
    
//     // Menu bars for welcome screen and game screen
//     MenuBar menuBarWelcome, menuBarGame;
//     Menu menuWelcome, menuGame;
    
//     // Primary stage (main window) of the application
//     Stage primaryStage;
    
//     // Manager classes for theme and game logic
//     ThemeManager themeManager;
//     GameLogic gameLogic;
//     GameBoard gameBoard;
    
//     // UI controls for game settings
//     ComboBox<Integer> spotsComboBox;        // Select number of spots to play
//     ComboBox<Integer> drawingsComboBox;     // Select number of drawings
//     Button randomPickButton;                 // Generate random number selections
//     Button startDrawingButton;               // Begin the drawing process
//     Label statusLabel;                       // Display current game status to user
//     Button confirmSelectionButton;           // Lock in player's number selections
//     Button nextDrawingButton;                // Button to continue to next drawing
    
//     // Container for settings (to hide during drawing)
//     VBox settingsContainer;
    
//     // New fields for drawing display
//     VBox drawingInfoPanel;                   // Panel showing drawing information
//     Label drawingNumberLabel;                // Current drawing number (e.g., "Drawing 1 of 4")
//     Label currentDrawnNumberLabel;           // Shows the number currently being drawn
//     Label matchesLabel;                      // Number of matches in current drawing
//     Label winningsLabel;                     // Winnings for current drawing
//     Label totalWinningsLabel;                // Total winnings across all drawings
//     FlowPane drawnNumbersDisplay;            // Visual display of all drawn numbers

//     /**
//      * Main entry point for the JavaFX application
//      */
//     public static void main(String[] args) {
//         launch(args);
//     }

//     /**
//      * Initializes and configures the primary stage and all game components.
//      * Sets up the window, menus, and initial scene.
//      * 
//      * @param primaryStage The main window of the application
//      */
//     @Override
//     public void start(Stage primaryStage) throws Exception {
//         this.primaryStage = primaryStage;
//         primaryStage.setTitle("Keno");

//         // Initialize the size of the entire game window
//         primaryStage.setResizable(false);
//         primaryStage.setWidth(1250);
//         primaryStage.setHeight(700);

//         // Initialize theme manager and game logic
//         themeManager = new ThemeManager();
//         gameLogic = new GameLogic();

//         // Creating Menu Bars for Welcome & Game Scene
//         menuWelcome = new Menu("✪ Menu");
//         menuGame = new Menu("✪ Menu");
//         menuBarWelcome = new MenuBar();
//         menuBarGame = new MenuBar();

//         // Setting Style for Welcome & Game Menu
//         menuWelcome.setStyle("-fx-font-size: 14px;");
//         menuGame.setStyle("-fx-font-size: 14px;");

//         // Menu Items for Welcome Scene
//         MenuItem mW1 = new MenuItem("Rules");
//         mW1.setOnAction(e -> showRules());

//         MenuItem mW2 = new MenuItem("Odds");
//         mW2.setOnAction(e -> showOdds());

//         MenuItem mW3 = new MenuItem("Exit");
//         mW3.setOnAction(e -> exitGame());

//         // Menu Items for Game Scene
//         MenuItem mG1 = new MenuItem("Rules");
//         mG1.setOnAction(e -> showRules());

//         MenuItem mG2 = new MenuItem("Odds");
//         mG2.setOnAction(e -> showOdds());

//         MenuItem mG3 = new MenuItem("Change Theme");
//         mG3.setOnAction(e -> toggleTheme());

//         MenuItem mG4 = new MenuItem("Exit");
//         mG4.setOnAction(e -> exitGame());

//         // Setting Up Menu Bars for Welcome & Game Scene
//         menuWelcome.getItems().addAll(mW1, mW2, mW3);
//         menuBarWelcome.getMenus().addAll(menuWelcome);
//         menuGame.getItems().addAll(mG1, mG2, mG3, mG4);
//         menuBarGame.getMenus().addAll(menuGame);

//         // Play Button - navigates from welcome screen to game
//         sceneChangeToGame = new Button();

//         // Back To Menu Button - returns to welcome screen
//         sceneChangeToMenu = new Button("Back To Menu");
//         sceneChangeToMenu.setPrefWidth(180);
//         sceneChangeToMenu.setPrefHeight(60);
//         sceneChangeToMenu.setStyle("-fx-background-radius: 50px;" + "-fx-border-radius: 50px;" + "-fx-font-size: 20px;"
//                 + "-fx-text-fill: #6750A4;" + "-fx-font-weight: bold;");

//         // Initialize scene map to store all scenes
//         sceneMap = new HashMap<String,Scene>();

//         // Event handler for transitioning to game scene
//         sceneChangeToGame.setOnAction(e -> {
//             Scene gameScene = createGameScene();
//             sceneMap.put("game", gameScene);
//             primaryStage.setScene(sceneMap.get("game"));
//         });
        
//         // Event handler for returning to menu scene
//         sceneChangeToMenu.setOnAction(e -> {
//             resetGame();
//             // Recreate menu scene with current theme before showing it
//             sceneMap.put("menu", createMenuScene());
//             primaryStage.setScene(sceneMap.get("menu"));
//         });

//         // Create and store both scenes
//         sceneMap.put("menu", createMenuScene());
//         sceneMap.put("game", createGameScene());

//         // Display the menu scene first
//         primaryStage.setScene(sceneMap.get("menu"));
//         primaryStage.show();
//     }

//     /**
//      * Creates the welcome/menu scene with background image and play button.
//      * This is the first screen users see when launching the game.
//      * 
//      * @return Scene object representing the menu screen
//      */
//         public Scene createMenuScene(){
//             // Sets up background image based on current theme
//             ImageView bgImage = themeManager.getBackgroundImageView();

//             // Sets up BorderPane layout
//             BorderPane pane = new BorderPane();
//             pane.setPrefSize(1250, 700);

//             // Adds padding and center container for play button
//             VBox centerBox = new VBox(20);
//             centerBox.setPadding(new Insets(10));
//             centerBox.setStyle("-fx-alignment:center;");

//             // Load and configure play button image
//             String buttonImagePath = "/images/" + themeManager.getButtonImage();
//             Image buttonImage = new Image(getClass().getResource(buttonImagePath).toExternalForm());
//             ImageView buttonImageView = new ImageView(buttonImage);

//             buttonImageView.setFitWidth(180);
//             buttonImageView.setFitHeight(60);

//             // Set button graphic and styling
//             sceneChangeToGame.setGraphic(buttonImageView);
//             sceneChangeToGame.setStyle("-fx-background-color: transparent;");
//             VBox.setMargin(sceneChangeToGame, new Insets(250, 0, 0, 0));

//             centerBox.getChildren().addAll(sceneChangeToGame);

//             // Assemble the scene layout
//             pane.setTop(menuBarWelcome);
//             pane.setCenter(centerBox);

//             StackPane root = new StackPane(bgImage, pane);
//             Scene scene = new Scene(root, 1250, 700);
//             themeManager.applyToScene(scene);
//             return scene;
//         }

//         public Scene createGameScene(){
//         BorderPane pane = new BorderPane();
//         pane.setPrefSize(1250, 700);

//         pane.setTop(menuBarGame);

//         // Create the Keno number grid (1-80) - KEEP ORIGINAL LAYOUT
//         gameBoard = new GameBoard();
//         GridPane grid = gameBoard.createGameBoard(e -> {
//             Button btn = (Button) e.getSource();
//             gameLogic.handleButtonPress(btn);
//             checkIfReadyToStart();
//         }, themeManager);
//         pane.setLeft(grid);

//         // Create right side control panel
//         VBox rightPanel = createRightPanel();

//         // Container for back button and control panel - REDUCED GAP
//         VBox rightSide = new VBox(10);
//         rightSide.setPadding(new Insets(10));
//         rightSide.getChildren().addAll(sceneChangeToMenu, rightPanel);
//         pane.setRight(rightSide);
        
//         Scene scene = new Scene(pane, 1250, 700);
        
//         // ADD CSS TO PREVENT DISABLED BUTTON GRAYING
//         scene.getRoot().setStyle("-fx-opacity: 1.0;");
//         String css = "data:text/css," +
//             ".button:disabled { " +
//             "    -fx-opacity: 1.0 !important; " +
//             "}";
        
//         themeManager.applyToScene(scene);
//         return scene;
//     }

//     /**
//      * Creates the right control panel containing game settings and action buttons.
//      * Includes combo boxes for spots/drawings selection and buttons for game actions.
//      * 
//      * @return VBox containing all control panel elements
//      */
//     private VBox createRightPanel() {
//         VBox rightPanel = new VBox(20);
//         rightPanel.setPadding(new Insets(20, 175, 0, 50)); // Reduced top padding from 50 to 20
//         rightPanel.setAlignment(Pos.TOP_CENTER);

//         // Status label to guide the user through the game flow
//         statusLabel = new Label("Select number of spots and drawings");
//         statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");
//         statusLabel.setWrapText(true);
//         statusLabel.setMaxWidth(200);

//         // Spots selection - how many numbers the player will pick
//         Label spotsLabel = new Label("Number of Spots:");
//         spotsLabel.setStyle("-fx-font-size: 14px;");
//         spotsComboBox = new ComboBox<>();
//         spotsComboBox.getItems().addAll(1, 4, 8, 10);
//         spotsComboBox.setPromptText("Choose spots...");
//         spotsComboBox.setPrefWidth(150);

//         // Drawings selection - how many times to play
//         Label drawingsLabel = new Label("Number of Drawings:");
//         drawingsLabel.setStyle("-fx-font-size: 14px;");
//         drawingsComboBox = new ComboBox<>();
//         drawingsComboBox.getItems().addAll(1, 2, 3, 4);
//         drawingsComboBox.setPromptText("Choose drawings...");
//         drawingsComboBox.setPrefWidth(150);

//         // Random pick button - automatically selects random numbers for player
//         randomPickButton = new Button("Random Pick");
//         randomPickButton.setPrefWidth(150);
//         randomPickButton.setPrefHeight(40);
//         randomPickButton.setStyle("-fx-font-size: 14px;");
//         randomPickButton.setDisable(true);

//         // Confirm selection button - locks in the player's choices (dual purpose)
//         confirmSelectionButton = new Button("Confirm Selection");
//         confirmSelectionButton.setPrefWidth(150);
//         confirmSelectionButton.setPrefHeight(40);
//         confirmSelectionButton.setStyle("-fx-font-size: 14px;");
//         confirmSelectionButton.setDisable(true);

//         // Start drawing button - begins the Keno drawing process
//         startDrawingButton = new Button("Start Drawing");
//         startDrawingButton.setPrefWidth(150);
//         startDrawingButton.setPrefHeight(40);
//         startDrawingButton.setStyle("-fx-font-size: 14px;");
//         startDrawingButton.setDisable(true);

//         // Settings container - will be hidden during drawing
//         settingsContainer = new VBox(10);
//         settingsContainer.setAlignment(Pos.CENTER);
//         settingsContainer.getChildren().addAll(
//             spotsLabel,
//             spotsComboBox,
//             drawingsLabel,
//             drawingsComboBox,
//             new Separator(),
//             randomPickButton,
//             confirmSelectionButton,
//             startDrawingButton
//         );

//         // Drawing info panel (hidden initially)
//         drawingInfoPanel = new VBox(10);
//         drawingInfoPanel.setAlignment(Pos.CENTER);
//         drawingInfoPanel.setPadding(new Insets(10));
//         drawingInfoPanel.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 10;");
//         drawingInfoPanel.setVisible(false);
//         drawingInfoPanel.setMaxWidth(250);

//         // Label showing which drawing is currently in progress
//         drawingNumberLabel = new Label();
//         drawingNumberLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

//         // Large label showing the current number being drawn
//         currentDrawnNumberLabel = new Label();
//         currentDrawnNumberLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #FF4444;");

//         // Label for "Drawn Numbers" header
//         Label drawnNumbersHeader = new Label("Drawn Numbers:");
//         drawnNumbersHeader.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

//         // FlowPane to display all 20 drawn numbers as circles
//         drawnNumbersDisplay = new FlowPane();
//         drawnNumbersDisplay.setHgap(4);
//         drawnNumbersDisplay.setVgap(4);
//         drawnNumbersDisplay.setAlignment(Pos.CENTER);
//         drawnNumbersDisplay.setPrefWrapLength(240);
//         drawnNumbersDisplay.setMaxWidth(240);

//         // Label showing number of matches found
//         matchesLabel = new Label();
//         matchesLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

//         // Label showing winnings for current drawing
//         winningsLabel = new Label();
//         winningsLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: green;");

//         // Label showing total winnings across all drawings
//         totalWinningsLabel = new Label();
//         totalWinningsLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #0066CC;");

//         // Create Next Drawing button
//         nextDrawingButton = new Button("Next Drawing");
//         nextDrawingButton.setPrefWidth(150);
//         nextDrawingButton.setPrefHeight(40);
//         nextDrawingButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
//         nextDrawingButton.setVisible(false);
//         nextDrawingButton.setManaged(false); // Don't take up space when hidden

//         // Add all drawing info components to the panel
//         drawingInfoPanel.getChildren().addAll(
//             drawingNumberLabel,
//             new Separator(),
//             currentDrawnNumberLabel,
//             drawnNumbersHeader,
//             drawnNumbersDisplay,
//             new Separator(),
//             matchesLabel,
//             winningsLabel,
//             totalWinningsLabel,
//             nextDrawingButton  // ADD THE BUTTON HERE
//         );

//         // Add event handlers for all controls
//         setupControlHandlers();

//         // Add all components to the right panel
//         rightPanel.getChildren().addAll(
//             statusLabel,
//             new Separator(),
//             settingsContainer,
//             drawingInfoPanel
//         );

//         return rightPanel;
//     }

//     /**
//      * Sets up event handlers for all control panel buttons and combo boxes.
//      * Manages the game flow from settings selection through number picking to drawing.
//      */
//     private void setupControlHandlers() {
//         // When spots combo box value changes, check if all settings are complete
//         spotsComboBox.setOnAction(e -> {
//             checkIfSettingsComplete();
//         });

//         // When drawings combo box value changes, check if all settings are complete
//         drawingsComboBox.setOnAction(e -> {
//             checkIfSettingsComplete();
//         });

//         // Random pick button handler - generates random number selections
//         randomPickButton.setOnAction(e -> {
//             // First, clear any existing selections
//             gameLogic.getPlayerNumbers().clear();
            
//             // Reset all button visuals to unselected state - NO OPACITY CHANGE
//             for (Button btn : gameBoard.getGridButtons()) {
//                 StackPane graphic = (StackPane) btn.getGraphic();
//                 // Remove any overlays
//                 graphic.getChildren().removeIf(node -> node instanceof Rectangle);
//                 graphic.setOpacity(1.0);
//                 btn.setStyle("-fx-background-color: white;");
//             }
            
//             // Generate random numbers based on selected spots
//             int spots = spotsComboBox.getValue();
//             ArrayList<Integer> randomNumbers = gameLogic.generateRandomPicks(spots);
            
//             // Visually select the random numbers on the grid
//             for (int number : randomNumbers) {
//                 for (Button btn : gameBoard.getGridButtons()) {
//                     if ((int) btn.getUserData() == number) {
//                         StackPane graphic = (StackPane) btn.getGraphic();
//                         graphic.setOpacity(0.4); // Dim to show selection
//                         break;
//                     }
//                 }
//             }
            
//             // Set the numbers in game logic
//             gameLogic.setPlayerNumbers(randomNumbers);
            
//             // Update status and enable confirm button
//             statusLabel.setText("Ready! Click Confirm Selection.");
//             confirmSelectionButton.setDisable(false);
//         });

//         // Confirm selection button handler - dual purpose button
//         confirmSelectionButton.setOnAction(e -> {
//             // FIRST STAGE: If combo boxes are enabled, this confirms settings
//             if (!spotsComboBox.isDisabled()) {
//                 int spots = spotsComboBox.getValue();
//                 int drawings = drawingsComboBox.getValue();
                
//                 // Lock the settings in game logic
//                 gameLogic.setGameSettings(spots, drawings);
                
//                 // Disable the combo boxes - user can't change these now
//                 spotsComboBox.setDisable(true);
//                 drawingsComboBox.setDisable(true);
                
//                 // Enable the game board for number selection
//                 gameBoard.enableAllButtons();
                
//                 // Enable random pick button
//                 randomPickButton.setDisable(false);
                
//                 // Disable confirm button until numbers are selected
//                 confirmSelectionButton.setDisable(true);
                
//                 // Update status to prompt user to select numbers
//                 statusLabel.setText("Select " + spots + " numbers on the bet card");
//             }
//             // SECOND STAGE: If combo boxes are disabled, this confirms number selection
//             else {
//                 // Lock the number selections - disable board (but keep appearance unchanged)
//                 gameBoard.disableAllButtons();
                
//                 // Disable random pick and confirm selection
//                 randomPickButton.setDisable(true);
//                 confirmSelectionButton.setDisable(true);
                
//                 // Enable start drawing
//                 statusLabel.setText("Numbers locked! Click Start Drawing.");
//                 startDrawingButton.setDisable(false);
//             }
//         });

//         // Start drawing button handler - initiates the Keno drawing
//         startDrawingButton.setOnAction(e -> {
//             // Hide settings, show drawing info
//             settingsContainer.setVisible(false);
//             settingsContainer.setManaged(false);
//             drawingInfoPanel.setVisible(true);
//             drawingInfoPanel.setManaged(true);
            
//             startDrawingButton.setDisable(true);
//             performDrawing();
//         });

//         // Next Drawing button handler
//         nextDrawingButton.setOnAction(e -> {
//             gameLogic.incrementDrawing();
//             resetBoardForNextDrawing();
//             currentDrawnNumberLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #FF4444;");
//             nextDrawingButton.setVisible(false);
//             nextDrawingButton.setManaged(false);
//             performDrawing();
//         });
//     }

//     /**
//      * Performs the drawing animation and handles the game logic.
//      * Draws 20 random numbers and checks for matches with player selections.
//      */
//     private void performDrawing() {
//         statusLabel.setText("Drawing in progress...");
//         drawnNumbersDisplay.getChildren().clear();
        
//         // Perform the drawing - generates 20 random numbers
//         ArrayList<Integer> drawnNumbers = gameLogic.performDrawing();
//         ArrayList<Integer> matches = gameLogic.getMatchedNumbers();
        
//         // Update drawing info
//         drawingNumberLabel.setText("Drawing " + gameLogic.getCurrentDrawing() + " of " + gameLogic.getNumDrawings());
        
//         // Animate the drawn numbers one by one
//         animateDrawing(drawnNumbers, matches);
//     }
    
//     /**
//      * Animates the drawing of numbers on the board one at a time.
//      * Shows each number as it's drawn and highlights matches in real-time.
//      * Uses color overlay (opacity) instead of borders to maintain board layout.
//      * 
//      * @param drawnNumbers List of 20 numbers drawn in this round
//      * @param matches List of numbers that match player's selections
//      */
//     private void animateDrawing(ArrayList<Integer> drawnNumbers, ArrayList<Integer> matches) {
//         SequentialTransition sequence = new SequentialTransition();
        
//         // Animate each drawn number one at a time
//         for (int i = 0; i < drawnNumbers.size(); i++) {
//             final int index = i;
            
//             // Create pause before showing next number (300ms between each number)
//             PauseTransition pause = new PauseTransition(Duration.millis(300));
            
//             pause.setOnFinished(event -> {
//                 int currentNumber = drawnNumbers.get(index);
//                 boolean currentIsMatch = matches.contains(currentNumber);
                
//                 // Update the large "currently drawing" number display
//                 currentDrawnNumberLabel.setText(String.valueOf(currentNumber));
                
//                 // Add this number to the drawn numbers display as a circle
//                 addNumberToDrawnDisplay(currentNumber, currentIsMatch);
                
//                 // Find and highlight the button on the board using color overlay
//                 for (Button btn : gameBoard.getGridButtons()) {
//                     if ((int) btn.getUserData() == currentNumber) {
//                         StackPane graphic = (StackPane) btn.getGraphic();
                        
//                         // Check if this is a player's number
//                         boolean isPlayerNumber = gameLogic.getPlayerNumbers().contains(currentNumber);
                        
//                         if (currentIsMatch) {
//                             // MATCH: Player selected this number and it was drawn
//                             // Use GREEN tint overlay (like selection but green)
//                             btn.setStyle("-fx-background-color: white;");
                            
//                             // Add green rectangle overlay to the StackPane
//                             Rectangle greenOverlay = new Rectangle(60, 60);
//                             greenOverlay.setFill(Color.rgb(76, 175, 80, 0.7)); // Green with 70% opacity
//                             graphic.getChildren().add(0, greenOverlay); // Add behind the image and text
//                             graphic.setOpacity(1.0);
                            
//                         } else if (isPlayerNumber) {
//                             // Player selected but NOT drawn yet - keep original faded look
//                             btn.setStyle("-fx-background-color: white;");
//                             graphic.setOpacity(0.4);
                            
//                         } else {
//                             // Drawn but player didn't select - light blue overlay
//                             btn.setStyle("-fx-background-color: white;");
                            
//                             // Add blue rectangle overlay
//                             Rectangle blueOverlay = new Rectangle(60, 60);
//                             blueOverlay.setFill(Color.rgb(176, 196, 222, 0.6)); // Light blue with 60% opacity
//                             graphic.getChildren().add(0, blueOverlay); // Add behind the image and text
//                             graphic.setOpacity(1.0);
//                         }
//                         break;
//                     }
//                 }
                
//                 // Update matches count in real-time
//                 int currentMatches = 0;
//                 for (int j = 0; j <= index; j++) {
//                     if (matches.contains(drawnNumbers.get(j))) {
//                         currentMatches++;
//                     }
//                 }
//                 matchesLabel.setText("Matches: " + currentMatches);
//             });
            
//             sequence.getChildren().add(pause);
//         }
        
//         // After all numbers are drawn, show final results
//         PauseTransition finalPause = new PauseTransition(Duration.millis(1500));
//         finalPause.setOnFinished(event -> showDrawingResults(matches));
//         sequence.getChildren().add(finalPause);
        
//         sequence.play();
//     }
//     /**
//      * Adds a drawn number to the visual display as a colored circle.
//      * 
//      * @param number The number that was drawn
//      * @param isMatch Whether this number matches a player selection
//      */
//     private void addNumberToDrawnDisplay(int number, boolean isMatch) {
//         // Create a circle to represent the drawn number
//         StackPane numberCircle = new StackPane();
//         Circle circle = new Circle(16);
        
//         // Color the circle based on whether it's a match
//         if (isMatch) {
//             // Green for matches
//             circle.setFill(Color.rgb(76, 175, 80));
//             circle.setStroke(Color.rgb(56, 142, 60));
//             circle.setStrokeWidth(2.5);
//         } else {
//             // Light blue for non-matches
//             circle.setFill(Color.rgb(176, 196, 222));
//             circle.setStroke(Color.DARKGRAY);
//             circle.setStrokeWidth(1.5);
//         }
        
//         Label numberLabel = new Label(String.valueOf(number));
//         numberLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        
//         numberCircle.getChildren().addAll(circle, numberLabel);
//         drawnNumbersDisplay.getChildren().add(numberCircle);
//     }

//     /**
//      * Shows the final results of the current drawing.
//      * Displays matches, winnings, and determines if more drawings remain.
//      * 
//      * @param matches List of numbers that matched player's selections
//      */
//     private void showDrawingResults(ArrayList<Integer> matches) {
//         int matchCount = matches.size();
//         int winnings = gameLogic.getCurrentDrawingWinnings();
        
//         // Clear the "currently drawing" display - SHOWS "Complete!"
//         currentDrawnNumberLabel.setText("Complete!");
//         currentDrawnNumberLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0066CC;");
        
//         // Show final match count and winnings
//         matchesLabel.setText("Matches: " + matchCount + " / " + gameLogic.getPlayerNumbers().size());
//         winningsLabel.setText("This Drawing: $" + winnings);
//         totalWinningsLabel.setText("Total: $" + gameLogic.getTotalWinnings());
        
//         statusLabel.setText("Drawing complete!");
        
//         // Check if this was the LAST drawing
//         boolean isLastDrawing = (gameLogic.getCurrentDrawing() >= gameLogic.getNumDrawings());
        
//         System.out.println("=== DRAWING COMPLETE ===");
//         System.out.println("Current Drawing: " + gameLogic.getCurrentDrawing());
//         System.out.println("Total Drawings: " + gameLogic.getNumDrawings());
//         System.out.println("Is this the last drawing? " + isLastDrawing);
        
//         if (isLastDrawing) {
//             // ALL DRAWINGS COMPLETE - SHOW GAME OVER POPUP
//             System.out.println(">>> SHOWING GAME OVER POPUP NOW <<<");
//             statusLabel.setText("All drawings complete! Total: $" + gameLogic.getTotalWinnings());
//             showGameOver();
//         } else {
//             // More drawings remain - show "Next Drawing" button
//             System.out.println("More drawings remain - showing Next Drawing button");
//             statusLabel.setText("Click to continue to next drawing");
//             nextDrawingButton.setVisible(true);
//             nextDrawingButton.setManaged(true);
//             nextDrawingButton.setDisable(false);
//         }
//     }

//     /**
//      * Resets the board appearance for the next drawing.
//      * Clears all drawing highlights and overlays, shows only player selections.
//      */
//     private void resetBoardForNextDrawing() {
//         // Clear the drawn numbers display
//         drawnNumbersDisplay.getChildren().clear();
//         currentDrawnNumberLabel.setText("");
//         matchesLabel.setText("");
//         winningsLabel.setText("");
        
//         // Reset board to show only player selections
//         for (Button btn : gameBoard.getGridButtons()) {
//             int number = (int) btn.getUserData();
//             StackPane graphic = (StackPane) btn.getGraphic();
            
//             // Remove all overlay rectangles (keep only ImageView and Label)
//             graphic.getChildren().removeIf(node -> node instanceof Rectangle);
            
//             // IMPORTANT: Always set opacity to 1.0 on the button style to prevent graying
//             // Reset to show player selections only (faded)
//             if (gameLogic.getPlayerNumbers().contains(number)) {
//                 btn.setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
//                 graphic.setOpacity(0.4);
//             } else {
//                 btn.setStyle("-fx-background-color: white; -fx-opacity: 1.0;");
//                 graphic.setOpacity(1.0);
//             }
//         }
//     }

//     /**
//      * Shows the game over popup with total winnings.
//      * Called when all drawings are complete.
//      */
//     private void showGameOver() {
//         System.out.println("=== showGameOver() method called ===");
//         System.out.println("Creating GameOverPopup with winnings: " + gameLogic.getTotalWinnings());
        
//         // Use Platform.runLater to show popup AFTER animation finishes
//         // This prevents "showAndWait is not allowed during animation" error
//         javafx.application.Platform.runLater(() -> {
//             try {
//                 GameOverPopup popup = new GameOverPopup(gameLogic.getTotalWinnings(), this);
//                 System.out.println("GameOverPopup created successfully");
//                 System.out.println("Calling popup.show()...");
//                 popup.show();
//                 System.out.println("popup.show() returned");
//             } catch (Exception e) {
//                 System.err.println("ERROR in showGameOver(): " + e.getMessage());
//                 e.printStackTrace();
//             }
//         });
//     }

//     /**
//      * Starts a new game without going back to menu.
//      * Called from GameOverPopup when user chooses "Play Again".
//      * Resets all game state and stays on the game screen.
//      */
//     public void startNewGame() {
//         resetGame();
//     }

//     /**
//      * Resets the game to initial state for a new game.
//      * Clears all selections, settings, overlays, and resets the UI.
//      */
//     private void resetGame() {
//         gameLogic.reset();
        
//         // Show settings, hide drawing info
//         settingsContainer.setVisible(true);
//         settingsContainer.setManaged(true);
//         drawingInfoPanel.setVisible(false);
//         drawingInfoPanel.setManaged(false);
        
//         // Reset UI controls
//         spotsComboBox.setValue(null);
//         spotsComboBox.setDisable(false);
//         drawingsComboBox.setValue(null);
//         drawingsComboBox.setDisable(false);
        
//         randomPickButton.setDisable(true);
//         confirmSelectionButton.setDisable(true);
//         startDrawingButton.setDisable(true);
//         nextDrawingButton.setVisible(false);
//         nextDrawingButton.setManaged(false);
        
//         statusLabel.setText("Select number of spots and drawings");
//         drawnNumbersDisplay.getChildren().clear();
//         currentDrawnNumberLabel.setText("");
//         currentDrawnNumberLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #FF4444;");
        
//         // Reset board visuals and remove overlays - NO OPACITY/STYLE CHANGES
//         for (Button btn : gameBoard.getGridButtons()) {
//             btn.setStyle("-fx-background-color: white;");
//             StackPane graphic = (StackPane) btn.getGraphic();
            
//             // Remove all overlay rectangles
//             graphic.getChildren().removeIf(node -> node instanceof Rectangle);
            
//             graphic.setOpacity(1.0);
//             btn.setDisable(true);
//         }
//     }

//     /**
//      * Checks if both spots and drawings have been selected.
//      * When complete, enables the confirm button to lock settings.
//      */
//     private void checkIfSettingsComplete() {
//         if (spotsComboBox.getValue() != null && drawingsComboBox.getValue() != null) {
//             // Enable confirm button to lock settings
//             confirmSelectionButton.setDisable(false);
//             statusLabel.setText("Click 'Confirm Selection' to lock your settings");
            
//             // Don't enable board or random pick yet - must confirm first
//             gameBoard.disableAllButtons();
//             randomPickButton.setDisable(true);
//         }
//     }

//     /**
//      * Checks if the player has selected the correct number of spots.
//      * Updates the status label and enables/disables buttons accordingly.
//      * Called each time a number is selected or deselected on the game board.
//      */
//     private void checkIfReadyToStart() {
//         // Exit if spots haven't been selected yet
//         if (spotsComboBox.getValue() == null) {
//             return;
//         }
        
//         int requiredSpots = spotsComboBox.getValue();
//         int selectedSpots = gameLogic.getPlayerNumbers().size();
        
//         // Player has selected exactly the right number of spots
//         if (selectedSpots == requiredSpots) {
//             statusLabel.setText("Ready! Click Confirm Selection.");
//             confirmSelectionButton.setDisable(false);
//             randomPickButton.setDisable(false);
//         } 
//         // Player hasn't selected enough spots yet
//         else if (selectedSpots < requiredSpots) {
//             statusLabel.setText("Selected " + selectedSpots + "/" + requiredSpots + " numbers");
//             confirmSelectionButton.setDisable(true);
//             startDrawingButton.setDisable(true);
//         } 
//         // Player has selected too many spots
//         else {
//             statusLabel.setText("Too many numbers selected!");
//             confirmSelectionButton.setDisable(true);
//             startDrawingButton.setDisable(true);
//         }
//     }

//     /**
//      * Displays a popup window showing the rules of Keno.
//      */
//     public void showRules() {
//         RulesPopup popup = new RulesPopup();
//         popup.show();
//     }

//     /**
//      * Displays a popup window showing the odds and payouts for Keno.
//      */
//     public void showOdds() {
//         OddsPopup popup = new OddsPopup();
//         popup.show();
//     }

//     /**
//      * Toggles between available themes without rebuilding the scene.
//      * Simply swaps the button images while preserving all game state.
//      */
//     public void toggleTheme() {
//         // Toggle the theme
//         themeManager.toggleTheme();
        
//         // Update the background if we're on the menu scene
//         Scene current = primaryStage.getScene();
//         if (current == sceneMap.get("menu")) {
//             // Rebuild menu scene since it's just the background
//             sceneMap.put("menu", createMenuScene());
//             primaryStage.setScene(sceneMap.get("menu"));
//         } else {
//             // For game scene, just update the button images without rebuilding
//             String newImagePath = "/images/" + themeManager.getBoardImage();
//             Image newImage = new Image(getClass().getResource(newImagePath).toExternalForm());
            
//             // Update each button's image
//             for (Button btn : gameBoard.getGridButtons()) {
//                 StackPane graphic = (StackPane) btn.getGraphic();
                
//                 // Find the ImageView in the StackPane (it's the first child, or after overlays)
//                 ImageView imageView = null;
//                 for (javafx.scene.Node node : graphic.getChildren()) {
//                     if (node instanceof ImageView) {
//                         imageView = (ImageView) node;
//                         break;
//                     }
//                 }
                
//                 // Update the image
//                 if (imageView != null) {
//                     imageView.setImage(newImage);
//                 }
                
//                 // Update label position based on theme
//                 for (javafx.scene.Node node : graphic.getChildren()) {
//                     if (node instanceof Label) {
//                         Label numText = (Label) node;
//                         if (themeManager.getBoardImage().equals("Fish.PNG")) {
//                             StackPane.setMargin(numText, new Insets(0, 0, 10, 10));
//                         } else {
//                             StackPane.setMargin(numText, new Insets(0, 0, 0, 10));
//                         }
//                         break;
//                     }
//                 }
//             }
            
//             // Update menu bar styling if needed
//             themeManager.applyToScene(current);
//         }
//     }

//     /**
//      * Helper class to store button state during theme changes
//      */
//     private class ButtonState {
//         int number;
//         double opacity;
//         boolean hasGreenOverlay;
//         boolean hasBlueOverlay;
//     }

//         /**
//          * Closes the application window, ending the game.
//          */
//         public void exitGame() {
//             primaryStage.close();
//         }

//         /**
//          * Getter method for the theme manager.
//          * 
//          * @return The ThemeManager instance managing the application's visual theme
//          */
//         public ThemeManager getThemeManager() {
//             return themeManager;
//         }
//     }

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
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
        primaryStage.setWidth(1250);
        primaryStage.setHeight(700);

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
        sceneChangeToMenu.setPrefWidth(180);
        sceneChangeToMenu.setPrefHeight(60);
        sceneChangeToMenu.setStyle("-fx-background-radius: 50px;" +
                "-fx-border-radius: 50px;" +
                "-fx-font-size: 20px;" +
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
        ImageView bgImage = themeManager.getBackgroundImageView();

        BorderPane pane = new BorderPane();
        pane.setPrefSize(1250, 700);

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
                popup.show();
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
        popup.show();
    }

    // opens popup showing keno odds table
    public void showOdds() {
        OddsPopup popup = new OddsPopup();
        popup.show();
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