

//Approach 1: HashMap-Based Solution
import java.util.*;

public class SimilarVideos {
    public List<List<Integer>> findSimilarVideos(List<List<String>> videos) {
        // Map of tag to list of video indices
        Map<String, List<Integer>> tagToVideos = new HashMap<>();
        // Result list for each video
        List<List<Integer>> similarVideos = new ArrayList<>();
        
        // Process each video
        for (int i = 0; i < videos.size(); i++) {
            List<String> tags = videos.get(i);
            Set<Integer> seenVideos = new HashSet<>();
            
            // Check each tag for prior similar videos
            for (String tag : tags) {
                if (tagToVideos.containsKey(tag)) {
                    for (int prevIndex : tagToVideos.get(tag)) {
                        if (prevIndex < i) {
                            seenVideos.add(prevIndex);
                        }
                    }
                }
            }
            
            // Store result for video i
            similarVideos.add(new ArrayList<>(seenVideos));
            
            // Update tagToVideos with current video
            for (String tag : tags) {
                tagToVideos.computeIfAbsent(tag, k -> new ArrayList<>()).add(i);
            }
        }
        
        return similarVideos;
    }

    public static void main(String[] args) {
        SimilarVideos sv = new SimilarVideos();
      
        List<List<String>> videos = Arrays.asList(
            Arrays.asList("action", "drama"),
            Arrays.asList("comedy", "drama"),
            Arrays.asList("action", "comedy")
        );
        
        List<List<Integer>> result = sv.findSimilarVideos(videos);
        System.out.println("HashMap Approach Result: " + result);
        // Expected Output: [[], [0], [0, 1]]
    }
}

// Approach 2 :  DSU-Based Solution

import java.util.*;

public class SimilarVideosDSU {
    // Disjoint Set Union (Union-Find) implementation
    static class DisjointSet {
        int[] parent;
        int[] rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // Initially, each element is its own parent
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }

    public List<List<Integer>> findSimilarVideos(List<List<String>> videos) {
        int n = videos.size();
        DisjointSet dsu = new DisjointSet(n);
        Map<String, List<Integer>> tagToVideos = new HashMap<>();
        List<List<Integer>> similarVideos = new ArrayList<>();

        // Process each video
        for (int i = 0; i < n; i++) {
            List<String> tags = videos.get(i);
            Set<Integer> seenVideos = new HashSet<>();

            // Check each tag for prior videos
            for (String tag : tags) {
                if (tagToVideos.containsKey(tag)) {
                    for (int j : tagToVideos.get(tag)) {
                        if (j < i) {
                            dsu.union(i, j); // Connect video i with prior video j
                            seenVideos.add(j);
                        }
                    }
                }
            }

            // Store result for video i
            similarVideos.add(new ArrayList<>(seenVideos));

            // Update tagToVideos
            for (String tag : tags) {
                tagToVideos.computeIfAbsent(tag, k -> new ArrayList<>()).add(i);
            }
        }

        return similarVideos;
    }

    public static void main(String[] args) {
        SimilarVideosDSU sv = new SimilarVideosDSU();
      
        List<List<String>> videos = Arrays.asList(
            Arrays.asList("action", "drama"),
            Arrays.asList("comedy", "drama"),
            Arrays.asList("action", "comedy")
        );
        
        List<List<Integer>> result = sv.findSimilarVideos(videos);
        System.out.println("DSU Approach Result: " + result);
        // Expected Output: [[], [0], [0, 1]]
    }
}
