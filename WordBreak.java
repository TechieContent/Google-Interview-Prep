import java.util.*;

public class WordBreakAllApproaches {

    public static void main(String[] args) {

        String s = "leetcode";
        List<String> wordDict = Arrays.asList("leet", "code");

        System.out.println("Brute Force Recursion: " +
                wordBreakRecursive(s, wordDict));

        System.out.println("Memoization (Top-Down DP): " +
                wordBreakMemo(s, wordDict));

        System.out.println("Bottom-Up DP (Tabulation): " +
                wordBreakDP(s, wordDict));
    }

    // 1. Brute Force (Recursion)

    public static boolean wordBreakRecursive(String s, List<String> wordDict) {
        return canBreakRecursive(0, s, new HashSet<>(wordDict));
    }

    private static boolean canBreakRecursive(int index, String s, Set<String> dict) {
        if (index == s.length()) {
            return true;
        }

        for (int end = index + 1; end <= s.length(); end++) {
            String prefix = s.substring(index, end);
            if (dict.contains(prefix)) {
                if (canBreakRecursive(end, s, dict)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 2. Memoization (Top-Down DP)

    public static boolean wordBreakMemo(String s, List<String> wordDict) {
        Boolean[] memo = new Boolean[s.length()];
        return canBreakMemo(0, s, new HashSet<>(wordDict), memo);
    }

    private static boolean canBreakMemo(int index, String s,
                                        Set<String> dict, Boolean[] memo) {

        if (index == s.length()) {
            return true;
        }

        if (memo[index] != null) {
            return memo[index];
        }

        for (int end = index + 1; end <= s.length(); end++) {
            String prefix = s.substring(index, end);
            if (dict.contains(prefix)) {
                if (canBreakMemo(end, s, dict, memo)) {
                    memo[index] = true;
                    return true;
                }
            }
        }

        memo[index] = false;
        return false;
    }

    // 3. Bottom-Up DP (Tabulation)

    public static boolean wordBreakDP(String s, List<String> wordDict) {

        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();

        boolean[] dp = new boolean[n + 1];
        dp[0] = true; // empty string

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}
