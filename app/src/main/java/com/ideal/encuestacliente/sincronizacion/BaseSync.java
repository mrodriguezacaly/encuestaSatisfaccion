package com.ideal.encuestacliente.sincronizacion;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.jsonHelper.JSONParser;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;
import com.ideal.encuestacliente.tablas.UsuariosDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class BaseSync
{

    private static final String TAG = BaseSync.class.getSimpleName();
    private final int NUMERO_MAX_INTENTOS_SYNC =  3;
    private WebServiceResponse webServiceResponse;
    private WebService webService;
    private String tabla;
    private final int NUMERO_MAX_INTENTOS_SINCRONIZAR = 3;
    private String nombreTablaSQLite;
    private IWebService IwebService;


    public BaseSync(WebServiceResponse webServiceResponse, String tabla)
    {
        this.webServiceResponse = webServiceResponse;
        this.tabla = tabla;
    }
    public BaseSync(IWebService webService, String tabla, String servicio)
    {
        this.IwebService = webService;
        this.tabla = tabla;
    }




    public void SyncDown(final TextView textView,final int intentosSync){

        String URL = UrlSincronizacion.URL_BASE_WS_DOWN + tabla;

        if(intentosSync > NUMERO_MAX_INTENTOS_SYNC){
            Utils.syncFailMessage(textView);
        }

            GenericRequest.requestJson(URL, new VolleyCallback() {
                @Override
                public void onSucces(JSONObject response) {

                    BaseDaoEncuesta.getInstance().truncateTable(tabla);
                    BaseDaoEncuesta.getInstance().resetAutoincrement(tabla);
                    webServiceResponse.insertResponse(response);
                    Utils.syncSuccessMessage(textView);
                }

                @Override
                public void onFail(VolleyError error) {
                    error.printStackTrace();
                    SyncDown(textView, intentosSync + 1);
                }
            });
    }
    public  void SyncDownAutopistas(final int idUsuario, final TextView textView, final int intentosSync){

        String URLtest = UrlSincronizacion.URL_BASE_WS_DOWN_AUTOPISTAS + tabla;
        String urlAutopistas = UrlSincronizacion.URL_BASE_WS_DOWN_AUTOPISTAS_ENCUESTA.concat("?tabla=").concat("Autopistas").concat("&Usuario=").concat(UsuariosDb.getUser());

        String URL = (tabla.equals(Constantes.TABLA_AUTOPISTAS)) ?
                UrlSincronizacion.URL_BASE_WS_DOWN_AUTOPISTAS_ENCUESTA.concat("?tabla=").concat("Autopistas").concat("&Usuario=").concat(UsuariosDb.getUser()) : Urls.URL_BASE_WS_DOWN + tabla;

        Log.e("URLautopistas", URL);

        if(intentosSync > NUMERO_MAX_INTENTOS_SYNC){
            Utils.syncFailMessage(textView);
        }

       /* JSONObject json = new JSONObject();
        try {
            json.put("idUsuario", idUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        GenericRequest.requestGet(URL, new VolleyCallback() {
            @Override
            public void onSucces(JSONObject response) {
                Log.d("Response", response.toString());
                BaseDaoEncuesta.getInstance().truncateTable(tabla);
                BaseDaoEncuesta.getInstance().resetAutoincrement(tabla);
                webServiceResponse.insertResponse(response);
                Utils.syncSuccessMessage(textView);
            }

            @Override
            public void onFail(VolleyError error) {
                error.printStackTrace();
                SyncDownAutopistas(idUsuario,textView,intentosSync + 1);
            }
        });

    }

    public  void SyncDownCatalogos(final int idUsuario, final TextView textView, final int intentosSync, final  int tipoSincronizacion){

        String URL = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + tabla;

        if(intentosSync > NUMERO_MAX_INTENTOS_SYNC){
            Utils.syncFailMessage(textView);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("idUsuario", idUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONParser jsonParser = new JSONParser();
        HashMap<String, String> params = new HashMap<>();
        params.put("idUsuario", idUsuario+"");

        JSONObject jsonPrueba = null;

        try {
            jsonPrueba = jsonParser.makeHttpRequest(URL,
                    "POST", params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        Log.e("Create Response", jsonPrueba.toString());

        GenericRequest.postJsonData(URL,json, new VolleyCallback() {
            @Override
            public void onSucces(JSONObject response) {
                BaseDaoEncuesta.getInstance().truncateTable(tabla);
                BaseDaoEncuesta.getInstance().resetAutoincrement(tabla);
                webServiceResponse.insertResponse(response);
                Utils.syncSuccessMessage(textView);
            }

            @Override
            public void onFail(VolleyError error) {
                error.printStackTrace();
                SyncDownCatalogos(idUsuario,textView,intentosSync + 1, 0);
            }
        });

    }

    public void SyncDownVersiones(final TextView textView, final int intentosSync, final String nombreUsuario){

        String URL = UrlSincronizacion.URL_BASE_WS_DOWN + tabla;

        if(intentosSync > NUMERO_MAX_INTENTOS_SYNC){
            Utils.syncFailMessage(textView);
        }

        GenericRequest.requestJson(URL, new VolleyCallback() {
            @Override
            public void onSucces(JSONObject response) {

                BaseDaoEncuesta.getInstance().truncateTable(tabla);
                BaseDaoEncuesta.getInstance().resetAutoincrement(tabla);
                webService.insertResponse(response,nombreUsuario);
                Utils.syncSuccessMessage(textView);
            }

            @Override
            public void onFail(VolleyError error) {
                error.printStackTrace();
                SyncDown(textView, intentosSync + 1);
            }
        });
    }


    public void Sync(final TextView textView){

        SyncDown(textView,0);
    }

    public void Sync(final  TextView textView, int idUsuario){
        SyncDownAutopistas(idUsuario,textView,0);
    }

    public void Sync(final TextView textView,final String nombreUsuario){
        SyncDownVersiones(textView,0,nombreUsuario);
    }

    public void SyncCatalogos(final  TextView textView, int idUsuario, int tipoSincronizacion){
        SyncDownCatalogos(idUsuario,textView,0, tipoSincronizacion);
    }

    public  void syncDown(final int intenosSincronizacion, String nombreTabla)
    {
        String url = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + nombreTabla;
        nombreTablaSQLite = nombreTabla;
        if(intenosSincronizacion > NUMERO_MAX_INTENTOS_SINCRONIZAR){
            Log.e(TAG, "Descarga: Error en la descarga");
        }

        if (nombreTabla.equals(Constantes.TABLA_USUARIOS)) {
            url = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS
                    .concat("?tabla=")
                    .concat(Constantes.TABLA_USUARIOS)
                    .concat("&Usuario=")
                    .concat(UsuariosDb.getUser());
            Log.d(TAG, "URL Catálogo Usuarios: " + url);
        }

        GenericRequest.requestJson(url, new VolleyCallback() {
            @Override
            public void onSucces(JSONObject jsonObject)
            {
                Log.d("Response", jsonObject.toString());
                BaseDaoEncuesta.getInstance().truncateTable(nombreTablaSQLite);
                BaseDaoEncuesta.getInstance().resetAutoincrement(nombreTablaSQLite);
                IwebService.insertResponse(jsonObject);
            }
            @Override
            public void onFail(VolleyError error) {
                error.printStackTrace();
                syncDown(intenosSincronizacion + 1, nombreTablaSQLite);
            }
        });
    }
    public void sync(String nombreTabla){
        syncDown(0, nombreTabla);
    }


    public  void syncDownPlaza(final int intenosSincronizacion, String nombreTabla, String nomTablaLocal)
    {
        String url = UrlSincronizacion.URL_BASE_WS_DOWN_PLAZA_COBRO + nombreTabla;
        Log.e("urlPlazaCobro", url);
        nombreTablaSQLite = nomTablaLocal;
        if(intenosSincronizacion > NUMERO_MAX_INTENTOS_SINCRONIZAR){
            Log.d("Descarga","Error en la descarga");
        }

        GenericRequest.requestJson(url, new VolleyCallback() {
            @Override
            public void onSucces(JSONObject jsonObject)
            {
                Log.d("Response", jsonObject.toString());
                BaseDaoEncuesta.getInstance().truncateTable(nombreTablaSQLite);
                BaseDaoEncuesta.getInstance().resetAutoincrement(nombreTablaSQLite);
                IwebService.insertResponse(jsonObject);
            }
            @Override
            public void onFail(VolleyError error) {
                error.printStackTrace();
                syncDown(intenosSincronizacion + 1, nombreTablaSQLite);
            }
        });
    }
    public void syncPlaza(String nombreTablaSQL, String nombreTablaLocal){
        syncDownPlaza(0, nombreTablaSQL, nombreTablaLocal);
    }


}
