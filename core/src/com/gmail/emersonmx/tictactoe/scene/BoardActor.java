package com.gmail.emersonmx.tictactoe.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.gmail.emersonmx.tictactoe.Point;

public class BoardActor extends Group {

    private Sprite[] lines;
    private Point[] layout;

    public BoardActor(Sprite[] lines) {
        this.lines = lines;

        layout = new Point[] {
            new Point(179, 401), new Point(240, 462),
            new Point(301, 401), new Point(240, 340)
        };

        Point point = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];
            lines[i].setOriginCenter();
            lines[i].setCenter(point.x, point.y);

            if ((i % 2) != 0) {
                lines[i].rotate(90);
            }
        }

        setName("board");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Sprite line : lines) {
            line.draw(batch, parentAlpha);
        }
    }

}
