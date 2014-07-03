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
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.gmail.emersonmx.tictactoe.model.GameApplication;
import com.gmail.emersonmx.tictactoe.model.Resource;

public class DesktopLauncher {

    public static void main(String[] arg) {
        Settings settings = new Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;

        TexturePacker.process(settings, "../../images/game", ".", "game");

        new LwjglApplication(new GameApplication(), "TicTacToe",
                             Resource.WINDOW_WIDTH,
                             Resource.WINDOW_HEIGHT);
    }

}
