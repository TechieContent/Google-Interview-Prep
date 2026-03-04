import java.util.*;

public class BrickWall {

    static List<Integer> patterns = new ArrayList<>();
    static Map<Integer, List<Integer>> compatible = new HashMap<>();

    // Step 1: Generate all valid row patterns
    static void generatePatterns(int width, int position, int mask) {

        if (position == width) {
            patterns.add(mask);
            return;
        }

        if (position > width) return;

        // Place brick of size 1
        if (position + 1 <= width) {
            int newMask = mask;
            if (position + 1 < width) {   // avoid marking crack at wall end
                newMask = mask | (1 << (position + 1));
            }
            generatePatterns(width, position + 1, newMask);
        }

        // Place brick of size 2
        if (position + 2 <= width) {
            int newMask = mask;
            if (position + 2 < width) {   // avoid marking crack at wall end
                newMask = mask | (1 << (position + 2));
            }
            generatePatterns(width, position + 2, newMask);
        }
    }

    // Step 2: Build compatibility list
    static void buildCompatibility() {

        for (int m1 : patterns) {
            compatible.put(m1, new ArrayList<>());

            for (int m2 : patterns) {
                if ((m1 & m2) == 0) {
                    compatible.get(m1).add(m2);
                }
            }
        }
    }

    // Step 3: DP to count ways
    static long countWays(int height) {

        // Base case
        Map<Integer, Long> dp = new HashMap<>();
        for (int mask : patterns) {
            dp.put(mask, 1L);
        }

        // Build height level by level
        for (int level = 2; level <= height; level++) {

            Map<Integer, Long> nextDP = new HashMap<>();

            for (int curr : patterns) {

                long ways = 0;

                for (int prev : compatible.get(curr)) {
                    ways += dp.getOrDefault(prev, 0L);
                }

                nextDP.put(curr, ways);
            }

            dp = nextDP;
        }

        long total = 0;
        for (long val : dp.values()) {
            total += val;
        }

        return total;
    }

    public static void main(String[] args) {

        int width = 4;
        int height = 3;

        generatePatterns(width, 0, 0);
        buildCompatibility();

        long result = countWays(height);

        System.out.println("Total ways to build wall: " + result);
    }
}
