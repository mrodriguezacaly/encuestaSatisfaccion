package com.ideal.encuestacliente.vistas.ui.components.dialogs;

import android.app.AlertDialog;
import android.content.Context;

public class CustomAlertDialog {

    public interface AlertDialogListener {
        void onPositiveButtonClicked();

        void onNegativeButtonClicked();
    }

    public static void showAlertDialog(Context context, String title, String message,
                                       String positiveButtonText, String negativeButtonText,
                                       final AlertDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(positiveButtonText, (dialog, which) -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onPositiveButtonClicked();
            }
        });

        builder.setNegativeButton(negativeButtonText, (dialog, which) -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onNegativeButtonClicked();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
