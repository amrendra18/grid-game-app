package com.amrendra.gridgame.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public class RoundTileDrawable extends Drawable {
    public static final float LINE_PADDING = 1;
    private static float ROUND_CORNER_RADIUS = 2;

    static RectF rectF = new RectF();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoundTileDrawable(int color) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        rectF.set(rect.left + LINE_PADDING, rect.top + LINE_PADDING, rect.right - LINE_PADDING,
                rect.bottom - LINE_PADDING);
        canvas.drawRoundRect(rectF, ROUND_CORNER_RADIUS, ROUND_CORNER_RADIUS, paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
