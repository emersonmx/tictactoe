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

package com.gmail.emersonmx.tictactoe.model;

public class Player {

    public static final int PLAYER_NONE = -1;
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;

    public static final int NO_MARK = -1;
    public static final int MARK_O = 0;
    public static final int MARK_X = 1;

    public int id;
    public int mark;
    public int score;

    public static int byId(int id, Player[] players) {
        int player = PLAYER_NONE;
        int playerId;

        for (int i = 0; i < players.length; ++i) {
            playerId = players[i].id;
            if (playerId == id) {
                player = playerId;
                break;
            }
        }

        return player;
    }

    public static int byMark(int mark, Player[] players) {
        int player = PLAYER_NONE;
        int playerMark;

        for (int i = 0; i < players.length; ++i) {
            playerMark = players[i].mark;
            if (playerMark == mark) {
                player = players[i].id;
                break;
            }
        }

        if (players[PLAYER_1].mark == mark) {
            player = PLAYER_1;
        } else {
            player = PLAYER_2;
        }

        return player;
    }

    public static int matchWinner(int match, Player[] players) {
        int player = PLAYER_NONE;
        if (players[PLAYER_1].score > players[PLAYER_2].score) {
            player = PLAYER_1;
        } else if (players[PLAYER_1].score < players[PLAYER_2].score) {
            player = PLAYER_2;
        }

        return player;
    }

    public Player(int id, int mark) {
        this.id = id;
        this.mark = mark;
        score = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player) obj;
            if (mark == other.mark) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String s = "Player " + id + " with mark: ";

        if (mark == NO_MARK) {
            s += "\" \"";
        } else if (mark == MARK_O) {
            s += "O";
        } else if (mark == MARK_X) {
            s += "X";
        }

        s += " and score " + score;

        return s;
    }

}
