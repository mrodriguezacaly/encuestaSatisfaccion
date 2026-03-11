package com.ideal.encuestacliente.sincronizacion;

import org.json.JSONObject;

public interface WebService {
    void insertResponse(JSONObject response, String nombreUsuario);
}
