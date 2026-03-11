package com.ideal.encuestacliente.generadorJson;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.model.Esatis_Cliente;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonDatosClientes
{
    public static JSONObject generarJson(Esatis_Cliente info, String nombreUsuario)
    {
        JSONObject object = new JSONObject();
        try{
            object.put(Constantes.camposDatosCliente.idAutopista, info.getIdAutopista());
            object.put(Constantes.camposDatosCliente.idPlazaCobro, info.getIdPlazaCobro());
           // String fechaInvertida = Utils.invertirFecha(info.getFecha());
            object.put(Constantes.camposDatosCliente.idUbicacion, info.getIdUbicacion());
            object.put(Constantes.camposDatosCliente.UbicacionDesc, info.getUbicacionDesc());

            object.put(Constantes.camposDatosCliente.fecha, info.getFecha());
            object.put(Constantes.camposDatosCliente.encuestador, info.getEncuestador());
            object.put(Constantes.camposDatosCliente.origen, info.getOrigen());
            object.put(Constantes.camposDatosCliente.destino, info.getDestino());
            object.put(Constantes.camposDatosCliente.idFormaPago, info.getIdFormaPago());
            object.put(Constantes.camposDatosCliente.idFrecuenciaUso, info.getIdFrecuenciaUso());
            object.put(Constantes.camposDatosCliente.observacionesCliente, info.getObservaciones());

            object.put(Constantes.camposDatosCliente.latitud, info.getLatitud());
            object.put(Constantes.camposDatosCliente.longitud, info.getLongitud());
            object.put(Constantes.camposDatosCliente.altitud, info.getAltitud());

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
