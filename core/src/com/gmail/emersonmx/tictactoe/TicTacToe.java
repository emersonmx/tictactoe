package com.gmail.emersonmx.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TicTacToe extends Application {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 640;

    OrthographicCamera camera;
    SpriteBatch batch;
    AssetManager manager;
    TextureAtlas atlas;

    Texture background;
    Sprite blackboard;
    Sprite score;
    Sprite[] hashLineHorizontal;
    Sprite[] hashLineVertical;

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
        manager.load("game.atlas", TextureAtlas.class);
        manager.load("background.png", Texture.class);
        manager.finishLoading();

        background = manager.get("background.png");
        background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        atlas = manager.get("game.atlas", TextureAtlas.class);

        blackboard = atlas.createSprite("board");
        score = atlas.createSprite("score");
        score.setPosition(38, 46);

        hashLineHorizontal = new Sprite[2];
        for (int i = 0; i < hashLineHorizontal.length; ++i) {
            hashLineHorizontal[i] = atlas.createSprite("hash_line_horizontal");
        }
        float hashLineCenterX = (WIDTH / 2 -
            hashLineHorizontal[0].getWidth() / 2);
        hashLineHorizontal[0].setPosition(hashLineCenterX, 462);
        hashLineHorizontal[1].setPosition(hashLineCenterX, 340);

        hashLineVertical = new Sprite[2];
        for (int i = 0; i < hashLineVertical.length; ++i) {
            hashLineVertical[i] = atlas.createSprite("hash_line_vertical");
        }
        float hashLineVerticalCenter = 224;
        hashLineVertical[0].setPosition(171, hashLineVerticalCenter);
        hashLineVertical[1].setPosition(293, hashLineVerticalCenter);
    }

    public void setupSystem() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        batch = new SpriteBatch();
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

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, WIDTH, HEIGHT, 0, 0, 5, 5);
        blackboard.draw(batch);
        score.draw(batch);
        for (int i = 0; i < hashLineHorizontal.length; ++i) {
           hashLineHorizontal[i].draw(batch);
        }
        for (int i = 0; i < hashLineVertical.length; ++i) {
           hashLineVertical[i].draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        manager.dispose();
        batch.dispose();
    }

}
