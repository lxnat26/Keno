import java.util.ArrayList;
import java.util.Random;

public class RandomNumberGenerator implements NumberGenerator {
    private Random random;

    public RandomNumberGenerator() {
        random = new Random();
    }

    @Override
    public ArrayList<Integer> generateNumbers(int count, int min, int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        return numbers;
    }
}
