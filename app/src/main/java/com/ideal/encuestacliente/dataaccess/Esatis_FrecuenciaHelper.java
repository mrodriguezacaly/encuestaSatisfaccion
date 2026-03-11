package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.model.Esatis_Frecuencia;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.sincronizacion.WebServiceResponse;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Esatis_FrecuenciaHelper implements IWebService
{

    private static final String TAG = "Esatis_Forma_Pago";
    @Override
    public void insertResponse(JSONObject response) {

        try{
            JSONArray formaPagoJSON = response.getJSONArray(Constantes.TABLA_CATALOGO_FRECUENCIA);
            for(int i = 0; i < formaPagoJSON.length(); i++) {
                insertJSON(formaPagoJSON.getJSONObject(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Frecuencia e = new Esatis_Frecuencia();

        e.setDescFrecuencia(json.getString(Constantes.camposFrecuencia.descFrecuencia));
        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Frecuencia esatis_frecuencia) {
        ContentValues values = new ContentValues();
        values.put(Constantes.camposFrecuencia.descFrecuencia, esatis_frecuencia.getDescFrecuencia());
        values.put(Constantes.usuarioCreacion                ,esatis_frecuencia.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,esatis_frecuencia.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,esatis_frecuencia.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,esatis_frecuencia.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,esatis_frecuencia.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,esatis_frecuencia.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaFrecuencia(esatis_frecuencia);

    }
}
