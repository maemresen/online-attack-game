package com.project.client.utils.runnable;


public class FXRunnableList extends RunnableList {

    public FXRunnableList(Runnable... runnableList) {
        super(runnableList);
    }

    @Override
    protected RunnableTimer getTimer(Runnable runnable) {
        return new FXRunnableTimer(runnable);
    }

}
