package com.amrendra.gridgame.board;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.amrendra.gridgame.bus.BusProvider;
import com.amrendra.gridgame.drawable.RoundTileDrawable;
import com.amrendra.gridgame.events.TileViewClickEvent;
import com.squareup.otto.Bus;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public class TileView extends View {
    private int X, Y;

    public TileView(Context context, int x, int y) {
        super(context);
        this.X = x;
        this.Y = y;
        init();
    }

    private void init() {
        setupListeners();
    }

    public void setupDrawableBackgrounds(int color) {
        setBackground(new RoundTileDrawable(color));
    }


    private void setupListeners() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Notify the game that the user has performed an action on this tile.
                BusProvider.getInstance().post(new TileViewClickEvent(TileView.this));
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Return true to consume event.
                return true;
            }
        });
    }


    public int getXGridCoordinate() {
        return X;
    }

    public int getYGridCoordinate() {
        return Y;
    }

    @Override
    public String toString() {
        return "TileView[" + X + "," + Y + "]";
    }

}
