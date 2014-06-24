package com.gmail.emersonmx.tictactoe.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HashBoardActor extends Actor {

    private class Point {
        public float x;
        public float y;

        public Point(float x, float y) { set(x, y); }
        public void set(float x, float y) { this.x = x; this.y = y; }
    }

    private Sprite[] hashLines;
    private Point[] layout;

    public HashBoardActor(Sprite[] hashLines) {
        this.hashLines = hashLines;
        layout = new Point[] {
            new Point(179, 401), new Point(240, 462),
            new Point(301, 401), new Point(240, 340)
        };

        Point point = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];
            hashLines[i].setOriginCenter();
            hashLines[i].setCenter(point.x, point.y);

            if ((i % 2) != 0) {
                hashLines[i].rotate(90);
            }
        }

        setName("hashboard");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Sprite line : hashLines) {
            line.draw(batch, parentAlpha);
        }
    }

}
