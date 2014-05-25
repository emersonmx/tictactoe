package com.gmail.emersonmx.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TicTacToeGame extends Application {

    OrthographicCamera camera;
    SpriteBatch batch;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 240, 320);

        batch = new SpriteBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
