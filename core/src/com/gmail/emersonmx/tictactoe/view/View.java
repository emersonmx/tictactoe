package com.gmail.emersonmx.tictactoe.view;

import com.badlogic.gdx.graphics.Color;

public interface View {

    public static final Color PLAYER_1_COLOR = new Color(0x8080ffff);
    public static final Color PLAYER_2_COLOR = new Color(0xff8080ff);

    public void draw();
    public void resize(int width, int height);
    public void dispose();

}
