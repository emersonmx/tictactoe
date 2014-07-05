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
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.gmail.emersonmx.tictactoe.model.Game;
import com.gmail.emersonmx.tictactoe.model.GameEvent;
import com.gmail.emersonmx.tictactoe.model.GameListener;
import com.gmail.emersonmx.tictactoe.model.Player;

public class GameView extends AbstractView implements GameListener {

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

    private TextureAtlas atlas;
    private Batch batch;

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

    public GameView(ViewManager viewManager) {
        super(viewManager);

        this.atlas = viewManager.atlas;
        this.batch = viewManager.batch;

        input = new GameInput(viewManager);
        input.setGameView(this);

        int size = Game.BOARD_WIDTH * Game.BOARD_HEIGHT;
        board = new int[size];
        scores = new int[] { 0, 0 };

        pauseToStart = true;
        pauseTap = NO_PAUSE_TAP;
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

    @Override
    public void create() {
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

        loaded = true;
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

    public Sprite[] createPlayerOneMarks() {
        Sprite[] sprites = createMarks();
        for (Sprite sprite : sprites) {
            sprite.setColor(PLAYER_1_COLOR);
        }

        return sprites;
    }

    public Sprite[] createPlayerTwoMarks() {
        Sprite[] sprites = createMarks();
        for (Sprite sprite : sprites) {
            sprite.setColor(PLAYER_2_COLOR);
        }

        return sprites;
    }

    protected Sprite[] createMarks() {
        Sprite[] sprites = new Sprite[2];
        sprites[Player.MARK_O] = atlas.createSprite("mark_o");
        sprites[Player.MARK_X] = atlas.createSprite("mark_x");

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

    protected Sprite createPlayerTurn() {
        Sprite sprite = atlas.createSprite("player_turn");

        return sprite;
    }

    protected GridPoint2[] createPlayerTurnLayout() {
        return new GridPoint2[] {
            new GridPoint2(57, 87), new GridPoint2(423, 87)
        };
    }

    public Sprite[] createTapWindowList() {
        Sprite[] sprites = new Sprite[TAP_WINDOW_LIST_SIZE];
        sprites[TAP_TO_START] = atlas.createSprite("tap_to_start");
        sprites[TAP_PLAYER_1_WINS] = atlas.createSprite("player_one_wins");
        sprites[TAP_PLAYER_2_WINS] = atlas.createSprite("player_two_wins");
        sprites[TAP_PLAYER_1_MATCH] = atlas.createSprite("match_winner_one");
        sprites[TAP_PLAYER_2_MATCH] = atlas.createSprite("match_winner_two");
        sprites[TAP_GAME_DRAWN] = atlas.createSprite("game_drawn");
        sprites[TAP_DRAW] = atlas.createSprite("draw");

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i].setCenter(ViewManager.WINDOW_WIDTH / 2.f,
                                 ViewManager.WINDOW_HEIGHT / 2.f);
        }

        return sprites;
    }

    @Override
    public void setup() {
        input.setController(controller);
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void logic(float deltaTime) {
    }

    @Override
    public void draw() {
        for (Sprite hashLine : hash) {
            hashLine.draw(batch);
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
                    mark.draw(batch);
                } else if (playerId == Player.PLAYER_2) {
                    mark = playerTwoMarks[boardMark];
                    mark.setCenter(point.x, point.y);
                    mark.draw(batch);
                }
            }

        }

        scoreLine.draw(batch);
        for (Sprite scoreSeparator : scoreSeparators) {
            scoreSeparator.draw(batch);
        }

        menu.draw(batch);
        playerOneScore.get(scores[Player.PLAYER_1]).draw(batch);
        playerTwoScore.get(scores[Player.PLAYER_2]).draw(batch);

        point = playerTurnLayout[playerTurn];
        if (playerTurn == Player.PLAYER_1) {
            turn.setColor(PLAYER_1_COLOR);
        } else if (playerTurn == Player.PLAYER_2) {
            turn.setColor(PLAYER_2_COLOR);
        }

        turn.setCenter(point.x, point.y);
        turn.draw(batch);

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

            window.draw(batch);
        }
    }

    @Override
    public void dispose() {
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

    public void copyScore(Game game) {
        Player[] players = game.getPlayers();
        scores[Player.PLAYER_1] = players[Player.PLAYER_1].score;
        scores[Player.PLAYER_2] = players[Player.PLAYER_2].score;
    }

}
