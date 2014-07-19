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

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TicTacToe extends com.badlogic.gdx.Game {

    public static final int WINDOW_WIDTH = 480;
    public static final int WINDOW_HEIGHT = 640;

    public AssetManager manager;
    public TextureAtlas atlas;
    public Batch batch;

    private Game game;

    private GameScreen gameScreen;

    public TicTacToe() {
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("background.png", Texture.class);
        manager.load("blackboard.png", Texture.class);
        manager.load("game.atlas", TextureAtlas.class);
        manager.finishLoading();

        atlas = manager.get("game.atlas");

        batch = new SpriteBatch();

        gameScreen = new GameScreen(this);

        game = new Game();
        game.setListener(gameScreen);

        setScreen(gameScreen);
    }

    @Override
    public void dispose() {
        super.dispose();

        getScreen().dispose();
        batch.dispose();
        manager.dispose();
    }

    public void playGame() {
        game.start();
    }

    public void restartGame() {
        game.restart();
    }

    public void quitGame() {
        game.quit();
    }

    public void mark(int index) {
        if (gameScreen == getScreen()) {
            int j = index % Game.BOARD_WIDTH;
            int i = (index - j) / Game.BOARD_HEIGHT;
            game.setBoardMark(i, j);
        }
    }

}
