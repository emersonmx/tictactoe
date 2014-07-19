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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter implements GameListener {

    public static final Color PLAYER_1_COLOR = new Color(0xaaaaffff);
    public static final Color PLAYER_2_COLOR = new Color(0xaaffaaff);

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

    private Texture background;
    private Texture blackboard;

    private Array<Sprite> hash;
    private Sprite[] playerOneMarks;
    private Sprite[] playerTwoMarks;
    private Sprite scoreLine;
    private Array<Sprite> scoreSeparators;
    private Sprite menu;
    private Array<Sprite> playerOneScore;
    private Array<Sprite> playerTwoScore;
    private Sprite turn;
    private Sprite[] tapWindowList;

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
        background = createBackground();
        blackboard = createBlackboard();
        hash = createHash();
        playerOneMarks = createPlayerOneMarks();
        playerTwoMarks = createPlayerTwoMarks();
        boardLayout = createBoardLayout();
        scoreLine = createScoreLine();
        scoreSeparators = createScoreSeparators();
        menu = createMenu();
        playerOneScore = createPlayerOneScore();
        playerTwoScore = createPlayerTwoScore();
        turn = createPlayerTurn();
        playerTurnLayout = createPlayerTurnLayout();
        tapWindowList = createTapWindowList();
    }

    protected Texture createBackground() {
        Texture texture = ttt.manager.get("background.png", Texture.class);
        texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        return texture;
    }

    protected Texture createBlackboard() {
        return ttt.manager.get("blackboard.png", Texture.class);
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

            sprite = ttt.atlas.createSprite("hash_line");
            sprite.setCenter(point.x, point.y);
            if (i % 2 != 0) {
                sprite.rotate(90);
            }

            sprites.add(sprite);
        }

        return sprites;
    }

    protected Sprite[] createPlayerOneMarks() {
        Sprite[] sprites = createMarks();
        for (Sprite sprite : sprites) {
            sprite.setColor(PLAYER_1_COLOR);
        }

        return sprites;
    }

    protected Sprite[] createPlayerTwoMarks() {
        Sprite[] sprites = createMarks();
        for (Sprite sprite : sprites) {
            sprite.setColor(PLAYER_2_COLOR);
        }

        return sprites;
    }

    protected Sprite[] createMarks() {
        Sprite[] sprites = new Sprite[2];
        sprites[Player.MARK_O] = ttt.atlas.createSprite("mark_o");
        sprites[Player.MARK_X] = ttt.atlas.createSprite("mark_x");

        return sprites;
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

    protected Sprite createScoreLine() {
        Sprite sprite = new Sprite();
        sprite = ttt.atlas.createSprite("score_line");
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

            sprite = ttt.atlas.createSprite("score_separator");
            sprite.setCenter(point.x, point.y);
            sprites.add(sprite);
        }

        return sprites;
    }

    protected Sprite createMenu() {
        Sprite sprite = ttt.atlas.createSprite("menu");
        sprite.setCenter(240, 87);

        return sprite;
    }

    protected Array<Sprite> createPlayerOneScore() {
        Array<Sprite> sprites = ttt.atlas.createSprites("score_number");
        for (Sprite score : sprites) {
            score.setCenter(118, 87);
            score.setColor(PLAYER_1_COLOR);
        }

        return sprites;
    }

    protected Array<Sprite> createPlayerTwoScore() {
        Array<Sprite> sprites = ttt.atlas.createSprites("score_number");
        for (Sprite score : sprites) {
            score.setCenter(362, 87);
            score.setColor(PLAYER_2_COLOR);
        }

        return sprites;
    }

    protected Sprite createPlayerTurn() {
        Sprite sprite = ttt.atlas.createSprite("player_turn");

        return sprite;
    }

    protected GridPoint2[] createPlayerTurnLayout() {
        return new GridPoint2[] {
            new GridPoint2(57, 87), new GridPoint2(423, 87)
        };
    }

    public Sprite[] createTapWindowList() {
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

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i].setCenter(TicTacToe.WINDOW_WIDTH / 2.f,
                                 TicTacToe.WINDOW_HEIGHT / 2.f);
        }

        return sprites;
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
        Gdx.input.setInputProcessor(input);
        ttt.playGame();
    }

    @Override
    public void hide() {
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
        ttt.batch.draw(background, 0, 0, TicTacToe.WINDOW_WIDTH,
                       TicTacToe.WINDOW_HEIGHT, 0, 0, 5, 5);
        ttt.batch.draw(blackboard, 0, 0);

        for (Sprite hashLine : hash) {
            hashLine.draw(ttt.batch);
        }

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
                    mark = playerOneMarks[boardMark];
                    mark.setCenter(point.x, point.y);
                    mark.draw(ttt.batch);
                } else if (playerId == Player.PLAYER_2) {
                    mark = playerTwoMarks[boardMark];
                    mark.setCenter(point.x, point.y);
                    mark.draw(ttt.batch);
                }
            }

        }

        scoreLine.draw(ttt.batch);
        for (Sprite scoreSeparator : scoreSeparators) {
            scoreSeparator.draw(ttt.batch);
        }

        menu.draw(ttt.batch);
        playerOneScore.get(scores[Player.PLAYER_1]).draw(ttt.batch);
        playerTwoScore.get(scores[Player.PLAYER_2]).draw(ttt.batch);

        point = playerTurnLayout[playerTurn];
        if (playerTurn == Player.PLAYER_1) {
            turn.setColor(PLAYER_1_COLOR);
        } else if (playerTurn == Player.PLAYER_2) {
            turn.setColor(PLAYER_2_COLOR);
        }

        turn.setCenter(point.x, point.y);
        turn.draw(ttt.batch);

        if (pauseTap != NO_PAUSE_TAP) {
            Sprite window;
            switch (pauseTap) {
                case TAP_PLAYER_1_WINS:
                    window = tapWindowList[TAP_PLAYER_1_WINS];
                    break;
                case TAP_PLAYER_2_WINS:
                    window = tapWindowList[TAP_PLAYER_2_WINS];
                    break;
                case TAP_PLAYER_1_MATCH:
                    window = tapWindowList[TAP_PLAYER_1_MATCH];
                    break;
                case TAP_PLAYER_2_MATCH:
                    window = tapWindowList[TAP_PLAYER_2_MATCH];
                    break;
                case TAP_GAME_DRAWN:
                    window = tapWindowList[TAP_GAME_DRAWN];
                    break;
                case TAP_DRAW:
                    window = tapWindowList[TAP_DRAW];
                    break;
                case TAP_TO_START:
                default:
                    window = tapWindowList[TAP_TO_START];
                    break;
            }

            window.draw(ttt.batch);
        }

        ttt.batch.end();
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

        Gdx.input.setInputProcessor(new InputAdapter() {
        });
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

    public void copyBoard(Game game) {
        for (int i = 0; i < Game.BOARD_HEIGHT; ++i) {
            for (int j = 0; j < Game.BOARD_WIDTH; ++j) {
                board[Game.indexMark(i, j)] = game.getBoardMark(i, j);
            }
        }
    }

    public void cleanBoard() {
        for (int i = 0; i < board.length; ++i) {
            board[i] = Player.NO_MARK;
        }
    }

    private void copyScore(Game game) {
        Player[] players = game.getPlayers();
        scores[Player.PLAYER_1] = players[Player.PLAYER_1].score;
        scores[Player.PLAYER_2] = players[Player.PLAYER_2].score;
    }

}
