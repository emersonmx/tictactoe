package com.gmail.emersonmx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gmail.emersonmx.tictactoe.application.TicTacToe;
import com.gmail.emersonmx.tictactoe.view.scene.BackgroundActor;
import com.gmail.emersonmx.tictactoe.view.scene.BlackboardActor;
import com.gmail.emersonmx.tictactoe.view.scene.BoardActor;
import com.gmail.emersonmx.tictactoe.view.scene.BottomActor;

public class GameView implements View {

    public static final Color PLAYER_1_COLOR = new Color(0x8080ffff);
    public static final Color PLAYER_2_COLOR = new Color(0xff8080ff);

    private Stage stage;

    public GameView(AssetManager manager, TextureAtlas atlas) {
        setup();
        setupScene(manager, atlas);
    }

    protected void setup() {
        FitViewport viewport =
            new FitViewport(TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    protected void setupScene(AssetManager manager, TextureAtlas atlas) {
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
        stage.addActor(bottomGroupActor);
    }

    @Override
    public void draw() {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
