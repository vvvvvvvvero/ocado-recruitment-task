package org.example;

import org.example.splitter.BasketSplitter;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter("src/main/resources/config.json");
        List<String> products = basketSplitter.getFileService().readCartFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/basket-1.json");
        System.out.println(basketSplitter.split(products));

    }
}
