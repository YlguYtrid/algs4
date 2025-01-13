import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null; // The current champion word
        int count = 0; // Number of words read

        // Read words from standard input
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString(); // Read the next word
            count++; // Increment the word count

            // Select the champion with probability 1/count
            if (StdRandom.bernoulli(1.0 / count)) {
                champion = word; // Update champion to the new word
            }
        }

        // Print the chosen word
        StdOut.println(champion);
    }
}
