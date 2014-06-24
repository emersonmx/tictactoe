package com.gmail.emersonmx.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gmail.emersonmx.tictactoe.scene.BackgroundActor;
import com.gmail.emersonmx.tictactoe.scene.BlackboardActor;
import com.gmail.emersonmx.tictactoe.scene.HashBoardActor;
import com.gmail.emersonmx.tictactoe.scene.ScoreActor;


public class TicTacToe extends Application {

    public static final int WINDOW_WIDTH = 480;
    public static final int WINDOW_HEIGHT = 640;

    AssetManager manager;
    TextureAtlas atlas;
    Stage stage;

    @Override
    public void create() {
        loadResources();
        setupSystem();
        setupScene();

        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
    }

    public void loadResources() {
        manager = new AssetManager();
        manager.load("background.png", Texture.class);
        manager.load("game.atlas", TextureAtlas.class);
        manager.finishLoading();

        atlas = manager.get("game.atlas");
    }

    public void setupSystem() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().update(WINDOW_WIDTH, WINDOW_HEIGHT, true);

        Texture background = manager.get("background.png", Texture.class);
        stage.addActor(new BackgroundActor(background));

        Sprite board = atlas.createSprite("board");
        stage.addActor(new BlackboardActor(board));

        Sprite[] hashLines = new Sprite[4];
        for (int i = 0; i < hashLines.length; ++i) {
            hashLines[i] = atlas.createSprite("hash_line");
        }
        stage.addActor(new HashBoardActor(hashLines));

        Sprite line = atlas.createSprite("line");
        Sprite divide = atlas.createSprite("divide");
        stage.addActor(new ScoreActor(line, divide));

        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    public void setupScene() {
    }

    public void events() {
    }

    public void logic() {
    }

    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        manager.dispose();
    }

}
