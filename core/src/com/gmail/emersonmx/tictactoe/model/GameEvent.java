package com.gmail.emersonmx.tictactoe.model;

import java.util.EventObject;

public class GameEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public GameEvent(Object source) {
        super(source);
    }

}
