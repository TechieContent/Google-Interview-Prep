//Please check the Medium article for a detailed approach and step-by-step debugging: 
//https://medium.com/@techiecontent/day-7-google-interview-preparation-minimum-contiguous-swaps-to-transform-lists-5a6c7fbbb0aa

import java.util.*;

public class MinSwaps {
    public static int minSwaps(String[] source, String[] destination) {
        // Step 1: Map elements in destination to their indices
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < destination.length; i++) {
            indexMap.put(destination[i], i);
        }

        // Step 2: Convert source array to an array of indices based on destination
        int[] arr = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            arr[i] = indexMap.get(source[i]);
        }

        // Step 3: Count inversions using merge sort
        return countInversions(arr, 0, arr.length - 1);
    }

    private static int countInversions(int[] arr, int left, int right) {
        int inversions = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            inversions += countInversions(arr, left, mid);       // Left half
            inversions += countInversions(arr, mid + 1, right);  // Right half
            inversions += mergeAndCount(arr, left, mid, right);  // Merge and count
        }
        return inversions;
    }

    // Merge two sorted subarrays and count inversions
    private static int mergeAndCount(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;      // Index for left subarray
        int j = mid + 1;   // Index for right subarray
        int k = 0;         // Index for temp array
        int inversions = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                // If arr[i] > arr[j], all elements from i to mid are greater than arr[j]
                inversions += (mid - i + 1);
                temp[k++] = arr[j++];
            }
        }

        // Copy remaining elements of left subarray, if any
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // Copy remaining elements of right subarray, if any
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // Copy back to original array
        for (i = 0; i < temp.length; i++) {
            arr[left + i] = temp[i];
        }

        return inversions;
    }

    public static void main(String[] args) {
        // Example 1: S = [B, C, A, D], D = [C, D, A, B]
        String[] S1 = {"B", "C", "A", "D"};
        String[] D1 = {"C", "D", "A", "B"};
        System.out.println("Minimum swaps for Example 1: " + minSwaps(S1, D1)); // Should print 4

        // Example 2: S = [A, B, C], D = [B, C, A]
        String[] S2 = {"A", "B", "C"};
        String[] D2 = {"B", "C", "A"};
        System.out.println("Minimum swaps for Example 2: " + minSwaps(S2, D2)); // Should print 2
    }
}
