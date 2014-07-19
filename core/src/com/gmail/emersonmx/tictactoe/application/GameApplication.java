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

package com.gmail.emersonmx.tictactoe.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gmail.emersonmx.tictactoe.view.ViewManager;

public class GameApplication extends Application {

    public AssetManager manager;
    public TextureAtlas atlas;
    public Viewport viewport;
    public Batch batch;

    private ViewManager viewManager;

    @Override
    public void create() {
        loadResources();
        setupGraphics();
        setupSystem();
    }

    public void loadResources() {
        manager = new AssetManager();
        manager.load("background.png", Texture.class);
        manager.load("game.atlas", TextureAtlas.class);
        manager.finishLoading();

        atlas = manager.get("game.atlas");
    }

    private void setupGraphics() {
        viewport = new FitViewport(ViewManager.WINDOW_WIDTH,
            ViewManager.WINDOW_HEIGHT);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.setToOrtho(false, ViewManager.WINDOW_WIDTH,
                          ViewManager.WINDOW_HEIGHT);

        batch = new SpriteBatch();

        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    public void setupSystem() {
        viewManager = new ViewManager(manager, atlas, viewport);
        viewManager.create();
    }

    @Override
    public void logic() {
        viewManager.logic(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewManager.draw(batch);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        viewManager.dispose();
        batch.dispose();
        manager.dispose();
    }

}
