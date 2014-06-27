package com.gmail.emersonmx.tictactoe.view.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gmail.emersonmx.tictactoe.application.TicTacToe;

public class BackgroundActor extends Actor {

    Texture background;

    public BackgroundActor(Texture background) {
        this.background = background;
        background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        setName("background");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, 0, 0,
            TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT, 0, 0, 5, 5);
    }

}
