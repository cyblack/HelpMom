package org.androidtown.helpmom;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {

    private Context context;
    private List<Room> roomList;
    private String me;

    public RoomListAdapter(Context context, List<Room> roomList,String me){
        this.context = context;
        this.roomList = roomList;
        this.me = me;
    }


    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int i) {
        return roomList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context,R.layout.room,null);

        TextView roomName = (TextView)v.findViewById(R.id.roomName);


        if(roomList.get(i).getRoomMaker().equals(me)) {
            roomName.setTextColor(Color.BLUE);
        }
        roomName.setText(roomList.get(i).getRoomName());

        return v;
    }
}
