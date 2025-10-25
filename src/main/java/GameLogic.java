import java.util.ArrayList;
import javafx.scene.control.*;

public class GameLogic {
    private int numSpots;
    private int numDrawings;
    private int currDrawing;
    private ArrayList<Integer> playerNumbers;
    private ArrayList<Integer> drawnNumbers;
    private int totalWinnings;
    private int currentDrawingWinnings;
    private NumberGenerator numberGenerator;

    public GameLogic() {
        numberGenerator = new RandomNumberGenerator();
        totalWinnings = 0;
        currDrawing = 0;
        playerNumbers = new ArrayList<>();
        drawnNumbers = new ArrayList<>();
    }

    public void setGameSettings(int spots, int drawings) {
        this.numSpots = spots;
        this.numDrawings = drawings;
        this.currDrawing = 1; // Start at drawing 1
    }

    public void setPlayerNumbers(ArrayList<Integer> numbers) {
        this.playerNumbers = new ArrayList<>(numbers);
    }

    public ArrayList<Integer> generateRandomPicks(int count) {
        return numberGenerator.generateNumbers(count, 1, 80);
    }

    public ArrayList<Integer> performDrawing() {
        drawnNumbers = numberGenerator.generateNumbers(20, 1, 80);
        ArrayList<Integer> matches = getMatchedNumbers();
        currentDrawingWinnings = calculateWinnings(numSpots, matches.size());
        totalWinnings += currentDrawingWinnings;

        return drawnNumbers;
    }

    public ArrayList<Integer> getMatchedNumbers() {
        ArrayList<Integer> matches = new ArrayList<>();

        for (Integer playerNum : playerNumbers) {
            if (drawnNumbers.contains(playerNum)) {
                matches.add(playerNum);
            }
        }

        return matches;
    }

    public int calculateWinnings(int spots, int matches) {
        if (spots == 1) {
            if (matches == 1) return 2;
        }
        else if (spots == 4) {
            if (matches == 2) return 1;
            if (matches == 3) return 5;
            if (matches == 4) return 75;
        }
        else if (spots == 8) {
            if (matches == 4) return 2;
            if (matches == 5) return 12;
            if (matches == 6) return 50;
            if (matches == 7) return 750;
            if (matches == 8) return 10000;
        }
        else if (spots == 10) {
            if (matches == 0) return 5;
            if (matches == 5) return 2;
            if (matches == 6) return 15;
            if (matches == 7) return 40;
            if (matches == 8) return 450;
            if (matches == 9) return 4250;
            if (matches == 10) return 100000;
        }
        return 0;
    }

    public boolean handleButtonPress(Button button) {
        int number = Integer.parseInt(button.getText());

        if (playerNumbers.contains(number)) {
            // Deselect
            playerNumbers.remove(Integer.valueOf(number));
            button.setStyle(""); // Reset style
            return false;
        } else if (playerNumbers.size() < numSpots) {
            // Select
            playerNumbers.add(number);
            button.setStyle("-fx-background-color: gold; -fx-text-fill: black;");
            return true;
        }
        return false;
    }

    public int getTotalWinnings() {
        return totalWinnings;
    }

    public int getCurrentDrawingWinnings() {
        return currentDrawingWinnings;
    }

    public int getCurrentDrawing() {
        return currDrawing;
    }

    public int getNumDrawings() {
        return numDrawings;
    }

    public boolean isGameComplete() {
        return currDrawing > numDrawings;
    }

    public void incrementDrawing() {
        currDrawing++;
    }

    public void reset() {
        numSpots = 0;
        numDrawings = 0;
        currDrawing = 0;
        playerNumbers.clear();
        drawnNumbers.clear();
        totalWinnings = 0;
        currentDrawingWinnings = 0;
    }
}
