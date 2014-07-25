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

public class BaseScreenOverlay extends BaseScreen {

    protected ScreenAdapter screen;

    public BaseScreenOverlay(TicTacToe ttt) {
        super(ttt);
    }

    public void setScreen(ScreenAdapter screen) {
        this.screen = screen;
    }

    public ScreenAdapter getScreen() {
        return screen;
    }

    @Override
    protected void create() {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void draw() {
        screen.draw();
        stage.draw();
    }

}
