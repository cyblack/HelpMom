package org.androidtown.helpmom;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MemberListAdapter extends BaseAdapter {
    private Context context;
    private List<Member> memberList;
    private ImageView img;
    private String leader;
    private String myName;

    public MemberListAdapter(Context context, List<Member> memberList, String leader,String myName){
        this.context = context;
        this.memberList = memberList;
        this.leader = leader;
        this.myName = myName;
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int i) {
        return memberList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context,R.layout.member,null);

        TextView memberName = (TextView)v.findViewById(R.id.memberName);


        if(memberList.get(i).getMemberName().equals(leader)) {
            img=v.findViewById(R.id.imgView_rowItem_icon);
            img.setImageResource(R.drawable.queen);
            if(memberList.get(i).getMemberName().equals(myName)){
                memberName.setText(memberList.get(i).getMemberName());
                memberName.setTextColor(Color.BLUE);
            }else {
                memberName.setText(memberList.get(i).getMemberName());
            }
        }else {
            if(memberList.get(i).getMemberName().equals(myName)){
                memberName.setTextColor(Color.BLUE);
                memberName.setText(memberList.get(i).getMemberName());
            }else {
                memberName.setText(memberList.get(i).getMemberName());
            }
        }
        return v;
    }
}
