package com.ideal.encuestacliente.vistas;

import static com.ideal.encuestacliente.configuracion.Utils.setMarginInsets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.configuracion.AutopistasAdapter;
import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.configuracion.SharedPreferencesManager;
import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import java.util.ArrayList;
import java.util.List;

public class ListaAutopistas extends AppCompatActivity implements View.OnClickListener{


    private ConstraintLayout rootMain;
    private AutopistasAdapter adapterAutopistas;
    private RecyclerView recyclerView;
    private List<Esatis_Autopistas> autopistas;
    int origenListaAutopistas=0;
    private BottomAppBar bottomAppBar;

    private SharedPreferencesManager preferences;

    private TextView txtUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_autopistas);
        rootMain = findViewById(R.id.rootMain);
        setMarginInsets(rootMain);

        Bundle parametros  = this.getIntent().getExtras();
        origenListaAutopistas = parametros.getInt("tipoListaAutopistas");
        Log.e("origenListaAutopistas", origenListaAutopistas+"");




        initControl();


        adapterAutopistas.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constantes.campos.nombreAutopista,
                        autopistas.get(recyclerView.getChildAdapterPosition(view)).getNombreAutopista());
                bundle.putInt("tipoListaAutopistas", origenListaAutopistas);

                if(origenListaAutopistas==1){

                    BaseDaoEncuesta con = new BaseDaoEncuesta(ListaAutopistas.this);

                    int idAutopist = con.regresaIDAutopista(autopistas.get(recyclerView.getChildAdapterPosition(view)).getNombreAutopista());
                    List listaPreguntasPorTipoAutopista = con.consultaPreguntasPorIdAutopista(idAutopist);

                    if(listaPreguntasPorTipoAutopista.size() > 0){
                        startActivity(new Intent(ListaAutopistas.this,DatosCliente.class).
                                putExtras(bundle));
                    }else{
                        Toast.makeText(ListaAutopistas.this, "Esta autopista no cuenta con preguntas para la encuesta", Toast.LENGTH_SHORT).show();
                    }

                }

                if(origenListaAutopistas==2){
                    startActivity(new Intent(ListaAutopistas.this,ListaEncuestasActivity.class).
                            putExtras(bundle));
                }

                if(origenListaAutopistas==3){
                    startActivity(new Intent(ListaAutopistas.this,ListaEncuestasSincronizarActivity.class).
                            putExtras(bundle));
                }

            }
        });

        recyclerView.setAdapter(adapterAutopistas);
    }

    @Override
    public void onClick(View view) {

    }


    private void initControl() {
        recyclerView = findViewById(R.id.newsRecyclerView);
        autopistas = new ArrayList<>();
        BaseDaoEncuesta con = new BaseDaoEncuesta(this);
        autopistas = con.consultaAutopistas();
        adapterAutopistas = new AutopistasAdapter(autopistas);


        bottomAppBar           = findViewById(R.id.buttonAppBarPersonas);
        setSupportActionBar(bottomAppBar);
        showUserData();
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

    private void regresaMainManu(){
        finish();
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
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

        }
        return super.onOptionsItemSelected(item);
    }




}
