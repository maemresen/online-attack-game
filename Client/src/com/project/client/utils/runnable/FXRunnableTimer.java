package com.project.client.utils.runnable;

import javafx.animation.AnimationTimer;

/**
 * Timer to organize runnable with JavaFX application thread
 */
public class FXRunnableTimer extends RunnableTimer {

    private AnimationTimer timer;

    public FXRunnableTimer(Runnable runnable) {
        super(runnable);
    }


    public void startRunnable() {

        timer = new AnimationTimer() {

            private long lastUpdate = 0;

            @Override
            public void handle(long now) {

                if (now - lastUpdate >= 200_000_000) {
                    runnable.run();
                    lastUpdate = now;
                }
            }

        };

        timer.start();

    }

    @Override
    public void stopRunnable() {
        timer.stop();
    }
}
