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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class SpritesActor extends Actor {

    public static final int HIDDEN = -1;

    private int index;
    private Array<Sprite> sprites;

    public SpritesActor() {
        index = 0;
    }

    public SpritesActor(String name, Array<Sprite> sprites) {
        index = 0;

        this.sprites = sprites;
        setName(name);
    }

    public void setIndex(int index) {
        if (index < 0 && index >= sprites.size) {
            index = HIDDEN;
        }

        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setSprites(Array<Sprite> sprites) {
        this.sprites = sprites;
    }

    public Array<Sprite> getSprites() {
        return sprites;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (index != HIDDEN) {
            Sprite sprite = sprites.get(index);
            if (sprite != null) {
                sprite.draw(batch, parentAlpha);
            }
        }
    }

}
