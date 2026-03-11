package com.ideal.encuestacliente.configuracion;

import static com.ideal.encuestacliente.configuracion.Constantes.USER_PREF;
import static com.ideal.encuestacliente.configuracion.Constantes.USER_PREF_DATA;
import static com.ideal.encuestacliente.configuracion.Utils.deserialize;
import static com.ideal.encuestacliente.configuracion.Utils.serialize;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ideal.encuestacliente.model.Usuario;

public class SharedPreferencesManager {

    private static final String TAG = SharedPreferencesManager.class.getSimpleName();
    private final SharedPreferences sharedPreferences;
    private static SharedPreferencesManager instance;

    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    public void saveUserData(Usuario data) {
        String json = serialize(data);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PREF_DATA, json);
        editor.apply();
        Log.d(TAG, "onSaveUserData successful");
    }

    private Usuario getUserData() {
        String serialized = sharedPreferences.getString(USER_PREF_DATA, null);
        return serialized != null ? deserialize(serialized, Usuario.class) : null;
    }

    public String getUserDataFormat() {
        Usuario userData = getUserData();
        return (userData != null) ? String.format("%s - %s - %s", userData.getNombre(), userData.getNombreUsuario(), userData.getPuesto()) : "";
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Log.d(TAG, "onClearUserData successful");
    }
}