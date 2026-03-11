package com.ideal.encuestacliente.tablas;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;


public class UsuariosDb {
    private static final String TAG = UsuariosDb.class.getSimpleName();

    public static boolean exists(){
        BaseDaoEncuesta dataBase = BaseDaoEncuesta.getInstance();
        try{
            Cursor cursor = dataBase.getWritableDatabase().
                                rawQuery("SELECT * FROM " +Tabla_usuario.TABLA,null);

            int numeroRegistros =  cursor.getCount();
            boolean existeUsuario =  (numeroRegistros > 0) ? true:false;
            return  existeUsuario;
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
            return  false;
        }finally {
            dataBase.close();
        }
    }
    public static void save(String usuario,String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tabla_usuario.campos.nombreUsuario,usuario);
        contentValues.put(Tabla_usuario.campos.password,password);
        BaseDaoEncuesta.getInstance().getWritableDatabase().insert(Tabla_usuario.TABLA,null,contentValues);
    }

    public static void update(String usuario,String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tabla_usuario.campos.nombreUsuario,usuario);
        contentValues.put(Tabla_usuario.campos.password,password);
        BaseDaoEncuesta.getInstance().getWritableDatabase().update(Tabla_usuario.TABLA,
                contentValues,
                Tabla_usuario.campos.idUsuario.concat("=?"),
                new String[]{String.valueOf(1)});
    }

    public static String getUser(){
        String usuario = "";
        BaseDaoEncuesta dataBase = BaseDaoEncuesta.getInstance();
        try{
            Cursor cursor = dataBase.getWritableDatabase().
                    rawQuery("SELECT * " +
                                " FROM "     + Tabla_usuario.TABLA +
                                " WHERE "    + Tabla_usuario.campos.idUsuario.concat("=?") ,
                            new String[]{String.valueOf(1)});
            if(cursor.moveToFirst())
                 usuario = cursor.getString(1);

            return usuario;
        }catch (Exception e){
            Log.e(TAG, "getUserError: " + e.getMessage());
            return  usuario;
        }finally {
            dataBase.close();
        }
    }


}
