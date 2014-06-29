package com.gmail.emersonmx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gmail.emersonmx.tictactoe.application.TicTacToe;
import com.gmail.emersonmx.tictactoe.util.Point;
import com.gmail.emersonmx.tictactoe.util.SpriteActor;
import com.gmail.emersonmx.tictactoe.view.scene.BackgroundActor;
import com.gmail.emersonmx.tictactoe.view.scene.ScoreActor;

public class GameView implements View {

    public static final Color PLAYER_1_COLOR = new Color(0x8080ffff);
    public static final Color PLAYER_2_COLOR = new Color(0xff8080ff);

    protected AssetManager manager;
    protected TextureAtlas atlas;
    protected Stage stage;

    public GameView(AssetManager manager, TextureAtlas atlas) {
        this.manager = manager;
        this.atlas = atlas;

        setup();
        setupScene();
    }

    protected void setup() {
        FitViewport viewport =
            new FitViewport(TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    protected void setupScene() {
        stage.addActor(createBackground());
        stage.addActor(createBlackboard());
        stage.addActor(createBoard());
        stage.addActor(createScoreLine());
        stage.addActor(createSeparator());
        stage.addActor(createMenu());
        stage.addActor(createScoreActor());
        //stage.addActor(createPlaces());
    }

    public Actor createBackground() {
        Texture background = manager.get("background.png", Texture.class);
        return new BackgroundActor(background);
    }

    public Actor createBlackboard() {
        Sprite blackboard = atlas.createSprite("blackboard");
        SpriteActor blackboardNode = new SpriteActor("blackboard", blackboard);
        blackboardNode.setPosition(0, 0);

        return blackboardNode;
    }

    public Actor createBoard() {
        Sprite[] lines = new Sprite[4];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = atlas.createSprite("board_line");
        }
        Point[] layout = new Point[] {
            new Point(179, 401), new Point(240, 462),
            new Point(301, 401), new Point(240, 340)
        };
        Point point = null;

        Group group = new Group();
        group.setName("board");
        Sprite sprite = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];
            sprite = lines[i];
            sprite.setCenter(point.x, point.y);

            if ((i % 2) != 0) {
                sprite.rotate(90);
            }

            group.addActor(new SpriteActor("board_line_" + String.valueOf(i),
                                           sprite));
        }

        return group;
    }

    public Actor createScoreLine() {
        Sprite line = atlas.createSprite("score_line");
        line.setCenter(240, 131);

        return new SpriteActor("score_line", line);
    }

    public Actor createSeparator() {
        Point[] layout = new Point[] {
            new Point(192, 87), new Point(288, 87)
        };
        Sprite[] separators = new Sprite[] {
            atlas.createSprite("separator"), atlas.createSprite("separator")
        };

        Point point = null;
        Sprite sprite = null;
        Group group = new Group();
        group.setName("separators");
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];
            sprite = separators[i];
            sprite.setOriginCenter();
            sprite.setCenter(point.x, point.y);

            group.addActor(new SpriteActor("separator_" + String.valueOf(i),
                                           sprite));
        }

        return group;
    }

    public Actor createMenu() {
        Sprite menu = atlas.createSprite("menu");
        menu.setCenter(240, 87);

        SpriteActor menuActor = new SpriteActor("menu", menu);
        menuActor.setTouchable(Touchable.enabled);
        menuActor.setBounds(193, 43, 95, 88);
        menuActor.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                    int pointer, int button) {

                System.out.println("<menu touch>");
                return true;
            }

        });

        return menuActor;
    }

    public Actor createScoreActor() {
        Array<Sprite> scoreArray = atlas.createSprites("number");
        return new ScoreActor(scoreArray);
    }

    public Actor createPlaces() {
        return new Group();
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
