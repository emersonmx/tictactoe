package com.gmail.emersonmx.tictactoe.view;

import com.gmail.emersonmx.tictactoe.controller.Controller;

public abstract class AbstractView implements View {

    protected Controller controller;
    protected boolean loaded;

    public AbstractView() {
        loaded = false;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

}
