package com.ideal.encuestacliente.dataaccess;

import android.content.ContentValues;
import android.util.Log;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Autopista_Pregunta;
import com.ideal.encuestacliente.model.Esatis_Catalogo_Preguntas;
import com.ideal.encuestacliente.sincronizacion.IWebService;
import com.ideal.encuestacliente.sincronizacion.WebServiceResponse;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Esatis_AutPreguntasHelper implements IWebService
{

    private static final String TAG = "Esatis_Forma_Pago";
    @Override
    public void insertResponse(JSONObject response) {

        try{
            JSONArray formaPagoJSON = response.getJSONArray(Constantes.TABLA_CATALOGO_AUTOPISTA_PREGUNTA);
            for(int i = 0; i < formaPagoJSON.length(); i++) {
                insertJSON(formaPagoJSON.getJSONObject(i));
            }
            Log.d(TAG, "generated from webservices");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void insertJSON(JSONObject json) throws JSONException {
        Esatis_Autopista_Pregunta e = new Esatis_Autopista_Pregunta();

        e.setIdAutopista(json.getInt(Constantes.camposAutopistasPreguntas.idAutopista));
        e.setIdPreguntaCatalogo(json.getInt(Constantes.camposAutopistasPreguntas.idPreguntaCatalogo));
        e.setUsuarioCreacion(json.getString(Constantes.usuarioCreacion));
        e.setUsuarioModificacion(json.getString(Constantes.usuarioModificacion));
        e.setUsuarioEliminacion(json.getString(Constantes.usuarioEliminacion));
        e.setFechaCreacion(json.getString(Constantes.fechaCreacion));
        e.setFechaModificacion(json.getString(Constantes.fechaModificacion));
        e.setFechaEliminacion(json.getString(Constantes.fechaEliminacion));
        insert(e);
    }
    public static void insert(Esatis_Autopista_Pregunta esatis_aut_preguntas) {
        ContentValues values = new ContentValues();
        values.put(Constantes.camposAutopistasPreguntas.idAutopista, esatis_aut_preguntas.getIdAutopista());
        values.put(Constantes.camposAutopistasPreguntas.idPreguntaCatalogo, esatis_aut_preguntas.getIdPreguntaCatalogo());
        values.put(Constantes.usuarioCreacion                ,esatis_aut_preguntas.getUsuarioCreacion());
        values.put(Constantes.usuarioModificacion            ,esatis_aut_preguntas.getUsuarioModificacion());
        values.put(Constantes.usuarioEliminacion             ,esatis_aut_preguntas.getUsuarioEliminacion());
        values.put(Constantes.fechaCreacion                  ,esatis_aut_preguntas.getFechaCreacion());
        values.put(Constantes.fechaModificacion              ,esatis_aut_preguntas.getFechaModificacion());
        values.put(Constantes.fechaEliminacion               ,esatis_aut_preguntas.getFechaEliminacion());

        BaseDaoEncuesta.getInstance().insertaAutopistaPreguntas(esatis_aut_preguntas);

    }
}
