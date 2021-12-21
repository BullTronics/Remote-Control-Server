package com.bulltronics.rc.server.service;

import com.bulltronics.rc.server.controller.KeyboardController;
import com.bulltronics.rc.server.controller.MouseController;
import com.bulltronics.rc.server.controller.MediaController;
import com.bulltronics.rc.server.controller.UtilityController;
import com.bulltronics.rc.server.model.Command;
import com.bulltronics.rc.server.model.Status;

import java.util.ArrayList;
import java.util.List;

public class CommandHandlerService {
    static MediaController mediaController = new MediaController();
    static MouseController mouseController = new MouseController();
    static KeyboardController keyboardController = new KeyboardController();
    static UtilityController utilityController = new UtilityController();

    public static List<Status> handle(List<Command> commandList) {
        List<Status> statusList = new ArrayList<>();

        for (Command command : commandList) {
            Status status = new Status();

            switch (command.getAction()) {
                case MEDIA_VOLUME_SET -> status = mediaController.setVolume(command.getData().get("volume").getAsFloat());
                case KEYBOARD_KEY_PRESS -> status = keyboardController.pressKey(command.getData().get("key").getAsString());
                case CURSOR_RANDOM_MOVE_START -> status = mouseController.randomCursorMoveSchedule(command.getData().get("delay").getAsLong(), command.getData().get("duration").getAsLong(), command.getData().get("step").getAsLong(), command.getData().get("pause").getAsLong());
                case CURSOR_RANDOM_MOVE_STOP -> status = mouseController.randomCursorMoveStop();
                case CONFIG_GET_SERVER_VERSION -> status = ConfigService.getServerVersion();
                case CONFIG_GET_SERVER_HOSTS -> status = ConfigService.getServerHosts();
                case CONFIG_GET_SERVER_MAME -> status = ConfigService.getServerName();
                case UTIL_WAIT -> status = utilityController.waitForMillis(command.getData().get("duration").getAsLong());
            }

            status.setSeqNum(command.getSeqNum());
            status.setAction(command.getAction());
            statusList.add(status);
        }

        return statusList;
    }
}
