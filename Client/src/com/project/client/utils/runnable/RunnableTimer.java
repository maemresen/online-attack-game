package com.project.client.utils.runnable;

import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Simple timer for a runnable
 */
public class RunnableTimer {

    final Runnable runnable;

    private boolean stop = false;

    RunnableTimer(Runnable runnable) {
        this.runnable = runnable;
    }


    public void startRunnable() {
        stop = false;
        new Thread(() -> Platform.runLater(this::execute)).start();
    }

    /**
     * executing the runnable
     */
    private void execute() {
        if (stop) {
            return;
        }
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(runnable, 100, 200, TimeUnit.MILLISECONDS);
    }

    public void stopRunnable() {
        stop = true;
    }


}
