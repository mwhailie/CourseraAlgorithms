
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mwhai_000
 */
public class Percolation {

    private WeightedQuickUnionUF uf;
    private int n;
    private int[] open;
    private int numberOfOpenSites;
    private boolean percolates;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("the indice is not in the gird");
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        open = new int[n * n];
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
        }
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IndexOutOfBoundsException("the indice is not in the gird");
        }
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * n + col;
    }

    public void open(int row, int col) {// open site (row, col) if it is not open already
        // to open a new site, connect it to all it open site, call Union
        int nIn1D = xyTo1D(row, col);
        validate(row, col);
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(nIn1D, nIn1D - 1);
        }
        if (col < n && isOpen(row, col + 1)) {
            uf.union(nIn1D, nIn1D + 1);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(nIn1D, nIn1D - n);
        }
        if (row < n && isOpen(row + 1, col)) {
            uf.union(nIn1D, nIn1D + n);
        }
        if (row == n) {

        }
        if (!isOpen(row, col)) {
            numberOfOpenSites++;
        }
        if (row == n) {
            uf.union(nIn1D, n * n + 1);
            open[nIn1D - 1] = 16;
            if (uf.connected(0, nIn1D)) {
                percolates = true;
            }
        } else {
            open[nIn1D - 1] = 1;
        }
    }

    public boolean isOpen(int row, int col) {// is site (row, col) open?
        validate(row, col);
        return open[xyTo1D(row, col) - 1] != 0;
    }

    public boolean isFull(int row, int col) {// is site (row, col) full?
        validate(row, col);
        return isOpen(row, col) && uf.connected(xyTo1D(row, col), 0);
    }

    public int numberOfOpenSites() {       // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {        //check virtual top connect to virtual bottom
        if (n > 1) {
            return uf.connected(0, n * n + 1);
        } else {
            return isOpen(1, 1);
        }
    }// does the system percolate?
}
