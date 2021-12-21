package com.bulltronics.rc.server.model;

import com.google.gson.JsonObject;

public class Status {
    private Integer seqNum = 0;
    private Action action;
    private String message = "";
    private JsonObject data = new JsonObject();

    public Status() {
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
