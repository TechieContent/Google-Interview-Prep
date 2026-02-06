// Please check the Medium article for a detailed approach and step-by-step debugging: 
// https://medium.com/@techiecontent/day-8-google-interview-preparation-filling-bookcase-shelves-leetcode-1105-777e685d57f2

import java.util.Arrays;

public class FillingBookcaseShelvesAllApproaches {

    // 1. Brute Force Recursive
    public static int bruteForce(int[][] books, int shelfWidth) {
        return dfsBrute(books, shelfWidth, 0);
    }
    private static int dfsBrute(int[][] books, int shelfWidth, int i) {
        if (i == books.length) return 0;
        int minHeight = Integer.MAX_VALUE;
        int width = 0, height = 0;
        for (int j = i; j < books.length; ++j) {
            width += books[j][0];
            if (width > shelfWidth) break;
            height = Math.max(height, books[j][1]);
            minHeight = Math.min(minHeight, height + dfsBrute(books, shelfWidth, j+1));
        }
        return minHeight;
    }

    // 2. Top-Down DP with Memoization
    public static int topDownDP(int[][] books, int shelfWidth) {
        int[] memo = new int[books.length];
        Arrays.fill(memo, -1);
        return dfsMemo(books, shelfWidth, 0, memo);
    }
    private static int dfsMemo(int[][] books, int shelfWidth, int i, int[] memo) {
        if (i == books.length) return 0;
        if (memo[i] != -1) return memo[i];
        int minHeight = Integer.MAX_VALUE;
        int width = 0, height = 0;
        for (int j = i; j < books.length; ++j) {
            width += books[j][0];
            if (width > shelfWidth) break;
            height = Math.max(height, books[j][1]);
            minHeight = Math.min(minHeight, height + dfsMemo(books, shelfWidth, j+1, memo));
        }
        memo[i] = minHeight;
        return minHeight;
    }

    // 3. Bottom-Up DP (Tabulation)
    public static int bottomUpDP(int[][] books, int shelfWidth) {
        int n = books.length;
        int[] dp = new int[n+1];
        dp[n] = 0; // base case
        for (int i = n - 1; i >= 0; --i) {
            int minHeight = Integer.MAX_VALUE;
            int width = 0, height = 0;
            for (int j = i; j < n; ++j) {
                width += books[j][0];
                if (width > shelfWidth) break;
                height = Math.max(height, books[j][1]);
                minHeight = Math.min(minHeight, height + dp[j+1]);
            }
            dp[i] = minHeight;
        }
        return dp[0];
    }

    // Main method for all three approaches
    public static void main(String[] args) {
        int[][] books = { {1,3}, {2,4}, {3,2} };
        int shelfWidth = 6;

        int resultBrute = bruteForce(books, shelfWidth);
        int resultTopDown = topDownDP(books, shelfWidth);
        int resultBottomUp = bottomUpDP(books, shelfWidth);

        System.out.println("Brute Force: " + resultBrute);       // Output: 4
        System.out.println("Top-Down DP (Memoization): " + resultTopDown); // Output: 4
        System.out.println("Bottom-Up DP (Tabulation): " + resultBottomUp); // Output: 4
    }
}
