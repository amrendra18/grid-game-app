package com.amrendra.gridgame.board;

/**
 * Created by Amrendra Kumar on 23/10/15.
 */
public class BoardSquare {
    private int x, y;

    public BoardSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BoardSquare) {
            BoardSquare boardSquare = (BoardSquare) o;
            if (this.x == boardSquare.x && this.y == boardSquare.y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Square[" + x + "," + y + "]";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
