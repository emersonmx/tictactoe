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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter implements GameListener {

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

    private final TicTacToe ttt;

    public Viewport viewport;
    private OrthographicCamera camera;

    private Array<Drawable> drawableList;

    private GameInput input;

    private int[] board;
    private int[] scores;
    private Player[] players;
    private int playerTurn;
    private GridPoint2[] boardLayout;
    private GridPoint2[] playerTurnLayout;

    private boolean pauseToStart;
    private int pauseTap;

    public GameScreen(TicTacToe ttt) {
        this.ttt = ttt;

        drawableList = new Array<Drawable>();

        int size = Game.BOARD_WIDTH * Game.BOARD_HEIGHT;
        board = new int[size];
        scores = new int[] { 0, 0 };

        pauseToStart = true;
        pauseTap = NO_PAUSE_TAP;

        create();
        setup();
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
        boardLayout = createBoardLayout();
        playerTurnLayout = createPlayerTurnLayout();

        drawableList.add(createBackground());
        drawableList.add(createBlackboard());
        drawableList.add(createHash());
        drawableList.add(createPlayerOneMarks());
        drawableList.add(createPlayerTwoMarks());
        drawableList.add(createScoreLine());
        drawableList.add(createScoreSeparators());
        drawableList.add(createMenu());
        drawableList.add(createPlayerOneScore());
        drawableList.add(createPlayerTwoScore());
        drawableList.add(createPlayerTurn());
        drawableList.add(createTapWindowList());

        for (Drawable drawable : drawableList) {
            drawable.create();
        }
    }

    protected GridPoint2[] createBoardLayout() {
        return new GridPoint2[] {
            new GridPoint2(118, 523), new GridPoint2(240, 523),
                new GridPoint2(362, 523),
            new GridPoint2(118, 401), new GridPoint2(240, 401),
                new GridPoint2(362, 401),
            new GridPoint2(118, 279), new GridPoint2(240, 279),
                new GridPoint2(362, 279)
        };
    }

    protected GridPoint2[] createPlayerTurnLayout() {
        return new GridPoint2[] {
            new GridPoint2(57, 87), new GridPoint2(423, 87)
        };
    }

    protected Drawable createBackground() {
        return new Drawable() {

            private Texture texture;

            @Override
            public void create() {
                texture = ttt.manager.get("background.png", Texture.class);
                texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
            }

            @Override
            public void draw(Batch batch) {
                batch.draw(texture, 0, 0, TicTacToe.WINDOW_WIDTH,
                    TicTacToe.WINDOW_HEIGHT, 0, 0, 5, 5);
            }

        };
    }

    protected Drawable createBlackboard() {
        return new Drawable() {

            private Texture texture;

            @Override
            public void create() {
                texture =  ttt.manager.get("blackboard.png", Texture.class);
            }

            @Override
            public void draw(Batch batch) {
                batch.draw(texture, 0, 0);
            }

        };
    }

    protected Drawable createHash() {
        return new Drawable() {

            private Array<Sprite> sprites;

            public void create() {
                sprites = new Array<Sprite>(4);
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

                    sprites.add(sprite);
                }
            }

            @Override
            public void draw(Batch batch) {
                for (Sprite sprite : sprites) {
                    sprite.draw(batch);
                }
            }
        };
    }

    protected Drawable createPlayerOneMarks() {
        return new Drawable() {

            private Sprite[] sprites;

            @Override
            public void create() {
                sprites = createMarks();

                for (Sprite sprite : sprites) {
                    sprite.setColor(PLAYER_1_COLOR);
                }
            }

            @Override
            public void draw(Batch batch) {
                Sprite mark;
                GridPoint2 point;
                int boardMark;
                int playerId;
                for (int i = 0; i < board.length; ++i) {
                    boardMark = board[i];
                    if (boardMark != Player.NO_MARK) {
                        point = boardLayout[i];
                        playerId = Player.byMark(boardMark, players);
                        if (playerId == Player.PLAYER_1) {
                            mark = sprites[boardMark];
                            mark.setCenter(point.x, point.y);
                            mark.draw(batch);
                        }
                    }

                }
            }

        };
    }

    protected Drawable createPlayerTwoMarks() {
        return new Drawable() {

            private Sprite[] sprites;

            @Override
            public void create() {
                sprites = createMarks();
                for (Sprite sprite : sprites) {
                    sprite.setColor(PLAYER_2_COLOR);
                }
            }

            @Override
            public void draw(Batch batch) {
                Sprite mark;
                GridPoint2 point;
                int boardMark;
                int playerId;
                for (int i = 0; i < board.length; ++i) {
                    boardMark = board[i];
                    if (boardMark != Player.NO_MARK) {
                        point = boardLayout[i];
                        playerId = Player.byMark(boardMark, players);
                        if (playerId == Player.PLAYER_2) {
                            mark = sprites[boardMark];
                            mark.setCenter(point.x, point.y);
                            mark.draw(batch);
                        }
                    }

                }
            }

        };
    }

    protected Sprite[] createMarks() {
        Sprite[] sprites = new Sprite[2];
        sprites[Player.MARK_O] = ttt.atlas.createSprite("mark_o");
        sprites[Player.MARK_X] = ttt.atlas.createSprite("mark_x");

        return sprites;
    }

    protected Drawable createScoreLine() {
        return new Drawable() {

            private Sprite sprite;

            @Override
            public void create() {
                sprite = ttt.atlas.createSprite("score_line");
                sprite.setCenter(240, 131);
            }

            @Override
            public void draw(Batch batch) {
                sprite.draw(batch);
            }

        };
    }

    protected Drawable createScoreSeparators() {
        return new Drawable() {

            private Array<Sprite> sprites;

            @Override
            public void create() {
                sprites = new Array<Sprite>(2);
                GridPoint2[] layout = new GridPoint2[] {
                    new GridPoint2(192, 87), new GridPoint2(288, 87)
                };

                GridPoint2 point = null;
                Sprite sprite = null;
                for (int i = 0; i < layout.length; ++i) {
                    point = layout[i];

                    sprite = ttt.atlas.createSprite("score_separator");
                    sprite.setCenter(point.x, point.y);
                    sprites.add(sprite);
                }
            }

            @Override
            public void draw(Batch batch) {
                for (Sprite sprite : sprites) {
                    sprite.draw(batch);
                }
            }

        };
    }

    protected Drawable createMenu() {
        return new Drawable() {

            private Sprite sprite;

            @Override
            public void create() {
                sprite = ttt.atlas.createSprite("menu");
                sprite.setCenter(240, 87);
                sprite.setColor(MENU_COLOR);
            }

            @Override
            public void draw(Batch batch) {
                sprite.draw(batch);
            }

        };
    }

    protected Drawable createPlayerOneScore() {
        return new Drawable() {

            private Array<Sprite> sprites;

            @Override
            public void create() {
                sprites = ttt.atlas.createSprites("score_number");
                for (Sprite score : sprites) {
                    score.setCenter(118, 87);
                    score.setColor(PLAYER_1_COLOR);
                }
            }

            @Override
            public void draw(Batch batch) {
                sprites.get(scores[Player.PLAYER_1]).draw(batch);
            }

        };
    }

    protected Drawable createPlayerTwoScore() {
        return new Drawable() {

            private Array<Sprite> sprites;

            @Override
            public void create() {
                sprites = ttt.atlas.createSprites("score_number");
                for (Sprite score : sprites) {
                    score.setCenter(362, 87);
                    score.setColor(PLAYER_2_COLOR);
                }
            }

            @Override
            public void draw(Batch batch) {
                sprites.get(scores[Player.PLAYER_2]).draw(batch);
            }

        };
    }

    protected Drawable createPlayerTurn() {
        return new Drawable() {

            private Sprite sprite;

            @Override
            public void create() {
                sprite = ttt.atlas.createSprite("player_turn");
            }

            @Override
            public void draw(Batch batch) {
                GridPoint2 point = playerTurnLayout[playerTurn];
                if (playerTurn == Player.PLAYER_1) {
                    sprite.setColor(PLAYER_1_COLOR);
                } else if (playerTurn == Player.PLAYER_2) {
                    sprite.setColor(PLAYER_2_COLOR);
                }

                sprite.setCenter(point.x, point.y);
                sprite.draw(batch);
            }

        };
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

    private void setup() {
        viewport = new FitViewport(TicTacToe.WINDOW_WIDTH,
                                   TicTacToe.WINDOW_HEIGHT);
        camera = (OrthographicCamera) viewport.getCamera();
        camera.setToOrtho(false, TicTacToe.WINDOW_WIDTH,
                          TicTacToe.WINDOW_HEIGHT);

        input = new GameInput(ttt, viewport);

        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void show() {
        ttt.playGame();
        Gdx.input.setInputProcessor(input);
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

    public void logic(float deltaTime) {
    }

    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.update();
        ttt.batch.setProjectionMatrix(camera.combined);

        ttt.batch.begin();
        for (Drawable drawable : drawableList) {
            drawable.draw(ttt.batch);
        }
        ttt.batch.end();
    }

    public void cleanBoard() {
        for (int i = 0; i < board.length; ++i) {
            board[i] = Player.NO_MARK;
        }
    }

    @Override
    public void gameStart(GameEvent event) {
        System.out.println("Game Start");
        Game game = (Game) event.getSource();
        copyBoard(game);
        copyScore(game);
        players = game.getPlayers();
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
        Game game = (Game) event.getSource();
        copyBoard(game);
    }

    @Override
    public void gameWinner(GameEvent event) {
        Game game = (Game) event.getSource();
        copyScore(game);
        if (game.getWinner() == Player.PLAYER_1) {
            pauseTap = TAP_PLAYER_1_WINS;
        } else if (game.getWinner() == Player.PLAYER_2) {
            pauseTap = TAP_PLAYER_2_WINS;
        }
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
        playerTurn = currentPlayer.id;
    }

    @Override
    public void invalidPosition(GameEvent event) {
        System.out.println("Invalid Position");
    }

    @Override
    public void positionIsNotEmpty(GameEvent event) {
        System.out.println("Position is not Empty");
    }

    private void copyBoard(Game game) {
        for (int i = 0; i < Game.BOARD_HEIGHT; ++i) {
            for (int j = 0; j < Game.BOARD_WIDTH; ++j) {
                board[Game.indexMark(i, j)] = game.getBoardMark(i, j);
            }
        }
    }

    private void copyScore(Game game) {
        Player[] players = game.getPlayers();
        scores[Player.PLAYER_1] = players[Player.PLAYER_1].score;
        scores[Player.PLAYER_2] = players[Player.PLAYER_2].score;
    }

}
