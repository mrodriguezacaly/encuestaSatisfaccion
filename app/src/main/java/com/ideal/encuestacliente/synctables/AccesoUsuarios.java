package com.ideal.encuestacliente.synctables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.sincronizacion.GenericRequest;
import com.ideal.encuestacliente.sincronizacion.UrlSincronizacion;
import com.ideal.encuestacliente.sincronizacion.VolleyCallback;
import com.ideal.encuestacliente.vistas.SyncDownActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AccesoUsuarios
{
    private Context context;
    private String nombreUsuario;
    private int idUsuario;
    public AccesoUsuarios(String nombreUsuario, Context context){
        this.nombreUsuario = nombreUsuario;
        this.context = context;
    }


    public void enviarUsuario(final TextView textView){
        JSONObject object = new JSONObject();
        try {
            object.put("nombreUsuario", nombreUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GenericRequest.postJsonData(UrlSincronizacion.URL_ACCESO_USUARIO, object, new VolleyCallback() {
            @Override
            public void onSucces(JSONObject response) {
                try {
                    idUsuario = response.getInt("idUsuario");

                    if(idUsuario != 0){
                         Utils.ocultarMensaje(textView);
                         open(context,idUsuario,nombreUsuario);
                        //MainActivity.usuario = nombreUsuario;
                    }else{
                        Utils.mostrarMensaje(textView);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(VolleyError error) {

            }
        });
    }

    public  void open(Context context,int idUsuario,String nombreUsuario){
        Bundle bundle = new Bundle();
        bundle.putInt("idUsuario",idUsuario);
        bundle.putString("nombreUsuario",nombreUsuario);

        Intent intent = new Intent(context.getApplicationContext(), SyncDownActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent.putExtras(bundle));
    }
}
