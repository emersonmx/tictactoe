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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class MenuScreen extends BaseScreen {

    public static final Color NORMAL_COLOR = new Color(0xffffffff);
    public static final Color CLICK_COLOR = new Color(0xff8000ff);

    public MenuScreen(TicTacToe ttt) {
        super(ttt);
    }

    @Override
    protected void create() {
        super.create();

        stage.addActor(createLogo());
        stage.addActor(createMenu());
    }

    private Actor createLogo() {
        Sprite logo = ttt.atlas.createSprite("logo");
        logo.setCenter(TicTacToe.WINDOW_WIDTH / 2, 444);

        return new SpriteActor("logo", logo);
    }

    private Actor createMenu() {
        Group group = new Group();
        group.setName("menu");

        group.addActor(createPlay());

        group.addActor(createQuit());

        return group;
    }

    private Actor createPlay() {
        Sprite play = ttt.atlas.createSprite("play");
        play.setCenter(TicTacToe.WINDOW_WIDTH / 2, 230);

        SpriteActor actor = new SpriteActor("play", play);
        actor.setTouchable(Touchable.enabled);
        actor.setBounds(play.getX(), play.getY(),
                        play.getWidth(), play.getHeight());
        actor.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                    int pointer, int button) {

                SpriteActor actor = (SpriteActor) event.getTarget();
                Sprite sprite = actor.getSprite();
                sprite.setColor(CLICK_COLOR);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                    int pointer, int button) {

                SpriteActor actor = (SpriteActor) event.getTarget();
                Sprite sprite = actor.getSprite();
                sprite.setColor(NORMAL_COLOR);

                GameScreenOverlay overlay = ttt.getGameScreenOverlay();
                overlay.setTapSprite(GameScreenOverlay.TAP_TO_START);
                ttt.setScreen(overlay);
            }

        });

        return actor;
    }

    private Actor createQuit() {
        Sprite quit = ttt.atlas.createSprite("quit");
        quit.setCenter(TicTacToe.WINDOW_WIDTH / 2, 120);

        SpriteActor actor = new SpriteActor("quit", quit);
        actor.setTouchable(Touchable.enabled);
        actor.setBounds(quit.getX(), quit.getY(),
                        quit.getWidth(), quit.getHeight());
        actor.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                    int pointer, int button) {

                SpriteActor actor = (SpriteActor) event.getTarget();
                Sprite sprite = actor.getSprite();
                sprite.setColor(CLICK_COLOR);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                    int pointer, int button) {

                SpriteActor actor = (SpriteActor) event.getTarget();
                Sprite sprite = actor.getSprite();
                sprite.setColor(NORMAL_COLOR);
                ttt.exit();
            }

        });

        return actor;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

}
