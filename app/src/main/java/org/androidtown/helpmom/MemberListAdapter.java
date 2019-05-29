package org.androidtown.helpmom;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MemberListAdapter extends BaseAdapter {
    private Context context;
    private List<Member> memberList;

    private String leader;

    public MemberListAdapter(Context context, List<Member> memberList, String leader){
        this.context = context;
        this.memberList = memberList;
        this.leader = leader;
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
            memberName.setTextColor(Color.BLUE);
        }
        memberName.setText(memberList.get(i).getMemberName());
        return v;
    }
}
