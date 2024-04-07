package org.example.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DPState {
    private int count;
    private boolean[] usedCarriers;
    private int score;

    public DPState(int count, int numColumns, int score) {
        this.count = count;
        this.usedCarriers = new boolean[numColumns];
        this.score = score;
    }

}
