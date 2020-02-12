package com.example.football_judge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class player_state extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_state);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_player);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        Player player=(Player)getIntent().getSerializableExtra("player");
        mAdapter = new MyAdapter(this,player);
        recyclerView.setAdapter(mAdapter);
    }
    void delete_player(View view){//删除球员数据
        //创建数据库对象
        dbHelper dbHelper=new dbHelper(this);
        //获取球员对象
        Player player=(Player)getIntent().getSerializableExtra("player");
        //打开可写数据库
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = Contract.FeedEntry.NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { player.getName() };
        // Issue SQL statement.
        int deletedRows = db.delete(Contract.FeedEntry.TABLE_NAME, selection, selectionArgs);
        Log.e("delete ok: ",player.getName());
        //关闭可写数据库
        db.close();
        //返回主界面
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
