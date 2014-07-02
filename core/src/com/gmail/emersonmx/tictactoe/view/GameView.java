package com.gmail.emersonmx.tictactoe.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.gmail.emersonmx.tictactoe.application.TicTacToe;

public class GameView implements View {

    public static final Color PLAYER_1_COLOR = new Color(0x8080ffff);
    public static final Color PLAYER_2_COLOR = new Color(0xff8080ff);

    TicTacToe game;

    private Texture background;
    private Sprite blackboard;
    private Array<Sprite> hash;
    private Sprite scoreLine;
    private Array<Sprite> scoreSeparators;
    private Sprite menu;
    private Array<Sprite> playerOneScore;
    private Array<Sprite> playerTwoScore;

    public GameView(TicTacToe game) {
        this.game = game;
    }

    @Override
    public void setup() {
        background = createBackground();
        blackboard = createBlackboard();
        hash = createHash();
        scoreLine = createScoreLine();
        scoreSeparators = createScoreSeparators();
        menu = createMenu();
        playerOneScore = createPlayerOneScore();
        playerTwoScore = createPlayerTwoScore();
    }

    protected Texture createBackground() {
        Texture texture = game.manager.get("background.png", Texture.class);
        texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        return texture;
    }

    protected Sprite createBlackboard() {
        return game.atlas.createSprite("blackboard");
    }

    protected Array<Sprite> createHash() {
        Array<Sprite> sprites = new Array<Sprite>(4);
        GridPoint2[] layout = new GridPoint2[] {
            new GridPoint2(179, 401), new GridPoint2(240, 462),
            new GridPoint2(301, 401), new GridPoint2(240, 340)
        };

        Sprite sprite = null;
        GridPoint2 point = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];

            sprite = game.atlas.createSprite("hash_line");
            sprite.setCenter(point.x, point.y);
            if (i % 2 != 0) {
                sprite.rotate(90);
            }

            sprites.add(sprite);
        }

        return sprites;
    }

    protected Sprite createScoreLine() {
        Sprite sprite = new Sprite();
        sprite = game.atlas.createSprite("score_line");
        sprite.setCenter(240, 131);

        return sprite;
    }

    protected Array<Sprite> createScoreSeparators() {
        Array<Sprite> sprites = new Array<Sprite>(2);
        GridPoint2[] layout = new GridPoint2[] {
            new GridPoint2(192, 87), new GridPoint2(288, 87)
        };

        GridPoint2 point = null;
        Sprite sprite = null;
        for (int i = 0; i < layout.length; ++i) {
            point = layout[i];

            sprite = game.atlas.createSprite("score_separator");
            sprite.setCenter(point.x, point.y);
            sprites.add(sprite);
        }

        return sprites;
    }

    protected Sprite createMenu() {
        Sprite sprite = game.atlas.createSprite("menu");
        sprite.setCenter(240, 87);

        return sprite;
    }

    protected Array<Sprite> createPlayerOneScore() {
        Array<Sprite> sprites = game.atlas.createSprites("score_number");
        for (Sprite score : sprites) {
            score.setCenter(118, 87);
            score.setColor(PLAYER_1_COLOR);
        }

        return sprites;
    }

    protected Array<Sprite> createPlayerTwoScore() {
        Array<Sprite> sprites = game.atlas.createSprites("score_number");
        for (Sprite score : sprites) {
            score.setCenter(362, 87);
            score.setColor(PLAYER_2_COLOR);
        }

        return sprites;
    }

    @Override
    public void draw() {
        // apenas um teste
        if (Gdx.input.isTouched()) {
            Vector3 pos = new Vector3(); // leak
            pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(pos);
            System.out.println(pos);
        }

        game.camera.update();

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0,
                   TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT, 0, 0, 5, 5);

        blackboard.draw(game.batch);

        for (Sprite hashLine : hash) {
            hashLine.draw(game.batch);
        }

        scoreLine.draw(game.batch);
        for (Sprite scoreSeparator : scoreSeparators) {
            scoreSeparator.draw(game.batch);
        }

        menu.draw(game.batch);

        playerOneScore.get(0).draw(game.batch);
        playerTwoScore.get(0).draw(game.batch);

        game.batch.end();
    }

}
