package com.ideal.encuestacliente.vistas;

import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.SharedPreferencesManager;
import com.ideal.encuestacliente.configuracion.SpinnerControl;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.controladores.ControladorDatosCliente;
import com.ideal.encuestacliente.model.Esatis_Autopista_Pregunta;
import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.model.Esatis_Catalogo_Preguntas;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Encuesta;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.model.Esatis_Frecuencia;
import com.ideal.encuestacliente.model.Esatis_Plaza_Cobro;
import com.ideal.encuestacliente.model.Esatis_Ubicaciones;
import com.ideal.encuestacliente.model.Esatis_Valoracion;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import java.util.ArrayList;
import java.util.List;

public class EncuestasDetalleActivity extends AppCompatActivity {

    private ConstraintLayout rootMain;
    String idEncuestas;
    Button btnMuestraDatosEncuesta, btnMuestraEncuesta;
    private ConstraintLayout datosCliente, preguntasCliente;
    Spinner spinnerAutopistas, spinnerPlazaCobro, spinnerFrecuencia, spinnerFormaPago, spinnerUbicacion;
    private LinearLayout linearUbicacion;
    List listaEncuestasConsulta, listaPreguntasCliente;
    private EditText edEncuestador, edOrigen, edDestino, edFecha, edUbicacion;
    private String[] autopistas;
    private FloatingActionButton btnRegresarBack, btnAvanzaPregunta, btnRegresaPregunta;
    private EditText edPreguntas, edSugerencias;
    private RadioGroup radioGruopRespuestas;
    private RadioButton radioRespuestaUno, radioRespuestaDos, radioRespuestaTres,
            radioRespuestaCuatro, radioRespuestaCinco, radioRespuestaSeis;

    private String[] plazaCobro = {
            "Selecciona"
    };

    private int[] idPlazaCobro = {
            0
    };

    private String[] ubicacionEncuesta;
    private int[] idUbicacionEncuesta;

    private String[] frecuenciaUso;

    private int[] idFrecuenciaUso;

    private String[] formaPago;

    private int[] idFormaPago;

    private String[] preguntas;

    private int[] idPreguntas;

    private int[] idPreguntasAutopista;

    /*private String[] preguntasTunel = {
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

    private int[] preguntasPorId;
    private int[] valoracionPorId;
    private String[] observacionesPorId;
    private int preguntaSiguiente = 0;

    String nomPregunta = "";
    String nomValoracion = "";
    int idAutopista = 0;
    BaseDaoEncuesta con = new BaseDaoEncuesta(this);
    int numPreguntaCuestionario = 1;
    String observacionesPorCliente;
    private BottomAppBar bottomAppBar;
    private TextView txtLatitud, txtLongitud, txtAltitud;
    private String nombreAutopista;
    private TextView txtAut;

    private SharedPreferencesManager preferences;

    private TextView txtUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuestas_detalle);
        rootMain = findViewById(R.id.rootMain);
        setMarginInsets(rootMain);

        nombreAutopista = getIntent().getExtras().getString(Constantes.campos.nombreAutopista);
        idEncuestas = getIntent().getExtras().getString("idEncuestas");
        Log.e("idEncuestas", idEncuestas);

        intControls();
        actionsControls();
        consultasCatalogos();
        consultaAutopista();

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

    private void intControls() {
        bottomAppBar = findViewById(R.id.buttonAppBarPersonas);
        setSupportActionBar(bottomAppBar);
        btnMuestraDatosEncuesta = (Button) findViewById(R.id.btnMuestraDatosEncuesta);
        btnMuestraDatosEncuesta.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pluss, 0);

        btnMuestraEncuesta = (Button) findViewById(R.id.btnMuestraEncuesta);
        btnMuestraEncuesta.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pluss, 0);

        btnRegresarBack = findViewById(R.id.btnRegresarBack);

        datosCliente = (ConstraintLayout) findViewById(R.id.datosCliente);
        datosCliente.setVisibility(View.GONE);

        preguntasCliente = (ConstraintLayout) findViewById(R.id.preguntasCliente);
        preguntasCliente.setVisibility(View.GONE);

        txtAut = (TextView) findViewById(R.id.txtAut);
        txtAut.setText(nombreAutopista);

        showUserData();
    }

    private void actionsControls() {

        btnMuestraDatosEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muestraFormaSiniestros();
                consultaFormDatosCliente();
            }
        });
        btnMuestraEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muestraFormaPreguntas();
                consultaFormPreguntasCliente();
            }
        });


        btnRegresarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aqui", "sale");
                regresaListaEncuestas();

            }
        });

    }


    private void muestraFormaSiniestros() {

        if (datosCliente.isShown()) {
            datosCliente.setVisibility(View.GONE);
            btnMuestraDatosEncuesta.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pluss, 0);
        } else if (!datosCliente.isShown()) {
            datosCliente.setVisibility(View.VISIBLE);
            btnMuestraDatosEncuesta.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.minus, 0);
        }

    }

    private void muestraFormaPreguntas() {

        if (preguntasCliente.isShown()) {
            preguntasCliente.setVisibility(View.GONE);
            btnMuestraEncuesta.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pluss, 0);
        } else if (!preguntasCliente.isShown()) {
            preguntasCliente.setVisibility(View.VISIBLE);
            btnMuestraEncuesta.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.minus, 0);
        }

    }

    private void consultaFormDatosCliente() {

        if (datosCliente.isShown()) {

            listaEncuestasConsulta = con.consultaDatosClientePorIdEncuesta(Integer.parseInt(idEncuestas));

            View datoCliente = findViewById(R.id.datosCliente);
            spinnerAutopistas = datoCliente.findViewById(R.id.spinnerAutopistas);
            spinnerPlazaCobro = datoCliente.findViewById(R.id.spinnerPlazaCobro);
            spinnerFrecuencia = datoCliente.findViewById(R.id.spinnerFrecuencia);
            spinnerFormaPago = datoCliente.findViewById(R.id.spinnerFormaPago);
            spinnerUbicacion = datoCliente.findViewById(R.id.spinnerUbicacion);
            edEncuestador = datoCliente.findViewById(R.id.edEncuestador);
            edOrigen = datoCliente.findViewById(R.id.edOrigen);
            edDestino = datoCliente.findViewById(R.id.edDestino);
            edFecha = datoCliente.findViewById(R.id.edFecha);
            edUbicacion = datoCliente.findViewById(R.id.edUbicacion);

            linearUbicacion = datoCliente.findViewById(R.id.linearUbicacion);

            txtLatitud = datoCliente.findViewById(R.id.txtLatitud);
            txtLongitud = datoCliente.findViewById(R.id.txtLongitud);
            txtAltitud = datoCliente.findViewById(R.id.txtAltitud);

            Utils.disableEditText(edEncuestador);
            Utils.disableEditText(edOrigen);
            Utils.disableEditText(edDestino);
            Utils.disableEditText(edFecha);
            Utils.disableEditText(edUbicacion);

            Utils.disableSpiner(spinnerAutopistas);
            Utils.disableSpiner(spinnerPlazaCobro);
            Utils.disableSpiner(spinnerUbicacion);
            Utils.disableSpiner(spinnerFrecuencia);
            Utils.disableSpiner(spinnerFormaPago);

            int idAutopista = 0;
            int idPlazaCobros = 0;
            int idUbicacion = 0;
            String nomUbicacion = "";
            String nomPlazaCobro = "";
            String nomFrecuenciaUso = "";
            int numFrecUso = 0;
            String nombreFormaPago = "";
            int numFormaPago = 0;
            String ubicacionDesc = "";

            for (Object datos : listaEncuestasConsulta) {
                Esatis_Cliente info = (Esatis_Cliente) datos;
                edEncuestador.setText(info.encuestador);
                edOrigen.setText(info.origen);
                edDestino.setText(info.destino);
                idAutopista = info.idAutopista;
                idPlazaCobros = info.idPlazaCobro;
                idUbicacion = info.idUbicacion;
                ubicacionDesc = info.UbicacionDesc;
                numFrecUso = info.idFrecuenciaUso;
                edFecha.setText(info.fecha);
                numFormaPago = info.idFormaPago;

                txtLatitud.setText("LATITUD: " + info.latitud + "");
                txtLongitud.setText("LONGITUD: " + info.longitud + "");
                txtAltitud.setText("ALTITUD: " + info.altitud + "");
            }


            edUbicacion.setText(ubicacionDesc);

            List listaFrecuencia = con.consultaFrecuencia();
            frecuenciaUso = new String[listaFrecuencia.size()];
            idFrecuenciaUso = new int[listaFrecuencia.size()];
            int contador = 0;

            for (Object datos : listaFrecuencia) {
                Esatis_Frecuencia elementos = (Esatis_Frecuencia) datos;
                idFrecuenciaUso[contador] = elementos.getIdFrecuenciaUso();
                frecuenciaUso[contador] = elementos.getDescFrecuencia();
                contador = contador + 1;
            }

            for (int i = 0; i < idFrecuenciaUso.length; i++) {
                if (numFrecUso == idFrecuenciaUso[i]) {
                    nomFrecuenciaUso = frecuenciaUso[i];
                }
            }

            List listaFormaPago = con.consultaFormaPago();
            formaPago = new String[listaFormaPago.size()];
            idFormaPago = new int[listaFormaPago.size()];
            contador = 0;

            for (Object datos : listaFormaPago) {
                Esatis_Forma_Pago elementos = (Esatis_Forma_Pago) datos;
                idFormaPago[contador] = elementos.getIdFormaPago();
                formaPago[contador] = elementos.getFormaPago();
                contador = contador + 1;
            }


            for (int i = 0; i < idFormaPago.length; i++) {
                if (numFormaPago == idFormaPago[i]) {
                    nombreFormaPago = formaPago[i];
                }
            }

            Spinner(spinnerAutopistas, autopistas);
            ArrayAdapter<String> adapterDetalleHor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, autopistas);
            spinnerAutopistas.setSelection(adapterDetalleHor.getPosition(ControladorDatosCliente.obtieneNombreAutopista(idAutopista, EncuestasDetalleActivity.this)));

            int idAutop = 0;
            List listaAutopistas = con.consultaIdAutopista(spinnerAutopistas.getSelectedItem().toString());
            for (Object datos : listaAutopistas) {
                Esatis_Autopistas elementos = (Esatis_Autopistas) datos;
                idAutop = elementos.getIdAutopistaSQL();
            }

            List listaPlazaCobro = con.consultaPlazaCobroPorIdAutopita(idAutop);
            plazaCobro = new String[listaPlazaCobro.size() + 1];
            idPlazaCobro = new int[listaPlazaCobro.size() + 1];
            plazaCobro[0] = "Selecciona";
            idPlazaCobro[0] = 0;
            contador = 1;

            for (Object datos : listaPlazaCobro) {
                Esatis_Plaza_Cobro elementos = (Esatis_Plaza_Cobro) datos;
                idPlazaCobro[contador] = elementos.getIdPlazaCobro();
                Log.e("idPlazaCobro[contador]", idPlazaCobro[contador] + "");
                plazaCobro[contador] = elementos.getPlazaCobro();
                contador = contador + 1;
            }

            for (int i = 0; i < idPlazaCobro.length; i++) {
                if (idPlazaCobros == idPlazaCobro[i]) {
                    nomPlazaCobro = plazaCobro[i];
                }
            }
            //////////////////////////

            List listaUbicacion = con.consultaUbicacion();
            ubicacionEncuesta = new String[listaUbicacion.size() + 1];
            ubicacionEncuesta[0] = "Selecciona";
            idUbicacionEncuesta = new int[listaUbicacion.size() + 1];
            idUbicacionEncuesta[0] = 0;
            contador = 1;

            for (Object datos : listaUbicacion) {
                Esatis_Ubicaciones elementos = (Esatis_Ubicaciones) datos;
                idUbicacionEncuesta[contador] = elementos.getIdUbicacion();
                ubicacionEncuesta[contador] = elementos.getUbicacion();
                contador = contador + 1;
            }

            for (int i = 0; i < idUbicacionEncuesta.length; i++) {
                if (idUbicacion == idUbicacionEncuesta[i]) {
                    nomUbicacion = ubicacionEncuesta[i];
                }
            }

            Spinner(spinnerUbicacion, ubicacionEncuesta);
            ArrayAdapter<String> adapterDetalleUbicacion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ubicacionEncuesta);
            spinnerUbicacion.setSelection(adapterDetalleUbicacion.getPosition(nomUbicacion));

            if (nomUbicacion.equalsIgnoreCase("Otro")) {
                linearUbicacion.setVisibility(View.VISIBLE);
            }

            Spinner(spinnerPlazaCobro, plazaCobro);
            ArrayAdapter<String> adapterDetallePlaza = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plazaCobro);
            spinnerPlazaCobro.setSelection(adapterDetallePlaza.getPosition(nomPlazaCobro));

            Spinner(spinnerFrecuencia, frecuenciaUso);
            ArrayAdapter<String> adapterDetalleFrecuencia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, frecuenciaUso);
            spinnerFrecuencia.setSelection(adapterDetalleFrecuencia.getPosition(nomFrecuenciaUso));

            Spinner(spinnerFormaPago, formaPago);
            ArrayAdapter<String> adapterDetalleFormaPago = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, formaPago);
            spinnerFormaPago.setSelection(adapterDetalleFormaPago.getPosition(nombreFormaPago));

        }
    }

    private void consultaFormPreguntasCliente() {

        if (preguntasCliente.isShown()) {
            BaseDaoEncuesta con = new BaseDaoEncuesta(this);
            listaPreguntasCliente = con.consultaPreguntasClientePorIdEncuesta(Integer.parseInt(idEncuestas));

            listaEncuestasConsulta = con.consultaDatosClientePorIdEncuesta(Integer.parseInt(idEncuestas));

            View datoCliente = findViewById(R.id.datosCliente);
            spinnerAutopistas = datoCliente.findViewById(R.id.spinnerAutopistas);
            spinnerPlazaCobro = datoCliente.findViewById(R.id.spinnerPlazaCobro);
            spinnerUbicacion = datoCliente.findViewById(R.id.spinnerUbicacion);
            spinnerFrecuencia = datoCliente.findViewById(R.id.spinnerFrecuencia);
            spinnerFormaPago = datoCliente.findViewById(R.id.spinnerFormaPago);
            edEncuestador = datoCliente.findViewById(R.id.edEncuestador);
            edOrigen = datoCliente.findViewById(R.id.edOrigen);
            edDestino = datoCliente.findViewById(R.id.edDestino);
            edFecha = datoCliente.findViewById(R.id.edFecha);

            int idAutopista = 0;

            for (Object datos : listaEncuestasConsulta) {
                Esatis_Cliente info = (Esatis_Cliente) datos;
                idAutopista = info.idAutopista;
                observacionesPorCliente = info.observaciones;
            }

            List listaValoraciones = con.consultaValoracion();
            respuestas = new String[listaValoraciones.size()];
            idRespuestas = new int[listaValoraciones.size()];

            int contador = 0;

            for (Object datos : listaValoraciones) {
                Esatis_Valoracion elementos = (Esatis_Valoracion) datos;
                respuestas[contador] = elementos.getDescValoraciones();
                idRespuestas[contador] = elementos.getIdValoracion();
                contador = contador + 1;
            }

            List listaPreguntasPorTipoAutopista = con.consultaPreguntasPorIdAutopista(idAutopista);
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

            final View preguntasCliente = findViewById(R.id.preguntasCliente);
            edPreguntas = preguntasCliente.findViewById(R.id.edPreguntas);
            edSugerencias = preguntasCliente.findViewById(R.id.edSugerencias);
            radioGruopRespuestas = preguntasCliente.findViewById(R.id.radioGruopRespuestas);
            radioRespuestaUno = preguntasCliente.findViewById(R.id.radioRespuestaUno);
            radioRespuestaDos = preguntasCliente.findViewById(R.id.radioRespuestaDos);
            radioRespuestaTres = preguntasCliente.findViewById(R.id.radioRespuestaTres);
            radioRespuestaCuatro = preguntasCliente.findViewById(R.id.radioRespuestaCuatro);
            radioRespuestaCinco = preguntasCliente.findViewById(R.id.radioRespuestaCinco);
            radioRespuestaSeis = preguntasCliente.findViewById(R.id.radioRespuestaSeis);
            btnAvanzaPregunta = preguntasCliente.findViewById(R.id.btnAvanzaPregunta);
            btnRegresaPregunta = preguntasCliente.findViewById(R.id.btnRegresaPregunta);

            btnRegresaPregunta.setVisibility(View.INVISIBLE);

            Utils.disableEditText(edPreguntas);
            Utils.disableEditText(edSugerencias);
            Utils.disableRadio(radioRespuestaUno);
            Utils.disableRadio(radioRespuestaDos);
            Utils.disableRadio(radioRespuestaTres);
            Utils.disableRadio(radioRespuestaCuatro);
            Utils.disableRadio(radioRespuestaCinco);
            Utils.disableRadio(radioRespuestaSeis);

            btnAvanzaPregunta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    preguntaSiguiente = preguntaSiguiente + 1;
                    numPreguntaCuestionario = numPreguntaCuestionario + 1;

                    if (preguntaSiguiente >= 11) {
                        preguntaSiguiente = 10;
                    }

                    if (numPreguntaCuestionario >= 11) {
                        numPreguntaCuestionario = 11;
                    }

                    if (preguntaSiguiente == 10) {
                        btnAvanzaPregunta.setVisibility(View.INVISIBLE);
                    }

                    if (preguntaSiguiente > 0) {
                        btnRegresaPregunta.setVisibility(View.VISIBLE);
                    }

                    if (preguntaSiguiente < 11) {
                        edPreguntas.setVisibility(View.VISIBLE);
                        radioGruopRespuestas.setVisibility(View.VISIBLE);
                        radioRespuestaUno.setVisibility(View.VISIBLE);
                        radioRespuestaDos.setVisibility(View.VISIBLE);
                        radioRespuestaTres.setVisibility(View.VISIBLE);
                        radioRespuestaCuatro.setVisibility(View.VISIBLE);
                        radioRespuestaCinco.setVisibility(View.VISIBLE);
                        radioRespuestaSeis.setVisibility(View.VISIBLE);
                        edSugerencias.setVisibility(View.GONE);
                        muestraPReguntas();
                    }
                    if (preguntaSiguiente == 10) {
                        edPreguntas.setVisibility(View.GONE);
                        radioGruopRespuestas.setVisibility(View.GONE);
                        radioRespuestaUno.setVisibility(View.GONE);
                        radioRespuestaDos.setVisibility(View.GONE);
                        radioRespuestaTres.setVisibility(View.GONE);
                        radioRespuestaCuatro.setVisibility(View.GONE);
                        radioRespuestaCinco.setVisibility(View.GONE);
                        radioRespuestaSeis.setVisibility(View.GONE);
                        edSugerencias.setVisibility(View.VISIBLE);
                    }
                }
            });

            btnRegresaPregunta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    preguntaSiguiente = preguntaSiguiente - 1;
                    numPreguntaCuestionario = numPreguntaCuestionario - 1;
                    if (preguntaSiguiente <= 0) {
                        preguntaSiguiente = 0;
                    }

                    if (numPreguntaCuestionario <= 0) {
                        numPreguntaCuestionario = 1;
                    }

                    if (preguntaSiguiente < 10) {
                        btnAvanzaPregunta.setVisibility(View.VISIBLE);
                    }

                    if (numPreguntaCuestionario == 1) {
                        btnRegresaPregunta.setVisibility(View.INVISIBLE);
                    } else {
                        btnRegresaPregunta.setVisibility(View.VISIBLE);
                    }

                    if (preguntaSiguiente <= 10) {
                        edPreguntas.setVisibility(View.VISIBLE);
                        radioGruopRespuestas.setVisibility(View.VISIBLE);
                        radioRespuestaUno.setVisibility(View.VISIBLE);
                        radioRespuestaDos.setVisibility(View.VISIBLE);
                        radioRespuestaTres.setVisibility(View.VISIBLE);
                        radioRespuestaCuatro.setVisibility(View.VISIBLE);
                        radioRespuestaCinco.setVisibility(View.VISIBLE);
                        radioRespuestaSeis.setVisibility(View.VISIBLE);
                        edSugerencias.setVisibility(View.GONE);
                        muestraPReguntas();
                    }
                }
            });

            radioRespuestaUno.setText(respuestas[0]);
            radioRespuestaDos.setText(respuestas[1]);
            radioRespuestaTres.setText(respuestas[2]);
            radioRespuestaCuatro.setText(respuestas[3]);
            radioRespuestaCinco.setText(respuestas[4]);
            radioRespuestaSeis.setText(respuestas[5]);

            preguntasPorId = new int[listaPreguntasCliente.size()];
            valoracionPorId = new int[listaPreguntasCliente.size()];
            observacionesPorId = new String[listaPreguntasCliente.size()];
            contador = 0;

            for (Object datos : listaPreguntasCliente) {
                Esatis_Encuesta info = (Esatis_Encuesta) datos;
                preguntasPorId[contador] = info.idPreguntaCatalogo;
                valoracionPorId[contador] = info.idValoracion;
                observacionesPorId[contador] = info.observaciones;
                contador = contador + 1;
            }

            if (preguntaSiguiente < 11) {
                muestraPReguntas();
            }


        }
    }

    private void muestraPReguntas() {

        /*if(idAutopista==14 || idAutopista==15){
            for(int i=0; i<idPreguntas.length; i++){
                if(preguntasPorId[preguntaSiguiente]==idPreguntas[i]){
                    nomPregunta = preguntas[i];
                }
            }
        }else if(idAutopista==10 || idAutopista==16){
            for(int i=0; i<idPreguntas.length; i++){
                if(preguntasPorId[preguntaSiguiente]==idPreguntas[i]){
                    nomPregunta = preguntas[i];
                }
            }
        }else{
            for(int i=0; i<idPreguntas.length; i++){
                if(preguntasPorId[preguntaSiguiente]==idPreguntas[i]){
                    nomPregunta = preguntas[i];
                }
            }
        }*/

        for (int i = 0; i < idPreguntas.length; i++) {
            if (preguntasPorId[preguntaSiguiente] == idPreguntas[i]) {
                nomPregunta = preguntas[i];
            }
        }

        edPreguntas.setText(numPreguntaCuestionario + ".-" + nomPregunta);
        edSugerencias.setText(observacionesPorCliente);

        if (valoracionPorId[preguntaSiguiente] == 1) {
            radioRespuestaUno.setChecked(true);
        }
        if (valoracionPorId[preguntaSiguiente] == 2) {
            radioRespuestaDos.setChecked(true);
        }
        if (valoracionPorId[preguntaSiguiente] == 3) {
            radioRespuestaTres.setChecked(true);
        }
        if (valoracionPorId[preguntaSiguiente] == 4) {
            radioRespuestaCuatro.setChecked(true);
        }
        if (valoracionPorId[preguntaSiguiente] == 5) {
            radioRespuestaCinco.setChecked(true);
        }
        if (valoracionPorId[preguntaSiguiente] == 6) {
            radioRespuestaSeis.setChecked(true);
        }


    }

    public void Spinner(Spinner spinner, String[] datosSpinner) {
        spinner.setAdapter(null);
        SpinnerControl spinnerControl = new SpinnerControl(getApplicationContext());
        ArrayAdapter<String> adaptador = spinnerControl.obtenerAdaptador(datosSpinner);
        spinner.setAdapter(adaptador);
    }

    private void consultasCatalogos() {

        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        List listaAutopistas = con.consultaAutopistas();
        autopistas = new String[listaAutopistas.size() + 1];
        autopistas[0] = "Selecciona";
        int contador = 1;

        for (Object datos : listaAutopistas) {
            Esatis_Autopistas elementos = (Esatis_Autopistas) datos;
            autopistas[contador] = elementos.getNombreAutopista();
            contador = contador + 1;
        }

        //plazaCobro = new String[60];
    }

    private void consultaAutopista() {
        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        listaEncuestasConsulta = con.consultaDatosClientePorIdEncuesta(Integer.parseInt(idEncuestas));

        for (Object datos : listaEncuestasConsulta) {
            Esatis_Cliente info = (Esatis_Cliente) datos;
            idAutopista = info.idAutopista;
        }
    }

    private void regresaListaEncuestas() {
        Intent linsertar = new Intent(this, ListaEncuestasActivity.class);
        startActivity(linsertar);
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

    private void regresaMainManu() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}
