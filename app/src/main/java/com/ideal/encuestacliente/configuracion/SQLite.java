package com.ideal.encuestacliente.configuracion;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class SQLite {

    private static final String TAG = SQLite.class.getSimpleName();
    private static final String folderData = "//data/";
    private static final String folderDatabases = "/databases/";

    public static boolean exportarDataBase(Context context) {
        String aplicacionId = Constantes.APPLICATION_ID;
        File sd = context.getApplicationContext().getExternalFilesDir(BaseDaoEncuesta.DATABASE_NAME);
        File data = Environment.getDataDirectory();

        if (sd.canWrite()) {
            try {
                FileChannel source = null;
                FileChannel destination = null;
                String currentDBPath = folderData + aplicacionId + folderDatabases + BaseDaoEncuesta.DATABASE_NAME;
                String backupDBPath = BaseDaoEncuesta.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    source = new FileInputStream(currentDB).getChannel();
                    destination = new FileOutputStream(backupDB).getChannel();
                    destination.transferFrom(source, 0, source.size());
                    source.close();
                    destination.close();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean importDatabase(Context context) {
        try {
            copyDatabaseFromAssets(context, BaseDaoEncuesta.DATABASE_NAME);
            Log.d(TAG, "onImport Database Successful");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "onImport Database Error: " + e.getMessage());
            return false;
        }
    }

    private static void copyDatabaseFromAssets(Context context, String dbName) throws IOException {
        InputStream myInput = context.getAssets().open(dbName);

        String outFileName = context.getDatabasePath(dbName).getPath();
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
