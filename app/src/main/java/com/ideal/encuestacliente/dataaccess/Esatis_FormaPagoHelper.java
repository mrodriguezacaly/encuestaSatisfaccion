package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.sincronizacion.WebServiceResponse;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Esatis_FormaPagoHelper implements IWebService
{

    private static final String TAG = "Esatis_Forma_Pago";
    @Override
    public void insertResponse(JSONObject response) {

        try{
            JSONArray formaPagoJSON = response.getJSONArray(Constantes.TABLA_FORMA_PAGO);
            for(int i = 0; i < formaPagoJSON.length(); i++) {
                insertJSON(formaPagoJSON.getJSONObject(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Forma_Pago e = new Esatis_Forma_Pago();

        e.setFormaPago(json.getString(Constantes.camposFormaPago.formaPago));
        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Forma_Pago esatis_formaPago) {
        ContentValues values = new ContentValues();
        values.put(Constantes.camposFormaPago.formaPago, esatis_formaPago.getFormaPago());
        values.put(Constantes.usuarioCreacion                ,esatis_formaPago.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,esatis_formaPago.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,esatis_formaPago.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,esatis_formaPago.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,esatis_formaPago.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,esatis_formaPago.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaFormaPago(esatis_formaPago);

    }
}
