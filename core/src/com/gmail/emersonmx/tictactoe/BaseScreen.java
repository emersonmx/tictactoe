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

package com.gmail.emersonmx.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BaseScreen extends ScreenAdapter {

    protected final TicTacToe ttt;

    protected Viewport viewport;
    protected OrthographicCamera camera;

    protected Sprite background;
    protected Sprite blackboard;

    public BaseScreen(TicTacToe ttt) {
        this.ttt = ttt;

        background = createBackground();
        blackboard = createBlackboard();
        setup();
    }

    public Viewport getViewport() {
        return viewport;
    }

    protected Sprite createBackground() {
        Texture texture = ttt.manager.get("background.png", Texture.class);
        texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        Sprite sprite = new Sprite(texture);
        sprite.setBounds(0, 0, TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT);
        sprite.setRegion(0, 0, 5, 5);

        return sprite;
    }

    protected Sprite createBlackboard() {
        return new Sprite(ttt.manager.get("blackboard.png", Texture.class));
    }

    protected void setup() {
        viewport = new FitViewport(TicTacToe.WINDOW_WIDTH,
                                   TicTacToe.WINDOW_HEIGHT);
        camera = (OrthographicCamera) viewport.getCamera();
        camera.setToOrtho(false, TicTacToe.WINDOW_WIDTH,
                          TicTacToe.WINDOW_HEIGHT);

        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.update();
        ttt.batch.setProjectionMatrix(camera.combined);

        ttt.batch.begin();
        background.draw(ttt.batch);
        blackboard.draw(ttt.batch);
        ttt.batch.end();
    }

}
