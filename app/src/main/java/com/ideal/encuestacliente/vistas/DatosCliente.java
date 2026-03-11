package com.ideal.encuestacliente.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.AppConstants;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.CoordenadasGps;
import com.ideal.encuestacliente.configuracion.Funcionalidades;
import com.ideal.encuestacliente.configuracion.GpsUtils;
import com.ideal.encuestacliente.configuracion.ServicioGPS;
import com.ideal.encuestacliente.configuracion.SharedPreferencesManager;
import com.ideal.encuestacliente.configuracion.SpinnerControl;
import com.ideal.encuestacliente.configuracion.TimePickerControl;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.controladores.ControladorDatosCliente;
import com.ideal.encuestacliente.jsonHelper.JSONParser;
import com.ideal.encuestacliente.model.Esatis_Autopista_Pregunta;
import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.model.Esatis_Frecuencia;
import com.ideal.encuestacliente.model.Esatis_Plaza_Cobro;
import com.ideal.encuestacliente.model.Esatis_Ubicaciones;
import com.ideal.encuestacliente.model.Esatis_Usuario;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.sincronizacion.UrlSincronizacion;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission_group.CAMERA;
import static com.ideal.encuestacliente.configuracion.Constantes.TABLA_FORMA_PAGO;
import static com.ideal.encuestacliente.configuracion.Constantes.TELEPEAJE;
import static com.ideal.encuestacliente.configuracion.Constantes.idUsuario;
import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;
import static com.ideal.encuestacliente.configuracion.Utils.syncSuccessMessage;
import static com.ideal.encuestacliente.controladores.ControladorDatosCliente.obtieneIdUltimaEncuesta;

public class DatosCliente extends AppCompatActivity {

    private TextView txtNomAutopista;
    private Spinner spinnerAutopistas, spinnerPlazaCobro, spinnerFrecuencia, spinnerFormaPago, spinnerUbicacion;
    private String[] autopistas;

    private String[] plazaCobro = {
            "Selecciona"
    };

    private int[] idPlazaCobro = {
            0
    };


    private String[] frecuenciaUso;
    private int[] idFrecuenciaUso;

    private String[] formaPago;
    private int[] idFormaPago;

    private String[] ubicacionEncuesta;
    private int[] idUbicacionEncuesta;

    private EditText edPlazaCobro, edFrecuencia, edFecha;
    private LinearLayout linearUbicacion;
    private TextInputEditText edEncuestador, edOrigen, edDestino, edUbicacion;
    private ConstraintLayout layoutDatosCliente;
    private MaterialButton btnHoraEncuesta;
    private String[] arraySpinners;
    private FloatingActionButton btnContinuarEncuesta, btnRegresar, btnCancelaReporte;
    public int idEncuestaPreguntas = 0;
    public int idAutopistas = 0;
    private int idEncuestaConsulta = 0;
    BaseDaoEncuesta con = new BaseDaoEncuesta(this);
    String usuarioCreacion, usuarioModificacion, usuarioEliminacion, fechaCreacion, fechaModificacion, fechaEliminacion;
    List listaEncuestasConsulta, listaEncuestasConsultaUltima;
    private BottomAppBar bottomAppBar;
    private com.google.android.material.floatingactionbutton.FloatingActionButton btnTerminarEncuesta;

    private double latitud = 0.0;
    private double longitud = 0.0;
    private double altitud = 0.0;
    private ArrayAdapter<String> adapterPlazasCobro, adapterAutopistas, adapterForma, adapterFrecuencia, adapterUbicacion;
    private Context context;

    private static final int PERMISSION_REQUEST_CODE = 200;

    //Variables GPS
    private FusedLocationProviderClient mFusedLocationClient;

    private double wayLatitude = 0.0, wayLongitude = 0.0, wayAltitud = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private android.widget.Button btnLocation;
    private TextView txtLocation;
    private android.widget.Button btnContinueLocation;
    private TextView txtContinueLocation;
    private StringBuilder stringBuilder;

    private boolean isContinue = false;
    private boolean isGPS = false;
    private TextView txtAut;
    private String nombreAutopista;
    private int idAutopistaUltimoRegistro = 0;

    private SharedPreferencesManager preferences;

    private TextView txtUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cliente);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle parametros = this.getIntent().getExtras();
        nombreAutopista = getIntent().getExtras().getString(Constantes.campos.nombreAutopista);
        idEncuestaConsulta = parametros.getInt("idEncuestas");
        Log.e("nombreAutopista", nombreAutopista + "");

        consultaLocalizacion();
        initControls();

        ctx = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        consultaCatalogos();
                    }
                });

                actionsControls();
            }
        }, 500);


        if (idEncuestaConsulta == 0) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            consultaUltimoDatoDatosCliente();
                        }
                    });

                    seteaValores();
                }
            }, 500);

        }


        new Thread(new Runnable() {
            public void run() {
                consultaFormDatosCliente();
            }
        }).start();


        //new descargaFormaPago().execute("");


        /*btnLocation.setOnClickListener(v -> {

            if (!isGPS) {
                Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                return;
            }
            isContinue = false;
            getLocation();
        });

        btnContinueLocation.setOnClickListener(v -> {
            if (!isGPS) {
                Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                return;
            }
            isContinue = true;
            stringBuilder = new StringBuilder();
            getLocation();
        });*/

        //consultaGPS();

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

    private ProgressDialog pDialogCateFormaPago;
    private Context ctx;

    class descargaFormaPago extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(ctx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialogCateFormaPago = new ProgressDialog(DatosCliente.this);
            pDialogCateFormaPago.setMessage("Cargando...");
            pDialogCateFormaPago.setIndeterminate(false);
            pDialogCateFormaPago.setCancelable(false);
            pDialogCateFormaPago.show();
        }


        protected String doInBackground(String... args) {

            try {


                isContinue = true;
                getLocation();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

            if (pDialogCateFormaPago != null && pDialogCateFormaPago.isShowing()) {
                pDialogCateFormaPago.dismiss();
            }


        }


    }

    private void consultaLocalizacion() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        /*new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });*/

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        wayAltitud = location.getAltitude();
                        Log.e("wayLatitude_", wayLatitude + "");
                        Log.e("wayLongitude_", wayLongitude + "");
                        Log.e("wayAltitud_", wayAltitud + "");
                        /*if (!isContinue) {
                        } else {
                            stringBuilder.append(wayLatitude);
                            stringBuilder.append("-");
                            stringBuilder.append(wayLongitude);
                            stringBuilder.append("\n\n");
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }*/
                    }
                }
            }
        };

    }


    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(DatosCliente.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(DatosCliente.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DatosCliente.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(DatosCliente.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();
                            wayAltitud = location.getAltitude();
                            //txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                        } else {
                            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                        }
                    }
                });
            }
        }
    }


    public void consultaGPS() {


        CoordenadasGps coordenadas =
                new CoordenadasGps(getApplicationContext(), this);

        ArrayList<String> coords = coordenadas.obtenerLocalización();

        if (coords != null && coords.size() > 0) {
            longitud = Double.parseDouble(coords.get(0));
            altitud = Double.parseDouble(coords.get(1));
            latitud = Double.parseDouble(coords.get(2));
        } else {
            longitud = Double.parseDouble(String.valueOf(coordenadas.longitudChanged));
            altitud = Double.parseDouble(String.valueOf(coordenadas.altitudChanged));
            latitud = Double.parseDouble(String.valueOf(coordenadas.latitudChanged));
        }

        if (longitud == 0 || altitud == 0 || latitud == 0) {

           /* CoordenadasGps coordenadasUpdate =
                    new CoordenadasGps(getApplicationContext(),this);

            ArrayList<String> coordsUpdate =   coordenadasUpdate.obtenerLocalización();

            longitud = Double.parseDouble(coordsUpdate.get(0));
            altitud  = Double.parseDouble(coordsUpdate.get(1));
            latitud  = Double.parseDouble(coordsUpdate.get(2));*/

            // requestPermission();

        }

        Log.e("longitud", longitud + "");
        Log.e("altitud", altitud + "");
        Log.e("latitud", latitud + "");

        //guardaDatosCliente();
    }

    private void consultaCatalogos() {

        List listaAutopistas = con.consultaAutopistas();
        autopistas = new String[listaAutopistas.size() + 1];
        autopistas[0] = "Selecciona";
        int contador = 1;

        for (Object datos : listaAutopistas) {
            Esatis_Autopistas elementos = (Esatis_Autopistas) datos;
            autopistas[contador] = elementos.getNombreAutopista();
            if (elementos.getNombreAutopista().trim().equalsIgnoreCase(nombreAutopista.trim())) {
                idAutopistaUltimoRegistro = elementos.getIdAutopistaSQL();
            }
            contador = contador + 1;
        }

        List listaFormaPago = con.consultaFormaPago();
        int contadorFormaPagoSinTelePeaje = 0;

        for (Object datos : listaFormaPago) {
            Esatis_Forma_Pago elementos = (Esatis_Forma_Pago) datos;

            if (!elementos.getFormaPago().trim().equalsIgnoreCase(TELEPEAJE)) {
                contadorFormaPagoSinTelePeaje = contadorFormaPagoSinTelePeaje + 1;
            }

        }

        if (nombreAutopista.trim().equalsIgnoreCase("Urbana Sur") ||
                nombreAutopista.trim().equalsIgnoreCase("Viaducto Elevado Tlalpan")) {
            formaPago = new String[listaFormaPago.size() + 1];
            idFormaPago = new int[listaFormaPago.size() + 1];
        } else {
            formaPago = new String[listaFormaPago.size()];
            idFormaPago = new int[listaFormaPago.size()];

            /*formaPago = new String[contadorFormaPagoSinTelePeaje + 1];
            idFormaPago = new int[contadorFormaPagoSinTelePeaje + 1];*/
        }

        formaPago[0] = "Selecciona";
        idFormaPago[0] = 0;
        contador = 1;

        for (Object datos : listaFormaPago) {
            Esatis_Forma_Pago elementos = (Esatis_Forma_Pago) datos;

            if (nombreAutopista.trim().equalsIgnoreCase("Urbana Sur") ||
                    nombreAutopista.trim().equalsIgnoreCase("Viaducto Elevado Tlalpan")) {

                idFormaPago[contador] = elementos.getIdFormaPago();
                formaPago[contador] = elementos.getFormaPago();

                contador = contador + 1;
            } else {
                if (!elementos.getFormaPago().trim().equalsIgnoreCase(TELEPEAJE)) {
                    idFormaPago[contador] = elementos.getIdFormaPago();
                    formaPago[contador] = elementos.getFormaPago();

                    contador = contador + 1;
                }

            }

            usuarioCreacion = elementos.getUsuarioCreacion();
            usuarioModificacion = elementos.getUsuarioModificacion();
            usuarioEliminacion = elementos.getUsuarioEliminacion();
            fechaCreacion = elementos.getFechaCreacion();
            fechaModificacion = "";
            fechaEliminacion = "";
        }

        Log.e("usuarioCreacion", usuarioCreacion);
        Log.e("usuarioModificacion", usuarioModificacion);
        Log.e("usuarioEliminacion", usuarioEliminacion);
        Log.e("fechaCreacion", fechaCreacion);
        Log.e("fechaModificacion", fechaModificacion);
        Log.e("fechaEliminacion", fechaEliminacion);

        List listaFrecuencia = con.consultaFrecuencia();
        frecuenciaUso = new String[listaFrecuencia.size() + 1];
        frecuenciaUso[0] = "Selecciona";
        idFrecuenciaUso = new int[listaFrecuencia.size() + 1];
        idFrecuenciaUso[0] = 0;
        contador = 1;

        for (Object datos : listaFrecuencia) {
            Esatis_Frecuencia elementos = (Esatis_Frecuencia) datos;
            idFrecuenciaUso[contador] = elementos.getIdFrecuenciaUso();
            frecuenciaUso[contador] = elementos.getDescFrecuencia();
            contador = contador + 1;
        }

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


        //plazaCobro = new String[60];
    }

    private void initControls() {
        context = DatosCliente.this;
        txtNomAutopista = (TextView) findViewById(R.id.txtNomAutopista);
        spinnerAutopistas = (Spinner) findViewById(R.id.spinnerAutopistas);
        spinnerPlazaCobro = (Spinner) findViewById(R.id.spinnerPlazaCobro);
        spinnerFrecuencia = (Spinner) findViewById(R.id.spinnerFrecuencia);
        spinnerFormaPago = (Spinner) findViewById(R.id.spinnerFormaPago);
        spinnerUbicacion = (Spinner) findViewById(R.id.spinnerUbicacion);
        edPlazaCobro = (EditText) findViewById(R.id.edPlazaCobro);
        edEncuestador = (TextInputEditText) findViewById(R.id.edEncuestador);
        edOrigen = (TextInputEditText) findViewById(R.id.edOrigen);
        edUbicacion = (TextInputEditText) findViewById(R.id.edUbicacion);
        linearUbicacion = (LinearLayout) findViewById(R.id.linearUbicacion);
        edDestino = (TextInputEditText) findViewById(R.id.edDestino);
        edFrecuencia = (EditText) findViewById(R.id.edFrecuencia);
        edFecha = (EditText) findViewById(R.id.edFecha);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        edFecha.setText(currentDate);
        edFecha.setFocusable(false);
        layoutDatosCliente = findViewById(R.id.layoutDatosCliente);
        bottomAppBar = findViewById(R.id.buttonAppBarPersonas);
        setSupportActionBar(bottomAppBar);
        btnHoraEncuesta = findViewById(R.id.btnHoraEncuesta);
        btnContinuarEncuesta = findViewById(R.id.btnContinuarEncuesta);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnTerminarEncuesta = findViewById(R.id.btnTerminarEncuesta);

        txtAut = findViewById(R.id.txtAut);

        setMarginInsets(layoutDatosCliente);
        txtAut.setText(nombreAutopista);

      /*  if (checkPermission()) {

            consultaGPS();

        } else {

            requestPermission();
        }*/

        /*if (!isGPS) {
            Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
            return;
        }*/

        //isContinue = false;

        isContinue = true;
        getLocation();
        showUserData();
    }

    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isContinue) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {
                        mFusedLocationClient.getLastLocation().addOnSuccessListener(DatosCliente.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    wayLatitude = location.getLatitude();
                                    wayLongitude = location.getLongitude();
                                    wayAltitud = location.getAltitude();

                                    //txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                                } else {
                                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }*/

 /*   private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted ){
                        consultaGPS();
                        Utils.Toast(DatosCliente.this, "Permisos otorgados, puede continuar con la encuesta");

                    }else {

                        Utils.Toast(DatosCliente.this, "Se han negado los permisos, no es posible continuar con la encuesta");
                        //regresaMainManu();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("Necesita otorgar los permisos",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }*/


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(DatosCliente.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void actionsControls() {

        Spinner(spinnerAutopistas, autopistas);
        Spinner(spinnerFrecuencia, frecuenciaUso);
        Spinner(spinnerFormaPago, formaPago);
        Spinner(spinnerUbicacion, ubicacionEncuesta);

        adapterAutopistas = Funcionalidades.getAdapter(context, autopistas);
        spinnerAutopistas.setAdapter(adapterAutopistas);

        adapterForma = Funcionalidades.getAdapter(context, formaPago);
        spinnerFormaPago.setAdapter(adapterForma);

        adapterFrecuencia = Funcionalidades.getAdapter(context, frecuenciaUso);
        spinnerFrecuencia.setAdapter(adapterFrecuencia);

        adapterUbicacion = Funcionalidades.getAdapter(context, ubicacionEncuesta);
        spinnerUbicacion.setAdapter(adapterUbicacion);

        int idAutop = 0;
        List listaAutopistas = con.consultaIdAutopista(nombreAutopista);
        for (Object datos : listaAutopistas) {
            Esatis_Autopistas elementos = (Esatis_Autopistas) datos;
            idAutop = elementos.getIdAutopistaSQL();
        }

        Log.e("idAutop", idAutop + "");

        List listaPlazaCobro = con.consultaPlazaCobroPorIdAutopita(idAutop);
        plazaCobro = new String[listaPlazaCobro.size() + 1];
        idPlazaCobro = new int[listaPlazaCobro.size() + 1];
        plazaCobro[0] = "Selecciona";
        idPlazaCobro[0] = 0;
        int contador = 1;

        for (Object datos : listaPlazaCobro) {
            Esatis_Plaza_Cobro elementos = (Esatis_Plaza_Cobro) datos;
            idPlazaCobro[contador] = elementos.getIdPlazaCobroSQL();
            Log.e("idPlazaCobro[contador]", idPlazaCobro[contador] + "");
            plazaCobro[contador] = elementos.getPlazaCobro();
            Log.e("plazaCobro[contador]", plazaCobro[contador] + "");
            contador = contador + 1;
        }

        //Spinner(spinnerPlazaCobro, plazaCobro);

        adapterPlazasCobro = Funcionalidades.getAdapter(context, plazaCobro);
        spinnerPlazaCobro.setAdapter(adapterPlazasCobro);

        spinnerAutopistas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    spinnerAutopistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            int idAutop = 0;
                            List listaAutopistas = con.consultaIdAutopista(spinnerAutopistas.getSelectedItem().toString());
                            for (Object datos : listaAutopistas) {
                                Esatis_Autopistas elementos = (Esatis_Autopistas) datos;
                                idAutop = elementos.getIdAutopistaSQL();
                            }

                            Log.e("idAutop", idAutop + "");

                            List listaPlazaCobro = con.consultaPlazaCobroPorIdAutopita(idAutop);
                            plazaCobro = new String[listaPlazaCobro.size() + 1];
                            idPlazaCobro = new int[listaPlazaCobro.size() + 1];
                            plazaCobro[0] = "Selecciona";
                            idPlazaCobro[0] = 0;
                            int contador = 1;

                            for (Object datos : listaPlazaCobro) {
                                Esatis_Plaza_Cobro elementos = (Esatis_Plaza_Cobro) datos;
                                idPlazaCobro[contador] = elementos.getIdPlazaCobroSQL();
                                Log.e("idPlazaCobro[contador]", idPlazaCobro[contador] + "");
                                plazaCobro[contador] = elementos.getPlazaCobro();
                                Log.e("plazaCobro[contador]", plazaCobro[contador] + "");
                                contador = contador + 1;
                            }

                            //Spinner(spinnerPlazaCobro, plazaCobro);

                            adapterPlazasCobro = Funcionalidades.getAdapter(context, plazaCobro);
                            spinnerPlazaCobro.setAdapter(adapterPlazasCobro);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // sometimes you need nothing here
                        }
                    });
                }
                return false;
            }
        });


        spinnerUbicacion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    spinnerUbicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String ubica = spinnerUbicacion.getSelectedItem().toString();

                            if (ubica.equalsIgnoreCase("Otro")) {
                                linearUbicacion.setVisibility(View.VISIBLE);
                                edUbicacion.requestFocus();
                            } else {
                                linearUbicacion.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // sometimes you need nothing here
                        }
                    });
                }
                return false;
            }
        });

        Spinner(spinnerPlazaCobro, plazaCobro);
        adapterPlazasCobro = Funcionalidades.getAdapter(context, plazaCobro);
        spinnerPlazaCobro.setAdapter(adapterPlazasCobro);

        /*edFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regresaInicio();
                TimePickerControl timePickerControl = new TimePickerControl(DatosCliente.this);
                timePickerControl.mostrarControlFechaAseguradora(layoutDatosCliente, edFecha);
            }
        });*/

        btnHoraEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regresaInicio();
                TimePickerControl timePickerControl = new TimePickerControl(DatosCliente.this);
                timePickerControl.mostrarControlFechaAseguradora(layoutDatosCliente, edFecha);
            }
        });

        btnContinuarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regresaInicio();
                //consultaGPS();

            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogEliminar();
            }
        });

        btnTerminarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regresaInicio();
                guardaDatosCliente();
                //consultaGPS();
            }
        });

        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(200);
        filterArray[1] = new InputFilter.AllCaps();

        edEncuestador.setFilters(filterArray);
        edOrigen.setFilters(filterArray);
        edDestino.setFilters(filterArray);
        edUbicacion.setFilters(filterArray);


        Log.e("wayLatitude", wayLatitude + "");
        Log.e("wayLongitude", wayLongitude + "");
        Log.e("wayAltitud", wayAltitud + "");

        if (nombreAutopista.trim().equalsIgnoreCase("Urbana Sur") ||
                nombreAutopista.trim().equalsIgnoreCase("Viaducto Elevado Tlalpan")) {

            ArrayAdapter<String> adapterDetallePlaza = new ArrayAdapter<String>(this, R.layout.custom_spinner, formaPago);
            spinnerFormaPago.setSelection(adapterDetallePlaza.getPosition(TELEPEAJE));
            spinnerFormaPago.setEnabled(false);
        } else {
            spinnerFormaPago.setEnabled(true);
        }

    }

    private void regresaMainManu() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void guardaDatosCliente() {

        altitud = wayAltitud;
        latitud = wayLatitude;
        longitud = wayLongitude;

        Log.e("altitud", altitud + "");
        Log.e("latitud", latitud + "");
        Log.e("longitud", longitud + "");

        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        Esatis_Cliente datos = new Esatis_Cliente();
        int idPlazaCobroGuarda = 0;
        int idFrecuenciaUsoGuarda = 0;
        int idFormaPagoGuarda = 0;
        int idUbicacion = 0;
        int indicadorSincronizacion = 1;
        String nomPlazaCobro = "";
        nomPlazaCobro = spinnerPlazaCobro.getSelectedItem().toString().trim();
        Log.e("nomPlazaCobro", nomPlazaCobro);

        for (int i = 0; i < plazaCobro.length; i++) {
            if (nomPlazaCobro.equalsIgnoreCase(plazaCobro[i].trim())) {
                idPlazaCobroGuarda = idPlazaCobro[i];
            }
        }

        Log.e("idPlazaCobroGuarda", idPlazaCobroGuarda + "");

        for (int i = 0; i < frecuenciaUso.length; i++) {
            if (spinnerFrecuencia.getSelectedItem().toString().equalsIgnoreCase(frecuenciaUso[i])) {
                idFrecuenciaUsoGuarda = idFrecuenciaUso[i];
            }
        }

        for (int i = 0; i < formaPago.length; i++) {
            if (spinnerFormaPago.getSelectedItem().toString().equalsIgnoreCase(formaPago[i])) {
                idFormaPagoGuarda = idFormaPago[i];
            }
        }

        for (int i = 0; i < ubicacionEncuesta.length; i++) {
            if (spinnerUbicacion.getSelectedItem().toString().equalsIgnoreCase(ubicacionEncuesta[i])) {
                idUbicacion = idUbicacionEncuesta[i];
            }
        }

        Log.e("idUbicacion", idUbicacion + "");

        List listaUsuaior = con.consultaUsuario();

        for (Object datosUsuario : listaUsuaior) {
            Esatis_Usuario elementos = (Esatis_Usuario) datosUsuario;
            usuarioCreacion = elementos.getNombreUsuario();
            usuarioModificacion = "";
            usuarioEliminacion = "";
        }

        fechaModificacion = "";
        fechaEliminacion = "";
        boolean validaUbicacion = true;

        if (spinnerUbicacion.getSelectedItem().toString().equalsIgnoreCase("Otro")
                && edUbicacion.getText().toString().equalsIgnoreCase("")) {
            validaUbicacion = false;
        }

        if (spinnerUbicacion.getSelectedItem().toString().equalsIgnoreCase("Otro")
                && !edUbicacion.getText().toString().equalsIgnoreCase("")) {
            validaUbicacion = true;
        }

        if (listaEncuestasConsulta.size() <= 0) {

            //if(!spinnerAutopistas.getSelectedItem().toString().equalsIgnoreCase("Selecciona")){
            if (!spinnerPlazaCobro.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                if (!edEncuestador.getText().toString().equalsIgnoreCase("")) {
                    if (!edOrigen.getText().toString().equalsIgnoreCase("")) {
                        if (!edDestino.getText().toString().equalsIgnoreCase("")) {
                            if (!spinnerFrecuencia.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                                if (!edFecha.getText().toString().equalsIgnoreCase("")) {
                                    if (!spinnerFormaPago.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                                        if (!spinnerUbicacion.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                                            if (validaUbicacion) {

                                                int idEncuestaSync = obtieneIdUltimaEncuesta(context) + 1;

                                                String currentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(new Date());
                                                //datos.setIdAutopista(ControladorDatosCliente.obtieneIdAutopistas(spinnerAutopistas.getSelectedItem().toString(), getApplicationContext()));
                                                fechaModificacion = idEncuestaSync + "_" + currentDate;

                                                datos.setIdAutopista(idAutopistaUltimoRegistro);
                                                datos.setIdPlazaCobro(idPlazaCobroGuarda);
                                                datos.setIdUbicacion(idUbicacion);
                                                datos.setUbicacionDesc(edUbicacion.getText().toString());
                                                datos.setEncuestador(edEncuestador.getText().toString());
                                                datos.setOrigen(edOrigen.getText().toString());
                                                datos.setDestino(edDestino.getText().toString());
                                                datos.setIdFrecuenciaUso(idFrecuenciaUsoGuarda);
                                                datos.setFecha(edFecha.getText().toString());
                                                datos.setIdFormaPago(idFormaPagoGuarda);
                                                datos.setIdIndicadorSinc(indicadorSincronizacion);
                                                datos.setObservaciones("");
                                                datos.setUsuarioCreacion(usuarioCreacion);
                                                datos.setUsuarioModificacion(usuarioModificacion);
                                                datos.setUsuarioEliminacion(usuarioEliminacion);
                                                datos.setFechaCreacion(currentDate);
                                                datos.setFechaModificacion(fechaModificacion);
                                                datos.setFechaEliminacion(fechaEliminacion);
                                                datos.setAltitud(altitud);
                                                datos.setLatitud(latitud);
                                                datos.setLongitud(longitud);

                                                if (con.insertaDatosCliente(datos)) {
                                                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.guardaExitosoDatosCliente));
                                                    edEncuestador.setText("");
                                                    edOrigen.setText("");
                                                    edDestino.setText("");
                                                    edFecha.setText("");
                                                    spinnerAutopistas.setSelection(0, true);
                                                    spinnerPlazaCobro.setSelection(0, true);
                                                    spinnerFrecuencia.setSelection(0, true);
                                                    spinnerFormaPago.setSelection(0, true);

                                                    List listaDatosClientes = con.consultaDatosCliente();
                                                    for (Object infoCliente : listaDatosClientes) {
                                                        Esatis_Cliente elem = (Esatis_Cliente) infoCliente;
                                                        idEncuestaPreguntas = elem.idEncuesta;
                                                        idAutopistas = elem.idAutopista;
                                                    }


                                                    continuaEncuesta();

                                                } else {
                                                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.noGuardaExitosoDatosCliente));
                                                }

                                            } else {
                                                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaDescUbicacion));
                                            }
                                        } else {
                                            Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaUbicacion));
                                        }


                                    } else {
                                        Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaFormaPago));
                                    }
                                } else {
                                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaFecha));
                                }
                            } else {
                                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaFrecuenciaUso));
                            }

                        } else {
                            Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaDestino));
                        }
                    } else {
                        Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaOrigen));
                    }

                } else {
                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaEncuestador));
                }

            } else {
                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaPlazaCobro));
            }
            /*}else{
                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaAutopista));
            }*/

        } else {

            //if(!spinnerAutopistas.getSelectedItem().toString().equalsIgnoreCase("Selecciona")){
            if (!spinnerPlazaCobro.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                if (!edEncuestador.getText().toString().equalsIgnoreCase("")) {
                    if (!edOrigen.getText().toString().equalsIgnoreCase("")) {
                        if (!edDestino.getText().toString().equalsIgnoreCase("")) {
                            if (!spinnerFrecuencia.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                                if (!edFecha.getText().toString().equalsIgnoreCase("")) {
                                    if (!spinnerFormaPago.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                                        if (!spinnerUbicacion.getSelectedItem().toString().equalsIgnoreCase("Selecciona")) {
                                            if (validaUbicacion) {

                                                String currentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(new Date());

                                                fechaModificacion = idEncuestaConsulta + "_" + currentDate;

                                                datos.setIdEncuesta(idEncuestaConsulta);
                                                //datos.setIdAutopista(ControladorDatosCliente.obtieneIdAutopistas(spinnerAutopistas.getSelectedItem().toString(), getApplicationContext()));
                                                datos.setIdAutopista(idAutopistaUltimoRegistro);
                                                datos.setIdPlazaCobro(idPlazaCobroGuarda);
                                                datos.setIdUbicacion(idUbicacion);
                                                datos.setUbicacionDesc(edUbicacion.getText().toString());
                                                datos.setEncuestador(edEncuestador.getText().toString());
                                                datos.setOrigen(edOrigen.getText().toString());
                                                datos.setDestino(edDestino.getText().toString());
                                                datos.setIdFrecuenciaUso(idFrecuenciaUsoGuarda);
                                                datos.setFecha(edFecha.getText().toString());
                                                datos.setIdFormaPago(idFormaPagoGuarda);
                                                datos.setIdIndicadorSinc(indicadorSincronizacion);
                                                datos.setObservaciones("");
                                                datos.setUsuarioCreacion(usuarioCreacion);
                                                datos.setUsuarioModificacion(usuarioModificacion);
                                                datos.setUsuarioEliminacion(usuarioEliminacion);
                                                datos.setFechaCreacion(currentDate);
                                                datos.setFechaModificacion(fechaModificacion);
                                                datos.setFechaEliminacion(fechaEliminacion);
                                                datos.setAltitud(altitud);
                                                datos.setLatitud(latitud);
                                                datos.setLongitud(longitud);

                                                if (con.actualizaDatosCliente(datos)) {
                                                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.guardaExitosoDatosCliente));
                                                    edEncuestador.setText("");
                                                    edOrigen.setText("");
                                                    edDestino.setText("");
                                                    edFecha.setText("");
                                                    spinnerAutopistas.setSelection(0, true);
                                                    spinnerPlazaCobro.setSelection(0, true);
                                                    spinnerFrecuencia.setSelection(0, true);
                                                    spinnerFormaPago.setSelection(0, true);
                                                    spinnerUbicacion.setSelection(0, true);

                                                    List listaDatosClientes = con.consultaDatosCliente();
                                                    for (Object infoCliente : listaDatosClientes) {
                                                        Esatis_Cliente elem = (Esatis_Cliente) infoCliente;
                                                        idEncuestaPreguntas = elem.idEncuesta;
                                                        idAutopistas = elem.idAutopista;
                                                    }

                                                    continuaEncuesta();

                                                } else {
                                                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.noGuardaExitosoDatosCliente));
                                                }

                                            } else {
                                                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaDescUbicacion));
                                            }
                                        } else {
                                            Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaUbicacion));
                                        }

                                    } else {
                                        Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaFormaPago));
                                    }
                                } else {
                                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaFecha));
                                }
                            } else {
                                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaFrecuenciaUso));
                            }

                        } else {
                            Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaDestino));
                        }
                    } else {
                        Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaOrigen));
                    }

                } else {
                    Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaEncuestador));
                }

            } else {
                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaPlazaCobro));
            }
           /* }else{
                Utils.Toast(DatosCliente.this, getResources().getString(R.string.validaAutopista));
            }*/

        }


    }

    private void regresaInicio() {
        finish();
        Intent linsertar = new Intent(DatosCliente.this, MainActivity.class);
        startActivity(linsertar);
    }


    public void Spinner(Spinner spinner, String[] datosSpinner) {
        spinner.setAdapter(null);
        SpinnerControl spinnerControl = new SpinnerControl(getApplicationContext());
        ArrayAdapter<String> adaptador = spinnerControl.obtenerAdaptador(datosSpinner);
        spinner.setAdapter(adaptador);

    }

    private void continuaEncuesta() {
      /*  Intent linsertar=new Intent(this, PreguntasActivity.class);
        linsertar.putExtra("idEncuestas", idEncuestaPreguntas);
        startActivity(linsertar);*/
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.campos.nombreAutopista, nombreAutopista);
        bundle.putInt("idEncuestas", idEncuestaPreguntas);
        bundle.putInt("idAutopista", idAutopistaUltimoRegistro);
        startActivity(new Intent(DatosCliente.this, PreguntasActivity.class).putExtras(bundle));
    }

    @Override
    public void onBackPressed() {
    }


    private void consultaFormDatosCliente() {

        listaEncuestasConsulta = con.consultaDatosClientePorIdEncuesta(idEncuestaConsulta);

        if (listaEncuestasConsulta.size() > 0) {
            int idAutopista = 0;
            int idPlazaCobros = 0;
            String nomPlazaCobro = "";
            String nomFrecuenciaUso = "";
            int numFrecUso = 0;
            String nombreFormaPago = "";
            int numFormaPago = 0;

            for (Object datos : listaEncuestasConsulta) {
                Esatis_Cliente info = (Esatis_Cliente) datos;
                edEncuestador.setText(info.encuestador);
                edOrigen.setText(info.origen);
                edDestino.setText(info.destino);
                idAutopista = info.idAutopista;
                idPlazaCobros = info.idPlazaCobro;
                numFrecUso = info.idFrecuenciaUso;
                edFecha.setText(info.fecha);
                numFormaPago = info.idFormaPago;
            }


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
            spinnerAutopistas.setSelection(adapterDetalleHor.getPosition(ControladorDatosCliente.obtieneNombreAutopista(idAutopista, DatosCliente.this)));

            int idAutop = 0;
            List listaAutopistas = con.consultaIdAutopista(spinnerAutopistas.getSelectedItem().toString());
            for (Object datos : listaAutopistas) {
                Esatis_Autopistas elementos = (Esatis_Autopistas) datos;
                idAutop = elementos.getIdAutopista();
            }

            List listaPlazaCobro = con.consultaPlazaCobroPorIdAutopita(idAutop);
            plazaCobro = new String[listaPlazaCobro.size() + 1];
            idPlazaCobro = new int[listaPlazaCobro.size() + 1];
            plazaCobro[0] = "Selecciona";
            idPlazaCobro[0] = 0;
            contador = 1;

            for (Object datos : listaPlazaCobro) {
                Esatis_Plaza_Cobro elementos = (Esatis_Plaza_Cobro) datos;
                idPlazaCobro[contador] = elementos.getIdPlazaCobroSQL();
                Log.e("idPlazaCobro[contador]", idPlazaCobro[contador] + "");
                plazaCobro[contador] = elementos.getPlazaCobro();
                contador = contador + 1;
            }

            for (int i = 0; i < idPlazaCobro.length; i++) {
                if (idPlazaCobros == idPlazaCobro[i]) {
                    nomPlazaCobro = plazaCobro[i];
                }
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


            if (nombreAutopista.trim().equalsIgnoreCase("Urbana Sur") ||
                    nombreAutopista.trim().equalsIgnoreCase("Viaducto Elevado Tlalpan")) {

                ArrayAdapter<String> adapterDetalleForma = new ArrayAdapter<String>(this, R.layout.custom_spinner, formaPago);
                spinnerFormaPago.setSelection(adapterDetalleForma.getPosition(TELEPEAJE));
                spinnerFormaPago.setEnabled(false);
            } else {
                spinnerFormaPago.setEnabled(true);
            }

        }


    }

    String nombreEncuestador = "";
    String nomPlazaCobro = "";

    private void consultaUltimoDatoDatosCliente() {

        listaEncuestasConsultaUltima = con.consultaUltimoDatosCliente(idAutopistaUltimoRegistro);

        if (listaEncuestasConsultaUltima.size() > 0) {
            int idAutopista = 0;
            int idPlazaCobros = 0;


            for (Object datos : listaEncuestasConsultaUltima) {
                Esatis_Cliente info = (Esatis_Cliente) datos;
                nombreEncuestador = info.encuestador;
                idAutopista = info.idAutopista;
                idPlazaCobros = info.idPlazaCobro;
            }

            int contador = 0;

            /*adapterAutopistas =  Funcionalidades.getAdapter(context, autopistas);
            spinnerAutopistas.setAdapter(adapterAutopistas);
            ArrayAdapter<String> adapterDetalleHor = new ArrayAdapter<String>(this, R.layout.custom_spinner, autopistas);
            spinnerAutopistas.setSelection(adapterDetalleHor.getPosition(ControladorDatosCliente.obtieneNombreAutopista(idAutopista, DatosCliente.this)));

            int idAutop = 0;
            List listaAutopistas = con.consultaIdAutopista(spinnerAutopistas.getSelectedItem().toString());
            for(Object datos: listaAutopistas){
                Esatis_Autopistas elementos=(Esatis_Autopistas) datos;
                idAutop= elementos.getIdAutopistaSQL();
            }*/

            List listaPlazaCobro = con.consultaPlazaCobroPorIdAutopita(idAutopistaUltimoRegistro);
            plazaCobro = new String[listaPlazaCobro.size() + 1];
            idPlazaCobro = new int[listaPlazaCobro.size() + 1];
            plazaCobro[0] = "Selecciona";
            idPlazaCobro[0] = 0;
            contador = 1;

            for (Object datos : listaPlazaCobro) {
                Esatis_Plaza_Cobro elementos = (Esatis_Plaza_Cobro) datos;
                idPlazaCobro[contador] = elementos.getIdPlazaCobroSQL();
                Log.e("idPlazaCobro[contador]", idPlazaCobro[contador] + "");
                plazaCobro[contador] = elementos.getPlazaCobro();
                contador = contador + 1;
            }

            for (int i = 0; i < idPlazaCobro.length; i++) {
                if (idPlazaCobros == idPlazaCobro[i]) {
                    nomPlazaCobro = plazaCobro[i];
                }
            }
            //Spinner(spinnerPlazaCobro, plazaCobro);

        }


    }

    private void seteaValores() {

        if (listaEncuestasConsultaUltima.size() > 0) {
            Log.e("nomPlazaCobroSetea", nomPlazaCobro);
            edEncuestador.setText(nombreEncuestador);
            adapterPlazasCobro = Funcionalidades.getAdapter(context, plazaCobro);
            spinnerPlazaCobro.setAdapter(adapterPlazasCobro);
            ArrayAdapter<String> adapterDetallePlaza = new ArrayAdapter<String>(this, R.layout.custom_spinner, plazaCobro);
            spinnerPlazaCobro.setSelection(adapterDetallePlaza.getPosition(nomPlazaCobro));
        }

        if (nombreAutopista.trim().equalsIgnoreCase("Urbana Sur") ||
                nombreAutopista.trim().equalsIgnoreCase("Viaducto Elevado Tlalpan")) {

            ArrayAdapter<String> adapterDetallePlaza = new ArrayAdapter<String>(this, R.layout.custom_spinner, formaPago);
            spinnerFormaPago.setSelection(adapterDetallePlaza.getPosition(TELEPEAJE));
            spinnerFormaPago.setEnabled(false);
        } else {
            spinnerFormaPago.setEnabled(true);
        }

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
                showAlertDialogEliminar();
                break;
            /*case R.id.menuGeneralTerminar:
                showAlertDialogEliminar();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAlertDialogEliminar() {
        AlertDialog alertDialog = new AlertDialog.Builder(DatosCliente.this).create();
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


}
