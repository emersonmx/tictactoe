package com.gmail.emersonmx.tictactoe.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Resource {

    public static final int WINDOW_WIDTH = 480;
    public static final int WINDOW_HEIGHT = 640;

    public static final int GAME_VIEW = 0;
    public static final int VIEW_SIZE = 1;

    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    public AssetManager manager;
    public TextureAtlas atlas;
    public Viewport viewport;
    public Batch batch;

    public Resource() {
    }

    public Resource(AssetManager manager, TextureAtlas atlas,
            Viewport viewport, Batch batch) {

        this.manager = manager;
        this.atlas = atlas;
        this.viewport = viewport;
        this.batch = batch;
    }

}
