package com.gmail.emersonmx.tictactoe.model;

public class Player {

    public int mark;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player) obj;
            if (mark == other.mark) {
                return true;
            }
        }

        return false;
    }

}
