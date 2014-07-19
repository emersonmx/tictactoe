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

package com.gmail.emersonmx.tictactoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.gmail.emersonmx.tictactoe.GameApplication;
import com.gmail.emersonmx.tictactoe.ViewManager;

public class DesktopLauncher {

    public static void main(String[] arg) {
        new LwjglApplication(new GameApplication(), "TicTacToe",
                             ViewManager.WINDOW_WIDTH,
                             ViewManager.WINDOW_HEIGHT);
    }

}
