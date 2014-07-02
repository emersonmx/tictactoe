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
import com.gmail.emersonmx.tictactoe.view.GameView;

public class GameApplication extends Application {

    public static final int WINDOW_WIDTH = 480;
    public static final int WINDOW_HEIGHT = 640;
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    public AssetManager manager;
    public TextureAtlas atlas;

    public Viewport viewport;
    public Batch batch;

    public GameView gameView;

    @Override
    public void create() {
        loadResources();
        setup();
    }

    public void loadResources() {
        manager = new AssetManager();
        manager.load("background.png", Texture.class);
        manager.load("game.atlas", TextureAtlas.class);
        manager.finishLoading();

        atlas = manager.get("game.atlas");
    }

    public void setup() {
        viewport = new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.setToOrtho(false);

        batch = new SpriteBatch();

        gameView = new GameView(this);
        gameView.setup();

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
    }

    public void events() {
    }

    public void logic() {
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameView.draw();
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
