package com.ideal.encuestacliente.synctables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.generadorJson.JsonEncuestaClientes;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Encuesta;
import com.ideal.encuestacliente.sincronizacion.GenericRequest;
import com.ideal.encuestacliente.sincronizacion.UrlSincronizacion;
import com.ideal.encuestacliente.sincronizacion.VolleyCallback;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;
import com.ideal.encuestacliente.vistas.ListaEncuestasSincronizarActivity;
import com.ideal.encuestacliente.vistas.SyncUpActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.ideal.encuestacliente.vistas.SyncUpActivity.contexto;

public class Esatis_EncuestaClienteSync
{
    private int succes;
    private int idVehiculoInvolucradoSQLite;
    private static final String TAG = "Rsin_VehiculosInvo";
    private int numeroSubidasCorrectas;
    private int numeroSubidasIncorrectas;
    private boolean falloSolicitud;

    public void enviarTablaVehiculosInvolucrados(final Context context,
                                                 final LinearLayout[] linearLayout,
                                                 final TextView[] textView,
                                                 final GifImageView[] gifImageView,
                                                 final int idEncuestaSQLite,
                                                 final int idReporteSiniestroSQL,
                                                 final int idAutopista,
                                                 final String nombreAutopista,
                                                 final String acronimoAutopista)
    {

        BaseDaoEncuesta con = new BaseDaoEncuesta(context);
        List datosEncuesta = con.consultaPreguntasClientePorIdEncuesta(idEncuestaSQLite);
        succes = 0;
        numeroSubidasCorrectas = 0;
        //String validaSync = "";

        Log.e("Encuesta_SQL_Preg", idReporteSiniestroSQL+"");

        for(Object datos: datosEncuesta) {
            Esatis_Encuesta info = (Esatis_Encuesta) datos;

                JSONObject jsonObject = JsonEncuestaClientes.generarJson(info, idReporteSiniestroSQL);
                Log.e("jsonObject_encuesta", jsonObject.toString());
                GenericRequest.postJsonData(UrlSincronizacion.URL_SINCRONIZACION_ESATIS_ENCUESTA, jsonObject, new VolleyCallback() {
                    @Override
                    public void onSucces(JSONObject response) {
                        try{
                            succes = response.getInt("idPregunta");
                            Log.e("idPreguntaSQL", succes+"");

                            if(succes==0){
                                SyncUpActivity.regresaListaEncuestasSync();
                                mensajesToasSincronizacion("Ha ocurrido un error, por favor intentalo nuevamente.", context);

                            }else{
                                numeroSubidasCorrectas = numeroSubidasCorrectas + 1;
                                    /*new Esatis_EncuestaClienteSync().
                                            enviarTablaVehiculosInvolucrados
                                                    (context,linearLayout,textView,gifImageView,idEncuestaSQLite,
                                                            idReporteSiniestroSQL, idAutopista,
                                                            nombreAutopista, acronimoAutopista);*/
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if (numeroSubidasCorrectas == 11) {
                            if (!falloSolicitud) {
                                Log.d(TAG, "Se enviarón todas las peticiones");
                                //Utils.sincronizaciónCorrecta(context , linearLayout[0],textView[0],gifImageView[0]);
                                Utils.sincronizaciónCorrecta(context , linearLayout[1],textView[1],gifImageView[1]);
                                SyncUpActivity.actualizaFinalizacion();
                                //SyncUpActivity.regresaListaEncuestasSync();
                                mensajesToasSincronizacion("Ha finalizado la sincronización.", context);
                            }
                        } else {
                            Log.d(TAG, "No se han enviado todas las preguntas");
                        }
                    }
                    @Override
                    public void onFail(VolleyError error) {
                        Log.e("error", error.toString());
                        falloSolicitud = true;
                        if (numeroSubidasIncorrectas == 0) {
                            Log.d(TAG, "No se han enviado todas las solicitudes");
                        }
                        SyncUpActivity.regresaListaEncuestasSync();
                        mensajesToasSincronizacion("Ha ocurrido un error, por favor intentalo nuevamente.", context);

                    }
                });

            if (numeroSubidasCorrectas == 11) {
                Log.e("numeroSubidasCorrectas", numeroSubidasCorrectas+"");
                    break;
            } else {
                Log.d(TAG, "No se han enviado todas las preguntas");
            }

        }

    }

    public void mensajesToasSincronizacion(String mesnsaje, Context con){
        Toast.makeText(con, mesnsaje,
                Toast.LENGTH_LONG).show();
    }

}
