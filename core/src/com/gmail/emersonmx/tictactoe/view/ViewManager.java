/*
  Copyright (C) 2014 Emerson Max de Medeiros Silva

  This file is part of tictactoe.

  tictactoe is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  tictactoe is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with tictactoe.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.gmail.emersonmx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewManager {

    public static final int WINDOW_WIDTH = 480;
    public static final int WINDOW_HEIGHT = 640;

    public static final int GAME_VIEW = 0;
    public static final int VIEWS_SIZE = 1;

    public AssetManager manager;
    public TextureAtlas atlas;
    public Viewport viewport;
    private Camera camera;
    public Batch batch;

    private View[] views;
    private View currentView;
    private View nextView;

    private Texture background;
    private Sprite blackboard;

    public ViewManager(AssetManager manager, TextureAtlas atlas,
            Viewport viewport, Batch batch) {

        this.manager = manager;
        this.atlas = atlas;
        this.viewport = viewport;
        this.camera = viewport.getCamera();
        this.batch = batch;

        views = new View[VIEWS_SIZE];
        currentView = nextView = null;
    }

    public View getView(int view) {
        return views[view];
    }

    public void changeView(int view) {
        nextView = views[view];

        if (!nextView.isLoaded()) {
            nextView.create();
        }
    }

    public void create() {
        background = createBackground();
        blackboard = createBlackboard();

        GameView gameView = new GameView(this);
        views[GAME_VIEW] = gameView;
    }

    protected Texture createBackground() {
        Texture texture = manager.get("background.png", Texture.class);
        texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        return texture;
    }

    protected Sprite createBlackboard() {
        return atlas.createSprite("blackboard");
    }

    public void setup() {
        currentView = views[GAME_VIEW];
        currentView.create();
        currentView.setup();
    }

    public void logic(float deltaTime) {
        if (nextView != null) {
            currentView = nextView;
            nextView = null;

            currentView.setup();
        }

        deltaTime = Gdx.graphics.getDeltaTime();

        currentView.logic(deltaTime);
    }

    public void draw() {
        viewport.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 0, 0, 5, 5);

        blackboard.draw(batch);

        currentView.draw(batch);
        batch.end();
    }

    public void dispose() {
        for (View view : views) {
            view.dispose();
        }
    }

}
