package com.ideal.encuestacliente.vistas;

import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.SharedPreferencesManager;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.controladores.ControladorDatosCliente;
import com.ideal.encuestacliente.model.Esatis_Autopista_Pregunta;
import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.model.Esatis_Catalogo_Preguntas;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Encuesta;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.model.Esatis_Frecuencia;
import com.ideal.encuestacliente.model.Esatis_Usuario;
import com.ideal.encuestacliente.model.Esatis_Valoracion;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PreguntasActivity extends AppCompatActivity {

    private ConstraintLayout rootMain;
    EditText edPreguntas;
    TextInputEditText edSugerencias;
    RadioGroup radioGruopRespuestas;
    RadioButton radioRespuestaUno, radioRespuestaDos, radioRespuestaTres,
            radioRespuestaCuatro, radioRespuestaCinco, radioRespuestaSeis;
    int idEncuestas, idAutopist;

    private String[] preguntas;

    private int[] idPreguntas;

    private int[] idPreguntasAutopista;

   /* private String[] preguntasTunel = {
            "1. ¿Cómo considera las condiciones de la superficie de rodamiento?",
            "2. ¿Cómo considera la señalización durante el recorrido?",
            "3.- ¿En general como considera la limpieza de la autopista?",
            "4. ¿Cómo valora las conciones de iluminación y páneles informativos dentro del túnel?",
            "5.- ¿Cómo considera el tiempo en recorrer la autopista?",
            "6.- ¿Cómo valora el tiempo para ser atendido en la plaza de cobro?",
            "7.- ¿Cómo considera la atención recibida por parte del personal?",
            "8.- ¿Cómo valora las opciones de pago y tarifas del recorrido?",
            "9.- ¿Cómo valora el servicio de telepeaje?",
            "10. De manera general, ¿Cómo considera los servicios prestados por la autopista?",
            "SUGERENCIAS / COMENTARIOS ADICIONALES"
    };

    private int[] idPreguntasTunel = {
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11
    };

    private String[] preguntasUrbana = {
            "1. ¿Cómo considera las condiciones de la superficie de rodamiento?",
            "2. ¿Cómo considera la señalización durante el recorrido?",
            "3.- ¿En general como considera la limpieza de la autopista?",
            "4. ¿En general como considera las conciones de los páneles informativos?",
            "5.- ¿Cómo considera el tiempo en recorrer la autopista?",
            "6.- ¿Cómo valora el tiempo para ser atendido en la entrada?",
            "7.- ¿Cómo considera la atención recibida por parte del personal?",
            "8.- ¿Cómo valora las tarifas?",
            "9.- ¿Cómo valora el servicio de lectura de Tag?",
            "10. De manera general, ¿Cómo considera los servicios prestados por la autopista?",
            "SUGERENCIAS / COMENTARIOS ADICIONALES"
    };

    private int[] idPreguntasUrbana = {
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11
    };*/

    private String[] respuestas;

    private int[] idRespuestas;

    int contadorPreguntas = 0;
    private FloatingActionButton btnContinuarEncuesta, btnRegresar, btnCancelaReporte, btnRegresaPregunta, btnAvanzaPregunta;
    int numRespuesta = 99;
    String usuarioCreacion, usuarioModificacion, usuarioEliminacion, fechaCreacion, fechaModificacion, fechaEliminacion;
    int numPreguntaCuestionario = 1;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton btnTerminarEncuesta;
    private String nombreAutopista;
    private TextView txtAut;
    private LinearLayout linearSugurencias;
    private Context contesto;

    private SharedPreferencesManager preferences;

    private TextView txtUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        rootMain = findViewById(R.id.rootMain);
        setMarginInsets(rootMain);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nombreAutopista = getIntent().getExtras().getString(Constantes.campos.nombreAutopista);
        idEncuestas = getIntent().getExtras().getInt("idEncuestas");
        idAutopist = getIntent().getExtras().getInt("idAutopista");
        Log.e("idAutopist", idAutopist + "");
        Log.e("contadorPreguntas", contadorPreguntas + "");
        contesto = this;


        initControls();
        actionControls();
        consultaCatalogos();


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

    private void consultaCatalogos() {

        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        List listaValoraciones = con.consultaValoracion();
        respuestas = new String[listaValoraciones.size()];
        idRespuestas = new int[listaValoraciones.size()];

        int contador = 0;

        for (Object datos : listaValoraciones) {
            Esatis_Valoracion elementos = (Esatis_Valoracion) datos;
            respuestas[contador] = elementos.getDescValoraciones();
            idRespuestas[contador] = elementos.getIdValoracion();
            contador = contador + 1;
            usuarioCreacion = elementos.getUsuarioCreacion();
            usuarioModificacion = elementos.getUsuarioModificacion();
            usuarioEliminacion = elementos.getUsuarioEliminacion();
            fechaCreacion = elementos.getFechaCreacion();
            fechaModificacion = elementos.getFechaModificacion();
            fechaEliminacion = elementos.getFechaEliminacion();
        }

        List listaPreguntasPorTipoAutopistaAll = con.consultaPreguntasPorIdAutopistaAll();
        List listaPreguntasPorTipoAutopista = con.consultaPreguntasPorIdAutopista(idAutopist);
        idPreguntasAutopista = new int[listaPreguntasPorTipoAutopista.size()];
        preguntas = new String[listaPreguntasPorTipoAutopista.size()];
        idPreguntas = new int[listaPreguntasPorTipoAutopista.size()];
        contador = 0;

        for (Object datos : listaPreguntasPorTipoAutopista) {
            Esatis_Autopista_Pregunta elementos = (Esatis_Autopista_Pregunta) datos;
            idPreguntasAutopista[contador] = elementos.getIdPreguntaCatalogo();
            contador = contador + 1;
        }

        contador = 0;

        for (int i = 0; i < idPreguntasAutopista.length; i++) {

            List listaPreguntas = con.consultaDescPreguntasPorIdAutopista(idPreguntasAutopista[i]);

            for (Object datos : listaPreguntas) {
                Esatis_Catalogo_Preguntas elementos = (Esatis_Catalogo_Preguntas) datos;
                Log.e("pregunta_", elementos.getDescPregunta());
                preguntas[contador] = elementos.getDescPregunta();
                idPreguntas[contador] = elementos.getIdPreguntaCatalogo();
                contador = contador + 1;
            }
        }


        iniciaCuestionario();


    }

    private void initControls() {
        edPreguntas = (EditText) findViewById(R.id.edPreguntas);
        edPreguntas.setFocusable(false);
        edSugerencias = (TextInputEditText) findViewById(R.id.edSugerencias);
        radioGruopRespuestas = (RadioGroup) findViewById(R.id.radioGruopRespuestas);
        radioRespuestaUno = (RadioButton) findViewById(R.id.radioRespuestaUno);
        radioRespuestaDos = (RadioButton) findViewById(R.id.radioRespuestaDos);
        radioRespuestaTres = (RadioButton) findViewById(R.id.radioRespuestaTres);
        radioRespuestaCuatro = (RadioButton) findViewById(R.id.radioRespuestaCuatro);
        radioRespuestaCinco = (RadioButton) findViewById(R.id.radioRespuestaCinco);
        radioRespuestaSeis = (RadioButton) findViewById(R.id.radioRespuestaSeis);
        //btnContinuarEncuesta = (FloatingActionButton) findViewById(R.id.btnContinuarEncuesta);
        btnRegresar = (FloatingActionButton) findViewById(R.id.btnRegresar);
        btnCancelaReporte = (FloatingActionButton) findViewById(R.id.btnCancelaReporte);
        btnRegresaPregunta = (FloatingActionButton) findViewById(R.id.btnRegresaPregunta);
        btnRegresaPregunta.setVisibility(View.GONE);
        btnAvanzaPregunta = (FloatingActionButton) findViewById(R.id.btnAvanzaPregunta);

        txtAut = (TextView) findViewById(R.id.txtAut);
        txtAut.setText(nombreAutopista);

        btnTerminarEncuesta = findViewById(R.id.btnTerminarEncuesta);
        bottomAppBar = findViewById(R.id.buttonAppBarPersonas);
        setSupportActionBar(bottomAppBar);

        linearSugurencias = (LinearLayout) findViewById(R.id.linearSugurencias);
        showUserData();
    }

    private void iniciaCuestionario() {

        Log.e("contadorPreguntas_", contadorPreguntas + "");

        if (contadorPreguntas == 10) {
            linearSugurencias.setVisibility(View.VISIBLE);
            edSugerencias.setVisibility(View.VISIBLE);
            radioGruopRespuestas.setVisibility(View.GONE);
            radioRespuestaUno.setVisibility(View.GONE);
            radioRespuestaDos.setVisibility(View.GONE);
            radioRespuestaTres.setVisibility(View.GONE);
            radioRespuestaCuatro.setVisibility(View.GONE);
            radioRespuestaCinco.setVisibility(View.GONE);
            radioRespuestaSeis.setVisibility(View.GONE);
            edSugerencias.requestFocus();
            btnAvanzaPregunta.setImageResource(R.drawable.ic_save_black_24dp);
            btnTerminarEncuesta.setImageResource(R.drawable.ic_save_black_24dp);
        } else {
            btnAvanzaPregunta.setImageResource(R.drawable.ic_navigate_next_black_24dp);
            btnTerminarEncuesta.setImageResource(R.drawable.ic_navigate_next_black_24dp);
            linearSugurencias.setVisibility(View.GONE);
            edSugerencias.setVisibility(View.GONE);
        }


        if (contadorPreguntas < 10) {
            linearSugurencias.setVisibility(View.GONE);
            edSugerencias.setVisibility(View.GONE);
        }

        if (idAutopist == 14 || idAutopist == 15) {
            if (preguntas[contadorPreguntas].trim().equalsIgnoreCase("¿Cómo considera la atención recibida por parte del personal?")
                    || preguntas[contadorPreguntas].trim().equalsIgnoreCase("¿Cómo valora el servicio de telepeaje?")) {
                radioRespuestaSeis.setVisibility(View.VISIBLE);
            } else {
                radioRespuestaSeis.setVisibility(View.INVISIBLE);
            }
            edPreguntas.setText(numPreguntaCuestionario + ".-" + preguntas[contadorPreguntas]);
        } else if (idAutopist == 10 || idAutopist == 16) {
            if (preguntas[contadorPreguntas].trim().equalsIgnoreCase("¿Cómo considera la atención recibida por parte del personal?")
                    || preguntas[contadorPreguntas].trim().equalsIgnoreCase("¿Cómo valora el servicio de lectura de Tag?")) {
                radioRespuestaSeis.setVisibility(View.VISIBLE);
            } else {
                radioRespuestaSeis.setVisibility(View.INVISIBLE);
            }
            edPreguntas.setText(numPreguntaCuestionario + ".-" + preguntas[contadorPreguntas]);
        } else {
            edPreguntas.setText(numPreguntaCuestionario + ".-" + preguntas[contadorPreguntas]);
            if (preguntas[contadorPreguntas].trim().equalsIgnoreCase("¿En general como considera el servicio de sanitarios?")
                    || preguntas[contadorPreguntas].trim().equalsIgnoreCase("¿Cómo considera la atención recibida por parte del personal?")
                    || preguntas[contadorPreguntas].trim().equalsIgnoreCase("¿Cómo valora el servicio de telepeaje?")) {
                radioRespuestaSeis.setVisibility(View.VISIBLE);
            } else {
                radioRespuestaSeis.setVisibility(View.INVISIBLE);
            }
        }

        radioRespuestaUno.setText(respuestas[0]);
        radioRespuestaDos.setText(respuestas[1]);
        radioRespuestaTres.setText(respuestas[2]);
        radioRespuestaCuatro.setText(respuestas[3]);
        radioRespuestaCinco.setText(respuestas[4]);
        radioRespuestaSeis.setText(respuestas[5]);

        radioRespuestaUno.setChecked(false);
        radioRespuestaDos.setChecked(false);
        radioRespuestaTres.setChecked(false);
        radioRespuestaCuatro.setChecked(false);
        radioRespuestaCinco.setChecked(false);
        radioRespuestaSeis.setChecked(false);
        radioGruopRespuestas.clearCheck();

        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        Esatis_Encuesta datos = new Esatis_Encuesta();
        List listadoPreguntasIdEncuesta = con.consultaPreguntasClientePorIdEncuesta(idEncuestas);

        int idPreguntasCons = 0;
        int idValoracionCons = 0;
        boolean inserta = true;
        for (Object preguntas : listadoPreguntasIdEncuesta) {
            Esatis_Encuesta preg = (Esatis_Encuesta) preguntas;
            idPreguntasCons = preg.idPreguntaCatalogo;
            if (idPreguntasCons == idPreguntas[contadorPreguntas]) {
                idValoracionCons = preg.idValoracion;
            }
        }

        if (idValoracionCons != 0) {
            if (idValoracionCons == 1) {
                radioRespuestaUno.setChecked(true);
            }
            if (idValoracionCons == 2) {
                radioRespuestaDos.setChecked(true);
            }
            if (idValoracionCons == 3) {
                radioRespuestaTres.setChecked(true);
            }
            if (idValoracionCons == 4) {
                radioRespuestaCuatro.setChecked(true);
            }
            if (idValoracionCons == 5) {
                radioRespuestaCinco.setChecked(true);
            }
            if (idValoracionCons == 6) {
                radioRespuestaSeis.setChecked(true);
            }
        }


    }

    private void actionControls() {

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresaDatosClientes();
            }
        });

        /*btnContinuarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numRespuesta = 99;
                guardaPregunta();
            }
        });*/

        btnRegresaPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegresaPregunta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        contadorPreguntas = contadorPreguntas - 1;
                        numPreguntaCuestionario = numPreguntaCuestionario - 1;
                        if (contadorPreguntas <= 0) {
                            contadorPreguntas = 0;
                            numPreguntaCuestionario = 1;
                        }

                        if (numPreguntaCuestionario <= 0) {
                            numPreguntaCuestionario = 1;
                        }

                        if (numPreguntaCuestionario == 1) {
                            btnRegresaPregunta.setVisibility(View.GONE);

                        } else {
                            btnRegresaPregunta.setVisibility(View.VISIBLE);
                        }

                        if (contadorPreguntas <= 10 || numPreguntaCuestionario <= 10) {
                            edPreguntas.setVisibility(View.VISIBLE);
                            radioGruopRespuestas.setVisibility(View.VISIBLE);
                            radioRespuestaUno.setVisibility(View.VISIBLE);
                            radioRespuestaDos.setVisibility(View.VISIBLE);
                            radioRespuestaTres.setVisibility(View.VISIBLE);
                            radioRespuestaCuatro.setVisibility(View.VISIBLE);
                            radioRespuestaCinco.setVisibility(View.VISIBLE);
                            radioRespuestaSeis.setVisibility(View.VISIBLE);
                            edSugerencias.setVisibility(View.GONE);
                            iniciaCuestionario();
                        }


                    }
                });
            }
        });

        btnAvanzaPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (contadorPreguntas >= 1) {
                    btnRegresaPregunta.setVisibility(View.VISIBLE);
                    if (item != null) {
                        item.setVisible(true);
                    }
                }
                if (contadorPreguntas == 10) {
                    showAlertDialogGuardar();
                } else {
                    guardaPregunta();
                }
            }
        });

        btnTerminarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("contadorPreguntas", contadorPreguntas + "");
                Log.e("numPreguntaCuestionario", numPreguntaCuestionario + "");

                if (contadorPreguntas >= 1) {
                    btnRegresaPregunta.setVisibility(View.VISIBLE);
                    if (item != null) {
                        item.setVisible(true);
                    }
                }

                if (numPreguntaCuestionario >= 1) {
                    if (item != null) {
                        item.setVisible(true);
                    }
                }

                if (contadorPreguntas == 10) {
                    showAlertDialogGuardar();
                } else {
                    guardaPregunta();
                }
            }
        });

        btnCancelaReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogEliminar();
            }
        });


        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(200);
        filterArray[1] = new InputFilter.AllCaps();

        edSugerencias.setFilters(filterArray);
        radioRespuestaSeis.setVisibility(View.INVISIBLE);

    }

    private void validaRespuestas() {

        numRespuesta = 99;

        if (radioRespuestaUno.isChecked()) {
            numRespuesta = idRespuestas[0];
        }
        if (radioRespuestaDos.isChecked()) {
            numRespuesta = idRespuestas[1];
        }
        if (radioRespuestaTres.isChecked()) {
            numRespuesta = idRespuestas[2];
        }
        if (radioRespuestaCuatro.isChecked()) {
            numRespuesta = idRespuestas[3];
        }
        if (radioRespuestaCinco.isChecked()) {
            numRespuesta = idRespuestas[4];
        }
        if (radioRespuestaSeis.isChecked()) {
            numRespuesta = idRespuestas[5];
        }

        if (numPreguntaCuestionario == 11) {
            numRespuesta = 1;
        }

    }

    private void finalizaCuestionario() {

       /* Utils.Toast(PreguntasActivity.this, getResources().getString(R.string.cuestionarioFinalizado));

        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        List listaPreguntas = con.consultaPreguntasClientePorIdEncuesta(idAutopist);

        for(Object infoPreg: listaPreguntas){
            Esatis_Encuesta detalle = (Esatis_Encuesta) infoPreg;
            Log.e("pregunta", detalle.idPreguntaCatalogo+"");
            Log.e("valoracion", detalle.idValoracion+"");
        }*/

        Intent linsertar = new Intent(this, MainActivity.class);
        startActivity(linsertar);
    }

    private void guardaPregunta() {
        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        Esatis_Encuesta datos = new Esatis_Encuesta();
        List listadoPreguntasIdEncuesta = con.consultaPreguntasClientePorIdEncuesta(idEncuestas);

        int idPreguntasCons = 0;
        int idPreguntaUpdate = 0;
        boolean inserta = true;
        for (Object preguntas : listadoPreguntasIdEncuesta) {
            Esatis_Encuesta preg = (Esatis_Encuesta) preguntas;
            idPreguntasCons = preg.idPreguntaCatalogo;
            if (idPreguntasCons == idPreguntas[contadorPreguntas]) {
                idPreguntaUpdate = preg.idPregunta;
                inserta = false;
            }
        }

        List listaUsuaior = con.consultaUsuario();

        for (Object datosUsuario : listaUsuaior) {
            Esatis_Usuario elementos = (Esatis_Usuario) datosUsuario;
            usuarioCreacion = elementos.getNombreUsuario();
            usuarioModificacion = "";
            usuarioEliminacion = "";
        }

        fechaModificacion = "";
        fechaEliminacion = "";

        validaRespuestas();

        if (numRespuesta != 99) {

            if (inserta) {
                Log.e("idPreguntas_inssrta", idPreguntas[contadorPreguntas] + "");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(new Date());

                int idPreguntaSync = ControladorDatosCliente.obtieneIdUltimaPregunta(contesto) + 1;
                fechaModificacion = idEncuestas + "_" + idPreguntaSync + "_" + currentDate;

                datos.setIdEncuesta(idEncuestas);
                datos.setIdPreguntaCatalogo(idPreguntas[contadorPreguntas]);
                datos.setIdValoracion(numRespuesta);
                //datos.setObservaciones(edSugerencias.getText().toString());
                datos.setUsuarioCreacion(usuarioCreacion);
                datos.setUsuarioModificacion(usuarioModificacion);
                datos.setUsuarioEliminacion(usuarioEliminacion);
                datos.setFechaCreacion(currentDate);
                datos.setFechaModificacion(fechaModificacion);
                datos.setFechaEliminacion(fechaEliminacion);

                if (con.insertaPreguntas(datos)) {

                    contadorPreguntas = contadorPreguntas + 1;
                    numPreguntaCuestionario = numPreguntaCuestionario + 1;
                    if (contadorPreguntas < 11) {
                        iniciaCuestionario();
                        Utils.Toast(PreguntasActivity.this, getResources().getString(R.string.guardaExitosoDatosCliente));
                    } else if (contadorPreguntas == 11) {
                        Esatis_Cliente cliente = new Esatis_Cliente();
                        cliente.setIdEncuesta(idEncuestas);
                        cliente.setIdIndicadorSinc(2);
                        cliente.setObservaciones(edSugerencias.getText().toString());
                        if (con.actualizaIndicadorSincronizacion(cliente)) {
                            Log.e("se guardo el indicador", "setIdIndicadorSinc");
                        } else {
                            Log.e("No se guardo el ind", "setIdIndicadorSinc");
                        }

                        //showAlertDialogGuardadoExitoso();
                        finalizaCuestionario();
                    }

                } else {
                    Utils.Toast(PreguntasActivity.this, getResources().getString(R.string.noGuardaExitosoDatosCliente));
                }
            } else {
                Log.e("idPreguntas_actualiza", idPreguntaUpdate + "");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(new Date());

                fechaModificacion = idEncuestas + "_" + idPreguntaUpdate + "_" + currentDate;

                datos.setIdPregunta(idPreguntaUpdate);
                datos.setIdEncuesta(idEncuestas);
                datos.setIdPreguntaCatalogo(idPreguntas[contadorPreguntas]);
                datos.setIdValoracion(numRespuesta);
                //datos.setObservaciones(edSugerencias.getText().toString());
                datos.setUsuarioCreacion(usuarioCreacion);
                datos.setUsuarioModificacion(usuarioModificacion);
                datos.setUsuarioEliminacion(usuarioEliminacion);
                datos.setFechaCreacion(currentDate);
                datos.setFechaModificacion(fechaModificacion);
                datos.setFechaEliminacion(fechaEliminacion);

                if (con.actualizaPreguntas(datos)) {
                    Utils.Toast(PreguntasActivity.this, getResources().getString(R.string.actualizacionExitosoDatosCliente));
                    contadorPreguntas = contadorPreguntas + 1;
                    numPreguntaCuestionario = numPreguntaCuestionario + 1;
                    if (contadorPreguntas < 11) {
                        iniciaCuestionario();
                    } else if (contadorPreguntas == 11) {
                        Esatis_Cliente cliente = new Esatis_Cliente();
                        cliente.setIdEncuesta(idEncuestas);
                        cliente.setIdIndicadorSinc(2);
                        cliente.setObservaciones(edSugerencias.getText().toString());
                        if (con.actualizaIndicadorSincronizacion(cliente)) {
                            Log.e("se guardo el indicador", "setIdIndicadorSinc");
                        } else {
                            Log.e("No se guardo el ind", "setIdIndicadorSinc");
                        }
                        showAlertDialogGuardadoExitoso();

                    }

                } else {
                    Utils.Toast(PreguntasActivity.this, getResources().getString(R.string.noActualizaExitosoDatosCliente));
                }
            }


        } else {
            Utils.Toast(PreguntasActivity.this, getResources().getString(R.string.seleccionaRespuesta));
        }

    }

    private void showAlertDialogEliminar() {
        AlertDialog alertDialog = new AlertDialog.Builder(PreguntasActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(R.string.eliminar);
        alertDialog.setMessage(getString(R.string.mensajeEliminar));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.mensajeAceptar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        cancelarEncuesta();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.mensajeContinuar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    private void showAlertDialogGuardadoExitoso() {
        AlertDialog alertDialog = new AlertDialog.Builder(PreguntasActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(R.string.guardado);
        alertDialog.setMessage(getString(R.string.mensajeGuardadoExitoso));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.mensajeAceptar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        finalizaCuestionario();

                    }
                });

        alertDialog.show();
    }

    private void cancelarEncuesta() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void regresaDatosClientes() {
        Bundle bundle = new Bundle();
        bundle.putInt("idEncuestas", idEncuestas);
        startActivity(new Intent(PreguntasActivity.this, DatosCliente.class).putExtras(bundle));
    }

    @Override
    public void onBackPressed() {
    }

    Menu myMenu;
    MenuItem item;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_preguntas, menu);

        myMenu = menu;
        item = menu.findItem(R.id.menuPreguntasPregAnte);
        if (item != null) {
            item.setVisible(false);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuPreguntasPregAnte:

                contadorPreguntas = contadorPreguntas - 1;
                numPreguntaCuestionario = numPreguntaCuestionario - 1;
                if (contadorPreguntas <= 0) {
                    contadorPreguntas = 0;
                    numPreguntaCuestionario = 1;
                }

                if (numPreguntaCuestionario <= 0) {
                    numPreguntaCuestionario = 1;
                }

                if (numPreguntaCuestionario == 1) {
                    btnRegresaPregunta.setVisibility(View.GONE);
                    if (item != null) {
                        item.setVisible(false);
                    }
                } else {
                    btnRegresaPregunta.setVisibility(View.VISIBLE);
                    if (item != null) {
                        item.setVisible(true);
                    }
                }

                if (contadorPreguntas <= 10 || numPreguntaCuestionario <= 10) {
                    edPreguntas.setVisibility(View.VISIBLE);
                    radioGruopRespuestas.setVisibility(View.VISIBLE);
                    radioRespuestaUno.setVisibility(View.VISIBLE);
                    radioRespuestaDos.setVisibility(View.VISIBLE);
                    radioRespuestaTres.setVisibility(View.VISIBLE);
                    radioRespuestaCuatro.setVisibility(View.VISIBLE);
                    radioRespuestaCinco.setVisibility(View.VISIBLE);
                    radioRespuestaSeis.setVisibility(View.VISIBLE);
                    edSugerencias.setVisibility(View.GONE);
                    iniciaCuestionario();
                }

                break;
            case R.id.menuPreguntasMenu:
                showAlertDialogEliminar();
                break;
            case R.id.menuPreguntasCancelar:
                showAlertDialogEliminar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAlertDialogMenu() {
        AlertDialog alertDialog = new AlertDialog.Builder(PreguntasActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(R.string.menu);
        alertDialog.setMessage(getString(R.string.salirEncuesta));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.mensajeAceptar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        regresaMainManu();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.mensajeContinuar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    private void regresaMainManu() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void showAlertDialogGuardar() {
        AlertDialog alertDialog = new AlertDialog.Builder(PreguntasActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(R.string.guardar);
        alertDialog.setMessage(getString(R.string.preguntaGuardar));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.mensajeAceptar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        guardaPregunta();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.mensajeContinuar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

}
