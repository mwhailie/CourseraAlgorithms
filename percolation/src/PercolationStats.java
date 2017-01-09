
import edu.princeton.cs.algs4.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mwhai_000
 */
public class PercolationStats {

    private int[] openSites;
    private int T;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Argument is not greater than 0 ");
        }

        openSites = new int[trials];
        this.T = trials;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            if (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n), StdRandom.uniform(1, n));
            } 
            openSites[i] = percolation.numberOfOpenSites();
        }

    }
    // perform trials independent experiments on an n-by-n grid

    public double mean() {
        return StdStats.mean(openSites);
    }                          // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(openSites);
    }                      // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(T);
    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(T);
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args) {

        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());

    }// test client (described below)
}
