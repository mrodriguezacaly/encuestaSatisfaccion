package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Plaza_Cobro;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlazasCobroHelper implements IWebService {
    private static final String TAG = "PlazasCobro";
    @Override
    public void insertResponse(JSONObject response) {
        try{
            JSONArray json = response.getJSONArray(Constantes.WS_PLAZA_COBRO);
            for(int i = 0; i < json.length(); i++) {
                insertJSON(json.getJSONObject(i));
                Log.i("data",String.valueOf(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Plaza_Cobro e = new Esatis_Plaza_Cobro();
        e.setIdPlazaCobroSQL(json.getInt(Constantes.camposPlazaCobro.idPlazaCobro));

        e.setIdAutopista(json.getInt(Constantes.camposPlazaCobro.idAutopista));
        e.setPlazaCobro(json.getString(Constantes.camposPlazaCobro.plazaCobro));
        e.setRazonSocialAutopista(json.getString(Constantes.camposPlazaCobro.razonSocialAutopista));
        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Plaza_Cobro plazasCobro) {
        ContentValues values = new ContentValues();

        values.put(Constantes.camposPlazaCobro.idPlazaCobroSQL, plazasCobro.getIdPlazaCobroSQL());
        values.put(Constantes.camposPlazaCobro.idAutopista, plazasCobro.getIdAutopista());
        values.put(Constantes.camposPlazaCobro.plazaCobro, plazasCobro.getPlazaCobro());
        values.put(Constantes.camposPlazaCobro.razonSocialAutopista, plazasCobro.getRazonSocialAutopista());

        values.put(Constantes.usuarioCreacion                ,plazasCobro.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,plazasCobro.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,plazasCobro.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,plazasCobro.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,plazasCobro.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,plazasCobro.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaPlazaCobro(plazasCobro);
    }
}
