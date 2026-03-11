package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Ubicaciones;
import com.ideal.encuestacliente.model.Esatis_Valoracion;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Esatis_UbicacionHelper implements IWebService
{

    private static final String TAG = "Esatis_Ubicaciones";
    @Override
    public void insertResponse(JSONObject response) {

        try{
            JSONArray formaPagoJSON = response.getJSONArray(Constantes.TABLA_CATALOGO_UBICACIONES);
            for(int i = 0; i < formaPagoJSON.length(); i++) {
                insertJSON(formaPagoJSON.getJSONObject(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Ubicaciones e = new Esatis_Ubicaciones();

        e.setIdUbicacion(json.getInt(Constantes.camposUbicacion.idUbicacion));
        e.setUbicacion(json.getString(Constantes.camposUbicacion.ubicacion));
        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Ubicaciones esatis_ubicacion) {
        ContentValues values = new ContentValues();
        values.put(Constantes.camposValoracion.descValoraciones, esatis_ubicacion.getUbicacion());
        values.put(Constantes.usuarioCreacion                ,esatis_ubicacion.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,esatis_ubicacion.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,esatis_ubicacion.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,esatis_ubicacion.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,esatis_ubicacion.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,esatis_ubicacion.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaUbicacion(esatis_ubicacion);

    }
}
