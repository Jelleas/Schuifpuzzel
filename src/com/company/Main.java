package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Puzzle p = Puzzle.initPuzzle1();

        long startTime = System.currentTimeMillis();

        List<Puzzle> solution = p.solve();

        long endTime = System.currentTimeMillis();

        for (int i = solution.size() - 1; i >= 0; i--)
            System.out.println(solution.get(i));

        System.out.println("Time taken in ms: " + (endTime - startTime));
    }
}
