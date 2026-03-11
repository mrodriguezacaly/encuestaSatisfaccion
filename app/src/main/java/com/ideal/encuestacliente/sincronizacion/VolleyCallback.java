package com.ideal.encuestacliente.sincronizacion;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyCallback<T>
{
    void onSucces(JSONObject response);
    void onFail(VolleyError error);
}
