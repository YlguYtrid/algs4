import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty())
            queue.enqueue(StdIn.readString());
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            String item = queue.dequeue();
            System.out.println(item);
        }
    }
}
