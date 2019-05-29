package org.androidtown.helpmom;

public class RegisterResult {

    private String res,name,id,joinRoom;
    private String[] roomName;
    private String[] member;
    private String[] task;
    private String[] roomNumber,roomMaker;

    //task위한것
    private String[] taskId;
    private String [] progress,comment,created,point,changedName;

    public String[] getTaskId() {
        return taskId;
    }

    public String [] getProgress() {
        return progress;
    }

    public String[] getComment() {
        return comment;
    }

    public String[] getCreated() {
        return created;
    }

    public String[] getPoint() {
        return point;
    }

    public String[] getChangedName() {
        return changedName;
    }

    public String[] getRoomNumber(){return roomNumber;}

    public String[] getTask(){return  task; }

    public String getName() {
        return name;
    }

    public String[] getRoomName() {
        return roomName;
    }

    public String getId() {
        return id;
    }

    public String getRes() {
        return res;
    }

    public String getJoinRoom() {return joinRoom;}
    public String[] getMember(){return member;}
    public String[] getRoomMaker(){return roomMaker;}
}
