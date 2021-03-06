package com.bulltronics.rc.server.model;

public enum Action {
    MEDIA_VOLUME_INCREASE,
    MEDIA_VOLUME_DECREASE,
    MEDIA_VOLUME_MUTE,
    MEDIA_VOLUME_GET,
    MEDIA_VOLUME_SET,
    MEDIA_VOLUME_UNMUTE,
    MEDIA_PREVIOUS,
    MEDIA_NEXT,
    MEDIA_STOP,
    MEDIA_PAUSE,
    MEDIA_PLAY,

    CURSOR_MOVE,
    CURSOR_GET,
    CURSOR_RANDOM_MOVE_START,
    CURSOR_RANDOM_MOVE_STOP,

    KEYBOARD_KEY_PRESS,
    KEYBOARD_TYPE,

    CONFIG_GET_SERVER_VERSION,
    CONFIG_GET_SERVER_MAME,
    CONFIG_GET_SERVER_HOSTS,

    UTIL_WAIT
}
