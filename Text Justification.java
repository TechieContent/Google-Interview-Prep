import java.util.ArrayList;
import java.util.List;

class TextJustifier {
    int LINE_WIDTH;

    public List<String> justifyText(String[] words, int maxWidth) {
        List<String> formattedLines = new ArrayList<>();
        int totalWords = words.length;
        LINE_WIDTH = maxWidth;
        int index = 0;

        while (index < totalWords) {
            int charCount = words[index].length();
            int nextWord = index + 1;
            int gaps = 0;

            while (nextWord < totalWords && gaps + charCount + words[nextWord].length() + 1 <= maxWidth) {
                charCount += words[nextWord].length();
                gaps++;
                nextWord++;
            }

            int remainingSpaces = maxWidth - charCount;
            int evenSpacing = (gaps == 0) ? 0 : remainingSpaces / gaps;
            int extraSpaces = (gaps == 0) ? 0 : remainingSpaces % gaps;

            if (nextWord == totalWords) { // Last line - Left justified
                evenSpacing = 1;
                extraSpaces = 0;
            }

            formattedLines.add(buildLine(index, nextWord, evenSpacing, extraSpaces, words));
            index = nextWord;
        }

        return formattedLines;
    }

    private String buildLine(int start, int end, int evenSpacing, int extraSpaces, String[] words) {
        StringBuilder lineBuilder = new StringBuilder();

        for (int i = start; i < end; i++) {
            lineBuilder.append(words[i]);

            if (i == end - 1) continue;

            for (int space = 1; space <= evenSpacing; space++)
                lineBuilder.append(" ");

            if (extraSpaces > 0) {
                lineBuilder.append(" ");
                extraSpaces--;
            }
        }

        while (lineBuilder.length() < LINE_WIDTH) {
            lineBuilder.append(" ");
        }

        return lineBuilder.toString();
    }

    public static void main(String[] args) {
        TextJustifier justifier = new TextJustifier();
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        int maxWidth = 16;

        List<String> result = justifier.justifyText(words, maxWidth);

        for (String line : result) {
            System.out.println("\"" + line + "\"");
        }
    }
}
