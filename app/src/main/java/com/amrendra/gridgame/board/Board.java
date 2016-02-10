package com.amrendra.gridgame.board;

/**
 * Created by Amrendra Kumar on 23/10/15.
 */
public class Board {
    public static final int DEFAULT_DIMENSION_X = 6;
    public static final int DEFAULT_DIMENSION_Y = 6;
    private BoardSquare[][] mBoardGrid = null;
    private int mDimensionsX;
    private int mDimensionsY;

    public Board(){
        this(DEFAULT_DIMENSION_X, DEFAULT_DIMENSION_Y);
    }

    public Board(int dimensions){
        this(dimensions,dimensions);
    }

    public Board(int dimensionsX, int dimensionsY){
        if(dimensionsX <= 0 || dimensionsY <= 0){
            mDimensionsX = DEFAULT_DIMENSION_Y;
            mDimensionsY = DEFAULT_DIMENSION_Y;
        }else {
            mDimensionsX = dimensionsX;
            mDimensionsY = dimensionsY;
        }
        init();
    }

    public void init(){
        initBoardGrid();
    }

    private void initBoardGrid() {
        mBoardGrid = new BoardSquare[mDimensionsX][mDimensionsY];
    }

    public BoardSquare[][] getBoardGrid() {
        return mBoardGrid;
    }

    public void setBoardGrid(BoardSquare[][] mBoardGrid) {
        this.mBoardGrid = mBoardGrid;
    }

    public int getDimensionsX() {
        return mDimensionsX;
    }

    public int getDimensionsY() {
        return mDimensionsY;
    }
}
