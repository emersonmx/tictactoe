package com.gmail.emersonmx.tictactoe.view.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class MenuActor extends Actor {

    private Sprite menu;

    public MenuActor(Sprite menu) {
        this.menu = menu;

        this.menu.setOriginCenter();
        this.menu.setCenter(240, 87);

        setTouchable(Touchable.enabled);
        setBounds(193, 43, 95, 88);

        setName("menu");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        menu.draw(batch, parentAlpha);
    }

}
