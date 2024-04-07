package org.example.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@Setter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShoppingCartOptimizer {
    int numRows;
    int numColumns;
    Map<Integer, Set<Integer>> allowedCarriers;
    Map<Integer, Integer> scores;
    DPState[][] dpTable;
    int[][] solutionTable;

    public ShoppingCartOptimizer(int numRows, int numColumns, Map<Integer, Set<Integer>> allowedCarriers, Map<Integer, Integer> scores) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.allowedCarriers = allowedCarriers;
        this.scores = scores;
        dpTable = new DPState[numRows][numColumns];
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
        Map<String, List<String>> resultMapped = new HashMap<>();
        for (int j = 0; j < solutionTable[0].length; j++) {
            List<String> productList = new ArrayList<>();
            for (int i = 0; i < solutionTable.length; i++) {
                if (solutionTable[i][j] == 1) {
                    productList.add(products.get(i));
                }
            }
            if (!productList.isEmpty()) {
                resultMapped.put(carriers.get(j), productList);
            }
        }
        return resultMapped;
    }

    private void initializeDPTable() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                dpTable[row][col] = new DPState(Integer.MAX_VALUE, numColumns, 0);
            }
        }
    }

    private void setBaseCase() {
        for (int column : allowedCarriers.get(0)) {
            dpTable[0][column].setCount(1);
            dpTable[0][column].getUsedCarriers()[column] = true;
            dpTable[0][column].setScore(scores.get(column));
        }
    }

    private void buildDPTable() {
        for (int row = 1; row < numRows; row++) {
            for (int prevCol = 0; prevCol < numColumns; prevCol++) {
                if (dpTable[row - 1][prevCol].getCount() != Integer.MAX_VALUE) {
                    for (int column : allowedCarriers.get(row)) {
                        int newCount = dpTable[row - 1][prevCol].getCount() + (dpTable[row - 1][prevCol].getUsedCarriers()[column] ? 0 : 1);
                        int newScore = dpTable[row - 1][prevCol].getScore() + scores.get(column);
                        if (newCount < dpTable[row][column].getCount() || (newCount == dpTable[row][column].getCount() && newScore > dpTable[row][column].getScore())) {
                            dpTable[row][column].setCount(newCount);
                            dpTable[row][column].setScore(newScore);
                            System.arraycopy(dpTable[row - 1][prevCol].getUsedCarriers(), 0, dpTable[row][column].getUsedCarriers(), 0, numColumns);
                            dpTable[row][column].getUsedCarriers()[column] = true;
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
            solutionTable[row][selectedColumn] = 1;
            boolean[] usedCols = dpTable[row][selectedColumn].getUsedCarriers();
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