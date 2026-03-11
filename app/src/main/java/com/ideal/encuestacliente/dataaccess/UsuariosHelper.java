package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsuariosHelper implements IWebService {

    private static final String TAG = UsuariosHelper.class.getSimpleName();

    @Override
    public void insertResponse(JSONObject response) {
        try {
            JSONArray json = response.getJSONArray(Constantes.TABLA_USUARIOS);
            for (int i = 0; i < json.length(); i++) {
                insertJSON(json.getJSONObject(i));
                Log.d(TAG, "data: " + i);
            }
            Log.d(TAG, "generated from webservices");

        } catch (Exception e) {
            Log.e(TAG, "insertResponseError: " + e.getMessage());
        }
    }

    public static void insertJSON(JSONObject json) throws JSONException {
        Usuario user = new Usuario();
        user.setIdUsuario(json.getInt(Constantes.camposUsuarios.idUsuario));
        user.setNombreUsuario(json.getString(Constantes.camposUsuarios.nombreUsuario));
        user.setNombre(json.getString(Constantes.camposUsuarios.nombre));
        user.setIdPuesto(json.getInt(Constantes.camposUsuarios.idPuesto));
        user.setPuesto(json.getString(Constantes.camposUsuarios.puesto));
        insert(user);
    }

    public static void insert(Usuario user) {
        ContentValues values = new ContentValues();
        values.put(Constantes.camposUsuarios.idUsuario, user.getIdUsuario());
        values.put(Constantes.camposUsuarios.nombreUsuario, user.getNombreUsuario());
        values.put(Constantes.camposUsuarios.nombre, user.getNombre());
        values.put(Constantes.camposUsuarios.idPuesto, user.getIdPuesto());
        values.put(Constantes.camposUsuarios.puesto, user.getPuesto());

        BaseDaoEncuesta.getInstance().insertUser(user);
    }
}
