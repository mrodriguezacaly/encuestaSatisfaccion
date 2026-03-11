package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Catalogo_Preguntas;
import com.ideal.encuestacliente.model.Esatis_Frecuencia;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.sincronizacion.WebServiceResponse;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Esatis_PreguntasHelper implements IWebService
{

    private static final String TAG = "Esatis_Forma_Pago";
    @Override
    public void insertResponse(JSONObject response) {

        try{
            JSONArray formaPagoJSON = response.getJSONArray(Constantes.TABLA_CATALOGO_PREGUNTAS);
            for(int i = 0; i < formaPagoJSON.length(); i++) {
                insertJSON(formaPagoJSON.getJSONObject(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Catalogo_Preguntas e = new Esatis_Catalogo_Preguntas();

        e.setIdPreguntaCatalogo(json.getInt(Constantes.camposPreguntas.idPreguntaCatalogo));
        e.setDescPregunta(json.getString(Constantes.camposPreguntas.descPregunta));
        e.setDescObservaciones(json.getString(Constantes.camposPreguntas.descObservaciones));
        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Catalogo_Preguntas esatis_preguntas) {
        ContentValues values = new ContentValues();
        values.put(Constantes.camposPreguntas.idPreguntaCatalogo, esatis_preguntas.getIdPreguntaCatalogo());
        values.put(Constantes.camposPreguntas.descPregunta, esatis_preguntas.getDescPregunta());
        values.put(Constantes.camposPreguntas.descObservaciones, esatis_preguntas.getDescObservaciones());
        values.put(Constantes.usuarioCreacion                ,esatis_preguntas.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,esatis_preguntas.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,esatis_preguntas.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,esatis_preguntas.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,esatis_preguntas.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,esatis_preguntas.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaPreguntas(esatis_preguntas);

    }
}
