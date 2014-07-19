package com.gmail.emersonmx.tictactoe.view;


public abstract class AbstractView implements View {

    protected ViewManager viewManager;

    public AbstractView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

}
