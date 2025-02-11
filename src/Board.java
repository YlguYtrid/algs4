import java.util.ArrayList;

public class Board {
    private final int[][] tiles;
    private final int n;
    private int blankRow;
    private int blankCol;
    private int hammingDistance;
    private int manhattanDistance;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("tiles cannot be null");
        n = tiles.length;
        if (n == 0)
            throw new IllegalArgumentException("tiles must be non-empty");
        int m = tiles[0].length;
        if (n != m)
            throw new IllegalArgumentException("tiles must be square");
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (this.tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                } else {
                    int d = distance(i, j);
                    if (d != 0) {
                        hammingDistance++;
                        manhattanDistance += d;
                    }
                }
            }
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    }

    private Board deepCopy() {
        return new Board(tiles);
    }

    private int distance(int i, int j) {
        int x = tiles[i][j] - 1;
        if (x < 0)
            return 0;
        int row = x / n;
        int col = x % n;
        return Math.abs(i - row) + Math.abs(j - col);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n);
        s.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(' ');
                s.append(tiles[i][j]);
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hammingDistance == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (getClass() != y.getClass())
            return false;
        if (n != ((Board) y).n)
            return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] != ((Board) y).tiles[i][j])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> result = new ArrayList<>();
        if (blankRow - 1 >= 0)
            result.add(move(blankRow - 1, blankCol));
        if (blankRow + 1 < n)
            result.add(move(blankRow + 1, blankCol));
        if (blankCol - 1 >= 0)
            result.add(move(blankRow, blankCol - 1));
        if (blankCol + 1 < n)
            result.add(move(blankRow, blankCol + 1));
        return result;
    }

    private Board move(int row, int col) {
        Board newBoard = deepCopy();
        newBoard.swap(blankRow, blankCol, row, col);
        newBoard.blankRow = row;
        newBoard.blankCol = col;
        return newBoard;
    }

    private void swap(int i0, int j0, int i1, int j1) {
        int d0 = distance(i0, j0);
        int d1 = distance(i1, j1);
        if (d0 != 0) {
            hammingDistance--;
            manhattanDistance -= d0;
        }
        if (d1 != 0) {
            hammingDistance--;
            manhattanDistance -= d1;
        }
        int temp = tiles[i0][j0];
        tiles[i0][j0] = tiles[i1][j1];
        tiles[i1][j1] = temp;
        d0 = distance(i0, j0);
        d1 = distance(i1, j1);
        if (d0 != 0) {
            hammingDistance++;
            manhattanDistance += d0;
        }
        if (d1 != 0) {
            hammingDistance++;
            manhattanDistance += d1;
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = deepCopy();
        if (blankRow != 0)
            twinBoard.swap(0, 0, 0, 1);
        else
            twinBoard.swap(1, 0, 1, 1);
        return twinBoard;
    }

}
