package com.gmail.emersonmx.tictactoe.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlackboardActor extends Actor {

    private Sprite blackboard;

    public BlackboardActor(Sprite blackboard) {
        this.blackboard = blackboard;
        this.blackboard.setPosition(0, 0);

        setName("blackboard");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        blackboard.draw(batch, parentAlpha);
    }

}
