package com.gmail.emersonmx.tictactoe.view;

import com.gmail.emersonmx.tictactoe.controller.Controller;

public abstract class AbstractView implements View {

    protected ViewManager viewManager;
    protected Controller controller;
    protected boolean loaded;

    public AbstractView(ViewManager viewManager) {
        this.viewManager = viewManager;
        loaded = false;
    }

    @Override
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public ViewManager getViewManager() {
        return viewManager;
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
