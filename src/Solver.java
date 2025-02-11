import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private final int move;
    private final Board[] sol;
    private boolean solved;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial board cannot be null");
        MinPQ<Node> boardPQ = new MinPQ<>();
        Node solutionNode = null;
        boardPQ.insert(new Node(initial, 0, null));
        while (!boardPQ.isEmpty()) {
            Node curr = boardPQ.delMin();
            if (curr.board.isGoal()) {
                solved = true;
                solutionNode = curr;
                break;
            }
            if (curr.board.manhattan() == 2 && curr.board.twin().isGoal())
                break;
            int newMove = curr.move + 1;
            for (Board neighbor : curr.board.neighbors()) {
                if (curr.prev == null || !neighbor.equals(curr.prev.board))
                    boardPQ.insert(new Node(neighbor, newMove, curr));
            }
        }
        if (solutionNode != null) {
            move = solutionNode.move;
            ArrayList<Board> solutionList = new ArrayList<>();
            Node curr = solutionNode;
            while (curr != null) {
                solutionList.add(curr.board);
                curr = curr.prev;
            }
            Collections.reverse(solutionList);
            sol = solutionList.toArray(new Board[0]);
        } else {
            move = -1;
            sol = null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return (sol == null) ? null : List.of(sol);
    }

    private class Node implements Comparable<Node> {
        final Board board;
        final int move;
        final int priority;
        final Node prev;

        private Node(Board board, int move, Node prev) {
            this.board = board;
            this.move = move;
            this.priority = move + board.manhattan();
            this.prev = prev;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.priority, that.priority);
        }

    }

}
