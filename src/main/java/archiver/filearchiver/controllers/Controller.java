package archiver.filearchiver.controllers;

import archiver.filearchiver.view.ViewFactory;

public abstract class Controller {
    private final String fxmlName;
    private final ViewFactory viewFactory;

    public Controller(String fxmlName, ViewFactory viewFactory) {
        this.fxmlName = fxmlName;
        this.viewFactory = viewFactory;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
