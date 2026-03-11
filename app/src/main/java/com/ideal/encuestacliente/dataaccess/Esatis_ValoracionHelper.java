package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Catalogo_Preguntas;
import com.ideal.encuestacliente.model.Esatis_Valoracion;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.sincronizacion.WebServiceResponse;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Esatis_ValoracionHelper implements IWebService
{

    private static final String TAG = "Esatis_Forma_Pago";
    @Override
    public void insertResponse(JSONObject response) {

        try{
            JSONArray formaPagoJSON = response.getJSONArray(Constantes.TABLA_CATALOGO_VALORACION);
            for(int i = 0; i < formaPagoJSON.length(); i++) {
                insertJSON(formaPagoJSON.getJSONObject(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Valoracion e = new Esatis_Valoracion();

        e.setDescValoraciones(json.getString(Constantes.camposValoracion.descValoraciones));
        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Valoracion esatis_valoracion) {
        ContentValues values = new ContentValues();
        values.put(Constantes.camposValoracion.descValoraciones, esatis_valoracion.getDescValoraciones());
        values.put(Constantes.usuarioCreacion                ,esatis_valoracion.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,esatis_valoracion.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,esatis_valoracion.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,esatis_valoracion.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,esatis_valoracion.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,esatis_valoracion.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaValoracion(esatis_valoracion);

    }
}
