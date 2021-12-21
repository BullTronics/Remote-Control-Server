package com.bulltronics.rc.server.controller;

import com.bulltronics.rc.server.model.Status;

import java.awt.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MouseController {
    Thread thread = null;

    Timer timerStart = null;
    TimerTask timerTaskStart = null;
    Timer timerStop = null;
    TimerTask timerTaskStop = null;

    Long stepDuration = 500L;
    Long pauseDuration = 500L;

    Runnable runnable = () -> {
        try {
            System.out.println("Starting Auto Cursor Movement");
            Point point = MouseInfo.getPointerInfo().getLocation();

            Robot r = new Robot();
            while (true) {
                for (int x = 0; x < 50; x++) {
                    r.mouseMove((int) point.getX() + x, (int) point.getY());
                    Thread.sleep(stepDuration);
                }
                for (int y = 0; y < 50; y++) {
                    r.mouseMove((int) point.getX() + 49, (int) point.getY() + y);
                    Thread.sleep(stepDuration);
                }
                for (int x = 49; x >= 0; x--) {
                    r.mouseMove((int) point.getX() + x, (int) point.getY() + 49);
                    Thread.sleep(stepDuration);
                }
                for (int y = 49; y >= 0; y--) {
                    r.mouseMove((int) point.getX(), (int) point.getY() + y);
                    Thread.sleep(stepDuration);
                }
                Thread.sleep(pauseDuration);
            }

            //r.mouseMove((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);

            //System.out.println("Finished Auto Cursor Movement");
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (Exception ignored) {
        }
    };

    /* Asynchronous Call */
    public Status randomCursorMoveSchedule(Long delay, Long duration, Long stepDuration, Long pauseDuration) {
        System.out.println("Scheduling Auto Cursor Movement");
        Status status = new Status();
        this.stepDuration = stepDuration;
        this.pauseDuration = pauseDuration;

        if (thread == null) {
            thread = new Thread(runnable);

            timerStart = new Timer();
            timerTaskStart = new TimerTask() {
                @Override
                public void run() {
                    thread.start();
                }
            };
            timerStart.schedule(timerTaskStart, new Date(System.currentTimeMillis() + delay));

            timerStop = new Timer();
            timerTaskStop = new TimerTask() {
                @Override
                public void run() {
                    randomCursorMoveStop();
                }
            };
            timerStop.schedule(timerTaskStop, new Date(System.currentTimeMillis() + delay + duration));

        } else {
            System.out.println("Auto Cursor Movement Already In Progress");
        }

        return status;
    }

    public Status randomCursorMoveStop() {
        System.out.println("Stopping Auto Cursor Movement");
        Status status = new Status();

        if (thread != null) {
            timerTaskStart.cancel();
            timerStart.cancel();
            timerStart.purge();
            timerTaskStop.cancel();
            timerStop.cancel();
            timerStop.purge();

            thread.interrupt();

            thread = null;
            timerStart = null;
            timerTaskStart = null;
            timerStop = null;
            timerTaskStop = null;
        }

        return status;
    }


}
