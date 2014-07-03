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

package com.gmail.emersonmx.tictactoe.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gmail.emersonmx.tictactoe.view.GameView;
import com.gmail.emersonmx.tictactoe.view.View;

public class GameApplication extends Application {

    public AssetManager manager;
    public TextureAtlas atlas;
    public Viewport viewport;
    public Batch batch;

    private Array<View> views;
    private View currentView;

    @Override
    public void create() {
        setup();
    }

    public void setup() {
        loadResources();
        setupGraphics();
        setupViews();
    }

    public void loadResources() {
        manager = new AssetManager();
        manager.load("background.png", Texture.class);
        manager.load("game.atlas", TextureAtlas.class);
        manager.finishLoading();

        atlas = manager.get("game.atlas");
    }

    private void setupGraphics() {
        viewport =
            new FitViewport(Resource.WINDOW_WIDTH, Resource.WINDOW_HEIGHT);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.setToOrtho(false);

        batch = new SpriteBatch();

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
    }

    private void setupViews() {
        Resource resource = new Resource(manager, atlas, viewport, batch);
        views = new Array<View>(Resource.VIEW_SIZE);

        GameView gameView = new GameView(resource);
        views.add(gameView);

        currentView = views.get(Resource.GAME_VIEW);
        currentView.setup();
    }

    @Override
    public void logic() {
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentView.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        batch.dispose();
        manager.dispose();
    }

}
