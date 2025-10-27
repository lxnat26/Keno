import java.util.ArrayList;

public interface NumberGenerator { // we were going to make a regular generator to test the specific numbers, which is why this is an interface, but we decided to just code using hardcoding the winning states of our other classes :(
    ArrayList<Integer> generateNumbers(int count, int min, int max);
}