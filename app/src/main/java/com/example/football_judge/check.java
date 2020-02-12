package com.example.football_judge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        //此处从数据库中获取到所有的球员数据
        dbHelper helper=new dbHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();//获取可读数据库
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

        // Filter results WHERE "title" = 'My Title'
        String selection = Contract.FeedEntry.NAME + " = ";
        String[] selectionArgs = { "*" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Contract.FeedEntry.GOAL + " DESC";

        Cursor cursor = db.query(
                Contract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        Log.e("cursor is :",String.valueOf(cursor.getCount()));
        Log.e("DATA BASE GET SUCCEED",projection.toString());

        final List <Player> players=new ArrayList<Player>();//球员离线数据库()
        int i=0;
        if(cursor.getCount()!=0){//防止查询结果为空

            cursor.moveToFirst();
            do{//从数据库中添加球员至players离线库中
                Log.e("data "+i+" is: ",cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.NAME)));
                Log.e("data "+i+" is: ",cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.TEAM)));
                Log.e("data "+i+" is: ",cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.NUM)));
                players.add(new Player(cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.TEAM)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedEntry.NUM))));
                Log.e("data "+i+" is: ",cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.GOAL)));
                players.get(i).set_Goal(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedEntry.GOAL)));
                Log.e("data "+i+" is: ",cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.ASSIST)));
                players.get(i).set_Assist(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.FeedEntry.ASSIST)));
                Log.e("data "+i+" is: ",cursor.getString(cursor.getColumnIndexOrThrow(Contract.FeedEntry.MARK)));
                players.get(i).set_mark(cursor.getDouble(cursor.getColumnIndexOrThrow(Contract.FeedEntry.MARK)),1);
                i++;
            }while(cursor.moveToNext());
        }

        Log.e("i is :",String.valueOf(i));
        i=0;
        Log.e("FINAL","SUCCESS");
        cursor.close();//关闭游标
        db.close();//关闭可读数据库
        //此处将数据库中读取到的数据全部输入进players列表
       /* Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {//按进球数排序
                return Integer.valueOf(o2.getGoal()).compareTo(Integer.valueOf(o1.getGoal()));
            }
        });*/

        List<String>player_name_goal=new ArrayList<String>();

        for (Player player: players){
            if(player.getGoal()>=0){
                player_name_goal.add(player.getName()+"("+player.getGoal()+")");//将排序后的射手名登记
            }

        }
        Log.e("player list",player_name_goal.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,player_name_goal );
        ListView listView=(ListView)findViewById(R.id.list_goal);
        listView.setAdapter(adapter);
        final Intent intent=new Intent(this,player_state.class);

        AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Do something in response to the click
                //打开球员个人数据activity
                intent.putExtra("player",players.get(position));
                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(messageClickedHandler);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    void back(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
