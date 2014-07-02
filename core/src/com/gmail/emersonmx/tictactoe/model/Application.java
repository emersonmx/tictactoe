package com.gmail.emersonmx.tictactoe.model;

import com.badlogic.gdx.ApplicationListener;

public class Application implements ApplicationListener {

    @Override
    public void create() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        logic();
        draw();
    }

    public void logic() {
    }

    public void draw() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
