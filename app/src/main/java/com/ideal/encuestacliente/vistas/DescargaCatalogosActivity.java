package com.ideal.encuestacliente.vistas;

import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_PLAZA_COBRO;
import static com.ideal.encuestacliente.configuracion.Constantes.idUsuario;
import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.Funcionalidades;
import com.ideal.encuestacliente.configuracion.SharedPreferencesManager;
import com.ideal.encuestacliente.synctables.AutopistasSync;
import com.ideal.encuestacliente.synctables.Esatis_AutPreguntasSync;
import com.ideal.encuestacliente.synctables.Esatis_FormaPagoSync;
import com.ideal.encuestacliente.synctables.Esatis_FrecuenciaSync;
import com.ideal.encuestacliente.synctables.Esatis_PreguntasSync;
import com.ideal.encuestacliente.synctables.Esatis_UbicacionesSync;
import com.ideal.encuestacliente.synctables.Esatis_ValoracionSync;
import com.ideal.encuestacliente.synctables.PlazasCobroSync;
import com.ideal.encuestacliente.synctables.UsuariosSync;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

public class DescargaCatalogosActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = DescargaCatalogosActivity.class.getSimpleName();

    private FloatingActionButton buttonInicio;
    private ConstraintLayout linearLayout;
    private ProgressBar progressBarPlazasCobro;
    private ProgressBar progressBarAutopistas;
    private ProgressBar progressBarTiposFotosDoc;
    private ProgressBar progressBarDocumentosIdentificacion;
    private ProgressBar progressBarEstadoFisicos;
    private ProgressBar progressBarTiposFotosFirmas,
            proBarSubtipoServicio, proBarDisponibilidad, proBarPlazaCobro,
            proBarPreguntas, proBarValoracion, proBarAutPreguntas, proBarUbicacion, proBarUsuarios;
    private ProgressBar[] progressBars = null;
    private ImageView imageViewPlazasCobro;
    private ImageView imageViewAutopistas, imageViewUsuarios;
    private ImageView imageViewTiposFotosDoc;
    private ImageView imageViewDocumentosIdentificacion;
    private ImageView imageViewEstadosFisicos;
    private ImageView imageViewTiposFotosFirmas, imageViewSubtipoServicio,
            imageViewDisponibilidad, imageViewPlaza,
            imageViewPreguntas, imageViewValoracion, imageViewAutPreguntas, imageViewUbicacion;
    private ImageView[] imageViews = null;
    private TextView txvAutopistas, txvPlazaCobro, txvConcepto, txvTurno;
    private Context context;
    private Activity activity;

    private SharedPreferencesManager preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_catalogos);
        inicializarControles();
    }

    public void inicializarControles() {
        context = this;
        activity = this;
        preferences = SharedPreferencesManager.getInstance(this);

        progressBarAutopistas = findViewById(R.id.proBarAutopistas);
        proBarSubtipoServicio = findViewById(R.id.proBarSubtipoServicio);
        proBarDisponibilidad = findViewById(R.id.proBarDisponibilidad);
        proBarPreguntas = findViewById(R.id.proBarPreguntas);
        proBarValoracion = findViewById(R.id.proBarValoracion);
        proBarAutPreguntas = findViewById(R.id.proBarAutPreguntas);
        proBarPlazaCobro = findViewById(R.id.proBarPlazaCobro);
        proBarUbicacion = findViewById(R.id.proBarUbicacion);
        proBarUsuarios = findViewById(R.id.proBarUsuarios);

        linearLayout = findViewById(R.id.linearDescargaCatalogos);
        imageViewPlazasCobro = findViewById(R.id.imageViewPlazasCobro);
        imageViewAutopistas = findViewById(R.id.imageViewAutopistas);
        imageViewSubtipoServicio = findViewById(R.id.imageViewSubtipoServicio);
        imageViewDisponibilidad = findViewById(R.id.imageViewDisponibilidad);
        imageViewPreguntas = findViewById(R.id.imageViewPreguntas);
        imageViewValoracion = findViewById(R.id.imageViewValoracion);
        imageViewAutPreguntas = findViewById(R.id.imageViewAutPreguntas);
        imageViewPlaza = findViewById(R.id.imageViewPlaza);
        imageViewUbicacion = findViewById(R.id.imageViewUbicacion);
        imageViewUsuarios = findViewById(R.id.imageViewUsuarios);

        buttonInicio = findViewById(R.id.buttonInicio);
        txvAutopistas = findViewById(R.id.txvAutopistas);
        setMarginInsets(linearLayout);

        progressBars = new ProgressBar[9];
        progressBars[0] = progressBarAutopistas;
        progressBars[1] = proBarSubtipoServicio;
        progressBars[2] = proBarDisponibilidad;
        progressBars[3] = proBarPreguntas;
        progressBars[4] = proBarValoracion;
        progressBars[5] = proBarAutPreguntas;
        progressBars[6] = proBarPlazaCobro;
        progressBars[7] = proBarUbicacion;
        progressBars[8] = proBarUsuarios;

        imageViews = new ImageView[9];
        imageViews[0] = imageViewAutopistas;
        imageViews[1] = imageViewSubtipoServicio;
        imageViews[2] = imageViewDisponibilidad;
        imageViews[3] = imageViewPreguntas;
        imageViews[4] = imageViewValoracion;
        imageViews[5] = imageViewAutPreguntas;
        imageViews[6] = imageViewPlaza;
        imageViews[7] = imageViewUbicacion;
        imageViews[8] = imageViewUsuarios;

        buttonInicio.setOnClickListener(this);
        iniciarDescarga();
    }


    private void init(int index, int maxIteraciones) {
        if (index < maxIteraciones) {
            new Descarga(index).execute();
            init(index + 1, maxIteraciones);
            borraTablasPrincipales(Constantes.TABLA_CATALOGO_DATOS_CLIENTE);
            borraTablasPrincipales(Constantes.TABLA_CATALOGO_ENCUESTA);
        }
    }

    private void borraTablasPrincipales(String nombreTabla) {
        BaseDaoEncuesta.getInstance().truncateTable(nombreTabla);
        BaseDaoEncuesta.getInstance().resetAutoincrement(nombreTabla);
    }

    private void iniciarDescarga() {
        init(0, progressBars.length);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonInicio:
                startActivity(new Intent(DescargaCatalogosActivity.this, MainActivity.class));
                break;
        }
    }


    public class Descarga extends AsyncTask<String, Void, Boolean> {
        private int index;

        public Descarga(int index) {
            this.index = index;
        }

        @Override
        protected void onPreExecute() {
            buttonInicio.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                switch (index) {
                    case 0:
                        new AutopistasSync().Sync(txvAutopistas, idUsuario);
                        break;
                    case 1:
                        new Esatis_FormaPagoSync().sync(Constantes.TABLA_FORMA_PAGO);
                        break;
                    case 2:
                        new Esatis_FrecuenciaSync().sync(Constantes.TABLA_CATALOGO_FRECUENCIA);
                        break;
                    case 3:
                        new Esatis_PreguntasSync().sync(Constantes.TABLA_CATALOGO_PREGUNTAS);
                        break;
                    case 4:
                        new Esatis_ValoracionSync().sync(Constantes.TABLA_CATALOGO_VALORACION);
                        break;
                    case 5:
                        new Esatis_AutPreguntasSync().sync(Constantes.TABLA_CATALOGO_AUTOPISTA_PREGUNTA);
                        break;
                    case 6:
                        new PlazasCobroSync().syncPlaza(Constantes.WS_PLAZA_COBRO, TABLA_PLAZA_COBRO);
                        break;
                    case 7:
                        new Esatis_UbicacionesSync().sync(Constantes.TABLA_CATALOGO_UBICACIONES);
                        break;
                    case 8:
                        new UsuariosSync().sync(Constantes.TABLA_USUARIOS);
                        break;
                }
                Thread.sleep(1500);
            } catch (Exception e) {
                buttonInicio.setEnabled(true);
                Log.e(TAG, "Descarga: " + e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Funcionalidades.
                    cambiarColorProgressBar(progressBars[index], context);
            Funcionalidades.mostrarImageView(imageViews[index]);
            if (index == 8) {
                preferences.clearUserData();
                buttonInicio.setEnabled(true);
            }
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}