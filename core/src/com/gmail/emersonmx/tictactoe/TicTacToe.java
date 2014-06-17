package com.gmail.emersonmx.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TicTacToe extends Application {

    public static final int WIDTH = 240;
    public static final int HEIGHT = 320;

    OrthographicCamera camera;
    SpriteBatch batch;
    AssetManager manager;

    Sprite blackboard;
    Texture blackboardImage;
    Sprite gameBoard;
    Texture gameBoardImage;
    Sprite collision;
    Texture collisionImage;
    Texture[] textures;

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
        manager.load("blackboard.png", Texture.class);
        manager.load("game_board.png", Texture.class);
        manager.load("collision.png", Texture.class);
        manager.load("square.png", Texture.class);
        manager.finishLoading();
    }

    public void setupSystem() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        batch = new SpriteBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    public void setupScene() {
        blackboard = new Sprite(manager.get("blackboard.png", Texture.class));
        blackboard.setBounds(WIDTH / 2.f - HEIGHT / 2.f, 0.f, HEIGHT, HEIGHT);
        gameBoard = new Sprite(manager.get("game_board.png", Texture.class));
        gameBoard.setBounds(0, HEIGHT - WIDTH, WIDTH, WIDTH);
        collision = new Sprite(manager.get("collision.png", Texture.class));
        collision.setBounds(0, HEIGHT - WIDTH, WIDTH, WIDTH);
    }

    public void events() {
    }

    public void logic() {
    }

    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        blackboard.draw(batch);
        collision.draw(batch);
        gameBoard.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        manager.dispose();
        batch.dispose();
    }

}
