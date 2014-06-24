package com.gmail.emersonmx.tictactoe.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ScoreActor extends Actor {

    private Sprite line;
    private Sprite divide;

    public ScoreActor(Sprite line, Sprite divide) {
        this.line = line;
        this.divide = divide;

        this.line.setOriginCenter();
        this.line.setCenter(240, 131);
        this.divide.setOriginCenter();
        this.divide.setCenter(240, 87);

        setName("score");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        line.draw(batch, parentAlpha);
        divide.draw(batch, parentAlpha);
    }

}
