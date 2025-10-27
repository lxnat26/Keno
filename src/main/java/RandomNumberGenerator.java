import java.util.ArrayList;
import java.util.Random;

// random number generator that ensures no duplicats are chosen
public class RandomNumberGenerator implements NumberGenerator {
    private Random random;

    public RandomNumberGenerator() {
        random = new Random();
    }

    @Override
    public ArrayList<Integer> generateNumbers(int count, int min, int max) {
        ArrayList<Integer> numbers = new ArrayList<>();

        while (numbers.size() < count) {
            int num = random.nextInt(max - min + 1) + min;
            if (!numbers.contains(num)) {
                numbers.add(num);
            }
        }

        return numbers;
    }
}
