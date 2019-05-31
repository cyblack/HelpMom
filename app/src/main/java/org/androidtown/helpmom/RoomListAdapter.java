package org.androidtown.helpmom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomListAdapter extends BaseAdapter {

    private Context context;
    private List<Room> roomList;
    private String me;

    public RoomListAdapter(Context context, List<Room> roomList,String me){
        this.context = context;
        this.roomList = roomList;
        this.me = me;
    }

    public String getLeader(){return "";}

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

                if(roomList.get(i).getRoomMaker().equals(me)) {
                    roomDelete(i);
                }
                else{
                    roomOut(i);
                }
            }
        });
        if(roomList.get(i).getRoomMaker().equals(me)) {
            roomName.setTextColor(Color.BLUE);
        }
        roomName.setText(roomList.get(i).getRoomName());

        return v;
    }

    public void roomDelete(final int i){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("그룹을 삭제하시겠습니까?");
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService service = retrofit.create(ApiService.class);
                Call<RegisterResult> call = service.roomDelete(roomList.get(i).getRoomNumber());

                call.enqueue(new Callback<RegisterResult>() {
                    @Override
                    public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {


                        if(!response.isSuccessful()){
                            Toast.makeText(context, "code " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        RegisterResult r = response.body();
                    }
                    @Override
                    public void onFailure(Call<RegisterResult> call, Throwable t) {
                        Toast.makeText(context, "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

    public void roomOut(final int i){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("그룹을 나가시겠습니까?");
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.d("jdj",me+roomList.get(i).getRoomName());
                ApiService service = retrofit.create(ApiService.class);
                Call<RegisterResult> call = service.roomOut(me,roomList.get(i).getRoomNumber());

                call.enqueue(new Callback<RegisterResult>() {
                    @Override
                    public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {


                        if(!response.isSuccessful()){
                            Toast.makeText(context, "code " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        RegisterResult r = response.body();
                    }
                    @Override
                    public void onFailure(Call<RegisterResult> call, Throwable t) {
                        Toast.makeText(context, "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
}
