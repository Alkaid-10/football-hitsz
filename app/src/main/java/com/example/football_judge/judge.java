package com.example.football_judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class judge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge);
    }
    void back(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
