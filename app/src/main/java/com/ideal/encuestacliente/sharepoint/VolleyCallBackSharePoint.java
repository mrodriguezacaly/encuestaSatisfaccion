package com.ideal.encuestacliente.sharepoint;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface VolleyCallBackSharePoint<T>
{
    void onSucces(JSONArray response);
    void onFail(VolleyError error);

}
