/*
  Copyright (C) 2014 Emerson Max de Medeiros Silva

  This file is part of tictactoe.

  tictactoe is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  tictactoe is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with tictactoe.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.gmail.emersonmx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.gmail.emersonmx.tictactoe.model.Game;

public class GameView extends AbstractView {

    public static final Color PLAYER_1_COLOR = new Color(0x8080ffff);
    public static final Color PLAYER_2_COLOR = new Color(0xff8080ff);

    private TextureAtlas atlas;
    private Batch batch;

    private Array<Sprite> hash;
    private Sprite scoreLine;
    private Array<Sprite> scoreSeparators;
    private Sprite menu;
    private Array<Sprite> playerOneScore;
    private Array<Sprite> playerTwoScore;
    private GameInput input;

    private int[] playerScores;

    public GameView(ViewManager viewManager) {
        super();

        this.atlas = viewManager.atlas;
        this.batch = viewManager.batch;

        playerScores = new int[] { 0, 0 };
        input = new GameInput(viewManager);
    }

    @Override
    public void create() {
        hash = createHash();
        scoreLine = createScoreLine();
        scoreSeparators = createScoreSeparators();
        menu = createMenu();
        playerOneScore = createPlayerOneScore();
        playerTwoScore = createPlayerTwoScore();

        loaded = true;
    }

    @Override
    public void setup() {
        Gdx.input.setInputProcessor(input);
    }

    protected Array<Sprite> createHash() {
        Array<Sprite> sprites = new Array<Sprite>(4);
        GridPoint2[] layout = new GridPoint2[] {
            new GridPoint2(179, 401), new GridPoint2(240, 462),
            new GridPoint2(301, 401), new GridPoint2(240, 340)
        };

        Sprite sprite = null;
        GridPoint2 point = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];

            sprite = atlas.createSprite("hash_line");
            sprite.setCenter(point.x, point.y);
            if (i % 2 != 0) {
                sprite.rotate(90);
            }

            sprites.add(sprite);
        }

        return sprites;
    }

    protected Sprite createScoreLine() {
        Sprite sprite = new Sprite();
        sprite = atlas.createSprite("score_line");
        sprite.setCenter(240, 131);

        return sprite;
    }

    protected Array<Sprite> createScoreSeparators() {
        Array<Sprite> sprites = new Array<Sprite>(2);
        GridPoint2[] layout = new GridPoint2[] {
            new GridPoint2(192, 87), new GridPoint2(288, 87)
        };

        GridPoint2 point = null;
        Sprite sprite = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];

            sprite = atlas.createSprite("score_separator");
            sprite.setCenter(point.x, point.y);
            sprites.add(sprite);
        }

        return sprites;
    }

    protected Sprite createMenu() {
        Sprite sprite = atlas.createSprite("menu");
        sprite.setCenter(240, 87);

        return sprite;
    }

    protected Array<Sprite> createPlayerOneScore() {
        Array<Sprite> sprites = atlas.createSprites("score_number");
        for (Sprite score : sprites) {
            score.setCenter(118, 87);
            score.setColor(PLAYER_1_COLOR);
        }

        return sprites;
    }

    protected Array<Sprite> createPlayerTwoScore() {
        Array<Sprite> sprites = atlas.createSprites("score_number");
        for (Sprite score : sprites) {
            score.setCenter(362, 87);
            score.setColor(PLAYER_2_COLOR);
        }

        return sprites;
    }

    @Override
    public void logic(float deltaTime) {
    }

    @Override
    public void draw() {
        batch.begin();
        for (Sprite hashLine : hash) {
            hashLine.draw(batch);
        }

        scoreLine.draw(batch);
        for (Sprite scoreSeparator : scoreSeparators) {
            scoreSeparator.draw(batch);
        }

        menu.draw(batch);

        playerOneScore.get(playerScores[Game.PLAYER_1]).draw(batch);
        playerTwoScore.get(playerScores[Game.PLAYER_2]).draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
    }

}
