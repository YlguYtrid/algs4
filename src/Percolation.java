import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n; // size of the grid
    private final boolean[] isOpened; // is the site `i` open?
    private final WeightedQuickUnionUF uf; // for connectivity check
    private final WeightedQuickUnionUF fullUF; // for full check
    private final int bottom; // index of the virtual bottom site
    private int openSites; // number of open sites


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Parameter n must be greater than 0.");
        this.n = n;
        int size = n * n + 2; // add 2 for the virtual top and bottom
        isOpened = new boolean[size];
        uf = new WeightedQuickUnionUF(size);
        size--; // remove the virtual bottom site
        fullUF = new WeightedQuickUnionUF(size);
        bottom = size;
        openSites = 0;
    }

    // test client (optional)
    public static void main(String[] args) {
        // do nothing
    }

    private boolean isLegalSite(int row, int col) {
        return 1 <= row && row <= n && 1 <= col && col <= n;
    }

    private void validateSite(int row, int col) {
        if (!isLegalSite(row, col))
            throw new IllegalArgumentException("Illegal site: " + row + ", " + col + ".");
    }

    // index of site (row, col) in 1D array
    private int indexOf(int row, int col) {
        return (row - 1) * n + col;
    }

    private void tryUnion(int i, int row, int col) {
        int j = indexOf(row, col);
        if (isLegalSite(row, col) && isOpened[j]) {
            uf.union(i, j);
            fullUF.union(i, j);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        int i = indexOf(row, col);
        if (!isOpened[i]) {
            isOpened[i] = true;
            openSites++;
            if (row == 1) {
                uf.union(0, i);
                fullUF.union(0, i);
            }
            if (row == n) {
                uf.union(bottom, i);
            }
            tryUnion(i, row - 1, col);
            tryUnion(i, row + 1, col);
            tryUnion(i, row, col - 1);
            tryUnion(i, row, col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return isOpened[indexOf(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        return fullUF.find(0) == fullUF.find(indexOf(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(bottom);
    }
}
