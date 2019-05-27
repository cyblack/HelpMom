package org.androidtown.helpmom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {

    private Context context;
    private List<Room> roomList;

    public RoomListAdapter(Context context, List<Room> roomList){
        this.context = context;
        this.roomList = roomList;

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

        roomName.setText(roomList.get(i).getRoomName());

        return v;
    }
}
