import java.util.*;

public class FindWinnerCircularGame {

    // Approach 1: Brute Force (ArrayList Simulation)
    // Time: O(n^2), Space: O(n)
  
    public static int findWinnerBruteForce(int n, int k) {
        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            arr.add(i);
        }

        int index = 0;

        while (arr.size() > 1) {
            int removeIdx = (index + k - 1) % arr.size();
            arr.remove(removeIdx);
            index = removeIdx;
        }

        return arr.get(0);
    }

    // Approach 2: Queue Simulation
    // Time: O(n * k), Space: O(n)
  
    public static int findWinnerUsingQueue(int n, int k) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            queue.add(i);
        }

        while (queue.size() > 1) {
            for (int i = 1; i < k; i++) {
                queue.add(queue.poll());
            }
            queue.poll(); // remove k-th friend
        }

        return queue.peek();
    }

    // Approach 3: Recursion (Josephus Problem)
    // Time: O(n), Space: O(1) logical (O(n) recursion stack)
  
    public static int findWinnerRecursive(int n, int k) {
        int winnerIdx = findWinnerIndex(n, k);
        return winnerIdx + 1; // convert 0-based to 1-based
    }

    private static int findWinnerIndex(int n, int k) {
        if (n == 1) {
            return 0;
        }

        int idx = findWinnerIndex(n - 1, k);
        return (idx + k) % n;
    }

    // Main Method
    public static void main(String[] args) {

        int n = 5;
        int k = 2;

        System.out.println("Brute Force Result     : " + findWinnerBruteForce(n, k));
        System.out.println("Queue Simulation Result: " + findWinnerUsingQueue(n, k));
        System.out.println("Recursive Result       : " + findWinnerRecursive(n, k));
    }
}
