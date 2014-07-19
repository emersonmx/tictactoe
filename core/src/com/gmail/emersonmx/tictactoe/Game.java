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

import java.util.Random;

public class Game {

    public static final int BOARD_WIDTH = 3;
    public static final int BOARD_HEIGHT = 3;

    private static final int WINNER_O = -3;
    private static final int WINNER_X = 3;

    public static final int DEFAULT_MATCH = 5;

    private Player currentPlayer;
    private Player[] players;
    private int winner;
    private int markCount;
    private int[] board;

    private int match;
    private int matchCount;

    private Random random;
    private int[] lines;
    private int[] columns;
    private int[] diagonals;

    private GameListener listener;
    private GameEvent event;

    public static int indexMark(int i, int j) {
        return j + i * BOARD_HEIGHT;
    }

    public Game() {
        players = new Player[] {
            new Player(Player.PLAYER_1, Player.MARK_O),
            new Player(Player.PLAYER_2, Player.MARK_X)
        };
        board = new int[BOARD_WIDTH * BOARD_HEIGHT];
        match = DEFAULT_MATCH;

        random = new Random();
        lines = new int[] { 0, 0, 0 };
        columns = new int[] { 0, 0, 0 };
        diagonals = new int[] { 0, 0 };
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

    public int getBoardMark(int i, int j) {
        return board[indexMark(i, j)];
    }

    public void setBoardMark(int i, int j) {
        if ((i >= 0 && i <= BOARD_HEIGHT) && (j >= 0 && j <= BOARD_WIDTH)) {
            int boardMark = getBoardMark(i, j);
            if (boardMark == Player.NO_MARK) {
                board[indexMark(i, j)] = currentPlayer.mark;
                markCount++;

                notifyMarked();

                int winnerMark = checkVictory();
                if (winnerMark != Player.NO_MARK) {
                    winner = Player.byMark(winnerMark, players);
                    players[winner].score++;
                    checkMatchWinner();
                } else if (markCount == (BOARD_WIDTH * BOARD_HEIGHT)) {
                    winner = Player.PLAYER_NONE;
                    notifyGameDraw();
                    checkMatchWinner();
                } else {
                    changePlayer();
                }
            } else {
                notifyPositionIsNotEmpty();
            }
        } else {
            notifyInvalidPosition();
        }
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public void setListener(GameListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    public void start() {
        reset();

        randomPlayer();
        cleanBoard();

        notifyGameStart();
    }

    public void quit() {
        notifyGameOver();
        notifyGameEnd();
    }

    public void restart() {
        notifyGameOver();

        markCount = 0;

        if (winner != Player.PLAYER_NONE) {
            currentPlayer = players[winner];
        } else {
            randomPlayer();
        }

        cleanBoard();
    }

    private int checkVictory() {
        resetConditions();

        int boardMark = Player.NO_MARK;
        for (int i = 0; i < BOARD_HEIGHT; ++i) {
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                boardMark = getBoardMark(i, j);

                if (boardMark == Player.MARK_O) {
                    lines[i]--;
                    columns[j]--;

                    if (i == j) {
                        diagonals[0]--;
                    }
                    if (((BOARD_WIDTH - 1) - j - i) == 0) {
                        diagonals[1]--;
                    }
                } else if (boardMark == Player.MARK_X) {
                    lines[i]++;
                    columns[j]++;

                    if (i == j) {
                        diagonals[0]++;
                    }
                    if (((BOARD_WIDTH - 1) - j - i) == 0) {
                        diagonals[1]++;
                    }
                }
            }
        }

        for (int i = 0; i < BOARD_WIDTH; ++i) {
            if (i < (BOARD_WIDTH - 1)) {
                if (diagonals[i] == WINNER_O) {
                    return Player.MARK_O;
                } else if (diagonals[i] == WINNER_X) {
                    return Player.MARK_X;
                }
            }

            if (lines[i] == WINNER_O) {
                return Player.MARK_O;
            } else if (lines[i] == WINNER_X) {
                return Player.MARK_X;
            }

            if (columns[i] == WINNER_O) {
                return Player.MARK_O;
            } else if (columns[i] == WINNER_X) {
                return Player.MARK_X;
            }
        }

        return Player.NO_MARK;
    }

    private void resetConditions() {
        for (int i = 0; i < 3; ++i) {
            lines[i] = columns[i] = 0;
            if (i < 2) {
                diagonals[i] = 0;
            }
        }
    }

    private void checkMatchWinner() {
        matchCount++;

        if (matchCount >= match) {
            notifyGameWinner();
            notifyGameMatchWinner();
            notifyGameEnd();
        } else {
            notifyGameWinner();
            restart();
        }
    }

    private void changePlayer() {
        if (currentPlayer.equals(players[Player.PLAYER_1])) {
            currentPlayer = players[Player.PLAYER_2];
        } else {
            currentPlayer = players[Player.PLAYER_1];
        }

        notifyCurrentPlayerChanged();
    }

    private void randomPlayer() {
        currentPlayer = players[random.nextInt(2)];
        notifyCurrentPlayerChanged();
    }

    private void cleanBoard() {
        for (int i = 0; i < board.length; ++i) {
            board[i] = Player.NO_MARK;
        }
    }

    private void reset() {
        markCount = 0;
        currentPlayer = null;
        winner = Player.PLAYER_NONE;
    }

    private void notifyGameStart() {
        listener.gameStart(event);
    }

    private void notifyGameOver() {
        listener.gameOver(event);
    }

    private void notifyGameEnd() {
        listener.gameEnd(event);
    }

    private void notifyMarked() {
        listener.marked(event);
    }

    private void notifyGameWinner() {
        listener.gameWinner(event);
    }

    private void notifyGameDraw() {
        listener.gameDraw(event);
    }

    private void notifyGameMatchWinner() {
        listener.gameMatchWinner(event);
    }

    private void notifyCurrentPlayerChanged() {
        listener.currentPlayerChanged(event);
    }

    private void notifyInvalidPosition() {
        listener.invalidPosition(event);
    }

    private void notifyPositionIsNotEmpty() {
        listener.positionIsNotEmpty(event);
    }

}
