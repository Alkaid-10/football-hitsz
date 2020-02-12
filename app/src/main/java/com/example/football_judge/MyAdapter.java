package com.example.football_judge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyTVHolder> {

    private final String[] mArray;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public MyAdapter(Context context,Player player) {
        String data[]=context.getResources().getStringArray(R.array.testArray);
        //对data进行球员各属性的赋值
        data[0]+=player.getName();
        data[1]+=player.getTeam();
        data[2]+=player.getNum();
        data[3]+=player.getGoal();
        data[4]+=player.getAssist();
        data[5]+=player.getMark();
        mArray = data;//此处是数据源
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MyAdapter.MyTVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyTVHolder(mLayoutInflater.inflate(R.layout.mycardview, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyTVHolder holder, int position) {
        holder.mTextView.setText(mArray[position]);
    }

    @Override
    public int getItemCount() {
        return mArray == null ? 0 : mArray.length;
    }

    public class MyTVHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyTVHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_test);
        }
    }
}