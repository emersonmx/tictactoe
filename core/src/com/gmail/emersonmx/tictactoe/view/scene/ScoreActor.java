package com.gmail.emersonmx.tictactoe.view.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ScoreActor extends Actor {

    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    private Array<Sprite> scoreArray;
    private Sprite[] scoreSprites;
    private int[] scores;
    private Color[] colors;

    public ScoreActor(Array<Sprite> scoreArray) {
        this.scoreArray = scoreArray;

        scoreSprites = new Sprite[] { null, null };

        for (Sprite score : scoreArray) {
            score.setOriginCenter();
        }

        scores = new int[] { 0, 0 };
        colors = new Color[] { null, null };

        setName("score");
    }

    public void setScore(int player, int score) {
        this.scores[player] = score;
    }

    public int getScore(int player) {
        return scores[player];
    }

    public void setColor(int player, Color color) {
        this.colors[player] = color;
    }

    public Color getColor(int player) {
        return colors[player];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        scoreSprites[PLAYER_1] = scoreArray.get(scores[PLAYER_1]);
        scoreSprites[PLAYER_1].setCenter(118, 87);
        scoreSprites[PLAYER_1].setColor(colors[PLAYER_1]);
        scoreSprites[PLAYER_1].draw(batch, parentAlpha);

        scoreSprites[PLAYER_2] = scoreArray.get(scores[PLAYER_2]);
        scoreSprites[PLAYER_2].setCenter(362, 87);
        scoreSprites[PLAYER_2].setColor(colors[PLAYER_2]);
        scoreSprites[PLAYER_2].draw(batch, parentAlpha);
    }

}
