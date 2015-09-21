package com.company;

import java.util.*;

public class Puzzle {
    private class Coord {
        public final int x, y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public List<Coord> getNeigbours() {
            List<Coord> coords = new ArrayList<Coord>();
            coords.add(new Coord(this.x - 1, this.y));
            coords.add(new Coord(this.x + 1, this.y));
            coords.add(new Coord(this.x, this.y - 1));
            coords.add(new Coord(this.x, this.y + 1));

            // TODO refactor with JDK 1.8: removeIf
            for (int i = coords.size() - 1; i >= 0; i--) {
                if (!inField(coords.get(i)))
                    coords.remove(coords.get(i));
            }

            return coords;
        }
    }

    private int size;
    private Token[][] puzzle;

    public static Puzzle initPuzzle1() {
        Puzzle p = new Puzzle();
        p.size = 3;
        p.puzzle = new Token[p.size][p.size];

        Integer[][] tokens = {{1, 4, 2},
                              {5, 0, 8},
                              {3, 6, 7}};

        for (int i = 0; i < p.size; i++)
            for (int j = 0; j < p.size; j++)
                p.puzzle[i][j] = new Token(tokens[i][j]);

        return p;
    }

    // Default blank constructor
    private Puzzle(){}

    // Copy constructor
    private Puzzle(Puzzle puzzle) {
        this.size = puzzle.size;
        this.puzzle = new Token[this.size][this.size];

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.puzzle[i][j] = new Token(puzzle.puzzle[i][j]);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Puzzle) {
            Puzzle p = (Puzzle)o;

            if (this.uniqueRepr().equals(p.uniqueRepr()))
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return uniqueRepr().hashCode();
    }

    private String uniqueRepr() {
        String repr = "";

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                repr += this.puzzle[i][j].getSign() + ",";

        return repr;
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
            Puzzle currentPuzzle = currentPath.peek();

            if (currentPuzzle.isSolved())
                return currentPath;

            List<Puzzle> reachables = currentPuzzle.getReachables();

            for (Puzzle reachable : reachables) {
                if (set.contains(reachable))
                    continue;

                set.add(reachable);
                LinkedList<Puzzle> reachablePath = new LinkedList<Puzzle>(currentPath);
                reachablePath.addFirst(reachable);
                queue.addLast(reachablePath);
            }
        }

        return null;
    }

    public List<Puzzle> getReachables() {
        Coord emptyCoord = findEmpty();
        List<Coord> neighbours = emptyCoord.getNeigbours();

        List<Puzzle> puzzles = new ArrayList<Puzzle>();

        for (Coord neighbour : neighbours) {
            Puzzle p = new Puzzle(this);

            Token tmp = p.puzzle[neighbour.x][neighbour.y];
            p.puzzle[neighbour.x][neighbour.y] = this.puzzle[emptyCoord.x][emptyCoord.y];
            p.puzzle[emptyCoord.x][emptyCoord.y] = tmp;
            puzzles.add(p);
        }

        return puzzles;
    }

    public boolean isSolved() {
        int previous = 0;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int current = this.puzzle[i][j].getSign();
                current = current == 0 ? size * size : current;

                if (current <= previous)
                    return false;

                previous = current;
            }
        }

        return true;
    }

    private boolean inField(Coord coord) {
        return coord.x >= 0 && coord.x < size &&
               coord.y >= 0 && coord.y < size;
    }

    public String toString() {
        String repr = "";

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                repr += this.puzzle[i][j] + ", ";
            }
            repr += "\n";
        }

        return repr;
    }

    private Coord findEmpty() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.puzzle[i][j].isEmpty())
                    return new Coord(i, j);
        return null;
    }
}
