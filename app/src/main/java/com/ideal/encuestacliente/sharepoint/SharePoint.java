package com.ideal.encuestacliente.sharepoint;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.model.Rsin_SharePointUsuarios;
import com.ideal.encuestacliente.sincronizacion.GenericRequest;
import com.ideal.encuestacliente.sincronizacion.UrlSincronizacion;
import com.ideal.encuestacliente.synctables.AccesoUsuarios;

import org.json.JSONArray;

public class SharePoint
{
    private WebServiceResponseUser webServiceResponseUser;
    private Context context;


    public SharePoint(WebServiceResponseUser webServiceResponseUser, Context context){
        this.webServiceResponseUser = webServiceResponseUser;
        this.context = context;
    }


    private static final String TAG ="SharePoint";

    public  void accesoUsuario(final JSONArray jsonArray, final TextView textView){

        GenericRequest.postJson(UrlSincronizacion.URL_SHAREPOINT, jsonArray, new VolleyCallBackSharePoint() {
            @Override
            public void onSucces(JSONArray response)
            {
                Log.d(TAG,"Request enviado correctamente");

                Rsin_SharePointUsuarios usuario =
                        webServiceResponseUser.obtenerUsuario(response);

                if(usuario.isEstatusConexion()){

                    new AccesoUsuarios(usuario.getUsuario(),context).enviarUsuario(textView);

                }else{
                    Utils.mostrarMensaje(textView);
                }


            }
            @Override
            public void onFail(VolleyError error) {
                 Log.d(TAG,"Fallo request");
                error.printStackTrace();
            }
        });
    }




}
