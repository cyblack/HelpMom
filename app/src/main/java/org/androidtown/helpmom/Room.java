package org.androidtown.helpmom;

public class Room {


    private String roomName;
    private String roomMaker;
    private String roomNumber;
    public Room(String roomName,String roomMaker,String roomNumber){
        this.roomName = roomName;
        this.roomMaker = roomMaker;
        this.roomNumber=roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomName() {
        return roomName;
    }
    public String getRoomMaker() {return roomMaker;}
}

