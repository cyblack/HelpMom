package org.androidtown.helpmom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageTaskActivty extends AppCompatActivity {

    ListView listView_addedTask;
    EditText editText_addTask;
    Button btn_addTask;
    Button btn_delete_tasks;
    Button btn_confirm_tasks;

    // EditText의 String값 받을 용도.
    String editTextValue;

    // ListView에 전시할 데이터.
    ArrayList<Data_Format> datas;
    String task;
    private static MyAdapter adapter;

    // 체크된 Task들은 이 arraylist에 저장될것임.
    ArrayList<Data_Format> list_decided_tasks;

    // ListView의 onItemClickListener 에 쓰임.
    int default_backgroundColor = Color.parseColor("#fcfdff"); //기존 배경색
    int checkedColor = Color.parseColor("#f9b37a"); //체크됬을때 배경색

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        listView_addedTask = (ListView) findViewById(R.id.listView_taskStored);
        editText_addTask = (EditText) findViewById(R.id.editText_addTask);
        btn_addTask = (Button) findViewById(R.id.btn_addTask);
        btn_delete_tasks = (Button) findViewById(R.id.btn_delete_tasks);
        btn_confirm_tasks=findViewById(R.id.confirm_tasks);
        // Data 리스트
        datas = new ArrayList<Data_Format>();

        // 선택된 task들을 담을 arrayList. 넘겨줄 것임.
        list_decided_tasks = new ArrayList<Data_Format>();

        // Adapter 정의
        adapter = new MyAdapter(datas, getApplicationContext());
        adapter.notifyDataSetChanged();

        // 리스트뷰에 데이터 연결 완료.
        listView_addedTask.setAdapter(adapter);

        btn_confirm_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
                finish();
            }
        });
        listView_addedTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // user selects item, get that item.
                Object itemObject = adapterView.getAdapter().getItem(position);

                // cast it to Data_Format format.
                Data_Format data_format_object = (Data_Format) itemObject;

                // get the checkBox and others of a row.
                CheckBox itemCheckBox = (CheckBox) view.findViewById(R.id.checkBox_listView_row_item);
                TextView txt_view = (TextView) view.findViewById(R.id.textView_rowItem_detail);
                ImageView imgView = (ImageView) view.findViewById(R.id.imgView_rowItem_icon);

                // Reverse the state of the checkBox, change backgroundColor.
                if (data_format_object.isChecked() == true) {
                    Log.d("itemClicked:", "cur state:" + data_format_object.toString());
                    itemCheckBox.setChecked(false);
                    data_format_object.setCheckedState(false);


                } else if (data_format_object.isChecked() == false) {
                    Log.d("itemClicked:", "cur state:" + data_format_object.toString());
                    itemCheckBox.setChecked(true);
                    data_format_object.setCheckedState(true);
                }
            }
        });

        // AddTask 버튼 이벤트리스너.
        // 할일추가버튼(에딧텍스트옆에있는) 을 누르면, editText에서 입력한 값을 Data_Format에 넣고 그 Data_Format 오브젝트를
        // 위에있는 할일 list에 추가.
        btn_addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editTextValue = editText_addTask.getText().toString();

                if (editTextValue.isEmpty()) {
                    Toast.makeText(ManageTaskActivty.this, "할 일을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {

                    Data_Format data_format1 = new Data_Format(editTextValue);

                    // 유저가 입력한 할일을 data_list (어레이 리스트)에 추가
                    datas.add(data_format1);

                    Log.d("taskAdd", data_format1.toString());

                    // 리스트뷰 갱신 (이래야 화면에 나타남)
                    adapter.notifyDataSetChanged();
                    // 가장 밑 row로 자동 스크롤
                    scrollMyListViewToBottom();
                    // 초기화
                    editText_addTask.setText("");
                    // ListView 높이 동적 변화 : 350dp -> 150dp
                }
            }
        });

        // delete 버튼누르면:
        btn_delete_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(ManageTaskActivty.this).create();
                alertDialog.setMessage("선택한 일을 삭제 하시겠습니까?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        int size = datas.size();
                        for (int i = 0; i < size; i++) {
                            // unselect the checkedboxes.
                            Data_Format data_format = datas.get(i);

                            if (data_format.isChecked()) {
                                datas.remove(i);
                                i--;
                                size = datas.size();
                            }
                        }
                        // update
                        adapter.notifyDataSetChanged();
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
    }


    // 각 listView의 row에 display할 내용의 포맷.
    public class Data_Format {

        // memebers
        String task_detail;
        boolean isChecked = false; //체크박스 상태 반영

        // 생성자
        public Data_Format(String task_detail) {
            this.task_detail = task_detail;
        }

        public String toString() {
            if (isChecked() == true) {
                return "isChecked";
            } else {
                return "isNotChecked";
            }
        }

        // 메소드
        public String getTask_detail() {
            return task_detail;
        }

        public boolean isChecked() {
            return isChecked == true;
        }

        public void setCheckedState(boolean bool) {
            isChecked = bool;
        }
    }


    public class MyAdapter extends BaseAdapter {

        private ArrayList<Data_Format> dataSet = null;
        private Context mContext = null;

        public MyAdapter(ArrayList<Data_Format> data, Context context) {
            this.dataSet = data;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            int ret = 0;
            if (dataSet != null) {
                ret = dataSet.size();
            }
            return ret;
        }

        @Override
        public Object getItem(int itemIndex) {
            Object ret = null;
            if (dataSet != null) {
                ret = dataSet.get(itemIndex);
            }
            return ret;
        }

        @Override
        public long getItemId(int itemIndex) {
            return itemIndex;
        }

        private int lastPosition = -1;

        @NonNull
        @Override
        public View getView(int itemPosition, @Nullable View convertView, @NonNull ViewGroup parent) {

            mViewHolder viewHolder = null;


            if (convertView != null) {
                viewHolder = (mViewHolder) convertView.getTag();
            } else {
//                // 수정됨(from 안에)
//                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
//                // row_item.xml 팽창.
//                convertView = inflater.inflate(R.layout.row_item, parent, false);
                convertView = View.inflate(mContext, R.layout.row_item, null);

                TextView item_task_detail = (TextView) convertView.findViewById(R.id.textView_rowItem_detail);
                CheckBox itemCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox_listView_row_item);

                viewHolder = new mViewHolder(convertView);
                viewHolder.setItemCheckBox(itemCheckBox);
                viewHolder.setItemTextView(item_task_detail);

                convertView.setTag(viewHolder);
            }

            Data_Format data_format = dataSet.get(itemPosition);
            viewHolder.getItemCheckBox().setChecked(data_format.isChecked());
            viewHolder.getItemTextView().setText(data_format.getTask_detail());


            int lastPosition = -1;

            // 수정: result->convertView.
            Animation animation = AnimationUtils.loadAnimation(mContext, (itemPosition > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            convertView.startAnimation(animation);
            lastPosition = itemPosition;


            // return the completed view to render on screen
            return convertView;
        }
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;
        private CheckBox itemCheckBox;

        public mViewHolder(View itemView) {
            super(itemView);
        }

        public CheckBox getItemCheckBox() {
            return itemCheckBox;
        }

        public void setItemCheckBox(CheckBox itemCheckBox) {
            this.itemCheckBox = itemCheckBox;
        }

        public TextView getItemTextView() {
            return itemTextView;
        }

        public void setItemTextView(TextView itemTextView) {
            this.itemTextView = itemTextView;
        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Task가 추가될 때마다 마지막 row로 자동스크롤.
    public void scrollMyListViewToBottom() {
        listView_addedTask.post(new Runnable() {
            @Override
            public void run() {
                // select the last row, so it will scroll into view..
                listView_addedTask.setSelection(adapter.getCount() - 1);
            }
        });
    }
    public void Confirm(){
        Intent intent=getIntent();
        String title=intent.getStringExtra("name");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        for(int i=0;i<list_decided_tasks.size();i++)
        {
            task=list_decided_tasks.get(i).getTask_detail();
            Call<RegisterResult> call = service.createTask(title,task);

            call.enqueue(new Callback<RegisterResult>() {
                @Override
                public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RegisterResult r = response.body();

                    Toast.makeText(getApplicationContext(), "result :  " + r.getTask(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<RegisterResult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }




    }
}
