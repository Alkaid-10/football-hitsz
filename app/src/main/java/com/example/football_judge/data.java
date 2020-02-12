package com.example.football_judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

    }

    void data_ok(View view){//从数据库中修改相应球员数据
        EditText name=(EditText)findViewById(R.id.input_name);
        EditText goal=(EditText)findViewById(R.id.input_goal);
        int data_goal=0;
        try{//获取球员本场进球
            data_goal=Integer.parseInt(goal.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        EditText assist=(EditText)findViewById(R.id.input_assist);
        int data_assist=0;
        try{//获取球员本场助攻
            data_assist=Integer.parseInt(assist.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        EditText opposite_mark=(EditText)findViewById(R.id.input_oppsite_mark);
        double data_opposite_mark=0.0;
        try{//获取本场对方评分
            data_opposite_mark=Double.parseDouble(opposite_mark.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        EditText referee_mark=(EditText)findViewById(R.id.input_referee_mark);
        double data_referee_mark=0.0;
        try{//获取本场裁判评分
            data_referee_mark=Double.parseDouble(referee_mark.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        EditText fourth_official_mark=(EditText)findViewById(R.id.input_fourth_official_mark);
        double data_fourth_official_mark=0.0;
        try{//获取第四官员评分
            data_fourth_official_mark=Double.parseDouble(fourth_official_mark.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        dbHelper dbHelper=new dbHelper(this);//开始处理数据库
        Log.e("dpHelper created","dpHelper created");
        //从数据库里拿到目标对象（）
        SQLiteDatabase db=dbHelper.getReadableDatabase();//获取可读数据库
        Log.e("DATA BASE OPEN SUCCEED","DATA BASE OPEN SUCCEED");
        String[] projection = {
                // BaseColumns._ID,
                Contract.FeedEntry.NAME,
                Contract.FeedEntry.TEAM,
                Contract.FeedEntry.NUM,
                Contract.FeedEntry.GOAL,
                Contract.FeedEntry.ASSIST,
                Contract.FeedEntry.MARK
        };

        // Filter results WHERE "title" = 'My Title'，筛选条件（输入的球员姓名）
        String selection = Contract.FeedEntry.NAME + " = ?";
        String[] selectionArgs = {name.getText().toString()};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Contract.FeedEntry.GOAL + " DESC";

        Cursor cursor = db.query(
                Contract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        if(cursor.getCount()!=0){//保证找到球员时才执行该操作
            cursor.moveToFirst();//移动到唯一的记录上
            //对对象进行数据处理
            Player player=new Player(cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.TEAM)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedEntry.NUM)));
            player.set_Goal(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedEntry.GOAL)));//设定已进球
            player.add_goal(data_goal);//更新总进球
            player.set_Assist(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedEntry.ASSIST)));//设定已助攻
            player.add_assist(data_assist);//更新总助攻
            player.set_mark((0.2*data_opposite_mark+0.3*data_referee_mark+0.5*data_fourth_official_mark),1);//目前还是当场评分，存数据库的时候注意一下

            //处理完数据后进行更新
            db.close();//关闭可读数据库
            //将待更新数据准备好
            ContentValues values=new ContentValues();
            values.put(Contract.FeedEntry.GOAL,player.getGoal());
            values.put(Contract.FeedEntry.ASSIST,player.getAssist());
            values.put(Contract.FeedEntry.MARK,player.getMark());
            //开始更新
            // Which row to update, based on the title
            selection = Contract.FeedEntry.NAME + " LIKE ?";
            selectionArgs = new String[]{player.getName().toString()};
            db=dbHelper.getWritableDatabase();//打开可写数据库
            int count = db.update(
                    Contract.FeedEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            Log.e("Update ok the name is:",player.getName().toString());
            cursor.close();//关闭游标
            db.close();//关闭可写数据库
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);//返回主界面
        }
        else {
            cursor.close();
            db.close();
            name.setText("无该球员");
            goal.setText("");
            assist.setText("");
            opposite_mark.setText("");
            referee_mark.setText("");
            fourth_official_mark.setText("");
        }
    }
    void clear(View view){
        EditText goal=(EditText)findViewById(R.id.input_goal);
        goal.setText("");
        EditText assist=(EditText)findViewById(R.id.input_assist);
        assist.setText("");
        EditText opposite_mark=(EditText)findViewById(R.id.input_oppsite_mark);
        opposite_mark.setText("");
        EditText referee_mark=(EditText)findViewById(R.id.input_referee_mark);
        referee_mark.setText("");
        EditText fourth_official_mark=(EditText)findViewById(R.id.input_fourth_official_mark);
        fourth_official_mark.setText("");
    }
    void back(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
