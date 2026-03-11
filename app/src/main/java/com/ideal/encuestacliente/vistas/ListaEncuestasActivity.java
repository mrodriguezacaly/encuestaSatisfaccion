package com.ideal.encuestacliente.vistas;

import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import com.ideal.encuestacliente.configuracion.SpinnerControl;
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

public class ListaEncuestasActivity extends AppCompatActivity {

    private ConstraintLayout rootMain;
    Spinner spinnerAutopista;
    ListView listaEncuestas;
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

    private BottomAppBar bottomAppBar;
    private String nomAutopistaSeleccionada;

    private SharedPreferencesManager preferences;

    private TextView txtUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_encuestas);
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
        bottomAppBar = findViewById(R.id.buttonAppBarPersonas);
        setSupportActionBar(bottomAppBar);
        spinnerAutopista = findViewById(R.id.spinnerAutopista);
        listaEncuestas = findViewById(R.id.listaEncuestas);
        btnRegresar = findViewById(R.id.btnRegresar);
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
            Log.e("idPlazaCobro", info.idPlazaCobro + "");
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

        ListAdapter adapter3 = new SimpleAdapter(ListaEncuestasActivity.this,
                productsList, R.layout.lista_personas_atendieron, new String[]{TAG_PID_ENCUESTA,
                TAG_PID_PLAZA_COBRO, TAG_NOMBRE_ENCUESTADOR},
                new int[]{R.id.LIid, R.id.nombre, R.id.puesto});


        listaEncuestas.setAdapter(adapter3);

        //your grid view as child
        listaEncuestas.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        listaEncuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int LIid, long id) {
                LidFinalPersona = LIid;

                String test[] = {};
                test = listaEncuestas.getItemAtPosition(LidFinalPersona).toString().replace("{", "").replace("}", "").split(",");

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

        Bundle bundle = new Bundle();
        bundle.putString(Constantes.campos.nombreAutopista, nomAutopistaSeleccionada);
        bundle.putString("idEncuestas", idEncuestas);
        startActivity(new Intent(ListaEncuestasActivity.this, EncuestasDetalleActivity.class).
                putExtras(bundle));
    }

    private void regresaMainManu() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
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
