package com.company;

import java.util.*;

public class Puzzle {
    private Board board;

    public static Puzzle initPuzzle1() {
        int size = 3;
        Token[][] puzzle = new Token[size][size];

        Integer[][] tokens = {{1, 5, 3},
                              {4, 0, 6},
                              {2, 8, 7}};

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                puzzle[i][j] = new Token(tokens[i][j]);

        return new Puzzle(puzzle);
    }

    private Puzzle(Token[][] puzzle) {
        this.board = new Board(puzzle);
    }

    private Puzzle(Puzzle otherPuzzle) {
        this.board = new Board(otherPuzzle.board);
    }

    private void move(Coord c1, Coord c2) {
        board.swap(c1, c2);
    }

    private Coord findEmpty() {
        int i = 0;

        for (Token token : board) {
            if (token.isEmpty()) {
                int x = i % size();
                int y = i / size();
                return new Coord(x, y);
            }
            i++;
        }

        return null;
    }

    public List<Puzzle> solve() {
        LinkedList<LinkedList<Puzzle>> queue = new LinkedList<LinkedList<Puzzle>>();
        Set<Puzzle> set = new HashSet<Puzzle>();

        LinkedList<Puzzle> path = new LinkedList<Puzzle>();
        path.addFirst(this);
        queue.addFirst(path);
        set.add(this);

        while (!queue.isEmpty()) {
            LinkedList<Puzzle> currentPath = queue.poll();
            Puzzle currentPuzzle = currentPath.peekLast();

            if (currentPuzzle.isSolved())
                return currentPath;

            List<Puzzle> reachables = currentPuzzle.getReachables();

            for (Puzzle reachable : reachables) {
                if (set.contains(reachable))
                    continue;

                set.add(reachable);
                LinkedList<Puzzle> reachablePath = new LinkedList<Puzzle>(currentPath);
                reachablePath.addLast(reachable);
                queue.addLast(reachablePath);
            }
        }

        return null;
    }

    public List<Puzzle> getReachables() {
        Coord emptyCoord = findEmpty();

        List<Coord> neighbours = new ArrayList<Coord>();
        for (Coord candidateNeighbour : emptyCoord.getNeigbours()) {
            if (board.inField(candidateNeighbour))
                neighbours.add(candidateNeighbour);
        }

        List<Puzzle> puzzles = new ArrayList<Puzzle>();

        for (Coord neighbour : neighbours) {
            Puzzle p = new Puzzle(this);
            p.move(emptyCoord, neighbour);
            puzzles.add(p);
        }

        return puzzles;
    }

    public boolean isSolved() {
        int previous = 0;

        for (Token token : board) {
            int current = token.getSign();
            current = current == 0 ? size() * size() : current;

            if (current <= previous)
                return false;

            previous = current;
        }

        return true;
    }

    public int size() {
        return this.board.size();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Puzzle) {
            Puzzle p = (Puzzle)o;

            if (board.equals(p.board))
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }

    @Override
    public String toString() {
        String repr = "";
        int i = 0;
        int size = size();

        for (Token token : board) {
            repr += token + ", ";

            if (i % size == size - 1)
                repr += "\n";

            i++;
        }

        return repr;
    }
}
