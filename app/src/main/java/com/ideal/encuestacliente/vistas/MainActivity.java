package com.ideal.encuestacliente.vistas;

import static com.ideal.encuestacliente.configuracion.Utils.getDatetimeAndEnvironment;
import static com.ideal.encuestacliente.configuracion.Utils.isOnlineNet;
import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;
import static com.ideal.encuestacliente.configuracion.Utils.toast;
import static com.ideal.encuestacliente.controladores.permissions.NotificationsPermissionManager.NOTIFICATION_PERMISSION_REQUEST_CODE;
import static com.ideal.encuestacliente.vistas.ui.components.dialogs.CustomAlertDialog.showAlertDialog;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.VolleyError;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.ideal.encuestacliente.BuildConfig;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.CoordenadasGps;
import com.ideal.encuestacliente.configuracion.SQLite;
import com.ideal.encuestacliente.configuracion.Utils;
import com.ideal.encuestacliente.controladores.permissions.NotificationsPermissionManager;
import com.ideal.encuestacliente.sincronizacion.GenericRequest;
import com.ideal.encuestacliente.sincronizacion.Urls;
import com.ideal.encuestacliente.sincronizacion.VolleyCallback;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;
import com.ideal.encuestacliente.tablas.UsuariosDb;
import com.ideal.encuestacliente.vistas.ui.components.dialogs.CustomAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NotificationsPermissionManager.NotificationPermissionListener {

    private CoordinatorLayout rootMain;
    private JSONArray jsonUsuario;
    private TextView txvErrorUsuario, txtVersion;
    private FloatingActionButton fab;
    private CardView cardViewNuevo, cardViewConsultaReporte, cardViewSincronizacion, buttonExportar;
    List listaAutopistas;
    List listaFormaPago;
    int tipoSinc = 0;
    private TextView textViewLogin;
    private TextInputEditText textInputEditTextUsuario;
    private TextInputEditText textInputEditTextPassword;
    private MaterialButton buttonAutenticar;
    private androidx.appcompat.app.AlertDialog alertDialogLogin;
    private Context contextoActivity;
    int tipoListaAutopistas = 0;

    private static final String TAG = MainActivity.class.getSimpleName();
    private NotificationsPermissionManager notificationsPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        actions();
        consultaCatalogos();
        notificationsPermission();
    }

    public void sincronizaCatalogos() {
        Log.e("pasa poraca", "1");
     /*   jsonUsuario = JsonSharePoint.Usuario("invitado_demo", "demo1234");
        SharePoint sharePoint = new SharePoint(new Rsin_UsuarioSharePointHelper(),getApplicationContext());
        sharePoint.accesoUsuario(jsonUsuario, txvErrorUsuario);*/
        open(MainActivity.this, 4, "invitado_demo");

    }

    public void init() {
        setContentView(R.layout.activity_main);
        rootMain = findViewById(R.id.rootMain);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setMarginInsets(rootMain);

        txtVersion = findViewById(R.id.txtVersion);
        txtVersion.setText(getDatetimeAndEnvironment(this));

        fab = findViewById(R.id.fab);

        cardViewNuevo = findViewById(R.id.cardViewNuevo);
        cardViewSincronizacion = findViewById(R.id.cardViewSincronizacion);
        cardViewConsultaReporte = findViewById(R.id.cardViewConsultaReporte);
        buttonExportar = findViewById(R.id.buttonExportar);
        contextoActivity = this.getApplicationContext();
    }

    public void actions() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Descarga catàlogos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                tipoSinc = 1;
                if (haveNetworkConnection() == true) {
                    //new conectividadCSVTask().execute();
                    customDialogLogin();
                } else {
                    showAlertDialogConexionCatalogos();
                }


            }
        });

        cardViewNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listaFormaPago.size() >= 3) {
                    //datosCliente();
                    tipoListaAutopistas = 1;
                    ListaAutopistas();

                } else {
                    Utils.Toast(MainActivity.this, getResources().getString(R.string.validaCatalogosDescargados));
                }
            }
        });

        cardViewConsultaReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listaFormaPago.size() >= 3) {

                    //listaEncuestas();
                    tipoListaAutopistas = 2;

                    ListaAutopistas();
                } else {
                    Utils.Toast(MainActivity.this, getResources().getString(R.string.validaCatalogosDescargadosConsulta));
                }

            }
        });

        cardViewSincronizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoSinc = 2;
                if (listaFormaPago.size() >= 3) {
                    if (haveNetworkConnection() == true) {
                        new conectividadCSVTask().execute();
                    } else {
                        showAlertDialogEliminarDatosConductores();
                    }

                } else {
                    Utils.Toast(MainActivity.this, getResources().getString(R.string.validaCatalogosDescargadosSinc));
                }
            }
        });

        buttonExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SQLite.exportarDataBase(contextoActivity))
                    Utils.Toast(MainActivity.this, getResources().getString(R.string.exportarCorrecto));
                else
                    Utils.Toast(MainActivity.this, getResources().getString(R.string.exportarIncorrecto));

            }
        });

    }

    public void mensajeGPS() {

        CoordenadasGps coordenadas =
                new CoordenadasGps(getApplicationContext(), this);

        ArrayList<String> coords = coordenadas.obtenerLocalización();

        if (coords != null) {
            ListaAutopistas();
        }

        /*if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            mensaje();
            return;
        }
        LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isEnabled =  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isEnabled){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            ListaAutopistas();
        }else{
            mensaje();
        }*/
    }

    private void mensaje() {
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("GPS");
        alertDialog.setMessage("Necesita habilitar el GPS antes de continuar.");
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void ListaAutopistas() {
        Bundle bundle = new Bundle();
        bundle.putInt("tipoListaAutopistas", tipoListaAutopistas);
        startActivity(new Intent(MainActivity.this, ListaAutopistas.class).putExtras(bundle));
    }


    private void consultaCatalogos() {

        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        listaAutopistas = con.consultaAutopistas();

        listaFormaPago = con.consultaFormaPago();

    }

    private void datosCliente() {
        Bundle bundle = new Bundle();
        bundle.putInt("idEncuestas", 0);
        startActivity(new Intent(MainActivity.this, DatosCliente.class).putExtras(bundle));
    }

    private void listaEncuestas() {
        Intent lencuestas = new Intent(this, ListaEncuestasActivity.class);
        startActivity(lencuestas);
    }

    private void listaEncuestasSincronizacion() {
        Intent lencuestas = new Intent(this, ListaEncuestasSincronizarActivity.class);
        startActivity(lencuestas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void open(Context context, int idUsuario, String nombreUsuario) {
        Bundle bundle = new Bundle();
        bundle.putInt("idUsuario", idUsuario);
        bundle.putString("nombreUsuario", nombreUsuario);

        //Intent intent = new Intent(context.getApplicationContext(), SyncDownActivity.class);
        Intent intent = new Intent(context.getApplicationContext(), DescargaCatalogosActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent.putExtras(bundle));
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

                if (tipoSinc == 2) {
                    tipoListaAutopistas = 3;
                    ListaAutopistas();
                    //listaEncuestasSincronizacion();
                } else if (tipoSinc == 1) {
                    sincronizaCatalogos();
                }

            }


        }

    }


    String nombreConexion = "";

    public void isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        nombreConexion = netInfo.getExtraInfo();
    }

    private void showAlertDialogConexion() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

    private void showAlertDialogConexionCatalogos() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(R.string.internet);
        alertDialog.setMessage(getString(R.string.conexionCatalogos));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.deAcuerdo),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }


    private void customDialogLogin() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.custom_dialog_autenticacion, null);
        textInputEditTextUsuario = view.findViewById(R.id.textInputUsuarioLogin);
        textInputEditTextPassword = view.findViewById(R.id.textInputPasswordLogin);
        buttonAutenticar = view.findViewById(R.id.buttonAutenticar);
        textViewLogin = view.findViewById(R.id.textViewLogin);
        buttonAutenticar.setOnClickListener(this);

        builder.setView(view);
        alertDialogLogin = builder.create();
        alertDialogLogin.show();

        if (BuildConfig.DEBUG) {
            textInputEditTextUsuario.setText("nsanchez");
            textInputEditTextPassword.setText("n5a7c8");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonAutenticar:
                Log.e("pasa", "Login");
                try {
                    final JSONObject json = new JSONObject();
                    json.put(getString(R.string.usuario), textInputEditTextUsuario.getText().toString());
                    json.put(getString(R.string.password), textInputEditTextPassword.getText().toString());
                    json.put(getString(R.string.idFormato), 19);
                    GenericRequest.requestPost(Urls.URL_AUTENTICACION, json, new VolleyCallback() {
                        @Override
                        public void onSucces(JSONObject jsonObject) {
                            /*try{
                                boolean acceso = jsonObject.getBoolean(getString(R.string.estatusAcceso));
                                if(acceso) {
                                    if(alertDialogLogin.isShowing())
                                        alertDialogLogin.dismiss();
                                    new conectividadCSVTask().execute("");
                                }
                                else{
                                    textViewLogin.setVisibility(View.VISIBLE);
                                    textViewLogin.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            textViewLogin.setVisibility(View.GONE);
                                        }},3000);
                                }
                            }catch (Exception e){ Log.e("",e.getMessage()); }*/
                            try {
                                boolean acceso = jsonObject.getBoolean(getString(R.string.estatusAcceso));
                                if (acceso) {
                                    if (!UsuariosDb.exists()) {
                                        UsuariosDb.save(json.getString(getString(R.string.usuario)), json.getString(getString(R.string.password)));
                                    } else {
                                        UsuariosDb.update(json.getString(getString(R.string.usuario)), json.getString(getString(R.string.password)));
                                    }
                                    if (alertDialogLogin.isShowing())
                                        alertDialogLogin.dismiss();
                                    startActivity(new Intent(MainActivity.this, DescargaCatalogosActivity.class));
                                } else {
                                    textViewLogin.setVisibility(View.VISIBLE);
                                    textViewLogin.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            textViewLogin.setVisibility(View.GONE);
                                        }
                                    }, 3000);
                                }
                            } catch (Exception e) {
                                Log.e("", e.getMessage());
                            }
                        }

                        @Override
                        public void onFail(VolleyError error) {
                            textViewLogin.setText(getString(R.string.errorServidor));
                            textViewLogin.setVisibility(View.VISIBLE);
                            textViewLogin.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textViewLogin.setVisibility(View.GONE);
                                }
                            }, 3000);
                        }
                    });
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                }
                break;
        }
    }

    private void notificationsPermission() {
        notificationsPermissionManager = new NotificationsPermissionManager(this, this);
        notificationsPermissionManager.checkNotificationsPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            notificationsPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onNotificationPermissionGranted() {
        Log.d(TAG, getString(R.string.permission_notifications_granted));
    }

    @Override
    public void onNotificationPermissionDenied() {
        toast(this, getString(R.string.permission_notifications_denied));
    }

    @Override
    public void onOpenSettings() {
        showAlertDialog(
                this,
                getString(R.string.permissions_title, getString(R.string.permissions_notifications_title)),
                getString(R.string.permissions_notifications_description),
                getString(R.string.open_settings),
                getString(R.string.not_now),
                new CustomAlertDialog.AlertDialogListener() {
                    @Override
                    public void onPositiveButtonClicked() {
                        notificationsPermissionManager.openSettings();
                    }

                    @Override
                    public void onNegativeButtonClicked() {
                        toast(MainActivity.this, getString(R.string.permission_notifications_denied));
                    }
                }
        );
    }
}
