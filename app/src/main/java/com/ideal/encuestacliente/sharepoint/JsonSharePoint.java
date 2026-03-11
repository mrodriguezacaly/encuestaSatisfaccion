package com.ideal.encuestacliente.sharepoint;

import android.util.Log;

import com.ideal.encuestacliente.model.Rsin_SharePointUsuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonSharePoint
{
    private static final String TAG =  "JSON SHAREPOINT";
    public static JSONArray Usuario(String usuario,String password) {

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put("usuario", usuario);
            jsonObject.put("contraseña", password);
            jsonArray.put(jsonObject);

            return jsonArray;
        }catch (JSONException e){
            Log.d(TAG,e.getMessage().toString());
            return  null;
        }
    }
    public static boolean obtenerEstatus(Rsin_SharePointUsuarios usuario){
        return  usuario.isEstatusConexion();
    }

}
