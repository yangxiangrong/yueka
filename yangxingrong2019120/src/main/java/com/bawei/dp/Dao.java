package com.bawei.dp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {
    private SQLiteDatabase database;
    public Dao(Context context){
        MyHlper myHlper = new MyHlper(context);
        database=myHlper.getWritableDatabase();
    }
    //插入
    public void  add(String url, String data){
        //先删除相同url地址的数据
        database.delete("stu","url=?",new String[]{url});
        ContentValues values = new ContentValues();
        values.put("url",url);
        values.put("jsonData", data);
        database.insert("stu",null,values);
    }
    //查询
    public String queryData(String url){
        String data="";
        Cursor cursor = database.query("stu", null, "url=?", new String[]{url}, null, null, null);
        while (cursor.moveToNext()){
            //获得jsonData字段的内容
            data= cursor.getString(cursor.getColumnIndex("jsonData"));
        }
        return  data;
    }
}
