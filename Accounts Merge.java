//Please check the Medium article for a detailed approach and step-by-step debugging: 
//https://medium.com/@techiecontent/day-4-google-interview-preparation-accounts-merge-leetcode-721-9194d05230b1
//Before solving this problem, it’s essential to understand Disjoint Set Union (DSU). 
//You can read more about DSU in my article: https://medium.com/@techiecontent/disjoint-set-union-dsu-a-detailed-explanation-222056d97968

import java.util.*;
class Main {
    
    static class DSU {
        private int[] parent;
        private int[] rank;
        public DSU(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;  // Each node is its own parent initially
                rank[i] = 1;    // Initial rank is 1
            }
        }
        // Find operation with path compression
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }
        // Union operation by rank
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
        // Debugging helper to print the parent array
        public void printParents() {
            System.out.println("DSU Parent Array: " + Arrays.toString(parent));
        }
    }
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        DSU dsu = new DSU(n);
        Map<String, Integer> emailToIndex = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        // Step 1: Map each email to an account index and merge using DSU
        for (int i = 0; i < n; i++) {
            String name = accounts.get(i).get(0);
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String email = accounts.get(i).get(j);
                emailToName.put(email, name); // Store the name for output
                if (emailToIndex.containsKey(email)) {
                    dsu.union(i, emailToIndex.get(email)); // Merge accounts
                } else {
                    emailToIndex.put(email, i);
                }
            }
        }
        // Debugging: Print email-to-index mapping
        System.out.println("Email to Index Mapping: " + emailToIndex);
        dsu.printParents(); // Print parent array after merging
        // Step 2: Group emails by their root parent
        Map<Integer, TreeSet<String>> rootToEmails = new HashMap<>();
        for (String email : emailToIndex.keySet()) {
            int root = dsu.find(emailToIndex.get(email));
            rootToEmails.computeIfAbsent(root, x -> new TreeSet<>()).add(email);
        }
        // Debugging: Print root-to-emails mapping
        System.out.println("Root to Emails Mapping: " + rootToEmails);
        // Step 3: Construct the final merged accounts
        List<List<String>> result = new ArrayList<>();
        for (int root : rootToEmails.keySet()) {
            List<String> mergedAccount = new ArrayList<>();
            mergedAccount.add(emailToName.get(rootToEmails.get(root).first())); // Add name
            mergedAccount.addAll(rootToEmails.get(root)); // Add sorted emails
            result.add(mergedAccount);
        }
        return result;
    }
    public static void main(String[] args) {
        List<List<String>> accounts = Arrays.asList(
            Arrays.asList("John", "john1@mail.com", "john2@mail.com"),
            Arrays.asList("John", "john2@mail.com", "john3@mail.com"),
            Arrays.asList("Mary", "mary@mail.com"),
            Arrays.asList("John", "john4@mail.com")
        );
        List<List<String>> mergedAccounts = accountsMerge(accounts);
        
        // Print the final output
        System.out.println("Merged Accounts:");
        for (List<String> account : mergedAccounts) {
            System.out.println(account);
        }
    }
}
