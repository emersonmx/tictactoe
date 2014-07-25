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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends BaseScreen implements GameListener {

    public static final Color PLAYER_1_COLOR = new Color(0xaaaaffff);
    public static final Color PLAYER_2_COLOR = new Color(0xaaffaaff);
    public static final Color MENU_COLOR = new Color(0xff8000ff);


    public GameScreen(TicTacToe ttt) {
        super(ttt);
    }

    @Override
    protected void create() {
        super.create();

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

        GridPoint2[] layout = createPlayerMarkLayout();
        Array<Rectangle> rectangles = createPlayerMarkRectangles();

        GridPoint2 point;
        Rectangle rectangle;
        for (int i = 0; i < layout.length; i++) {
            point = layout[i];
            rectangle = rectangles.get(i);

            Sprite[] sprites = createMarks();
            for (Sprite sprite : sprites) {
                sprite.setCenter(point.x, point.y);
                sprite.setColor(color);
            }

            Array<Sprite> array = new Array<Sprite>(sprites);
            SpritesActor actor = new SpritesActor(name + "_" + i, array);
            actor.setIndex(SpritesActor.HIDDEN);
            actor.setTouchable(Touchable.enabled);
            actor.setBounds(rectangle.x, rectangle.y,
                            rectangle.width, rectangle.height);
            actor.setUserObject(i);
            actor.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SpritesActor actor =
                        (SpritesActor) event.getListenerActor();
                    Integer mark = (Integer) actor.getUserObject();
                    ttt.mark(mark);
                    System.out.println("Mark " + mark + " touched");
                }

            });

            group.addActor(actor);
        }

        return group;
    }

    private GridPoint2[] createPlayerMarkLayout() {
        return new GridPoint2[] {
            new GridPoint2(118, 523), new GridPoint2(240, 523),
                new GridPoint2(362, 523),
            new GridPoint2(118, 401), new GridPoint2(240, 401),
                new GridPoint2(362, 401),
            new GridPoint2(118, 279), new GridPoint2(240, 279),
                new GridPoint2(362, 279)
        };
    }

    private Array<Rectangle> createPlayerMarkRectangles() {
        Array<Rectangle> rectangles = new Array<Rectangle>(10);

        float width, height;
        width = height = 108;

        GridPoint2 point = new GridPoint2(64, 469);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                rectangles.add(
                        new Rectangle(point.x, point.y, width, height));
                point.x += 122;
            }

            point.x = 64;
            point.y -= 122;
        }

        return rectangles;
    }

    public Sprite[] createMarks() {
        Sprite[] sprites = new Sprite[2];
        sprites[Player.MARK_O] = ttt.atlas.createSprite("mark_o");
        sprites[Player.MARK_X] = ttt.atlas.createSprite("mark_x");

        return sprites;
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

        SpriteActor actor = new SpriteActor("menu", sprite);
        actor.setTouchable(Touchable.enabled);
        actor.setBounds(198, 43, 85, 83);
        actor.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Menu touched");
            }

        });

        return actor;
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        ttt.playGame();
    }

    @Override
    public void gameStart(GameEvent event) {
        System.out.println("Game Start");
        cleanBoard(stage.getRoot());
    }

    private void cleanBoard(Group root) {
        SpritesActor actor;
        for (int i = 0; i < 9; i++) {
            actor = root.findActor("player_1_marks_" + i);
            actor.setIndex(SpritesActor.HIDDEN);
            actor = root.findActor("player_2_marks_" + i);
            actor.setIndex(SpritesActor.HIDDEN);
        }
    }

    @Override
    public void gameOver(GameEvent event) {
        System.out.println("Game Over");
        Game game = (Game) event.getSource();

        GameScreenOverlay overlay = ttt.getGameScreenOverlay();
        if (game.isMatchDone()) {
            int winner = game.getMatchWinner();
            if (winner == Player.PLAYER_1) {
                overlay.setTapSprite(GameScreenOverlay.TAP_PLAYER_1_MATCH);
            } else if (winner == Player.PLAYER_2) {
                overlay.setTapSprite(GameScreenOverlay.TAP_PLAYER_2_MATCH);
            } else {
                overlay.setTapSprite(GameScreenOverlay.TAP_DRAW);
            }

            updateScore(game);
            overlay.matchDone();

            ttt.setScreen(overlay);
            System.out.println("Match done");
        } else if (game.hasWinner()) {
            int winner = game.getWinner();
            if (winner == Player.PLAYER_1) {
                overlay.setTapSprite(GameScreenOverlay.TAP_PLAYER_1_WINS);
            } else if (winner == Player.PLAYER_2) {
                overlay.setTapSprite(GameScreenOverlay.TAP_PLAYER_2_WINS);
            } else {
                overlay.setTapSprite(GameScreenOverlay.TAP_GAME_DRAWN);
            }

            updateScore(game);

            ttt.setScreen(overlay);
            System.out.println("Winner");
        } else if (game.isDraw()) {
            overlay.setTapSprite(GameScreenOverlay.TAP_DRAW);

            ttt.setScreen(overlay);
            System.out.println("Draw");
        }
    }

    private void updateScore(Game game) {
        Group root = stage.getRoot();
        SpritesActor actor;
        int winner = game.getWinner();
        Player[] players = game.getPlayers();

        if (winner == Player.PLAYER_1) {
            actor = root.findActor("player_1_score");
            actor.setIndex(players[winner].score);
        } else if (winner == Player.PLAYER_2) {
            actor = root.findActor("player_2_score");
            actor.setIndex(players[winner].score);
        }
    }

    @Override
    public void playerChanged(GameEvent event) {
        System.out.println("Player Changed");
        Game game = (Game) event.getSource();
        Player currentPlayer = game.getCurrentPlayer();

        Group root = stage.getRoot();
        SpritesActor playerTurn = root.findActor("player_turn");

        playerTurn.setIndex(currentPlayer.id);
    }

    @Override
    public void marked(GameEvent event) {
        System.out.println("Marked");
        Game game = (Game) event.getSource();
        Group root = stage.getRoot();

        int boardMark;
        int playerId;
        int mark;
        String name;
        SpritesActor actor;
        for (int i = 0; i < Game.BOARD_HEIGHT; i++) {
            for (int j = 0; j < Game.BOARD_WIDTH; j++) {
                boardMark = game.getBoardMark(i, j);
                playerId = Player.byMark(boardMark, game.getPlayers());

                if (playerId == Player.PLAYER_1) {
                    name = "player_1_marks";
                } else if (playerId == Player.PLAYER_2) {
                    name = "player_2_marks";
                } else {
                    name = "";
                }

                if (!name.isEmpty()) {
                    mark = Game.indexMark(i, j);
                    actor = root.findActor(name + "_" + mark);
                    actor.setIndex(boardMark);
                }
            }
        }
    }

}
