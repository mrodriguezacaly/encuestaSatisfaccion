package com.ideal.encuestacliente.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.SharedPreferencesManager;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.controladores.ControladorDatosCliente;
import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Plaza_Cobro;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ideal.encuestacliente.configuracion.Utils.isOnlineNet;
import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;

public class ListaEncuestasSincronizarActivity extends AppCompatActivity {

    private ConstraintLayout rootMain;
    Spinner spinnerAutopista;
    ListView listaSincronizarEncuestas;
    private String[] autopistas;
    BaseDaoEncuesta con = new BaseDaoEncuesta(this);
    private static final String TAG_PID_ENCUESTA = "idEncuesta";
    private static final String TAG_NOMBRE_ENCUESTADOR = "encuestador";
    private static final String TAG_PID_PLAZA_COBRO = "idPlazaCobro";
    List listaEncuestasConsulta;
    int LidFinalPersona = 0;
    private FloatingActionButton btnRegresar;

    private int[] idPlazaCobroInt = {
            0
    };

    private String[] plazaCobro = {
            "Selecciona"
    };

    String idEncuestasCadena = "";
    private BottomAppBar bottomAppBar;
    private String nomAutopistaSeleccionada;

    private SharedPreferencesManager preferences;

    private TextView txtUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_encuestas_sincronizar);
        rootMain = findViewById(R.id.rootMain);
        setMarginInsets(rootMain);

        Bundle parametros = this.getIntent().getExtras();
        nomAutopistaSeleccionada = parametros.getString(Constantes.campos.nombreAutopista);

        initControls();
        consultas();
        actionsControls();
    }

    private void showUserData() {
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

    private void initControls() {
        spinnerAutopista = findViewById(R.id.spinnerAutopista);
        listaSincronizarEncuestas = findViewById(R.id.listaSincronizarEncuestas);
        btnRegresar = findViewById(R.id.btnRegresar);
        bottomAppBar = findViewById(R.id.buttonAppBarPersonas);
        setSupportActionBar(bottomAppBar);
        showUserData();
    }


    private void consultas() {

        List listaAutopistas = con.consultaAutopistas();
        autopistas = new String[listaAutopistas.size() + 1];
        autopistas[0] = "Selecciona Autopista";
        int contador = 1;

        for (Object datos : listaAutopistas) {
            Esatis_Autopistas elementos = (Esatis_Autopistas) datos;
            autopistas[contador] = elementos.getNombreAutopista();
            contador = contador + 1;
        }

        List listaPlazaCobro = con.consultaPlazaCobroPorIdPlaza();
        plazaCobro = new String[listaPlazaCobro.size() + 1];
        idPlazaCobroInt = new int[listaPlazaCobro.size() + 1];
        plazaCobro[0] = "Selecciona";
        idPlazaCobroInt[0] = 0;
        contador = 1;

        for (Object datos : listaPlazaCobro) {
            Esatis_Plaza_Cobro elementos = (Esatis_Plaza_Cobro) datos;
            idPlazaCobroInt[contador] = elementos.getIdPlazaCobroSQL();
            Log.e("idPlazaCobro[contador]", idPlazaCobroInt[contador] + "");
            plazaCobro[contador] = elementos.getPlazaCobro();
            Log.e("plazaCobro[contador]", plazaCobro[contador] + "");
            contador = contador + 1;
        }


        //plazaCobro = new String[60];
    }

    private void actionsControls() {
        Utils.Spinner(spinnerAutopista, autopistas, getApplicationContext());

        ArrayAdapter<String> adapterDetalleHor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, autopistas);
        spinnerAutopista.setSelection(adapterDetalleHor.getPosition(nomAutopistaSeleccionada));

        spinnerAutopista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                listaEncuestasConsulta = con.consultaDatosClientePorIdAutopista(ControladorDatosCliente.obtieneIdAutopistas(spinnerAutopista.getSelectedItem().toString(), getApplicationContext()));
                consultaEncuestasPorAutopista();

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresaMainManu();
            }
        });
    }

    private void consultaEncuestasPorAutopista() {

        ArrayList<HashMap<String, Object>> productsList = null;
        productsList = new ArrayList<HashMap<String, Object>>();
        String plazaConbroNombre = "";
        int indicadorCompleto = 0;

        for (Object infoCliente : listaEncuestasConsulta) {
            Esatis_Cliente info = (Esatis_Cliente) infoCliente;
            indicadorCompleto = info.getIdIndicadorSinc();
            Log.e("idAutopista", info.idAutopista + "");
            Log.e("idPlazaCobroCons", info.idPlazaCobro + "");
            Log.e("encuestador", info.encuestador + "");
            Log.e("origen", info.origen + "");
            Log.e("destino", info.destino + "");
            Log.e("idFrecuenciaUso", info.idFrecuenciaUso + "");
            Log.e("fecha", info.fecha + "");
            Log.e("idFormaPago", info.idFormaPago + "");


            if (indicadorCompleto == 2) {
                for (int i = 0; i < idPlazaCobroInt.length; i++) {
                    if (info.idPlazaCobro == idPlazaCobroInt[i]) {
                        plazaConbroNombre = plazaCobro[i];
                    }
                }

                HashMap<String, Object> map = new HashMap<String, Object>();

                map.put(TAG_PID_ENCUESTA, info.idEncuesta);
                map.put(TAG_NOMBRE_ENCUESTADOR, "No. " + info.idEncuesta);
                map.put(TAG_PID_PLAZA_COBRO, plazaConbroNombre);

                productsList.add(map);
            }


        }

        ListAdapter adapter3 = new SimpleAdapter(ListaEncuestasSincronizarActivity.this,
                productsList, R.layout.lista_personas_atendieron, new String[]{TAG_PID_ENCUESTA,
                TAG_PID_PLAZA_COBRO, TAG_NOMBRE_ENCUESTADOR},
                new int[]{R.id.LIid, R.id.nombre, R.id.puesto});


        listaSincronizarEncuestas.setAdapter(adapter3);

        //your grid view as child
        listaSincronizarEncuestas.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        listaSincronizarEncuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int LIid, long id) {
                LidFinalPersona = LIid;

                String test[] = {};
                test = listaSincronizarEncuestas.getItemAtPosition(LidFinalPersona).toString().replace("{", "").replace("}", "").split(",");

                String numRep[] = {};
                String cadenaValida = "";
                numRep = test[0].split("=");

                for (int i = 0; i < test.length; i++) {
                    if (test[i].contains("idEncuesta")) {
                        numRep = test[i].split("=");
                    }
                }

                cadenaValida = numRep[1].toString();

                listaEncuestas(cadenaValida);

            }
        });

    }

    private void listaEncuestas(String idEncuestas) {

        idEncuestasCadena = idEncuestas;
        if (haveNetworkConnection() == true) {
            new conectividadCSVTask().execute();
        } else {
            showAlertDialogEliminarDatosConductores();
        }


    }

    private void regresaMainManu() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    Context ctx = this;
    boolean conectividad;

    class conectividadCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        // can use UI thread here

        protected void onPreExecute() {
            this.dialog.setMessage("Verificando conectividad...");
            this.dialog.show();
            this.dialog.setCancelable(false);
            this.dialog.setCanceledOnTouchOutside(false);
        }

        // automatically done on worke r thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {

            conectividad = isOnlineNet();
            isOnline();

            return true;

        }

        // can use UI thread here
        @Override
        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing() && this.dialog != null) {
                this.dialog.dismiss();
            }

            if (!conectividad) {
                showAlertDialogConexion();
            }

            if (conectividad) {

                continuaSinc();

            }


        }

    }

    private void continuaSinc() {

    /*    Intent linsertar=new Intent(this, SyncUpActivity.class);
        linsertar.putExtra("idEncuestas", idEncuestasCadena);
        startActivity(linsertar);*/

        Bundle bundle = new Bundle();
        bundle.putString(Constantes.campos.nombreAutopista,
                nomAutopistaSeleccionada);
        bundle.putString("idEncuestas",
                idEncuestasCadena);
        startActivity(new Intent(ListaEncuestasSincronizarActivity.this, SyncUpActivity.class).
                putExtras(bundle));

    }


    String nombreConexion = "";

    public void isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        nombreConexion = netInfo.getExtraInfo();
    }

    private void showAlertDialogConexion() {
        AlertDialog alertDialog = new AlertDialog.Builder(ListaEncuestasSincronizarActivity.this).create();
        alertDialog.setTitle(R.string.internet);
        alertDialog.setMessage("Con la siguiente red: " + nombreConexion + ", no cuentas con acceso a " +
                "internet y la aplicación no funcionará adecuadamente, por favor cambia de red.");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });

        alertDialog.show();
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void showAlertDialogEliminarDatosConductores() {
        AlertDialog alertDialog = new AlertDialog.Builder(ListaEncuestasSincronizarActivity.this).create();
        alertDialog.setTitle(R.string.internet);
        alertDialog.setMessage(getString(R.string.conexion));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.deAcuerdo),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_general, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
