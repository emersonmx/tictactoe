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

    public AssetManager manager;
    public TextureAtlas atlas;
    public Viewport viewport;
    public Camera camera;

    private GameView gameView;
    private View currentView;

    private Texture background;
    private Sprite blackboard;

    public ViewManager(AssetManager manager, TextureAtlas atlas,
            Viewport viewport) {

        this.manager = manager;
        this.atlas = atlas;
        this.viewport = viewport;
        this.camera = viewport.getCamera();

        currentView = null;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void changeView(View view) {
        if (view != null) {
            currentView.leave();
            view.enter();
            currentView = view;
        }
    }

    public void create() {
        background = createBackground();
        blackboard = createBlackboard();

        gameView = createGameView();

        currentView = gameView;
        currentView.enter();
    }

    protected Texture createBackground() {
        Texture texture = manager.get("background.png", Texture.class);
        texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        return texture;
    }

    protected Sprite createBlackboard() {
        return atlas.createSprite("blackboard");
    }

    protected GameView createGameView() {
        GameView gameView = new GameView(this);
        gameView.create();

        GameController controller = new GameController();
        Game game = new Game();

        gameView.setController(controller);
        controller.setView(gameView);
        controller.setGame(game);
        game.setListener(gameView);

        game.setup();

        return gameView;
    }

    public void logic(float deltaTime) {
        currentView.logic(deltaTime);
    }

    public void draw(Batch batch) {
        viewport.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 0, 0, 5, 5);

        blackboard.draw(batch);
        batch.end();

        currentView.draw(batch);
    }

    public void dispose() {
        gameView.dispose();
    }

}
