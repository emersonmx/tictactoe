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

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

// TODO: Pode ser que seja melhor usar um multiplexer e criar um inputadapter
// para cada retângulo.
public class GameInput extends InputAdapter {

    private final TicTacToe ttt;
    private final Viewport viewport;

    private Array<Rectangle> rectangles;
    private Vector3 point;

    public GameInput(TicTacToe ttt, Viewport viewport) {
        this.ttt = ttt;
        this.viewport = viewport;

        createRectangles();
    }

    public void createRectangles() {
        point = new Vector3();
        rectangles = new Array<Rectangle>(10);

        float width, height;
        width = height = 108;

        GridPoint2 point = new GridPoint2(64, 469);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                rectangles.add(
                        new Rectangle(point.x, point.y, width, height));
                point.x += 122;
            }

            point.x = 64;
            point.y -= 122;
        }

        rectangles.add(new Rectangle(198, 43, 85, 83));
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        GameScreen gameScreen = ttt.getGameScreen();

        // TODO: Pode ser que seja melhor o input comunicar diretamente com o
        // TicTacToe. Esse cleanBoard tá meio estranho.
        if (gameScreen.getPauseTap() != GameScreen.NO_PAUSE_TAP) {
            gameScreen.setPauseTap(GameScreen.NO_PAUSE_TAP);
            gameScreen.cleanBoard();
            return true;
        }

        point.x = screenX;
        point.y = screenY;
        viewport.unproject(point);

        Rectangle rectangle = null;
        for (int i = 0; i < rectangles.size; ++i) {
            rectangle = rectangles.get(i);
            if (i < rectangles.size - 1) {
                if (rectangle.contains(point.x, point.y)) {
                    System.out.println("Mark " + i + " touched");
                    ttt.mark(i);
                }
            } else {
                if (rectangle.contains(point.x, point.y)) {
                    System.out.println("Menu touched");
                }
            }
        }

        return true;
    }

}
