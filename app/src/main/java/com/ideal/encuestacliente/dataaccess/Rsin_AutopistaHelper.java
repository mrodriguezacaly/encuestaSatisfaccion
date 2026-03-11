package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.sincronizacion.WebServiceResponse;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Rsin_AutopistaHelper implements WebServiceResponse
{

    private static final String TAG = "Rsin_Autopistas";
    @Override
    public void insertResponse(JSONObject response) {

        try{
            JSONArray autopistasJSON = response.getJSONArray(Constantes.AUTOPISTAS);
            for(int i = 0; i < autopistasJSON.length(); i++) {
                insertJSON(autopistasJSON.getJSONObject(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Autopistas e = new Esatis_Autopistas();

        e.setIdAutopistaSQL(json.getInt(Constantes.campos.idAutopista));
        e.setNombreAutopista(json.getString(Constantes.campos.nombreAutopista));
        e.setAcronimoAutopista(json.getString(Constantes.campos.acronimoAutopista));
        e.setIdOrden(json.getInt(Constantes.campos.idOrden));

        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Autopistas esatis_autopistas) {
        ContentValues values = new ContentValues();

        values.put(Constantes.campos.idAutopistaSQL,esatis_autopistas.getIdAutopistaSQL());
        values.put(Constantes.campos.nombreAutopista,esatis_autopistas.getNombreAutopista());
        values.put(Constantes.campos.acronimoAutopista,esatis_autopistas.getAcronimoAutopista());
        values.put(Constantes.campos.idOrden,esatis_autopistas.getIdOrden());

        values.put(Constantes.usuarioCreacion                ,esatis_autopistas.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,esatis_autopistas.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,esatis_autopistas.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,esatis_autopistas.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,esatis_autopistas.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,esatis_autopistas.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaAutopistas(esatis_autopistas);

    }
}
