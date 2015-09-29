package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Puzzle p = Puzzle.initPuzzle1();

        long startTime = System.currentTimeMillis();

        List<Puzzle> solution = p.solve();

        long endTime = System.currentTimeMillis();

        if (solution == null)
            System.out.println("Puzzle is unsolvable.");
        else
            for (Puzzle puzzle : solution)
                System.out.println(puzzle);

        System.out.println("Time taken in ms: " + (endTime - startTime) + ".");
    }
}
