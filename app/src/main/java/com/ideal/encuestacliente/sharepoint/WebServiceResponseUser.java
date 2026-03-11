package com.ideal.encuestacliente.sharepoint;

import com.ideal.encuestacliente.model.Rsin_SharePointUsuarios;

import org.json.JSONArray;

public interface WebServiceResponseUser {

    Rsin_SharePointUsuarios obtenerUsuario(JSONArray response);
}
