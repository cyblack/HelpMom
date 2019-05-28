package org.androidtown.helpmom;

public class Member {


    private String memberName;

    public Member(String memberName){
        this.memberName = memberName;
    }

    public void setRoomName(String memberName) {
        this.memberName = memberName;
    }
    public String getMemberName() {
        return memberName;
    }
}
