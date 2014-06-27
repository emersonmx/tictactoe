package com.gmail.emersonmx.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gmail.emersonmx.tictactoe.scene.BackgroundActor;
import com.gmail.emersonmx.tictactoe.scene.BlackboardActor;
import com.gmail.emersonmx.tictactoe.scene.BoardActor;
import com.gmail.emersonmx.tictactoe.scene.BottomActor;
import com.gmail.emersonmx.tictactoe.scene.ScoreActor;


public class TicTacToe extends Application {

    public static final int WINDOW_WIDTH = 480;
    public static final int WINDOW_HEIGHT = 640;

    AssetManager manager;
    TextureAtlas atlas;
    Stage stage;

    @Override
    public void create() {
        loadResources();
        setup();
    }

    public void loadResources() {
        manager = new AssetManager();
        manager.load("background.png", Texture.class);
        manager.load("game.atlas", TextureAtlas.class);
        manager.finishLoading();

        atlas = manager.get("game.atlas");
    }

    public void setup() {
        stage = new Stage(new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        Texture background = manager.get("background.png", Texture.class);
        stage.addActor(new BackgroundActor(background));
        Sprite blackboard = atlas.createSprite("blackboard");
        stage.addActor(new BlackboardActor(blackboard));

        Sprite[] lines = new Sprite[4];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = atlas.createSprite("board_line");
        }
        stage.addActor(new BoardActor(lines));

        Sprite line = atlas.createSprite("score_line");
        Sprite[] separators = new Sprite[] {
            atlas.createSprite("separator"), atlas.createSprite("separator")
        };
        Array<Sprite> scoreArray = atlas.createSprites("number");
        Sprite menu = atlas.createSprite("menu");
        BottomActor bottomGroupActor =
            new BottomActor(line, separators, scoreArray, menu);

        ScoreActor scoreActor =
            (ScoreActor) bottomGroupActor.findActor("score");
        scoreActor.setColor(ScoreActor.PLAYER_1, new Color(0x8080ffff));
        scoreActor.setColor(ScoreActor.PLAYER_2, new Color(0xff8080ff));
        stage.addActor(bottomGroupActor);

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
    }

    public void events() {
    }

    public void logic() {
    }

    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        manager.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
