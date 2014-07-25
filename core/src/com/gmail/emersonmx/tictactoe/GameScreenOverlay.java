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

package com.gmail.emersonmx.tictactoe;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class GameScreenOverlay extends BaseScreenOverlay {

    public static final int NO_PAUSE_TAP = SpritesActor.HIDDEN;
    public static final int TAP_TO_START = 0;
    public static final int TAP_PLAYER_1_WINS = 1;
    public static final int TAP_PLAYER_2_WINS = 2;
    public static final int TAP_PLAYER_1_MATCH = 3;
    public static final int TAP_PLAYER_2_MATCH = 4;
    public static final int TAP_GAME_DRAWN = 5;
    public static final int TAP_DRAW = 6;
    public static final int TAP_WINDOW_LIST_SIZE = 7;

    private SpritesActor tapSprite;

    private boolean matchDone;

    public GameScreenOverlay(TicTacToe ttt) {
        super(ttt);

        matchDone = false;
    }

    public void setTapSprite(int index) {
        tapSprite.setIndex(index);
    }

    public int getTapSprite() {
        return tapSprite.getIndex();
    }

    public void matchDone() {
        matchDone = true;
    }

    @Override
    protected void create() {
        stage.addActor(createTapWindowList());
    }

    private Actor createTapWindowList() {
        Sprite[] sprites = new Sprite[TAP_WINDOW_LIST_SIZE];
        sprites[TAP_TO_START] = ttt.atlas.createSprite("tap_to_start");
        sprites[TAP_PLAYER_1_WINS] = ttt.atlas.createSprite("player_one_wins");
        sprites[TAP_PLAYER_2_WINS] = ttt.atlas.createSprite("player_two_wins");
        sprites[TAP_PLAYER_1_MATCH] =
            ttt.atlas.createSprite("match_winner_one");
        sprites[TAP_PLAYER_2_MATCH] =
            ttt.atlas.createSprite("match_winner_two");
        sprites[TAP_GAME_DRAWN] = ttt.atlas.createSprite("game_drawn");
        sprites[TAP_DRAW] = ttt.atlas.createSprite("draw");

        for (Sprite sprite : sprites) {
            sprite.setCenter(TicTacToe.WINDOW_WIDTH / 2.f,
                             TicTacToe.WINDOW_HEIGHT / 2.f);

        }

        tapSprite = new SpritesActor("tap_window", new Array<Sprite>(sprites));
        tapSprite.setIndex(TAP_TO_START);
        tapSprite.setTouchable(Touchable.enabled);
        tapSprite.setBounds(0, 0,
                            TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT);
        tapSprite.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TAP");
                if (matchDone) {
                    ttt.setScreen(ttt.getMenuScreen());
                } else {
                    ttt.setScreen(ttt.getGameScreen());
                }
            }

        });

        return tapSprite;
    }

    @Override
    public void hide() {
        tapSprite.setIndex(NO_PAUSE_TAP);
    }

}
