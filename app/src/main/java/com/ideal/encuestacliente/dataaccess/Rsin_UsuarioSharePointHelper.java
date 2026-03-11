package com.ideal.encuestacliente.dataaccess;

import android.util.Log;

import com.ideal.encuestacliente.model.Rsin_SharePointUsuarios;
import com.ideal.encuestacliente.sharepoint.WebServiceResponseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Rsin_UsuarioSharePointHelper implements WebServiceResponseUser {

    public static final String TAG ="Rsin_UsuarioSharePoint";

    @Override
    public Rsin_SharePointUsuarios obtenerUsuario(JSONArray response) {
        try{
            Rsin_SharePointUsuarios usuario = new Rsin_SharePointUsuarios();
            JSONObject Json = response.getJSONObject(0);
            usuario.setUsuario(Json.getString("usuario"));
            usuario.setPassword(Json.getString("contraseña"));
            usuario.setEstatusConexion( Boolean.parseBoolean(Json.get("estatus").toString()));


            return  usuario;

        }catch (JSONException e)
        {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
