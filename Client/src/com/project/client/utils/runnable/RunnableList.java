package com.project.client.utils.runnable;


import java.util.ArrayList;

/**
 * Simple list structure to keep runnable timer in the list
 */
public class RunnableList extends ArrayList<RunnableTimer> {

    /**/
    public RunnableList(Runnable... runnableList) {
        init(runnableList);
    }

    private void init(Runnable... runnableList) {
        for (Runnable runnable : runnableList) {
            if (runnable == null) {
                continue;
            }
            addRunnable(runnable);
        }
    }

    private void addRunnable(Runnable runnable) {
        add(getTimer(runnable));
    }

    /**/

    /**
     * To get a new timer thread to run runnable periodically
     *
     * @param runnable get timer for the runnable
     * @return a new timer
     */
    RunnableTimer getTimer(Runnable runnable) {
        return new RunnableTimer(runnable);
    }

    /* starting */
    public void startAll() {
        forEach(this::startRunnable);
    }

    private void startRunnable(RunnableTimer runnableTimer) {
        runnableTimer.startRunnable();
    }

    /* stopping */
    public void stopAll() {
        forEach(this::stopRunnable);
    }

    private void stopRunnable(RunnableTimer runnableTimer) {
        runnableTimer.stopRunnable();
    }


}
