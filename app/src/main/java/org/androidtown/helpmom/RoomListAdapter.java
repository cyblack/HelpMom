package org.androidtown.helpmom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context,R.layout.room,null);

        TextView roomName = (TextView)v.findViewById(R.id.roomName);
        ImageView btn_delete_room=v.findViewById(R.id.btn_delete_room);

        btn_delete_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setMessage("그룹을 나가시겠습니까?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        roomList.remove(i);
                        notifyDataSetChanged();
                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        });
        if(roomList.get(i).getRoomMaker().equals(me)) {
            roomName.setTextColor(Color.BLUE);
        }
        roomName.setText(roomList.get(i).getRoomName());

        return v;


    }
}
