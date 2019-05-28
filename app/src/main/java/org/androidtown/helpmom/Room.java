package org.androidtown.helpmom;

public class Room {


    private String roomName;
    private String roomMaker;

    public Room(String roomName,String roomMaker){
        this.roomName = roomName;
        this.roomMaker = roomMaker;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomName() {
        return roomName;
    }
    public String getRoomMaker() {return roomMaker;}
}

