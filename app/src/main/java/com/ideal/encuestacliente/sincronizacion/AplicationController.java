package com.ideal.encuestacliente.sincronizacion;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class AplicationController extends Application {

    public static final String TAG = AplicationController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AplicationController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // Initialize Firebase:
        FirebaseApp.initializeApp(getInstance());
        getTokenFirebase();
    }

    public static synchronized AplicationController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    private void getTokenFirebase() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String tokenId = task.getResult();
                        Log.d(TAG, "tokenId: " + tokenId);
                    } else {
                        Log.e(TAG, "Error: No se pudo obtener el token");
                    }
                });
    }
}
