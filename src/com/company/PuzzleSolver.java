package com.company;

import java.util.*;

/**
 * Created by Jelle on 22-9-2015.
 */
public class PuzzleSolver {
    private final LinkedList<LinkedList<Puzzle>> queue = new LinkedList<LinkedList<Puzzle>>();
    private final Set<Puzzle> set = new HashSet<Puzzle>();

    public List<Puzzle> solve(Puzzle p) {
        queue.clear();
        set.clear();

        LinkedList<Puzzle> path = new LinkedList<Puzzle>();
        path.addFirst(p);
        queue.addFirst(path);
        set.add(p);

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
}
