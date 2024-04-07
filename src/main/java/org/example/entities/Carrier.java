package org.example.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Carrier {
    private String name;
    private int rank;

    public Carrier(String name) {
        this.name = name;
    }

}
