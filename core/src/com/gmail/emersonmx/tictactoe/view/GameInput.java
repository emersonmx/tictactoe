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

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gmail.emersonmx.tictactoe.controller.Controller;

public class GameInput extends InputAdapter {

    private ViewManager viewManager;
    private GameView gameView;
    private Controller controller;

    private Viewport viewport;

    private Array<Rectangle> rectangles;
    private Vector3 point;

    public GameInput(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.viewport = viewManager.viewport;

        createRectangles();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public GameView getGameView() {
        return gameView;
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

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (gameView.getPauseTap() != GameView.NO_PAUSE_TAP) {
            gameView.setPauseTap(GameView.NO_PAUSE_TAP);
            gameView.cleanBoard();
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
                    controller.mark(i);
                }
            } else {
                if (rectangle.contains(point.x, point.y)) {
                    System.out.println("Menu touched");
                    viewManager.changeView(ViewManager.GAME_VIEW);
                }
            }
        }

        return true;
    }

}
