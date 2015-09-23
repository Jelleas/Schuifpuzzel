package com.company;

import java.util.ArrayList;
import java.util.List;

public class Coord {
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

        return coords;
    }
}