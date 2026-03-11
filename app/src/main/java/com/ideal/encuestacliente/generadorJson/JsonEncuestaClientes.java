package com.ideal.encuestacliente.generadorJson;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.model.Esatis_Encuesta;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonEncuestaClientes
{
    public static JSONObject generarJson(Esatis_Encuesta info, int idEncuestaSQL)
    {
        JSONObject object = new JSONObject();

        try{
            object.put(Constantes.camposEncuestaClientes.idPreguntaCatalogo, info.getIdPreguntaCatalogo());
            object.put(Constantes.camposEncuestaClientes.idEncuesta, idEncuestaSQL);
            object.put(Constantes.camposEncuestaClientes.idValoracion, info.getIdValoracion());
            //object.put(Constantes.camposEncuestaClientes.observaciones, info.getObservaciones());
            object.put(Constantes.usuarioCreacion, info.getUsuarioCreacion());
            object.put(Constantes.usuarioModificacion, info.getUsuarioModificacion());
            object.put(Constantes.usuarioEliminacion, info.getUsuarioEliminacion());
            object.put(Constantes.fechaCreacion, info.getFechaCreacion());
            object.put(Constantes.fechaModificacion, "");
            object.put(Constantes.fechaEliminacion, info.getFechaEliminacion());
            object.put(Constantes.camposEncuestaClientes.clave, info.getFechaModificacion());
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return object;
    }
}
