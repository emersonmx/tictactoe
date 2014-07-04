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

    public int id;
    public int mark;

    public Player(int id, int mark) {
        this.id = id;
        this.mark = mark;
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

        if (mark == Game.NO_MARK) {
            s += "\" \"";
        } else if (mark == Game.MARK_O) {
            s += "O";
        } else if (mark == Game.MARK_X) {
            s += "X";
        }

        return s;
    }

}
