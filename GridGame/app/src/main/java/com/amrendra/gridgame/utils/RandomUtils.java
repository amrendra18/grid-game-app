package com.amrendra.gridgame.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public class RandomUtils {

    public static int getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);

        return Color.argb(255, red, green, blue);
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
