package com.bulltronics.rc.server.model;

import com.google.gson.JsonObject;

public class Command {
    private Integer seqNum = 0;
    private Action action = Action.UTIL_WAIT;
    private JsonObject data = new JsonObject();

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
