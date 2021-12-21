package com.bulltronics.rc.server.controller;

import com.bulltronics.rc.server.model.Status;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardController {
    public Status pressKey(String key) {
        System.out.println("KeyboardController.pressKey() --> " + key);
        Status status = new Status();

        try {
            Robot r = new Robot();

            Integer keyEvent = switch (key) {
                case "LEFT" -> KeyEvent.VK_LEFT;
                case "RIGHT" -> KeyEvent.VK_RIGHT;
                case "DOWN" -> KeyEvent.VK_DOWN;
                case "UP" -> KeyEvent.VK_UP;
                default -> null;
            };

            if(keyEvent != null) {
                r.keyPress(keyEvent);
                r.keyRelease(keyEvent);
            }
        } catch (Exception e) {
            System.out.println("--> " + e.getMessage());
            e.printStackTrace();
        }

        return status;
    }
}
