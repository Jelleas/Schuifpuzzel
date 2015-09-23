package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board implements Iterable<Token> {
    private final class BoardIterator implements Iterator<Token> {
        private int cursor;
        private final int end;
        private Board board;

        public BoardIterator(Board board) {
            this.board = board;
            cursor = 0;
            end = board.size() * board.size();
        }

        public boolean hasNext() {
            return cursor < end;
        }

        public Token next() {
            if (hasNext()) {
                int x = cursor % board.size();
                int y = cursor / board.size();
                Token token = board.get(new Coord(x, y));

                cursor += 1;
                return token;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private int size;
    private Token[][] board;

    public Board(Token[][] puzzleRepr) {
        size = puzzleRepr.length;
        board = new Token[size][size];

        for (int i = 0; i < size; i++)
            board[i] = puzzleRepr[i].clone();
    }

    public Board(Board otherBoard) {
        size = otherBoard.size();
        board = new Token[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = otherBoard.get(new Coord(i, j));
    }

    public Token get(Coord c) {
        return board[c.x][c.y];
    }

    private void put(Coord c, Token t) {
        board[c.x][c.y] = t;
    }

    public void swap(Coord c1, Coord c2) {
        Token tmp = get(c1);
        put(c1, get(c2));
        put(c2, tmp);
    }

    public boolean inField(Coord coord) {
        return coord.x >= 0 && coord.x < size &&
                coord.y >= 0 && coord.y < size;
    }

    public int size() {
        return this.size;
    }

    private String uniqueRepr() {
        String repr = "";

        for (Token token : this)
            repr += token.getSign() + ",";

        return repr;
    }

    public Iterator<Token> iterator() {
        return new BoardIterator(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Board) {
            Board b = (Board)o;

            if (this.uniqueRepr().equals(b.uniqueRepr()))
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return uniqueRepr().hashCode();
    }
}
