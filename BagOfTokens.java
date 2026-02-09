import java.util.Arrays;

public class BagOfTokens {

    public static int bagOfTokensScore(int[] tokens, int power) {

        Arrays.sort(tokens);

        int score = 0;
        int maxScore = 0;
        int i = 0;
        int j = tokens.length - 1;

        while (i <= j) {

            // Case 1: Use power to gain score (buy smallest token)
            if (power >= tokens[i]) {
                power -= tokens[i];
                score++;
                i++;
                maxScore = Math.max(maxScore, score);
            }

            // Case 2: Use score to gain power (sell largest token)
            else if (score >= 1) {
                power += tokens[j];
                score--;
                j--;
            }

            // Case 3: No valid move left
            else {
                break;
            }
        }

        return maxScore;
    }

    public static void main(String[] args) {

        int[] tokens = {100, 200, 300, 400};
        int power = 200;

        int result = bagOfTokensScore(tokens, power);

        System.out.println("Maximum Score: " + result);
    }
}
