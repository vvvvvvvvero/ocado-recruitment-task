package org.example.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DPState {
    private int count;
    private boolean[] usedCarriers;
    private int score;

    DPState(int count, int numColumns, int score) {
        this.count = count;
        this.usedCarriers = new boolean[numColumns];
        this.score = score;
    }

}
