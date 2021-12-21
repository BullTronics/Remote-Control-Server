package com.bulltronics.rc.server.controller;

import com.bulltronics.rc.server.model.Status;

import javax.sound.sampled.*;
import java.util.LinkedList;

public class MediaController {

    private final LinkedList<Line> speakers = new LinkedList<Line>();

    public MediaController() {
        findSpeakers();
    }

    private void findSpeakers() {
        System.out.println("MediaController.findSpeakers()");
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        System.out.println("MediaController.findSpeakers() mixers.length: " + mixers.length);
        for (Mixer.Info mixerInfo : mixers) {
            if (!mixerInfo.getName().equals("Java Sound Audio Engine")) continue;

            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lines = mixer.getSourceLineInfo();

            for (Line.Info info : lines) {
                try {
                    Line line = mixer.getLine(info);
                    speakers.add(line);

                } catch (LineUnavailableException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private float limit(FloatControl control, float level) {
        return Math.min(control.getMaximum(), Math.max(control.getMinimum(), level));
    }


    public Status setVolume(float level) {
        Status status = new Status();

        System.out.println("setting volume to " + level);
        for (Line line : speakers) {
            try {
                line.open();
                FloatControl control = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue(limit(control, level));
            } catch (LineUnavailableException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

}
