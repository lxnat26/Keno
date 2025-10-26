import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.HashSet;

class MyTest {

	private GameLogic gameLogic;
    private RandomNumberGenerator randomGenerator;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
        randomGenerator = new RandomNumberGenerator();
    }

    @Test
    void randomSelectNumberForUserGeneratesCorrectCount() {
        // ensures random selection for user generates the exact number of picks requested
        ArrayList<Integer> picks = gameLogic.generateRandomPicks(10);
        assertEquals(10, picks.size());
    }

    @Test
    void randomSelectNumberForUserGeneratesUniqueNumbers() {
        // ensures random selection for user generates unique numbers without duplicates
        ArrayList<Integer> picks = gameLogic.generateRandomPicks(20);
        HashSet<Integer> uniquePicks = new HashSet<>(picks);
        assertEquals(picks.size(), uniquePicks.size());
    }

    @Test
    void randomSelectNumberForUserGeneratesValidRange() {
        // ensures random selection for user generates numbers within valid keno range of 1 to 80
        ArrayList<Integer> picks = gameLogic.generateRandomPicks(15);
        for (Integer pick : picks) {
            assertTrue(pick >= 1 && pick <= 80);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 8, 10})
    void randomSelectNumberForUserWorksForAllValidSpotCounts(int spots) {
        // ensures random selection for user works correctly for all valid spot counts in keno
        ArrayList<Integer> picks = gameLogic.generateRandomPicks(spots);
        assertEquals(spots, picks.size());
    }

    @Test
    void randomNumbersSelectedBySystemGeneratesExactlyTwenty() {
        // ensures system drawing generates exactly 20 random numbers
        gameLogic.setGameSettings(4, 1);
        ArrayList<Integer> playerNums = new ArrayList<>();
        playerNums.add(10);
        playerNums.add(20);
        playerNums.add(30);
        playerNums.add(40);
        gameLogic.setPlayerNumbers(playerNums);
        
        ArrayList<Integer> drawnNumbers = gameLogic.performDrawing();
        assertEquals(20, drawnNumbers.size());
    }

    @Test
    void randomNumbersSelectedBySystemAreUnique() {
        // ensures system drawing never generates duplicate numbers
        gameLogic.setGameSettings(4, 1);
        ArrayList<Integer> playerNums = new ArrayList<>();
        playerNums.add(5);
        playerNums.add(15);
        playerNums.add(25);
        playerNums.add(35);
        gameLogic.setPlayerNumbers(playerNums);
        
        ArrayList<Integer> drawnNumbers = gameLogic.performDrawing();
        HashSet<Integer> uniqueDrawn = new HashSet<>(drawnNumbers);
        assertEquals(20, uniqueDrawn.size());
    }

    @Test
    void randomNumbersSelectedBySystemAreInValidRange() {
        // ensures system drawing generates numbers between 1 and 80
        gameLogic.setGameSettings(4, 1);
        ArrayList<Integer> playerNums = new ArrayList<>();
        playerNums.add(10);
        playerNums.add(20);
        playerNums.add(30);
        playerNums.add(40);
        gameLogic.setPlayerNumbers(playerNums);
        
        ArrayList<Integer> drawnNumbers = gameLogic.performDrawing();
        for (Integer num : drawnNumbers) {
            assertTrue(num >= 1 && num <= 80);
        }
    }

    @Test
    void userSelectedNumberMatchingSystemSelectedAreIdentified() {
        // ensures matched numbers are correctly identified when user numbers match system drawn numbers
        gameLogic.setGameSettings(4, 1);
        ArrayList<Integer> playerNums = new ArrayList<>();
        playerNums.add(1);
        playerNums.add(2);
        playerNums.add(3);
        playerNums.add(4);
        gameLogic.setPlayerNumbers(playerNums);
        
        ArrayList<Integer> drawnNumbers = gameLogic.performDrawing();
        ArrayList<Integer> matches = gameLogic.getMatchedNumbers();
        
        for (Integer match : matches) {
            assertTrue(playerNums.contains(match));
            assertTrue(drawnNumbers.contains(match));
        }
    }

    @Test
    void userSelectedNumberMatchingSystemSelectedAreSubsetOfPlayerNumbers() {
        // ensures all matched numbers are actually numbers that the player selected
        gameLogic.setGameSettings(8, 1);
        ArrayList<Integer> playerNums = new ArrayList<>();
        for (int i = 10; i <= 17; i++) {
            playerNums.add(i);
        }
        gameLogic.setPlayerNumbers(playerNums);
        
        gameLogic.performDrawing();
        ArrayList<Integer> matches = gameLogic.getMatchedNumbers();
        
        for (Integer match : matches) {
            assertTrue(playerNums.contains(match));
        }
    }

    @Test
    void userSelectedNumberMatchingSystemSelectedCalculatesCorrectWinnings() {
        // ensures matching numbers correctly calculate winnings based on nc lottery rules
        assertEquals(2, gameLogic.calculateWinnings(1, 1));
        assertEquals(75, gameLogic.calculateWinnings(4, 4));
        assertEquals(10000, gameLogic.calculateWinnings(8, 8));
        assertEquals(100000, gameLogic.calculateWinnings(10, 10));
    }

    @Test
    void userSelectedNumberNotMatchingSystemSelectedReturnsPartialOrZeroMatches() {
        // ensures getmatchednumbers can return zero or partial matches when user numbers don't match
        gameLogic.setGameSettings(10, 1);
        ArrayList<Integer> playerNums = new ArrayList<>();
        for (int i = 71; i <= 80; i++) {
            playerNums.add(i);
        }
        gameLogic.setPlayerNumbers(playerNums);
        
        gameLogic.performDrawing();
        ArrayList<Integer> matches = gameLogic.getMatchedNumbers();
        
        assertTrue(matches.size() <= playerNums.size());
    }

    @Test
    void userSelectedNumberNotMatchingSystemSelectedReturnsZeroWinnings() {
        // ensures calculatewinnings returns 0 when matches don't result in payout
        assertEquals(0, gameLogic.calculateWinnings(1, 0));
        assertEquals(0, gameLogic.calculateWinnings(4, 0));
        assertEquals(0, gameLogic.calculateWinnings(4, 1));
        assertEquals(0, gameLogic.calculateWinnings(8, 3));
    }


    @Test
    void calculateWinningsForOneSpotWithOneMatch() {
        // ensures 1 spot with 1 match returns 2 dollars according to nc lottery rules
        assertEquals(2, gameLogic.calculateWinnings(1, 1));
    }

    @Test
    void calculateWinningsForOneSpotWithZeroMatches() {
        // ensures 1 spot with 0 matches returns 0 dollars
        assertEquals(0, gameLogic.calculateWinnings(1, 0));
    }

    @Test
    void calculateWinningsForFourSpotsWithTwoMatches() {
        // ensures 4 spots with 2 matches returns 1 dollar according to nc lottery rules
        assertEquals(1, gameLogic.calculateWinnings(4, 2));
    }

    @Test
    void calculateWinningsForFourSpotsWithThreeMatches() {
        // ensures 4 spots with 3 matches returns 5 dollars according to nc lottery rules
        assertEquals(5, gameLogic.calculateWinnings(4, 3));
    }

    @Test
    void calculateWinningsForFourSpotsWithFourMatches() {
        // ensures 4 spots with 4 matches returns 75 dollars according to nc lottery rules
        assertEquals(75, gameLogic.calculateWinnings(4, 4));
    }

    @Test
    void calculateWinningsForEightSpotsWithFourMatches() {
        // ensures 8 spots with 4 matches returns 2 dollars according to nc lottery rules
        assertEquals(2, gameLogic.calculateWinnings(8, 4));
    }

    @Test
    void calculateWinningsForEightSpotsWithSixMatches() {
        // ensures 8 spots with 6 matches returns 50 dollars according to nc lottery rules
        assertEquals(50, gameLogic.calculateWinnings(8, 6));
    }

    @Test
    void calculateWinningsForEightSpotsWithEightMatches() {
        // ensures 8 spots with 8 matches returns 10000 dollars according to nc lottery rules
        assertEquals(10000, gameLogic.calculateWinnings(8, 8));
    }

    @Test
    void calculateWinningsForTenSpotsWithZeroMatches() {
        // ensures 10 spots with 0 matches returns 5 dollars according to nc lottery rules
        assertEquals(5, gameLogic.calculateWinnings(10, 0));
    }

    @Test
    void calculateWinningsForTenSpotsWithSixMatches() {
        // ensures 10 spots with 6 matches returns 15 dollars according to nc lottery rules
        assertEquals(15, gameLogic.calculateWinnings(10, 6));
    }

    @Test
    void calculateWinningsForTenSpotsWithNineMatches() {
        // ensures 10 spots with 9 matches returns 4250 dollars according to nc lottery rules
        assertEquals(4250, gameLogic.calculateWinnings(10, 9));
    }

    @Test
    void calculateWinningsForTenSpotsWithTenMatches() {
        // ensures 10 spots with 10 matches returns 100000 dollars jackpot according to nc lottery rules
        assertEquals(100000, gameLogic.calculateWinnings(10, 10));
    }

    @Test
    void gameLogicInitializesWithZeroTotalWinnings() {
        // ensures gamelogic starts with no initial points
        assertEquals(0, gameLogic.getTotalWinnings());
    }

    @Test
    void gameLogicInitializesWithZeroCurrentDrawing() {
        // ensures gamelogic is on current drawing 0 since user hasn't chosen yet
        assertEquals(0, gameLogic.getCurrentDrawing());
    }

    @Test
    void setPlayerNumbersStoresSelectedNumbers() {
        // ensures setplayernumbers correctly stores all player selected numbers
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(15);
        numbers.add(25);
        numbers.add(35);
        
        gameLogic.setPlayerNumbers(numbers);
        
        assertEquals(4, gameLogic.getPlayerNumbers().size());
        assertTrue(gameLogic.getPlayerNumbers().contains(5));
        assertTrue(gameLogic.getPlayerNumbers().contains(35));
    }

    @Test
    void performDrawingAccumulatesWinningsToTotal() {
        // ensures performdrawing correctly accumulates winnings to total winnings
        gameLogic.setGameSettings(10, 2);
        ArrayList<Integer> playerNums = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            playerNums.add(i);
        }
        gameLogic.setPlayerNumbers(playerNums);
        
        int initialTotal = gameLogic.getTotalWinnings();
        gameLogic.performDrawing();
        int afterFirstDraw = gameLogic.getTotalWinnings();
        
        assertTrue(afterFirstDraw >= initialTotal);
    }

    @Test
    void incrementDrawingIncreasesCurrentDrawingByOne() {
        // ensures incrementdrawing increases the current drawing number by 1 each time
        gameLogic.setGameSettings(4, 3);
        assertEquals(1, gameLogic.getCurrentDrawing());
        
        gameLogic.incrementDrawing();
        assertEquals(2, gameLogic.getCurrentDrawing());
    }

    @Test
    void isGameCompleteReturnsTrueWhenAllDrawingsFinished() {
        // ensures isgamecomplete returns true when all drawings have been completed
        gameLogic.setGameSettings(4, 2);
        gameLogic.incrementDrawing();
        gameLogic.incrementDrawing();
        
        assertTrue(gameLogic.isGameComplete());
    }

    @Test
    void resetClearsAllGameStateToInitialValues() {
        // ensures reset method clears all game state back to initial values
        gameLogic.setGameSettings(8, 4);
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        gameLogic.setPlayerNumbers(numbers);
        gameLogic.performDrawing();
        
        gameLogic.reset();
        
        assertEquals(0, gameLogic.getTotalWinnings());
        assertEquals(0, gameLogic.getCurrentDrawing());
        assertTrue(gameLogic.getPlayerNumbers().isEmpty());
    }

}
