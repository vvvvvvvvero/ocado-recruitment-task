package org.example.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DPState {
    private int count; // min number of columns used so far
    private boolean[] usedColumns; // boolean array representing used columns
    private int score; // max score gathered so far

    DPState(int count, int numColumns, int score) {
        this.count = count;
        this.usedColumns = new boolean[numColumns];
        this.score = score;
    }

}
