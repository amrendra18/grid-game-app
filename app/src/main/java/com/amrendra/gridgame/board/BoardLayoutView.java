package com.amrendra.gridgame.board;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.amrendra.gridgame.R;
import com.amrendra.gridgame.bus.BusProvider;
import com.amrendra.gridgame.events.TileViewCreatedEvent;
import com.amrendra.gridgame.utils.Debug;
import com.amrendra.gridgame.utils.GraphicsUtils;
import com.squareup.otto.Bus;

/**
 * Created by Amrendra Kumar on 23/10/15.
 */
public class BoardLayoutView extends ViewGroup {

    private static final int DEFAULT_LINE_COLOR = Color.BLACK;

    private static final int DEFAULT_BORDER_WIDTH = 3;
    private static final int DEFAULT_GRIDLINE_WIDTH = 2;

    private Paint mBorderPaint;
    private int mBorderColor;
    private float mBorderStrokeWidth;

    private Paint mGridPaint;
    private int mGridColor;
    private float mGridStrokeWidth;

    private int diffX = 0;
    private int diffY = 0;


    private Board mBoard;
    private Bus mGameBus;

    public BoardLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mBoard = new Board();
        mGameBus = BusProvider.getInstance();

        // Enable drawing for ViewGroup object
        setWillNotDraw(false);

        extractAttributes(attrs);
        setupDrawObject();
    }

    private void extractAttributes(AttributeSet attrs) {
        TypedArray attributesArray = getContext().obtainStyledAttributes(attrs, R.styleable.BoardLayoutView);

        float borderStrokeWidthInPx = GraphicsUtils.dpToPx(DEFAULT_BORDER_WIDTH, getContext());
        float gridLinesStrokeWidthInPx = GraphicsUtils.dpToPx(DEFAULT_GRIDLINE_WIDTH, getContext());


        try {
            mGridColor = attributesArray.getColor(R.styleable.BoardLayoutView_gridColor, DEFAULT_LINE_COLOR);
            mGridStrokeWidth = attributesArray.getDimension(R.styleable.BoardLayoutView_gridLineWidth, gridLinesStrokeWidthInPx);

            mBorderColor = attributesArray.getColor(R.styleable.BoardLayoutView_borderColor, DEFAULT_LINE_COLOR);
            mBorderStrokeWidth = attributesArray.getDimension(R.styleable.BoardLayoutView_borderLineWidth, borderStrokeWidthInPx);
        } finally {
            attributesArray.recycle();
        }

    }

    private void setupDrawObject() {
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setColor(mGridColor);
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setStrokeWidth(mGridStrokeWidth);

        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderStrokeWidth);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Debug.c();
        Debug.i("check : " + changed + "l: " + l + " t:" + t + " r:" + r + " b:" + b, false);
        int childCount = getChildCount();
        int dimensionX = mBoard.getDimensionsX();
        int dimensionY = mBoard.getDimensionsY();
        int intervalX = getMeasuredWidth() / dimensionX;
        int intervalY = getMeasuredHeight() / dimensionY;
        Debug.i("onLayout w:"+getMeasuredWidth()+" h:"+getMeasuredHeight(), false);
/*        int idx = 0;
        float startX, startY, endX, endY;
        for (int i = 0; i < dimensionY; i++) {
            for (int j = 0; j < dimensionX; j++) {
                TileView tileView = (TileView) getChildAt(idx);
                startX = intervalX * j;
                endX = startX + intervalX;
                startY = intervalY * i;
                endY = startY + intervalY;
                tileView.layout((int) startX, (int) startY, (int) endX, (int) endY);
                idx++;
            }
        }*/

        // now place all the tiles
        for (int i = 0; i < childCount; i++) {
            TileView tileView = (TileView) getChildAt(i);

            int top = (i / dimensionX) * intervalY;
            int bottom = top + intervalY;
            int left = (i % dimensionX) * intervalX;
            int right = left + intervalX;
            tileView.layout(left, top, left + intervalX, top + intervalY);
        }



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Debug.c();

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int properWidth = (width/mBoard.getDimensionsX()) * mBoard.getDimensionsX();
        int properHeight = (height/mBoard.getDimensionsY())*mBoard.getDimensionsY();
        diffX = (width - properWidth);
        diffY = (height - properHeight);
        Debug.i("Measured w:"+width+" h:"+height, false);
        Debug.i("Proper w:"+properWidth+" h:"+properHeight, false);
        Debug.e("diffX: "+diffX+" diffY: "+diffY,false);
        setMeasuredDimension(properWidth, properHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Debug.c();
        int w = getWidth();
        int h = getHeight();
        Debug.i("Canvas w:"+w+" h:"+h, false);
        drawGridLines(w, h, canvas);
        drawBorder(w, h, canvas);
    }

    @SuppressWarnings("unused")
    private void drawBorder(int width, int height, Canvas canvas) {
        canvas.drawRect(0, 0, width, height, mBorderPaint);
    }

    @SuppressWarnings("unused")
    private void drawGridLines(int width, int height, Canvas canvas) {
        if (mBoard != null) {
            float dimensionX = mBoard.getDimensionsX();
            float dimensionY = mBoard.getDimensionsY();
            float intervalX = width / dimensionX;
            float intervalY = height / dimensionY;

            float startX = 0;
            float endX = intervalX * dimensionX;
            float startY, endY;

            // horizontal lines
            for (int i = 1; i < dimensionY; i++) {
                startY = endY = intervalY * i;
                canvas.drawLine(startX, startY, endX, endY, mGridPaint);
            }

            startY = 0;
            endY = intervalY * dimensionY;

            // vertical lines
            for (int i = 1; i < dimensionX; i++) {
                startX = endX = intervalX * i;
                canvas.drawLine(startX, startY, endX, endY, mGridPaint);
            }

        } else {
            Debug.e("Trying to draw gridLines of null board", false);
        }
    }

    public void setupBoard(Board board) {
        Debug.c();
        this.mBoard = board;

        //clear old tiles
        this.removeAllViews();

        // create new tiles
        createTileViews();
    }

    private void createTileViews() {
        Debug.c();
        int dimensionX = mBoard.getDimensionsX();
        int dimensionY = mBoard.getDimensionsY();

        for (int i = 0; i < dimensionY; i++) {
            for (int j = 0; j < dimensionX; j++) {
                TileView tileView = new TileView(getContext(), j, i);
                addView(tileView);
                mGameBus.post(new TileViewCreatedEvent(tileView));
            }
        }
    }
}
