package org.androidtown.helpmom;

public class RegisterResult {

    private String res,name,id;
    private String[] roomName;
    private String joinRoom;
    private String[] member;
    private String[] task;

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
}
