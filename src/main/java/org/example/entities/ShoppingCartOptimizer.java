package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ShoppingCartOptimizer {
    private final int numRows;
    private final int numColumns;
    private final Map<Integer, Set<Integer>> allowedColumns;
    private final Map<Integer, Integer> scores;
    private DPState[][] dpTable;
    private int[][] solutionTable;

    public ShoppingCartOptimizer(int numRows, int numColumns, Map<Integer, Set<Integer>> allowedColumns, Map<Integer, Integer> scores) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.allowedColumns = allowedColumns;
        this.scores = scores;
        this.solutionTable = new int[numRows][numColumns];
    }

    private void computeSolutionTable() {
        initializeDPTable();
        setBaseCase();
        buildDPTable();
        backtrackToFillTable();
    }

    public Map<String, List<String>> createSolutionMap(Map<Integer, String> products, Map<Integer, String> carriers){
        computeSolutionTable();
        Map<String, List<String>> resultMapped = new HashMap<>(Map.of());
        for (int i = 0; i < solutionTable.length; i++) {
            for (int j = 0; j < solutionTable[i].length; j++) {
                if (solutionTable[i][j] == 1) {
                    resultMapped.put(products.get(i), List.of(carriers.get(j)));
                }
            }
        }
        return resultMapped;

    }

    private void initializeDPTable() {
        dpTable = new DPState[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                dpTable[row][col] = new DPState(Integer.MAX_VALUE, numColumns, 0);
            }
        }
    }

    private void setBaseCase() {
        for (int column : allowedColumns.get(0)) {
            dpTable[0][column].setCount(1);
            dpTable[0][column].getUsedColumns()[column] = true;
            dpTable[0][column].setScore(scores.get(column));
        }
    }

    private void buildDPTable() {
        for (int row = 1; row < numRows; row++) {
            for (int prevCol = 0; prevCol < numColumns; prevCol++) {
                if (dpTable[row - 1][prevCol].getCount() != Integer.MAX_VALUE) {
                    for (int column : allowedColumns.get(row)) {
                        int newCount = dpTable[row - 1][prevCol].getCount() + (dpTable[row - 1][prevCol].getUsedColumns()[column] ? 0 : 1);
                        int newScore = dpTable[row - 1][prevCol].getScore() + scores.get(column);
                        if (newCount < dpTable[row][column].getCount() || (newCount == dpTable[row][column].getCount() && newScore > dpTable[row][column].getScore())) {
                            dpTable[row][column].setCount(newCount);
                            dpTable[row][column].setScore(newScore);
                            System.arraycopy(dpTable[row - 1][prevCol].getUsedColumns(), 0, dpTable[row][column].getUsedColumns(), 0, numColumns);
                            dpTable[row][column].getUsedColumns()[column] = true;
                        }
                    }
                }
            }
        }
    }

    private void backtrackToFillTable() {
        int selectedColumn = -1;
        int minCount = Integer.MAX_VALUE;
        int maxScore = 0;

        for (int col = 0; col < numColumns; col++) {
            if (dpTable[numRows - 1][col].getCount() < minCount ||
                    (dpTable[numRows - 1][col].getCount() == minCount && dpTable[numRows - 1][col].getScore() > maxScore)) {
                minCount = dpTable[numRows - 1][col].getCount();
                maxScore = dpTable[numRows - 1][col].getScore();
                selectedColumn = col;
            }
        }

        for (int row = numRows - 1; row >= 0; row--) {
            solutionTable[row][selectedColumn] = 1; // Mark this carrier as selected
            boolean[] usedCols = dpTable[row][selectedColumn].getUsedColumns();
            selectedColumn = -1;
            minCount = Integer.MAX_VALUE;
            maxScore = 0;

            for (int col = 0; col < numColumns; col++) {
                if (row > 0 && usedCols[col]) {
                    boolean isCountEqual = dpTable[row - 1][col].getCount() == dpTable[row][col].getCount();
                    boolean isScoreHigher = dpTable[row - 1][col].getScore() > maxScore;
                    boolean isNewMinCount = dpTable[row - 1][col].getCount() < minCount;

                    if (isNewMinCount || (isCountEqual && isScoreHigher)) {
                        selectedColumn = col;
                        minCount = dpTable[row - 1][col].getCount();
                        maxScore = dpTable[row - 1][col].getScore();
                    }
                }
            }
        }
    }
}