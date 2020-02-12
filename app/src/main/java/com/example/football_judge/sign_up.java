package com.example.football_judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
public class sign_up extends AppCompatActivity {
    //public String[] teams={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Spinner spinner=(Spinner) findViewById(R.id.teams);
        String[] teams=getResources().getStringArray(R.array.teams);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(sign_up.this, android.R.layout.simple_spinner_dropdown_item, teams);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void ok(View view){//传送数据至数据库并返回主界面
        EditText name=(EditText)findViewById(R.id.name);//获取输入名字
        Spinner teams=(Spinner)findViewById(R.id.teams);//获取选择的队伍

        EditText num=(EditText)findViewById(R.id.num);//获取球员号码
        //将球员报名数据录入数据库
        dbHelper dbHelper=new dbHelper(this);
        Log.e("dpHelper created","dpHelper created");
        // Gets the data repository in write mode


        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.FeedEntry.NAME, name.getText().toString());
        Log.e("first data input: ",values.get(Contract.FeedEntry.NAME).toString());
        values.put(Contract.FeedEntry.TEAM, teams.getSelectedItem().toString());
        Log.e("second data input: ",values.get(Contract.FeedEntry.TEAM).toString());
        values.put(Contract.FeedEntry.NUM,num.getText().toString());
        Log.e("third data input: ",values.get(Contract.FeedEntry.NUM).toString());
        values.put(Contract.FeedEntry.GOAL,0);
        Log.e("fourth data input: ",values.get(Contract.FeedEntry.GOAL).toString());
        values.put(Contract.FeedEntry.ASSIST,0);
        Log.e("fifth data input: ",values.get(Contract.FeedEntry.ASSIST).toString());
        values.put(Contract.FeedEntry.MARK,6.0);
        Log.e("sixth data input: ",values.get(Contract.FeedEntry.MARK).toString());
        Log.e("value set :",values.toString());
        SQLiteDatabase db=dbHelper.getReadableDatabase();//打开可读数据库防止重复
        //Insert the new row, returning the primary key value of the new row
        String[] projection = {
                // BaseColumns._ID,
                Contract.FeedEntry.NAME,
                Contract.FeedEntry.TEAM,
                Contract.FeedEntry.NUM,
                Contract.FeedEntry.GOAL,
                Contract.FeedEntry.ASSIST,
                Contract.FeedEntry.MARK
        };
        Cursor cursor = db.query(Contract.FeedEntry.TABLE_NAME,
                projection,
                Contract.FeedEntry.NAME +" = ?",
                new String[]{name.getText().toString()},
                null,
                null,
                null);
        cursor.moveToFirst();
       if(cursor.getCount()==0) {//防止重复添加
            db.close();//关闭可读数据库，打开可写数据库
            db = dbHelper.getWritableDatabase();
            long newRowId = db.insert(Contract.FeedEntry.TABLE_NAME, null, values);
            Log.e("newRowId is:",String.valueOf(newRowId));
           //返回主界面
           cursor.close();//关闭游标
           db.close();//关闭可写数据库
           Intent intent=new Intent(this,MainActivity.class);
           startActivity(intent);
        }
        else{
            name.setText("重复啦！！！");
           //返回主界面
           cursor.close();
           db.close();
        }



    }
    void back(View view){//回到主界面
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    void clear(View view){//清空当前内容
        EditText name=(EditText)findViewById(R.id.name);//获取输入名字
        Spinner teams=(Spinner)findViewById(R.id.teams);//获取选择的队伍
        EditText num=(EditText)findViewById(R.id.num);//获取球员号码
        name.setText("");
        teams.setSelection(0);
        num.setText("");
        }
}
