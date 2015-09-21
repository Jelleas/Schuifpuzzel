package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        PuzzleSolver solver = new PuzzleSolver();

        long startTime = System.currentTimeMillis();

        List<Puzzle> solution = solver.solve(Puzzle.initPuzzle1());

        long endTime = System.currentTimeMillis();

        for (int i = solution.size() - 1; i >= 0; i--)
            System.out.println(solution.get(i));

        System.out.println("Time taken in ms: " + (endTime - startTime));
    }
}
