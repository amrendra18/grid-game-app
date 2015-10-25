package com.amrendra.gridgame.events;

import com.amrendra.gridgame.board.TileView;

/**
 * Created by Amrendra Kumar on 24/10/15.
 */
public final class TileViewCreatedEvent {

    TileView mTileView;

    public TileViewCreatedEvent(TileView tileView) {
        mTileView = tileView;
    }

    public TileView getTileView() {
        return mTileView;
    }
}
