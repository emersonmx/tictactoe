package com.gmail.emersonmx.tictactoe.view;

import com.gmail.emersonmx.tictactoe.controller.Controller;

public abstract class AbstractView implements View {

    protected Controller controller;

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

}
