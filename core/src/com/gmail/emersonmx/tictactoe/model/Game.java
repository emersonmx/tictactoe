/*
  Copyright (C) Emerson Max de Medeiros Silva

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

package com.gmail.emersonmx.tictactoe.model;

import java.util.Random;

import com.badlogic.gdx.utils.Array;

public class Game {

    public static final int BOARD_WIDTH = 3;
    public static final int BOARD_HEIGHT = 3;
    private static final int WINNER_O = -3;
    private static final int WINNER_X = 3;

    public static final int PLAYER_NONE = -1;
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    public static final int NO_MARK = 0;
    public static final int MARK_X = 1;
    public static final int MARK_O = 2;

    private Player currentPlayer;
    private Player[] players;
    private int winner;
    private int markCount;
    private boolean gameDone;
    private int[] board;

    private Random random;
    private int[] horizontals;
    private int[] verticals;
    private int[] diagonals;
    private Array<GameListener> listeners;
    private GameEvent event;

    public Game() {
        players = new Player[2];
        board = new int[BOARD_WIDTH * BOARD_HEIGHT];

        random = new Random();
        horizontals = new int[] { 0, 0, 0 };
        verticals = new int[] { 0, 0, 0 };
        diagonals = new int[] { 0, 0 };
        listeners = new Array<GameListener>(2);
        event = new GameEvent(this);

        reset();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getWinner() {
        return winner;
    }

    public int getMark(int i, int j) {
        return board[indexMark(i, j)];
    }

    public void setMark(int i, int j) {
        if (gameDone) {
            if (markCount == (BOARD_WIDTH * BOARD_HEIGHT)) {
                fireGameDraw();
            } else {
                fireGameWinner();
            }
        } else {
            if ((i >= 0 && i <= BOARD_HEIGHT) && (j >= 0 && j <= BOARD_WIDTH)) {
                int boardMark = getMark(i, j);
                if (boardMark == NO_MARK) {
                    board[indexMark(i, j)] = currentPlayer.mark;
                    markCount++;

                    fireMarked();

                    int winnerMark = checkVictory();
                    if (winnerMark != NO_MARK) {
                        if (players[PLAYER_1].mark == winnerMark) {
                            winner = PLAYER_1;
                        } else {
                            winner = PLAYER_2;
                        }

                        gameDone = true;
                        fireGameWinner();
                    } else if (markCount == (BOARD_WIDTH * BOARD_HEIGHT)) {
                        gameDone = true;
                        fireGameDraw();
                    } else {
                        changePlayer();
                    }
                } else {
                    firePositionIsNotEmpty();
                }
            } else {
                fireInvalidPosition();
            }
        }
    }

    public void setup() {
        reset();

        randomPlayer();
        cleanBoard();
    }

    public void dispose() {
        fireGameOver();
    }

    public void restart() {
        fireGameOver();

        markCount = 0;
        gameDone = false;

        if (winner != PLAYER_NONE) {
            currentPlayer = players[winner];
        } else {
            randomPlayer();
        }

        cleanBoard();
    }
    public void randomPlayer() {
        currentPlayer = players[random.nextInt(2)];
    }

    public void cleanBoard() {
        for (int i = 0; i < board.length; ++i) {
            board[i] = NO_MARK;
        }
    }

    public void reset() {
        markCount = 0;
        gameDone = false;
        currentPlayer = null;
        winner = PLAYER_NONE;
    }

    public void changePlayer() {
        if (currentPlayer.equals(players[PLAYER_1])) {
            currentPlayer = players[PLAYER_2];
        } else {
            currentPlayer = players[PLAYER_1];
        }

        fireCurrentPlayerChanged();
    }

    public int checkVictory() {
        resetConditions();

        int boardMark = NO_MARK;
        for (int i = 0; i < BOARD_HEIGHT; ++i) {
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                boardMark = getMark(i, j);

                if (boardMark == MARK_O) {
                    horizontals[i]--;
                    verticals[i]--;

                    if (i == j) {
                        diagonals[0]--;
                    }
                    if (((BOARD_WIDTH - 1) - j - i) == 0) {
                        diagonals[1]--;
                    }
                } else if (boardMark == MARK_X) {
                    horizontals[i]++;
                    verticals[i]++;

                    if (i == j) {
                        diagonals[0]++;
                    } else if (((BOARD_WIDTH - 1) - j - i) == 0) {
                        diagonals[1]++;
                    }
                }
            }
        }

        for (int i = 0; i < BOARD_WIDTH; ++i) {
            if (i < (BOARD_WIDTH - 1)) {
                if (diagonals[i] == WINNER_O) {
                    return MARK_O;
                } else if (diagonals[i] == WINNER_X) {
                    return MARK_X;
                }
            }

            if (horizontals[i] == WINNER_O) {
                return MARK_O;
            } else if (horizontals[i] == WINNER_X) {
                return MARK_X;
            }

            if (verticals[i] == WINNER_O) {
                return MARK_O;
            } else if (verticals[i] == WINNER_X) {
                return MARK_X;
            }
        }

        return NO_MARK;
    }

    public void resetConditions() {
        for (int i = 0; i < 3; ++i) {
            horizontals[i] = verticals[i] = 0;
            if (i < 2) {
                diagonals[i] = 0;
            }
        }
    }

    public void addListener(GameListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(GameListener listener) {
        if (listener != null) {
            listeners.removeValue(listener, true);
        }
    }

    public void fireGameStart() {
        for (GameListener listener : listeners) {
            listener.gameStart(event);
        }
    }

    public void fireGameOver() {
        for (GameListener listener : listeners) {
            listener.gameOver(event);
        }
    }

    public void fireMarked() {
        for (GameListener listener : listeners) {
            listener.marked(event);
        }
    }

    public void fireGameWinner() {
        for (GameListener listener : listeners) {
            listener.gameWinner(event);
        }
    }

    public void fireGameDraw() {
        for (GameListener listener : listeners) {
            listener.gameDraw(event);
        }
    }

    public void fireCurrentPlayerChanged() {
        for (GameListener listener : listeners) {
            listener.currentPlayerChanged(event);
        }
    }

    public void fireInvalidPosition() {
        for (GameListener listener : listeners) {
            listener.invalidPosition(event);
        }
    }

    public void firePositionIsNotEmpty() {
        for (GameListener listener : listeners) {
            listener.positionIsNotEmpty(event);
        }
    }

    private int indexMark(int i, int j) {
        return i + j * BOARD_WIDTH;
    }

}
