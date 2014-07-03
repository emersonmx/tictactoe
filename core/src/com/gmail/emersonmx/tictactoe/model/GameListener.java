package com.gmail.emersonmx.tictactoe.model;

import java.util.EventListener;

interface GameListener extends EventListener {

    public void gameStart(GameEvent event);
    public void gameOver(GameEvent event);
    public void marked(GameEvent event);
    public void gameWinner(GameEvent event);
    public void gameDraw(GameEvent event);
    public void currentPlayerChanged(GameEvent event);
    public void invalidPosition(GameEvent event);
    public void positionIsNotEmpty(GameEvent event);

}
