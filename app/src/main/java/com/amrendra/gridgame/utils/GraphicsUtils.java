package com.amrendra.gridgame.utils;

import android.content.Context;
import android.graphics.Rect;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public class GraphicsUtils {

    public static int getColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }

    public static float spToPx(float sp, Context context) {
        return sp * getScaledDensity(context);
    }

    public static float dpToPx(float dp, Context context) {
        return dp * getDensity(context);
    }

    private static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    private static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    private Rect createInnerRect(Rect bounds, float mFillPercent) {
        float height = bounds.height();
        float width = bounds.width();

        float innerHeight = height * mFillPercent;
        float innerWidth = width * mFillPercent;

        int left = (int) ((width - innerWidth) / 2);
        int top = (int) ((height - innerHeight) / 2);
        int bottom = (int) (top + innerHeight);
        int right = (int) (left + innerWidth);

        return new Rect(left, top, right, bottom);
    }
}