package com.amrendra.gridgame.events;

import com.amrendra.gridgame.board.TileView;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public final class TileViewClickEvent {
    TileView mTileView;

    public TileViewClickEvent(TileView tileView){
        mTileView = tileView;
    }

    public TileView getTileView() {
        return mTileView;
    }
}
