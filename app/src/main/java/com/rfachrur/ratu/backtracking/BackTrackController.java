package com.rfachrur.ratu.backtracking;



public class BackTrackController {

    private int[] column;
    private int[][] solutions;
    private int numberOfSolutions;

    public BackTrackController(int n) {
        column = new int[n + 1];
        solutions = new int[1000][n];
        numberOfSolutions = 0;
        SolveQueenBackTrack(n, 0);
    }

    public int getNumberOfSolutions() { return numberOfSolutions; }

    public int[][] getSolutions() { return solutions; }

    private void SolveQueenBackTrack(int n, int i) {
        int j;
        if (Promising(i)) {
            if (i == n) {
                System.arraycopy(column, 1, solutions[numberOfSolutions], 0, n);
                // line above is equal to lines below
//                for (int m = 0; m < n; m++)
//                    solutions[numberOfSolutions][m] = col[m + 1];
                numberOfSolutions++;
            } else {
                for (j = 1; j <= n; j++) {
                    column[i + 1] = j;
                    SolveQueenBackTrack(n, i + 1);
                }
            }
        }
    }

    private boolean Promising(int i) {
        int k;
        boolean _switch;
        k = 1;
        _switch = true;
        while (k < i && _switch) {
            if (column[i] == column[k] || Math.abs(column[i] - column[k]) == i - k)
                _switch = false;
            k++;
        }
        return _switch;
    }

}
