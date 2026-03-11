package com.ideal.encuestacliente.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.SharedPreferencesManager;
import com.ideal.encuestacliente.generadorJson.JsonDatosClientes;
import com.ideal.encuestacliente.jsonHelper.JSONParser;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.sincronizacion.UrlSincronizacion;
import com.ideal.encuestacliente.synctables.Esatis_DatosClienteSync;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;

import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_FORMA_PAGO;
import static com.ideal.encuestacliente.configuracion.Constantes.idUsuario;
import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;
import static com.ideal.encuestacliente.configuracion.Utils.syncSuccessMessage;

public class SyncUpActivity extends AppCompatActivity {

    private FloatingActionButton btnSincronizar, btnRegresar;
    private TextView txvDatosClientes, txvEncuestasClientes;
    private GifImageView gifDatosClientes, gifEncuestasClientes;
    private LinearLayout linearDatosClientes, linearEncuestas, rootMain;
    private int idEncuesta;
    private int idAutopista;
    private String nombreAutopista;
    private String numeroReporte;
    private String acronimoAutopista;
    String idEncuestas;

    private ProgressDialog pDialogCateFormaPago, pDialogPreguntas;
    Context ctx = this;
    private static final String TAG_SUCCESS = "idEncuesta";
    int idEncuestaSQL;
    public static Context contexto;
    static int idEncuestaIndSync;
    private BottomAppBar bottomAppBar;
    private static String nombreAutopistaEstatica;

    private SharedPreferencesManager preferences;

    private TextView txtUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_up);
        rootMain = findViewById(R.id.rootMain);
        setMarginInsets(rootMain);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        idEncuestas = getIntent().getExtras().getString("idEncuestas");
        nombreAutopista = getIntent().getExtras().getString(Constantes.campos.nombreAutopista);
        nombreAutopistaEstatica = getIntent().getExtras().getString(Constantes.campos.nombreAutopista);

        idEncuestaIndSync=Integer.parseInt(idEncuestas);
        Log.e("idEncuestas", idEncuestas);

        bottomAppBar           = findViewById(R.id.buttonAppBarPersonas);
        setSupportActionBar(bottomAppBar);

        init();
        actionControls();
        SyncUp();
        //new envioDatosCliente().execute("");
    }

    private void showUserData(){
        txtUserData = findViewById(R.id.userData);
        preferences = SharedPreferencesManager.getInstance(this);

        String userData = preferences.getUserDataFormat();
        String usuario;

        if (userData == null || userData.isEmpty()) {
            List<Usuario> userList = BaseDaoEncuesta.getInstance().getUserData();

            for (Usuario user : userList) {
                usuario = user.getNombre() + " - " + user.getNombreUsuario() + " - " + user.getPuesto();
                txtUserData.setText(usuario);
                preferences.saveUserData(user);
            }
        } else {
            txtUserData.setText(userData);
        }
    }

    private void init(){
        linearDatosClientes = findViewById(R.id.linearDatosClientes);
        txvDatosClientes = findViewById(R.id.txvDatosClientes);
        gifDatosClientes = findViewById(R.id.gifDatosClientes);
        linearEncuestas = findViewById(R.id.linearEncuestas);
        txvEncuestasClientes = findViewById(R.id.txvEncuestasClientes);
        gifEncuestasClientes = findViewById(R.id.gifEncuestasClientes);

        btnSincronizar = findViewById(R.id.btnSincronizar);
        btnRegresar = findViewById(R.id.btnRegresar);
        contexto = this;

        showUserData();
    }

    private void actionControls(){
        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresaMainManu();
            }
        });
    }

    private void regresaMainManu(){

        Bundle bundle = new Bundle();
        bundle.putString(Constantes.campos.nombreAutopista,
                nombreAutopista);
        startActivity(new Intent(SyncUpActivity.this, MainActivity.class).
                putExtras(bundle));

    }

    public static void regresaListaEncuestasSync(){
        Intent i=new Intent(contexto, ListaEncuestasSincronizarActivity.class);
        i.putExtra(Constantes.campos.nombreAutopista, nombreAutopistaEstatica);
        contexto.startActivity(i);
    }

    public void SyncUp(){
        idEncuesta = Integer.parseInt(idEncuestas);
        new Esatis_DatosClienteSync().enviarTablaDatosEncuesta(
                getApplicationContext(),
                new LinearLayout[]{linearDatosClientes, linearEncuestas } ,
                new TextView[]    {txvDatosClientes, txvEncuestasClientes } ,
                new GifImageView[]{gifDatosClientes, gifEncuestasClientes }, idEncuesta, idAutopista, nombreAutopista, acronimoAutopista );
    }

    public static void actualizaFinalizacion(){
        BaseDaoEncuesta con = new BaseDaoEncuesta(contexto);
        Esatis_Cliente cliente = new Esatis_Cliente();
        cliente.setIdEncuesta(idEncuestaIndSync);
        cliente.setIdIndicadorSinc(3);
        if(con.actualizaIndicadorFinalizaSincronizacion(cliente)){
            Log.e("Act_Indicador_Sync", "Se actualizò el indicador");
        }

    }



    class envioDatosCliente extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogCateFormaPago = new ProgressDialog(SyncUpActivity.this);
            pDialogCateFormaPago.setMessage("Datos Cliente");
            pDialogCateFormaPago.setIndeterminate(false);
            pDialogCateFormaPago.setCancelable(false);
            pDialogCateFormaPago.show();
        }


        protected String doInBackground(String... args) {

            BaseDaoEncuesta con = new BaseDaoEncuesta(ctx);
            List datosEncuesta = con.consultaDatosClientePorIdEncuesta(Integer.parseInt(idEncuestas));

            String mensajeRepSiniestros = "";

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;

            String URL = UrlSincronizacion.URL_SINCRONIZACION_ESATIS_DATOS_CLIENTE;
            Log.e("URL_datos_cliente", URL);
            //String URL = "https://api.bitso.com/v3/available_books/";

            HashMap<String, String> params = new HashMap<>();
            JSONObject jsonObject;

            for(Object datos: datosEncuesta) {
                Esatis_Cliente info = (Esatis_Cliente) datos;
                jsonObject = JsonDatosClientes.generarJson(info, "");
                //params.put("idUsuario", idUsuario+"");
                params.put(Constantes.camposDatosCliente.idAutopista, info.getIdAutopista()+"");
                params.put(Constantes.camposDatosCliente.idPlazaCobro, info.getIdPlazaCobro()+"");
                params.put(Constantes.camposDatosCliente.fecha, info.fecha);
                params.put(Constantes.camposDatosCliente.encuestador, info.getEncuestador());
                Log.e("encuestador", info.getEncuestador()+"");
                params.put(Constantes.camposDatosCliente.origen, info.getOrigen());
                params.put(Constantes.camposDatosCliente.destino, info.getDestino());
                params.put(Constantes.camposDatosCliente.idFormaPago, info.getIdFormaPago()+"");
                params.put(Constantes.camposDatosCliente.idFrecuenciaUso, info.getIdFrecuenciaUso()+"");

                params.put(Constantes.usuarioCreacion, info.getUsuarioCreacion());
                params.put(Constantes.usuarioModificacion, info.getUsuarioModificacion());
                params.put(Constantes.usuarioEliminacion, info.getUsuarioEliminacion());
                params.put(Constantes.fechaCreacion, info.getFechaCreacion());
                params.put(Constantes.fechaModificacion, info.getFechaModificacion());
                params.put(Constantes.fechaEliminacion, info.getFechaEliminacion());
            }

            try {
                json = jsonParser.makeHttpRequest(URL,
                        "POST", params);
                Log.e("json_datos_clientes", json.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            try {
                idEncuestaSQL = json.getInt(TAG_SUCCESS);
                Log.e("idEncuestaSQL", idEncuestaSQL+"");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

            if(idEncuestaSQL==1){
                Toast.makeText(SyncUpActivity.this, getString(R.string.infoClienteCorrecto),Toast.LENGTH_SHORT).show();
                new envioPreguntasCliente().execute("");
                //syncSuccessMessage(txvFormaPago);
                //new SyncDownActivity.descargaFrecuencia().execute("");
            }else{
                Toast.makeText(SyncUpActivity.this, getString(R.string.infoClienteNoCorrecto),Toast.LENGTH_SHORT).show();
            }

            if (pDialogCateFormaPago != null && pDialogCateFormaPago.isShowing()) {
                pDialogCateFormaPago.dismiss();
            }

        }

    }

    class envioPreguntasCliente extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogPreguntas = new ProgressDialog(SyncUpActivity.this);
            pDialogPreguntas.setMessage("Preguntas Cliente");
            pDialogPreguntas.setIndeterminate(false);
            pDialogPreguntas.setCancelable(false);
            pDialogPreguntas.show();
        }


        protected String doInBackground(String... args) {

            BaseDaoEncuesta con = new BaseDaoEncuesta(ctx);
            List datosEncuesta = con.consultaDatosClientePorIdEncuesta(Integer.parseInt(idEncuestas));

            String mensajeRepSiniestros = "";

            JSONParser jsonParser = new JSONParser();
            JSONObject json = null;

            String URL = UrlSincronizacion.URL_SINCRONIZACION_ESATIS_ENCUESTA;
            //String URL = "https://api.bitso.com/v3/available_books/";

            HashMap<String, String> params = new HashMap<>();

            for(Object datos: datosEncuesta) {
                Esatis_Cliente info = (Esatis_Cliente) datos;
                final JSONObject jsonObject = JsonDatosClientes.generarJson(info, "");
                //params.put("idUsuario", idUsuario+"");
                params.put(Constantes.camposDatosCliente.idAutopista, info.getIdAutopista()+"");
                params.put(Constantes.camposDatosCliente.idPlazaCobro, info.getIdPlazaCobro()+"");
                params.put(Constantes.camposDatosCliente.fecha, info.fecha);
                params.put(Constantes.camposDatosCliente.encuestador, info.getEncuestador());
                params.put(Constantes.camposDatosCliente.origen, info.getOrigen());
                params.put(Constantes.camposDatosCliente.destino, info.getDestino());
                params.put(Constantes.camposDatosCliente.idFormaPago, info.getIdFormaPago()+"");
                params.put(Constantes.camposDatosCliente.idFrecuenciaUso, info.getIdFrecuenciaUso()+"");

                params.put(Constantes.usuarioCreacion, info.getUsuarioCreacion());
                params.put(Constantes.usuarioModificacion, info.getUsuarioModificacion());
                params.put(Constantes.usuarioEliminacion, info.getUsuarioEliminacion());
                params.put(Constantes.fechaCreacion, info.getFechaCreacion());
                params.put(Constantes.fechaModificacion, info.getFechaModificacion());
                params.put(Constantes.fechaEliminacion, info.getFechaEliminacion());
            }



            try {
                json = jsonParser.makeHttpRequest(URL,
                        "POST", params);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            try {
                idEncuestaSQL = json.getInt(TAG_SUCCESS);
                Log.e("idEncuestaSQL", idEncuestaSQL+"");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

            if(idEncuestaSQL==1){
                Toast.makeText(SyncUpActivity.this, getString(R.string.preguntasClienteCorrecto),Toast.LENGTH_SHORT).show();
                //syncSuccessMessage(txvFormaPago);
                //new SyncDownActivity.descargaFrecuencia().execute("");
            }else{
                Toast.makeText(SyncUpActivity.this, getString(R.string.infoClienteCorrecto),Toast.LENGTH_SHORT).show();
            }

            if (pDialogPreguntas != null && pDialogPreguntas.isShowing()) {
                pDialogPreguntas.dismiss();
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_general,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuGeneralRegresar:
                regresaMainManu();
                break;
           /* case R.id.menuGeneralTerminar:
                showAlertDialogEliminar();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }


}
