package com.example.football_judge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String name;//球员名
    private String team;//球队名
    private int num;//球衣号
    private int goal;//进球数
    private int assist;//助攻数
    private double mark;//评分
   // public static List <Player> players=new ArrayList<Player>();//球员离线数据库()
    Player(String name,String team,int num){//构造函数用于登记新加入的球员
    this.name=name;
    this.team=team;
    this.num=num;
    goal=0;
    assist=0;
    mark=6.0;
    }


    void add_goal(int num){//记录进球（记得刷新数据库）
        this.goal+=num;
    }
    void set_Goal(int goal){this.goal=goal;}
    void set_Assist(int assist){this.assist=assist;}
    void add_assist(int num){//记录助攻（记得刷新数据库）
        this.assist+=num;
    }
    void set_mark(double mark,int wieght){//根据权重，本场评分计算综合评分
       this.mark=mark;//公式待定
    }
    String getName(){
        return this.name;
    }
    String getTeam(){
        return this.team;
    }
    int getNum(){
        return this.num;
    }
    int getGoal(){
       return this.goal;
    }
    int getAssist(){
        return this.assist;
    }
    double getMark(){
        return this.mark;
    }
}
