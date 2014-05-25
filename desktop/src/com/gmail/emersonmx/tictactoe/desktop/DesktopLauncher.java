package com.gmail.emersonmx.tictactoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gmail.emersonmx.tictactoe.TicTacToeGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "TicTacToe";
        config.width = 240;
        config.width = 320;
        new LwjglApplication(new TicTacToeGame(), config);
    }

}
