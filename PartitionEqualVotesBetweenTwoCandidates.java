// Please check the Medium article for a detailed approach and step-by-step debugging: 
// https://medium.com/@techiecontent/day-3-google-interview-preparation-partitionequalvotesbetweentwocandidates-a8ece850063e
// The article covers the transition from recursion to memoization, and then to bottom-up DP and backtracking 

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Function to check if partition is possible using recursion
    public static boolean canPartitionRecursive(int[] votesPower, int index, int target) {
        if (target == 0) return true;  // Found a valid subset
        if (index < 0 || target < 0) return false;  // Out of bounds
        // Choice: Include or exclude the current state's votes
        boolean include = canPartitionRecursive(votesPower, index - 1, target - votesPower[index]);
        boolean exclude = canPartitionRecursive(votesPower, index - 1, target);
        return include || exclude;
    }

    // Memoization approach (Top-Down DP)
    public static boolean canPartitionMemo(int[] votesPower, int index, int target, HashMap<String, Boolean> memo) {
        if (target == 0) return true;
        if (index < 0 || target < 0) return false;
        String key = index + "," + target;
        if (memo.containsKey(key)) return memo.get(key);
        boolean include = canPartitionMemo(votesPower, index - 1, target - votesPower[index], memo);
        boolean exclude = canPartitionMemo(votesPower, index - 1, target, memo);
        memo.put(key, include || exclude);
        return memo.get(key);
    }

    // Bottom-Up DP (Tabulation)
    public static boolean canPartitionDP(int[] votesPower, int target) {
        int n = votesPower.length;
        boolean[][] dp = new boolean[n + 1][target + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = true; // Base case
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                if (votesPower[i - 1] <= j)
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - votesPower[i - 1]];
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[n][target];
    }

    // Backtracking approach to find the partitions
    public static void findPartitions(int[] votesPower, String[] states, int index, int target, 
                                       List<String> path, List<List<String>> result) {
        if (target == 0) {
            // Group the partition into two sets
            List<String> remainingStates = new ArrayList<>();
            for (int i = 0; i < states.length; i++) {
                if (!path.contains(states[i])) {
                    remainingStates.add(states[i]);
                }
            }

            // Add both groups of states to the result, ensuring distinct partitions
            List<String> partition1 = new ArrayList<>(path);
            List<String> partition2 = new ArrayList<>(remainingStates);
            result.add(partition1);
            result.add(partition2);
            return;
        }

        if (index < 0) return;

        // Include the current state's vote if possible
        if (target >= votesPower[index]) {
            path.add(states[index]);
            findPartitions(votesPower, states, index - 1, target - votesPower[index], path, result);
            path.remove(path.size() - 1);
        }

        // Exclude the current state's vote and move to the next
        findPartitions(votesPower, states, index - 1, target, path, result);
    }

    public static void main(String[] args) {
        // Input data
        int[] votesPower = {1, 5, 7, 8, 9, 10, 20};
        String[] states = {"California", "Texas", "Florida", "Indiana", "Alaska", "Ohio", "Hawaii"};
        
        int totalSum = 0;
        for (int vote : votesPower) {
            totalSum += vote;
        }
        
        // If total sum is odd, it's not possible to partition
        if (totalSum % 2 != 0) {
            System.out.println("Partition is not possible");
            return;
        }
        
        int target = totalSum / 2;
        
        // Step 1: Use Bottom-Up DP (Tabulation) to check if partitioning is possible
        boolean canPartition = canPartitionDP(votesPower, target);
        
        if (canPartition) {
            System.out.println("Partition is possible");

            // Step 2: Backtracking to find all possible state partitions
            List<List<String>> result = new ArrayList<>();
            findPartitions(votesPower, states, votesPower.length - 1, target, new ArrayList<>(), result);

            // Print the results (two partitions for each group of states)
            System.out.println("All valid partitions:");
            List<String> printedPartitions = new ArrayList<>();
            for (int i = 0; i < result.size(); i += 2) {
                List<String> partition1 = result.get(i);
                List<String> partition2 = result.get(i + 1);
                
                // Sort both partitions to avoid duplicate entries
                partition1.sort(String::compareTo);
                partition2.sort(String::compareTo);
                
                // Create a key to check for duplicates
                String partitionKey = partition1.toString() + " | " + partition2.toString();

                // Check if the partition combination is already printed
                if (!printedPartitions.contains(partitionKey)) {
                    // Print each pair of partitions directly in the specified format
                    System.out.println("[" + String.join(", ", partition1) + "], [" + String.join(", ", partition2) + "]");
                    printedPartitions.add(partitionKey);
                }
            }
        } else {
            System.out.println("Partition is not possible");
        }
    }
}
