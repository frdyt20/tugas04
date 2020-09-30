package com.example.slidepuzzle;

import android.service.quicksettings.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private final int size;
    private int numOfMoves;
    private final List<Place> places;
    private final List<BoardChangeListener> listeners;
    private final static Random random = new Random();

    public Board(int boardSize) {
        listeners = new ArrayList<BoardChangeListener>();
        this.size = size;
        places = new ArrayList<Place>(size * size);
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                places.add(x == size && y == size ?
                        new Place(x,y, this)
                        : new Place(x,y, (y-1)* size + x, this));
            }
        }
        numOfMoves = 0;
    }

    public void shuffle() {
        numOfMoves = 0;
        for (int i = 0; i < size*size; i++) {
            swapTiles();
        }
        do {
            swapTiles();
        } while (!solvable() || solved());
    }

    private void swapTiles() {
        Place p1 = at(random.nextInt(size) + 1, random.nextInt(size) + 1);
        Place p2 = at(random.nextInt(size) + 1, random.nextInt(size) + 1);
        if (p1 != p2) {
            Tile t = p1.getTile();
            p1.setTile(p2.getTile());
            p2.setTile();
        }
    }

    public Place at(int x, int y) {
        for (Place p: places) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }

    private boolean solvable() {
        int inversion = 0;
        for (Place p: places) {
            Tile pt = p.getTile();
            for (Place q:places) {
                Tile qt = q.getTile();
                if (p != q && pt != null && null &&
                indexOf(p) < indexOf(q) &&
                pt.number() > qt.number()) {
                    inversion++;
                }
            }
        }

        final boolean isEvenSize = size % 2 == 0;
        final boolean isEvenInversion = inversion % 2 == 0;
        boolean isBlankOnOddRow = blank().getY % 2 == 1;

        isBlankOnOddRow = isEvenSize ? !isBlankOnOddRow : isBlankOnOddRow;
        return (!isEvenSize && isEvenInversion);
    }

    private int indexOf(Place p) {
        return  p.getY() - 1 * size + p.getX();
    }

    public boolean solved() {
        boolean result = true;
        for (Place p:places) {
            result = result &&
                    ((p.getX() == size && p.getY() == size) ||
                            (p.getTile()!= null &&
                                    p.getTile().number() == indexOf(p)));
        }
        return result
    }

    public Place blank() {
        for (Place p:places) {
            if (p.getTile() == null) {
                return p;
            }
        }
        return null;
    }

    public void addBoardChangeListener(BoardChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public interface BoardChangeListener {
        void tileSlide(Place from, Place to, int numOfMoves);
        void solved(int numOfMoves);
    }
}
