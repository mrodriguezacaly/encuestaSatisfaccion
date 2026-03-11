package com.ideal.encuestacliente.vistas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.jsonHelper.JSONParser;
import com.ideal.encuestacliente.model.Esatis_Autopista_Pregunta;
import com.ideal.encuestacliente.model.Esatis_Catalogo_Preguntas;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.model.Esatis_Frecuencia;
import com.ideal.encuestacliente.model.Esatis_Plaza_Cobro;
import com.ideal.encuestacliente.model.Esatis_Valoracion;
import com.ideal.encuestacliente.sincronizacion.AplicationController;
import com.ideal.encuestacliente.sincronizacion.UrlSincronizacion;
import com.ideal.encuestacliente.synctables.Esatis_AutopistasSync;
import com.ideal.encuestacliente.synctables.Esatis_FormaPagoSync;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_CATALOGO_AUTOPISTA_PREGUNTA;
import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_CATALOGO_FRECUENCIA;
import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_CATALOGO_PREGUNTAS;
import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_CATALOGO_VALORACION;
import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_FORMA_PAGO;
import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_PLAZA_COBRO;
import static com.ideal.encuestacliente.configuracion.Constantes.WS_PLAZA_COBRO;
import static com.ideal.encuestacliente.configuracion.Constantes.idUsuario;
import static com.ideal.encuestacliente.configuracion.Constantes.nombreUsuario;
import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;
import static com.ideal.encuestacliente.configuracion.Utils.syncSuccessMessage;

public class SyncDownActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout rootMain;
    private TextView txvAutopistas, txvPlazaCobro, txvFormaPago, txvFrecuencia, txvPreguntas, txvValoracion, txvAutopistaPregunta, txvUbicaciones;
    private TextView formatoTitulo;
    private String nomFormato;
    private FloatingActionButton btnRegresar, btnSyncCatalogos;
    private ProgressDialog pDialogCateFormaPago;
    private ProgressDialog pDialogFrecuencia;
    private ProgressDialog pDialogValoracion;
    private ProgressDialog pDialogCatalogoPreguntas;
    private ProgressDialog pDialogAutopistasPreguntas;
    private ProgressDialog pDialogPlazaCobro;
    private Context ctx;
    private static final String TAG_SUCCESS = "success";

    int exitoso;
    JSONArray catalogos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_down);
        rootMain = findViewById(R.id.rootMain);
        setMarginInsets(rootMain);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txvAutopistas = findViewById(R.id.txvAutopistas);
        txvPlazaCobro = findViewById(R.id.txvPlazaCobro);
        txvFormaPago = findViewById(R.id.txvFormaPago);
        txvFrecuencia = findViewById(R.id.txvFrecuencia);
        txvPreguntas = findViewById(R.id.txvPreguntas);
        txvValoracion = findViewById(R.id.txvValoracion);
        txvUbicaciones = findViewById(R.id.txvUbicaciones);
        txvAutopistaPregunta = findViewById(R.id.txvAutopistaPregunta);
        formatoTitulo = findViewById(R.id.formatoTitulo);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnSyncCatalogos = findViewById(R.id.btnSyncCatalogos);
        ctx = this;


       /*  Bundle extras = getIntent().getExtras();
         int idUsuario         =  extras.getInt("idUsuario");
         String nombreUsuario  =  extras.getString("nombreUsuario");*/

        actionControls();

        Log.e("idUsuario", idUsuario + "");
        Log.e("nombreUsuario", nombreUsuario + "");

        Sync(idUsuario, nombreUsuario);
        new descargaFormaPago().execute("");
        // nomFormato = Utils.nomFormato();
        formatoTitulo.setText(nomFormato);

    }

    private void actionControls() {
        btnSyncCatalogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regresaInicio();
                txvAutopistas.setText(getResources().getString(R.string.autopistas));
                txvPlazaCobro.setText(getResources().getString(R.string.plazaCobroCata));
                txvFormaPago.setText(getResources().getString(R.string.formaPagoCat));
                txvFrecuencia.setText(getResources().getString(R.string.formaFrecuenciaCat));
                txvPreguntas.setText(getResources().getString(R.string.preguntasCat));
                txvValoracion.setText(getResources().getString(R.string.valoracionCat));
                txvAutopistaPregunta.setText(getResources().getString(R.string.autopistaPreguntasCat));
                txvUbicaciones.setText(getResources().getString(R.string.ubicacions));
                Sync(idUsuario, nombreUsuario);
                new descargaFormaPago().execute("");
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresaMainManu();
            }
        });
    }

    private void regresaMainManu() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void Sync(int idUsuario, String nombreUsuario) {
        new Esatis_AutopistasSync().Sync(txvAutopistas, idUsuario);
        //new Esatis_FormaPagoSync().SyncCatalogos(txvFormaPago, idUsuario, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AplicationController.getInstance().cancelPendingRequests();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.btnSincronizarNuevamente:
                finish();
                startActivity(getIntent());
                break;*/
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.menuGeneralRegresar:

                startActivity(new Intent(SyncDownActivity.this,MainActivity.class));
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(R.menu.menu_general,menu);

        return true;
    }

    class descargaFormaPago extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogCateFormaPago = new ProgressDialog(SyncDownActivity.this);
            pDialogCateFormaPago.setMessage("Forma pago");
            pDialogCateFormaPago.setIndeterminate(false);
            pDialogCateFormaPago.setCancelable(false);
            pDialogCateFormaPago.show();
        }


        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;
            BaseDaoEncuesta conexion = new BaseDaoEncuesta(SyncDownActivity.this);
            Esatis_Forma_Pago forma = new Esatis_Forma_Pago();

            BaseDaoEncuesta.getInstance().truncateTable(TABLA_FORMA_PAGO);
            BaseDaoEncuesta.getInstance().resetAutoincrement(TABLA_FORMA_PAGO);

            String URL = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + TABLA_FORMA_PAGO;
            //String URL = "https://api.bitso.com/v3/available_books/";

            HashMap<String, String> params = new HashMap<>();
            params.put("idUsuario", idUsuario + "");

            try {
                json = jsonParser.makeHttpRequest(URL,
                        "GET");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                exitoso = json.getInt(TAG_SUCCESS);

                if (exitoso == 1) {

                    catalogos = json.getJSONArray(TABLA_FORMA_PAGO);

                    for (int i = 0; i < catalogos.length(); i++) {
                        JSONObject c = catalogos.getJSONObject(i);

                        String formaPago = c.getString(Constantes.camposFormaPago.formaPago);
                        String usuarioCreacion = c.getString(Constantes.usuarioCreacion);
                        String usuarioModificacion = c.getString(Constantes.usuarioModificacion);
                        String usuarioEliminacion = c.getString(Constantes.usuarioEliminacion);
                        String fechaCreacion = c.getString(Constantes.fechaCreacion);
                        String fechaModificacion = c.getString(Constantes.fechaModificacion);
                        String fechaEliminacion = c.getString(Constantes.fechaEliminacion);

                        forma.setFormaPago(formaPago);
                        forma.setUsuarioCreacion(usuarioCreacion);
                        forma.setUsuarioModificacion(usuarioModificacion);
                        forma.setUsuarioEliminacion(usuarioEliminacion);
                        forma.setFechaCreacion(fechaCreacion);
                        forma.setFechaModificacion(fechaModificacion);
                        forma.setFechaEliminacion(fechaEliminacion);

                        if (conexion.insertaFormaPago(forma)) {
                            Log.e("se guardo", "se guardo");
                            //Toast.makeText(getApplicationContext(), "Se guardó la forma pago correctamente...", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("No se guardo", "no se guardo");
                            //Toast.makeText(getApplicationContext(), "Error al guardar forma pago varifique datos...", Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

            if (exitoso == 1) {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
                syncSuccessMessage(txvFormaPago);
                new descargaFrecuencia().execute("");
            } else {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
            }


            if (pDialogCateFormaPago != null && pDialogCateFormaPago.isShowing()) {
                pDialogCateFormaPago.dismiss();
            }


        }


    }


    class descargaFrecuencia extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogFrecuencia = new ProgressDialog(SyncDownActivity.this);
            pDialogFrecuencia.setMessage("Frecuencia");
            pDialogFrecuencia.setIndeterminate(false);
            pDialogFrecuencia.setCancelable(false);
            pDialogFrecuencia.show();
        }


        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;
            BaseDaoEncuesta conexion = new BaseDaoEncuesta(SyncDownActivity.this);
            Esatis_Frecuencia frecuencia = new Esatis_Frecuencia();

            BaseDaoEncuesta.getInstance().truncateTable(TABLA_CATALOGO_FRECUENCIA);
            BaseDaoEncuesta.getInstance().resetAutoincrement(TABLA_CATALOGO_FRECUENCIA);

            String URL = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + TABLA_CATALOGO_FRECUENCIA;
            Log.e("urlFrecuencia", URL);

            try {
                json = jsonParser.makeHttpRequest(URL,
                        "GET");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                exitoso = json.getInt(TAG_SUCCESS);

                if (exitoso == 1) {

                    catalogos = json.getJSONArray(TABLA_CATALOGO_FRECUENCIA);

                    for (int i = 0; i < catalogos.length(); i++) {
                        JSONObject c = catalogos.getJSONObject(i);

                        String frec = c.getString(Constantes.camposFrecuencia.descFrecuencia);
                        String usuarioCreacion = c.getString(Constantes.usuarioCreacion);
                        String usuarioModificacion = c.getString(Constantes.usuarioModificacion);
                        String usuarioEliminacion = c.getString(Constantes.usuarioEliminacion);
                        String fechaCreacion = c.getString(Constantes.fechaCreacion);
                        String fechaModificacion = c.getString(Constantes.fechaModificacion);
                        String fechaEliminacion = c.getString(Constantes.fechaEliminacion);

                        frecuencia.setDescFrecuencia(frec);
                        frecuencia.setUsuarioCreacion(usuarioCreacion);
                        frecuencia.setUsuarioModificacion(usuarioModificacion);
                        frecuencia.setUsuarioEliminacion(usuarioEliminacion);
                        frecuencia.setFechaCreacion(fechaCreacion);
                        frecuencia.setFechaModificacion(fechaModificacion);
                        frecuencia.setFechaEliminacion(fechaEliminacion);

                        if (conexion.insertaFrecuencia(frecuencia)) {
                            Log.e("se guardo", "se guardo");
                            //Toast.makeText(getApplicationContext(), "Se guardó la forma pago correctamente...", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("No se guardo", "no se guardo");
                            //Toast.makeText(getApplicationContext(), "Error al guardar forma pago varifique datos...", Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String file_url) {

            if (exitoso == 1) {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
                syncSuccessMessage(txvFrecuencia);
                new descargaValoracion().execute("");
            } else {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
            }


            if (pDialogFrecuencia != null && pDialogFrecuencia.isShowing()) {
                pDialogFrecuencia.dismiss();
            }


        }


    }


    class descargaValoracion extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogValoracion = new ProgressDialog(SyncDownActivity.this);
            pDialogValoracion.setMessage("Valoracion");
            pDialogValoracion.setIndeterminate(false);
            pDialogValoracion.setCancelable(false);
            pDialogValoracion.show();
        }

        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;
            BaseDaoEncuesta conexion = new BaseDaoEncuesta(SyncDownActivity.this);
            Esatis_Valoracion valoracion = new Esatis_Valoracion();

            BaseDaoEncuesta.getInstance().truncateTable(TABLA_CATALOGO_VALORACION);
            BaseDaoEncuesta.getInstance().resetAutoincrement(TABLA_CATALOGO_VALORACION);

            String URL = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + TABLA_CATALOGO_VALORACION;
            Log.e("urlFrecuencia", URL);

            try {
                json = jsonParser.makeHttpRequest(URL,
                        "GET");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                exitoso = json.getInt(TAG_SUCCESS);

                if (exitoso == 1) {

                    catalogos = json.getJSONArray(TABLA_CATALOGO_VALORACION);

                    for (int i = 0; i < catalogos.length(); i++) {
                        JSONObject c = catalogos.getJSONObject(i);

                        String frec = c.getString(Constantes.camposValoracion.descValoraciones);
                        String usuarioCreacion = c.getString(Constantes.usuarioCreacion);
                        String usuarioModificacion = c.getString(Constantes.usuarioModificacion);
                        String usuarioEliminacion = c.getString(Constantes.usuarioEliminacion);
                        String fechaCreacion = c.getString(Constantes.fechaCreacion);
                        String fechaModificacion = c.getString(Constantes.fechaModificacion);
                        String fechaEliminacion = c.getString(Constantes.fechaEliminacion);

                        valoracion.setDescValoraciones(frec);
                        valoracion.setUsuarioCreacion(usuarioCreacion);
                        valoracion.setUsuarioModificacion(usuarioModificacion);
                        valoracion.setUsuarioEliminacion(usuarioEliminacion);
                        valoracion.setFechaCreacion(fechaCreacion);
                        valoracion.setFechaModificacion(fechaModificacion);
                        valoracion.setFechaEliminacion(fechaEliminacion);

                        if (conexion.insertaValoracion(valoracion)) {
                            Log.e("se guardo", "se guardo");
                            //Toast.makeText(getApplicationContext(), "Se guardó la forma pago correctamente...", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("No se guardo", "no se guardo");
                            //Toast.makeText(getApplicationContext(), "Error al guardar forma pago varifique datos...", Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String file_url) {

            if (exitoso == 1) {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
                syncSuccessMessage(txvValoracion);
                new descargaCatalogPreguntas().execute("");
            } else {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
            }


            if (pDialogValoracion != null && pDialogValoracion.isShowing()) {
                pDialogValoracion.dismiss();
            }


        }


    }


    class descargaCatalogPreguntas extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogCatalogoPreguntas = new ProgressDialog(SyncDownActivity.this);
            pDialogCatalogoPreguntas.setMessage("Catalogo preguntas");
            pDialogCatalogoPreguntas.setIndeterminate(false);
            pDialogCatalogoPreguntas.setCancelable(false);
            pDialogCatalogoPreguntas.show();
        }

        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;
            BaseDaoEncuesta conexion = new BaseDaoEncuesta(SyncDownActivity.this);
            Esatis_Catalogo_Preguntas preguntas = new Esatis_Catalogo_Preguntas();

            BaseDaoEncuesta.getInstance().truncateTable(TABLA_CATALOGO_PREGUNTAS);
            BaseDaoEncuesta.getInstance().resetAutoincrement(TABLA_CATALOGO_PREGUNTAS);

            String URL = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + TABLA_CATALOGO_PREGUNTAS;
            Log.e("urlFrecuencia", URL);

            try {
                json = jsonParser.makeHttpRequest(URL,
                        "GET");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                exitoso = json.getInt(TAG_SUCCESS);

                if (exitoso == 1) {

                    catalogos = json.getJSONArray(TABLA_CATALOGO_PREGUNTAS);

                    for (int i = 0; i < catalogos.length(); i++) {
                        JSONObject c = catalogos.getJSONObject(i);

                        int idPreguntaWS = c.getInt(Constantes.camposPreguntas.idPreguntaCatalogo);
                        String descPreguntas = c.getString(Constantes.camposPreguntas.descPregunta);
                        String observaciones = c.getString(Constantes.camposPreguntas.descObservaciones);
                        String usuarioCreacion = c.getString(Constantes.usuarioCreacion);
                        String usuarioModificacion = c.getString(Constantes.usuarioModificacion);
                        String usuarioEliminacion = c.getString(Constantes.usuarioEliminacion);
                        String fechaCreacion = c.getString(Constantes.fechaCreacion);
                        String fechaModificacion = c.getString(Constantes.fechaModificacion);
                        String fechaEliminacion = c.getString(Constantes.fechaEliminacion);

                        preguntas.setIdPreguntaCatalogo(idPreguntaWS);
                        preguntas.setDescPregunta(descPreguntas);
                        preguntas.setDescObservaciones(observaciones);
                        preguntas.setUsuarioCreacion(usuarioCreacion);
                        preguntas.setUsuarioModificacion(usuarioModificacion);
                        preguntas.setUsuarioEliminacion(usuarioEliminacion);
                        preguntas.setFechaCreacion(fechaCreacion);
                        preguntas.setFechaModificacion(fechaModificacion);
                        preguntas.setFechaEliminacion(fechaEliminacion);

                        if (conexion.insertaPreguntas(preguntas)) {
                            Log.e("se guardo", "se guardo");
                            //Toast.makeText(getApplicationContext(), "Se guardó la forma pago correctamente...", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("No se guardo", "no se guardo");
                            //Toast.makeText(getApplicationContext(), "Error al guardar forma pago varifique datos...", Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String file_url) {

            if (exitoso == 1) {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
                syncSuccessMessage(txvPreguntas);
                new descargaAutopistasPreguntas().execute("");
            } else {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
            }


            if (pDialogCatalogoPreguntas != null && pDialogCatalogoPreguntas.isShowing()) {
                pDialogCatalogoPreguntas.dismiss();
            }


        }


    }


    class descargaAutopistasPreguntas extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogAutopistasPreguntas = new ProgressDialog(SyncDownActivity.this);
            pDialogAutopistasPreguntas.setMessage("Autopistas preguntas");
            pDialogAutopistasPreguntas.setIndeterminate(false);
            pDialogAutopistasPreguntas.setCancelable(false);
            pDialogAutopistasPreguntas.show();
        }

        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;
            BaseDaoEncuesta conexion = new BaseDaoEncuesta(SyncDownActivity.this);
            Esatis_Autopista_Pregunta autopistasPreguntas = new Esatis_Autopista_Pregunta();

            BaseDaoEncuesta.getInstance().truncateTable(TABLA_CATALOGO_AUTOPISTA_PREGUNTA);
            BaseDaoEncuesta.getInstance().resetAutoincrement(TABLA_CATALOGO_AUTOPISTA_PREGUNTA);

            String URL = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + TABLA_CATALOGO_AUTOPISTA_PREGUNTA;
            Log.e("urlFrecuencia", URL);

            try {
                json = jsonParser.makeHttpRequest(URL,
                        "GET");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                exitoso = json.getInt(TAG_SUCCESS);

                if (exitoso == 1) {

                    catalogos = json.getJSONArray(TABLA_CATALOGO_AUTOPISTA_PREGUNTA);

                    for (int i = 0; i < catalogos.length(); i++) {
                        JSONObject c = catalogos.getJSONObject(i);

                        int idAutopista = c.getInt(Constantes.camposAutopistasPreguntas.idAutopista);
                        Log.e("idAutopista_", idAutopista + "");
                        int idPreguntaCatalogo = c.getInt(Constantes.camposPreguntas.idPreguntaCatalogo);
                        Log.e("idPreguntaCatalogo_", idPreguntaCatalogo + "");
                        String usuarioCreacion = c.getString(Constantes.usuarioCreacion);
                        String usuarioModificacion = c.getString(Constantes.usuarioModificacion);
                        String usuarioEliminacion = c.getString(Constantes.usuarioEliminacion);
                        String fechaCreacion = c.getString(Constantes.fechaCreacion);
                        String fechaModificacion = c.getString(Constantes.fechaModificacion);
                        String fechaEliminacion = c.getString(Constantes.fechaEliminacion);

                        autopistasPreguntas.setIdAutopista(idAutopista);
                        autopistasPreguntas.setIdPreguntaCatalogo(idPreguntaCatalogo);
                        autopistasPreguntas.setUsuarioCreacion(usuarioCreacion);
                        autopistasPreguntas.setUsuarioModificacion(usuarioModificacion);
                        autopistasPreguntas.setUsuarioEliminacion(usuarioEliminacion);
                        autopistasPreguntas.setFechaCreacion(fechaCreacion);
                        autopistasPreguntas.setFechaModificacion(fechaModificacion);
                        autopistasPreguntas.setFechaEliminacion(fechaEliminacion);

                        if (conexion.insertaAutopistaPreguntas(autopistasPreguntas)) {
                            Log.e("se guardo", "se guardo");
                            //Toast.makeText(getApplicationContext(), "Se guardó la forma pago correctamente...", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("No se guardo", "no se guardo");
                            //Toast.makeText(getApplicationContext(), "Error al guardar forma pago varifique datos...", Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String file_url) {

            if (exitoso == 1) {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
                syncSuccessMessage(txvAutopistaPregunta);
                new descargaPlazasCobro().execute("");
            } else {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
            }


            if (pDialogAutopistasPreguntas != null && pDialogAutopistasPreguntas.isShowing()) {
                pDialogAutopistasPreguntas.dismiss();
            }


        }


    }


    class descargaPlazasCobro extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogPlazaCobro = new ProgressDialog(SyncDownActivity.this);
            pDialogPlazaCobro.setMessage("Plaza cobro");
            pDialogPlazaCobro.setIndeterminate(false);
            pDialogPlazaCobro.setCancelable(false);
            pDialogPlazaCobro.show();
        }

        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;
            BaseDaoEncuesta conexion = new BaseDaoEncuesta(SyncDownActivity.this);
            Esatis_Plaza_Cobro plazaCobroModel = new Esatis_Plaza_Cobro();

            BaseDaoEncuesta.getInstance().truncateTable(TABLA_PLAZA_COBRO);
            BaseDaoEncuesta.getInstance().resetAutoincrement(TABLA_PLAZA_COBRO);

            String URL = UrlSincronizacion.URL_BASE_WS_DOWN_CATALOGOS + WS_PLAZA_COBRO;
            Log.e("urlFrecuencia", URL);

            try {
                json = jsonParser.makeHttpRequest(URL,
                        "GET");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                exitoso = json.getInt(TAG_SUCCESS);

                if (exitoso == 1) {

                    catalogos = json.getJSONArray(WS_PLAZA_COBRO);

                    for (int i = 0; i < catalogos.length(); i++) {
                        JSONObject c = catalogos.getJSONObject(i);

                        int idAutopista = c.getInt(Constantes.camposPlazaCobro.idAutopista);
                        Log.e("idAutopista_", idAutopista + "");
                        String nomPlazaCob = c.getString(Constantes.camposPlazaCobro.plazaCobro);
                        Log.e("nomPlazaCob", nomPlazaCob + "");
                        String razonSocialAutopista = c.getString(Constantes.camposPlazaCobro.razonSocialAutopista);
                        String usuarioCreacion = "";
                        String usuarioModificacion = "";
                        String usuarioEliminacion = "";
                        String fechaCreacion = "";
                        String fechaModificacion = "";
                        String fechaEliminacion = "";
                        usuarioCreacion = c.getString(Constantes.usuarioCreacion);
                        usuarioModificacion = c.getString(Constantes.usuarioModificacion);
                        usuarioEliminacion = c.getString(Constantes.usuarioEliminacion);
                        fechaCreacion = c.getString(Constantes.fechaCreacion);
                        fechaModificacion = c.getString(Constantes.fechaModificacion);
                        fechaEliminacion = c.getString(Constantes.fechaEliminacion);

                        plazaCobroModel.setIdAutopista(idAutopista);
                        plazaCobroModel.setPlazaCobro(nomPlazaCob);
                        plazaCobroModel.setRazonSocialAutopista(razonSocialAutopista);
                        plazaCobroModel.setUsuarioCreacion(usuarioCreacion);
                        plazaCobroModel.setUsuarioModificacion(usuarioModificacion);
                        plazaCobroModel.setUsuarioEliminacion(usuarioEliminacion);
                        plazaCobroModel.setFechaCreacion(fechaCreacion);
                        plazaCobroModel.setFechaModificacion(fechaModificacion);
                        plazaCobroModel.setFechaEliminacion(fechaEliminacion);

                        if (conexion.insertaPlazaCobro(plazaCobroModel)) {
                            Log.e("se guardo", "se guardo");
                            //Toast.makeText(getApplicationContext(), "Se guardó la forma pago correctamente...", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("No se guardo", "no se guardo");
                            //Toast.makeText(getApplicationContext(), "Error al guardar forma pago varifique datos...", Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String file_url) {

            if (exitoso == 1) {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
                syncSuccessMessage(txvPlazaCobro);
            } else {
                Toast.makeText(SyncDownActivity.this, getString(R.string.catalogosCorrectos), Toast.LENGTH_SHORT).show();
            }


            if (pDialogPlazaCobro != null && pDialogPlazaCobro.isShowing()) {
                pDialogPlazaCobro.dismiss();
            }


        }


    }


}
