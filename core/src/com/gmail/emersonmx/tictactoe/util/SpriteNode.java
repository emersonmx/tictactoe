package com.gmail.emersonmx.tictactoe.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteNode extends Actor {

    private Sprite sprite;

    public SpriteNode(String name, Sprite sprite) {
        this.sprite = sprite;

        this.sprite.setOriginCenter();
        setName(name);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }

}
