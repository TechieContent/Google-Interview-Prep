import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("eat", "tea", "tan", "ate", "nat", "bat");
        
        List<List<String>> anagramGroups = groupAnagrams(words);
        
        for (List<String> anagramGroup : anagramGroups) {
            for (String word : anagramGroup) {
                System.out.print(word + ",");
            }
            System.out.println();
        }
    }

    private static List<List<String>> groupAnagrams(List<String> words) {
        Map<String, List<String>> anagramMap = new HashMap<>();
        
        for (String word : words) {
            String signature = createAnagramSignature(word);
            
            if (!anagramMap.containsKey(signature)) {
                anagramMap.put(signature, new ArrayList<>());
            }
            
            anagramMap.get(signature).add(word);
        }
        
        return new ArrayList<>(anagramMap.values());
    }

    private static String createAnagramSignature(String word) {
        int[] charFrequency = new int[26];
        
        for (char character : word.toCharArray()) {
            charFrequency[character - 'a']++;
        }
        
        StringBuilder signature = new StringBuilder();
        
        for (int i = 0; i < 26; i++) {
            if (charFrequency[i] > 0) {
                char currentChar = (char)(i + 'a');
                signature.append(String.valueOf(currentChar).repeat(charFrequency[i]));
            }
        }
        
        return signature.toString();
    }
}
