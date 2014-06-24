package com.gmail.emersonmx.tictactoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.gmail.emersonmx.tictactoe.TicTacToe;

public class DesktopLauncher {

    public static void main(String[] arg) {
        Settings settings = new Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;

        TexturePacker.process(settings, "../../images/game", ".", "game");

        new LwjglApplication(new TicTacToe(), "TicTacToe",
                             TicTacToe.WINDOW_WIDTH, TicTacToe.WINDOW_HEIGHT);
    }

}
