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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BaseScreen extends ScreenAdapter {

    protected final TicTacToe ttt;

    protected Stage stage;

    public BaseScreen(TicTacToe ttt) {
        this.ttt = ttt;

        setup();

        stage.addActor(createBackground());
        stage.addActor(createBlackboard());
    }

    protected void setup() {
        Viewport viewport =
            new FitViewport(TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.setToOrtho(false, TicTacToe.WINDOW_WIDTH,
                          TicTacToe.WINDOW_HEIGHT);

        Gdx.gl.glClearColor(0, 0, 0, 1);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    protected Actor createBackground() {
        Texture texture = ttt.manager.get("background.png", Texture.class);
        texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        Sprite sprite = new Sprite(texture);
        sprite.setBounds(0, 0, TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT);
        sprite.setRegion(0, 0, 5, 5);

        return new SpriteActor("background", sprite);

    }

    protected Actor createBlackboard() {
        Sprite sprite =
            new Sprite(ttt.manager.get("blackboard.png", Texture.class));
        return new SpriteActor("blackboard", sprite);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void logic(float delta) {
        stage.act(delta);
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

}
