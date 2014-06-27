package com.gmail.emersonmx.tictactoe.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.gmail.emersonmx.tictactoe.Point;

public class BottomGroupActor extends Group {

    private ScoreActor scoreActor;
    private MenuActor menuActor;
    private Sprite line;
    private Sprite[] separators;

    public BottomGroupActor(Sprite line, Sprite[] separators,
            Array<Sprite> scoreArray, Sprite menu) {

        this.line = line;
        this.separators = separators;

        line.setOriginCenter();
        line.setCenter(240, 131);
        Point[] separatorLayout = new Point[] {
            new Point(192, 87), new Point(288, 87)
        };

        Point point = null;
        Sprite separator = null;
        for (int i = 0; i < separatorLayout.length; ++i) {
            point = separatorLayout[i];
            separator = separators[i];
            separator.setOriginCenter();
            separator.setCenter(point.x, point.y);
        }

        scoreActor = new ScoreActor(scoreArray);
        addActor(scoreActor);
        menuActor = new MenuActor(menu);
        menuActor.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                    int pointer, int button) {

                System.out.println("<menu touch>");
                return true;
            }

        });
        addActor(menuActor);

        setName("bottom_group");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        line.draw(batch, parentAlpha);
        for (Sprite separator : separators) {
            separator.draw(batch, parentAlpha);
        }

        scoreActor.draw(batch, parentAlpha);
        menuActor.draw(batch, parentAlpha);
    }

}
