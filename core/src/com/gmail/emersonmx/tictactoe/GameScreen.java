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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends BaseScreen implements GameListener {

    public static final Color PLAYER_1_COLOR = new Color(0xaaaaffff);
    public static final Color PLAYER_2_COLOR = new Color(0xaaffaaff);
    public static final Color MENU_COLOR = new Color(0xff8000ff);

    public static final int NO_PAUSE_TAP = -1;
    public static final int TAP_TO_START = 0;
    public static final int TAP_PLAYER_1_WINS = 1;
    public static final int TAP_PLAYER_2_WINS = 2;
    public static final int TAP_PLAYER_1_MATCH = 3;
    public static final int TAP_PLAYER_2_MATCH = 4;
    public static final int TAP_GAME_DRAWN = 5;
    public static final int TAP_DRAW = 6;
    public static final int TAP_WINDOW_LIST_SIZE = 7;

    //private int playerTurn;

    private boolean pauseToStart;
    private int pauseTap;

    public GameScreen(TicTacToe ttt) {
        super(ttt);

        pauseToStart = true;
        pauseTap = NO_PAUSE_TAP;

        create();
    }

    public void setPauseToStart(boolean tapToStart) {
        this.pauseToStart = tapToStart;
    }

    public boolean isPauseToStart() {
        return pauseToStart;
    }

    public void setPauseTap(int pauseTap) {
        this.pauseTap = pauseTap;
    }

    public int getPauseTap() {
        return pauseTap;
    }

    private void create() {
        stage.addActor(createHash());
        stage.addActor(createPlayerMarks("player_1_marks", PLAYER_1_COLOR));
        stage.addActor(createPlayerMarks("player_2_marks", PLAYER_2_COLOR));
        stage.addActor(createScoreLine());
        stage.addActor(createScoreSeparators());
        stage.addActor(createMenu());
        stage.addActor(createPlayerScore("player_1_score", 118, 87,
                                         PLAYER_1_COLOR));
        stage.addActor(createPlayerScore("player_2_score", 362, 87,
                                         PLAYER_2_COLOR));
        stage.addActor(createPlayerTurn());
        //stage.addActor(createTapWindowList());
    }

    private Actor createHash() {
        Group group = new Group();
        group.setName("hash");

        GridPoint2[] layout = new GridPoint2[] {
            new GridPoint2(179, 401), new GridPoint2(240, 462),
                new GridPoint2(301, 401), new GridPoint2(240, 340)
        };

        Sprite sprite = null;
        GridPoint2 point = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];

            sprite = ttt.atlas.createSprite("hash_line");
            sprite.setCenter(point.x, point.y);
            if (i % 2 != 0) {
                sprite.rotate(90);
            }

            group.addActor(new SpriteActor("hash_line_" + i, sprite));
        }

        return group;
    }

    private Actor createPlayerMarks(String name, Color color) {
        Group group = new Group();
        group.setName(name);

        GridPoint2[] layout = new GridPoint2[] {
            new GridPoint2(118, 523), new GridPoint2(240, 523),
                new GridPoint2(362, 523),
            new GridPoint2(118, 401), new GridPoint2(240, 401),
                new GridPoint2(362, 401),
            new GridPoint2(118, 279), new GridPoint2(240, 279),
                new GridPoint2(362, 279)
        };

        GridPoint2 point;
        for (int i = 0; i < layout.length; i++) {
            point = layout[i];

            Sprite[] sprites = new Sprite[2];
            sprites[Player.MARK_O] = ttt.atlas.createSprite("mark_o");
            sprites[Player.MARK_X] = ttt.atlas.createSprite("mark_x");
            for (Sprite sprite : sprites) {
                sprite.setCenter(point.x, point.y);
                sprite.setColor(color);
            }

            Array<Sprite> array = new Array<Sprite>(sprites);
            SpritesActor actor = new SpritesActor(name + "_" + i, array);
            actor.setIndex(SpritesActor.HIDDEN);
        }

        return group;
    }

    private Actor createScoreLine() {
        Sprite sprite = ttt.atlas.createSprite("score_line");
        sprite.setCenter(240, 131);

        return new SpriteActor("score_line", sprite);
    }

    private Actor createScoreSeparators() {
        Group group = new Group();
        group.setName("score_separators");

        GridPoint2[] layout = new GridPoint2[] {
            new GridPoint2(192, 87), new GridPoint2(288, 87)
        };

        GridPoint2 point = null;
        Sprite sprite = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];

            sprite = ttt.atlas.createSprite("score_separator");
            sprite.setCenter(point.x, point.y);
            group.addActor(new SpriteActor("score_separator_" + i, sprite));
        }

        return group;
    }

    private Actor createMenu() {
        Sprite sprite = ttt.atlas.createSprite("menu");
        sprite.setCenter(240, 87);
        sprite.setColor(MENU_COLOR);

        return new SpriteActor("menu", sprite);
    }

    private Actor createPlayerScore(String name, float x, float y,
            Color color) {

        Array<Sprite> sprites = ttt.atlas.createSprites("score_number");

        for (Sprite sprite : sprites) {
            sprite.setCenter(x, y);
            sprite.setColor(color);
        }

        return new SpritesActor(name, sprites);
    }

    private Actor createPlayerTurn() {
        Sprite[] sprites = new Sprite[2];

        Sprite sprite = ttt.atlas.createSprite("player_turn");
        sprite.setCenter(57, 87);
        sprite.setColor(PLAYER_1_COLOR);
        sprites[Player.PLAYER_1] = sprite;

        sprite = ttt.atlas.createSprite("player_turn");
        sprite.setCenter(423, 87);
        sprite.setColor(PLAYER_2_COLOR);
        sprites[Player.PLAYER_2] = sprite;

        Array<Sprite> array = new Array<Sprite>(sprites);
        SpritesActor actor = new SpritesActor("player_turn", array);
        actor.setIndex(SpritesActor.HIDDEN);

        return actor;
    }

    public Drawable createTapWindowList() {
        return new Drawable() {

            private Sprite[] sprites;

            @Override
            public void create() {
                sprites = new Sprite[TAP_WINDOW_LIST_SIZE];
                sprites[TAP_TO_START] = ttt.atlas.createSprite("tap_to_start");
                sprites[TAP_PLAYER_1_WINS] =
                    ttt.atlas.createSprite("player_one_wins");
                sprites[TAP_PLAYER_2_WINS] =
                    ttt.atlas.createSprite("player_two_wins");
                sprites[TAP_PLAYER_1_MATCH] =
                    ttt.atlas.createSprite("match_winner_one");
                sprites[TAP_PLAYER_2_MATCH] =
                    ttt.atlas.createSprite("match_winner_two");
                sprites[TAP_GAME_DRAWN] = ttt.atlas.createSprite("game_drawn");
                sprites[TAP_DRAW] = ttt.atlas.createSprite("draw");

                for (int i = 0; i < sprites.length; ++i) {
                    sprites[i].setCenter(TicTacToe.WINDOW_WIDTH / 2.f,
                            TicTacToe.WINDOW_HEIGHT / 2.f);
                }
            }

            @Override
            public void draw(Batch batch) {
                if (pauseTap != NO_PAUSE_TAP) {
                    Sprite window;
                    switch (pauseTap) {
                        case TAP_PLAYER_1_WINS:
                            window = sprites[TAP_PLAYER_1_WINS];
                            break;
                        case TAP_PLAYER_2_WINS:
                            window = sprites[TAP_PLAYER_2_WINS];
                            break;
                        case TAP_PLAYER_1_MATCH:
                            window = sprites[TAP_PLAYER_1_MATCH];
                            break;
                        case TAP_PLAYER_2_MATCH:
                            window = sprites[TAP_PLAYER_2_MATCH];
                            break;
                        case TAP_GAME_DRAWN:
                            window = sprites[TAP_GAME_DRAWN];
                            break;
                        case TAP_DRAW:
                            window = sprites[TAP_DRAW];
                            break;
                        case TAP_TO_START:
                        default:
                            window = sprites[TAP_TO_START];
                            break;
                    }

                    window.draw(batch);
                }
            }

        };
    }

    @Override
    public void show() {
        ttt.playGame();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(new InputAdapter());
        ttt.quitGame();
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void cleanBoard() {
    }

    @Override
    public void gameStart(GameEvent event) {
        System.out.println("Game Start");
        //Game game = (Game) event.getSource();

        pauseTap = TAP_TO_START;
    }

    @Override
    public void gameOver(GameEvent event) {
        System.out.println("Game Over");
    }

    @Override
    public void gameEnd(GameEvent event) {
        System.out.println("Game End");
    }

    @Override
    public void marked(GameEvent event) {
        System.out.println("Marked");
        //Game game = (Game) event.getSource();
    }

    @Override
    public void gameWinner(GameEvent event) {
        //Game game = (Game) event.getSource();
        //copyScore(game);
        //if (game.getWinner() == Player.PLAYER_1) {
        //    pauseTap = TAP_PLAYER_1_WINS;
        //} else if (game.getWinner() == Player.PLAYER_2) {
        //    pauseTap = TAP_PLAYER_2_WINS;
        //}
    }

    @Override
    public void gameDraw(GameEvent event) {
        System.out.println("Game Draw");
        pauseTap = TAP_DRAW;
    }

    @Override
    public void gameMatchWinner(GameEvent event) {
        System.out.println("Match Winner");
        Game game = (Game) event.getSource();

        int matchWinner = Player.matchWinner(game.getMatch(),
                                             game.getPlayers());
        if (matchWinner == Player.PLAYER_1) {
            pauseTap = TAP_PLAYER_1_MATCH;
        } else if (matchWinner == Player.PLAYER_2) {
            pauseTap = TAP_PLAYER_2_MATCH;
        } else {
            pauseTap = TAP_GAME_DRAWN;
        }

        Gdx.input.setInputProcessor(new InputAdapter());
    }

    @Override
    public void currentPlayerChanged(GameEvent event) {
        System.out.println("Player Changed");
        Game game = (Game) event.getSource();
        Player currentPlayer = game.getCurrentPlayer();

        Group root = stage.getRoot();
        SpritesActor playerTurn = root.findActor("player_turn");

        playerTurn.setIndex(currentPlayer.id);
    }

    @Override
    public void invalidPosition(GameEvent event) {
        System.out.println("Invalid Position");
    }

    @Override
    public void positionIsNotEmpty(GameEvent event) {
        System.out.println("Position is not Empty");
    }

}
