package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    static class DPState {
        int count; // minimal number of columns used so far
        boolean[] usedColumns; // boolean array representing used columns
        int score;

        DPState(int count, int numColumns, int score) {
            this.count = count;
            this.usedColumns = new boolean[numColumns];
            this.score = score;
        }
    }

    public static int[][] minimizeColumnsWithOnes(int numRows, int numColumns, Map<Integer, Set<Integer>> allowedColumns, Map<Integer, Integer> scores) {
        // Initialize DP table
        DPState[][] DP = new DPState[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                DP[row][col] = new DPState(Integer.MAX_VALUE, numColumns, 0);
            }
        }

        // Base case: first row
        for (int column : allowedColumns.get(0)) {
            DP[0][column].count = 1;
            DP[0][column].usedColumns[column] = true;
            DP[0][column].score = scores.get(column);
        }

        // Build the DP table
        for (int row = 1; row < numRows; row++) {
            for (int prevCol = 0; prevCol < numColumns; prevCol++) {
                if (DP[row - 1][prevCol].count != Integer.MAX_VALUE) {
                    for (int column : allowedColumns.get(row)) {
                        int newCount = DP[row - 1][prevCol].count + (DP[row - 1][prevCol].usedColumns[column] ? 0 : 1);
                        int newScore = DP[row - 1][prevCol].score + scores.get(column);
                        if (newCount < DP[row][column].count || (newCount == DP[row][column].count && newScore > DP[row][column].score)) {
                            DP[row][column].count = newCount;
                            DP[row][column].score = newScore;
                            System.arraycopy(DP[row - 1][prevCol].usedColumns, 0, DP[row][column].usedColumns, 0, numColumns);
                            DP[row][column].usedColumns[column] = true;
                        }
                    }
                }
            }
        }

        // Backtrack to construct the solution
        int[][] table = new int[numRows][numColumns];
        backtrackToFillTable(numRows, numColumns, DP, table);

        return table;
    }

    private static void backtrackToFillTable(int numRows, int numColumns, DPState[][] DP, int[][] table) {
        int selectedColumn = -1;
        int minCount = Integer.MAX_VALUE;
        int maxScore = 0;

        // Find the starting column based on minCount and maxScore at the last row
        for (int col = 0; col < numColumns; col++) {
            if (DP[numRows - 1][col].count < minCount ||
                    (DP[numRows - 1][col].count == minCount && DP[numRows - 1][col].score > maxScore)) {
                minCount = DP[numRows - 1][col].count;
                maxScore = DP[numRows - 1][col].score;
                selectedColumn = col;
            }
        }

        // Backtrack from the last row
        for (int row = numRows - 1; row >= 0; row--) {
            table[row][selectedColumn] = 1; // Mark this carrier as selected
            boolean[] usedCols = DP[row][selectedColumn].usedColumns;
            selectedColumn = -1; // Reset for next row
            minCount = Integer.MAX_VALUE;
            maxScore = 0;

            // For the given row, select the previous column (carrier) based on usedCols, minCount, and maxScore
            for (int col = 0; col < numColumns; col++) {
                if (row > 0 && usedCols[col]) {
                    boolean isCountEqual = DP[row - 1][col].count == DP[row][col].count;
                    boolean isScoreHigher = DP[row - 1][col].score > maxScore;
                    boolean isNewMinCount = DP[row - 1][col].count < minCount;

                    if (isNewMinCount || (isCountEqual && isScoreHigher)) {
                        selectedColumn = col;
                        minCount = DP[row - 1][col].count;
                        maxScore = DP[row - 1][col].score;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Example usage
        // Define allowedColumns based on your specific problem
        // Call minimizeColumnsWithOnes with appropriate arguments


        // express 0
        // cnc 1
        // courier 2


        var products = Map.of(
                0, "Steak (300g)",
                1, "Carrots (1kg)",
                2, "Cold Beer (300 ml.)"
        );

        var carriers = Map.of(
                0, "Express",
                1, "CNC"
        );

        var scores = Map.of(
                0, 30,
                1, 20
        );


        var res = minimizeColumnsWithOnes(3, 2, Map.of(
                0, Set.of(0),
                1, Set.of(1),
                2, Set.of(0, 1)
        ), scores);


    }


}