package com.ideal.encuestacliente.tablas;

import android.database.sqlite.SQLiteDatabase;

public class BaseTabla {
    private String tableName;
    private String createStatement;

    BaseTabla(String tableName, String createStatement){
        this.tableName = tableName;
        this.createStatement = createStatement;

    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        db.execSQL(createStatement);
    }
    public  void onUpgrade(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        db.execSQL(createStatement);

    }
}
