package com.ideal.encuestacliente.synctables;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.generadorJson.JsonDatosClientes;
import com.ideal.encuestacliente.generadorJson.JsonEncuestaClientes;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Encuesta;
import com.ideal.encuestacliente.sincronizacion.GenericRequest;
import com.ideal.encuestacliente.sincronizacion.UrlSincronizacion;
import com.ideal.encuestacliente.sincronizacion.VolleyCallback;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;
import com.ideal.encuestacliente.vistas.ListaEncuestasSincronizarActivity;
import com.ideal.encuestacliente.vistas.MainActivity;
import com.ideal.encuestacliente.vistas.SyncUpActivity;


import org.json.JSONObject;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.ideal.encuestacliente.vistas.SyncUpActivity.contexto;

@SuppressWarnings("ALL")
public class Esatis_DatosClienteSync
{

        private int idReporteSiniestroSQL;
        private int succes;
        private static final String TAG = "Calificaciones Sync";
        private int numeroSubidasCorrectas;
        private int numeroSubidasIncorrectas;
        private boolean falloSolicitud = false;
        private boolean versionCorrecta = false;
        public static String nomVersionconsula = "";

        public void enviarTablaDatosEncuesta
                (final Context context,
                 final LinearLayout[] linearLayout,
                 final TextView[] textView,
                 final GifImageView[] gifImageView,
                 final int idEncuestaSQLite,
                 final int idAutopista,
                 final String nombreAutopista,
                 final String acronimoAutopista)
        {

            BaseDaoEncuesta con = new BaseDaoEncuesta(context);
            List datosEncuesta = con.consultaDatosClientePorIdEncuesta(idEncuestaSQLite);

            String mensajeRepSiniestros = "";
            succes = 0;
            String validaSync = "";

            numeroSubidasCorrectas = datosEncuesta.size();

            if(numeroSubidasCorrectas == 0){
            }

          //  String nombreUsuario = ControladorVersiones.obtenerNombreUsuario();
            String nombreUsuario = "";

            /*List datosPreguntas = con.consultaPreguntasClientePorIdEncuesta(idEncuestaSQLite);
            JSONObject jsonObjectEncuesta = null;
            for(Object datos: datosPreguntas) {
                Esatis_Encuesta info = (Esatis_Encuesta) datos;

                jsonObjectEncuesta = JsonEncuestaClientes.generarJson(info);

            }

            Log.e("jsonObjectEncuesta", jsonObjectEncuesta.toString());*/

           /* List datosPreguntas = con.consultaPreguntasClientePorIdEncuesta(idEncuestaSQLite);
            for(Object datos: datosPreguntas) {
                Esatis_Encuesta info = (Esatis_Encuesta) datos;

                JSONObject jsonObject = JsonEncuestaClientes.generarJson(info, idReporteSiniestroSQL);
                Log.e("jsonObject_preguntas", jsonObject.toString());
            }*/

            //for(Object datos: datosEncuesta){

            JSONObject jsonObject = null;
            for(Object datos: datosEncuesta) {
                Esatis_Cliente info = (Esatis_Cliente)datos;
                jsonObject = JsonDatosClientes.generarJson(info, nombreUsuario);
                Log.e("jsonObject_datos_clientes", jsonObject.toString());
                break;
            }

            if(jsonObject != null){
                GenericRequest.postJsonData(UrlSincronizacion.URL_SINCRONIZACION_ESATIS_DATOS_CLIENTE, jsonObject, new VolleyCallback() {
                    @Override
                    public void onSucces(JSONObject response)
                    {
                        try{
                            /*succes = response.getInt("succes");
                            if(succes==3) {
                                regresaListaReportesActivty(idEncuestaSQLite, idAutopista,
                                        nombreAutopista, acronimoAutopista, context);
                                mensajesToasSincronizacion("Error reporte sinestro" , context);
                            }*/

                            idReporteSiniestroSQL = response.getInt("idEncuesta");
                            succes = response.getInt("idEncuesta");
                            Log.e("idReporteSiniestroSQL", idReporteSiniestroSQL+"");

                            if(succes==0) {

                                SyncUpActivity.regresaListaEncuestasSync();
                                mensajesToasSincronizacion("Ha ocurrido un error, por favor intentalo nuevamente.", context);
                            }

                            Utils.sincronizaciónCorrecta(context , linearLayout[0],textView[0],gifImageView[0]);
                            new Esatis_EncuestaClienteSync().
                                    enviarTablaVehiculosInvolucrados
                                            (context,linearLayout,textView,gifImageView,idEncuestaSQLite,
                                                    idReporteSiniestroSQL, idAutopista,
                                                    nombreAutopista, acronimoAutopista);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        numeroSubidasCorrectas--;
                        if(numeroSubidasCorrectas == 0){
                            if(falloSolicitud == false){

                             /*   Utils.sincronizaciónCorrecta(context , linearLayout[0],textView[0],gifImageView[0]);
                                new Esatis_EncuestaClienteSync().
                                        enviarTablaVehiculosInvolucrados
                                                (context,linearLayout,textView,gifImageView,idEncuestaSQLite,
                                                        idReporteSiniestroSQL, idAutopista,
                                                        nombreAutopista, acronimoAutopista);*/
                            }
                        }else{
                            Log.d(TAG, "No se han enviado todas las solicitudes");
                        }
                    }
                    @Override
                    public void onFail(VolleyError error) {
                        numeroSubidasCorrectas--;
                        numeroSubidasIncorrectas++;
                        falloSolicitud = true;
                        if(numeroSubidasIncorrectas == 0){
                            Log.d(TAG, "No se han enviado todas las solicitudes");
                        }

                        //SyncUpActivity.actualizaFinalizacion();
                        mensajesToasSincronizacion("Ha ocurrido un error, por favor intentalo nuevamente.", context);

                    }
                });
            }




           // }


        }


    public void mensajesToasSincronizacion(String mesnsaje, Context con){
        Toast.makeText(con, mesnsaje,
                Toast.LENGTH_LONG).show();
    }

}
