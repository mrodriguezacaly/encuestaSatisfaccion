package com.ideal.encuestacliente.sincronizacion;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ideal.encuestacliente.sharepoint.VolleyCallBackSharePoint;

import org.json.JSONArray;
import org.json.JSONObject;

public class GenericRequest
{
    public static final int NUMERO_INTENTOS_REQUEST = 3;
    private static final String TAG = GenericRequest.class.getSimpleName();

    public static void requestGet(String url, final VolleyCallback callBack){

        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSucces(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onFail(error);

                    }
                });
        request.setRetryPolicy(customRetryPolicy());
        AplicationController.getInstance().addToRequestQueue(request);
    }

    public static void requestJson(final String URL,final VolleyCallback callback)
    {
        JsonObjectRequest request = new JsonObjectRequest( URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Request ejecutado correctamente : " + URL);
                callback.onSucces(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Fallo request : " + URL);
                callback.onFail(error);
            }
        });

        request.setRetryPolicy(customRetryPolicy());
        AplicationController.getInstance().addToRequestQueue(request);
    }

    public static void postJson(final String URL, final JSONArray parametros, final VolleyCallBackSharePoint volleyCallback)
    {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, parametros, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                   Log.d(TAG,"Request ejecutado correctamente");
                   volleyCallback.onSucces(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error request ");
                volleyCallback.onFail(error);
            }
        });

        request.setRetryPolicy(customRetryPolicy());
        AplicationController.getInstance().addToRequestQueue(request);
    }
    public static void postJsonData(final String URL, final JSONObject parametros, final VolleyCallback volleyCallback)
    {
        Log.e("json_prueba",parametros.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST , URL, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "Request ejecutado correctamente : " + URL);
                volleyCallback.onSucces(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Fallo request : " + URL);
                volleyCallback.onFail(error);
            }
        });

        request.setRetryPolicy(customRetryPolicy());
        AplicationController.getInstance().addToRequestQueue(request);

    }


    private static RetryPolicy customRetryPolicy(){
        return new DefaultRetryPolicy(
             DefaultRetryPolicy.DEFAULT_TIMEOUT_MS  *  NUMERO_INTENTOS_REQUEST,
             DefaultRetryPolicy.DEFAULT_MAX_RETRIES *  NUMERO_INTENTOS_REQUEST,
                           DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
    }

    public static void requestPost(final String url, final JSONObject jsonObject, final VolleyCallback callBack){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST ,url ,jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,url.concat(" Request correcto"));
                        callBack.onSucces(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,url.concat(" fallo request"));
                callBack.onFail(error);
            }
        });
        request.setRetryPolicy(customRetryPolicy());
        AplicationController.getInstance().addToRequestQueue(request);
    }


}
