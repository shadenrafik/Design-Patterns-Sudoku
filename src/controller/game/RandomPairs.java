package controller.game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomPairs {
    private static final int MAX_COORD = 8;
    private final Random random = new Random(System.currentTimeMillis());

    public List<int[]> generateDistinctPairs(int n) {
        Set<Integer> used = new HashSet<>();
        List<int[]> result = new ArrayList<>();

        while (result.size() < n) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            int key = x * 9 + y;

            if (used.add(key)) {
                result.add(new int[]{x, y});
            }
        }
        return result;
    }
}

