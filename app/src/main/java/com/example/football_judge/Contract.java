package com.example.football_judge;

import android.provider.BaseColumns;

public final class Contract  {//定义数据库结构
    private Contract(){};
    public static class FeedEntry implements BaseColumns {
        public  static final String TABLE_NAME="entry";
        public static final String NAME = "name";
        public static final String TEAM = "team";
        public static final String NUM = "num";
        public static final String GOAL="goal";
        public static final String ASSIST="assist";
        public static final String MARK="mark";
    }
}
