package com.ideal.encuestacliente.controladores.permissions;

import static com.ideal.encuestacliente.configuracion.Utils.isAndroid13OrHigher;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NotificationsPermissionManager {

    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1003;

    private final Activity activity;
    private final NotificationPermissionListener permissionListener;

    public interface NotificationPermissionListener {
        void onNotificationPermissionGranted();

        void onNotificationPermissionDenied();

        void onOpenSettings();
    }

    public NotificationsPermissionManager(Activity activity, NotificationPermissionListener listener) {
        this.activity = activity;
        this.permissionListener = listener;
    }

    public void checkNotificationsPermission() {
        if (isAndroid13OrHigher()) {
            requestNotificationPermission();
        } else {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                permissionListener.onNotificationPermissionGranted();
            } else {
                permissionListener.onOpenSettings();
            }
        }
    }

    private void requestNotificationPermission() {
        String[] permissions;
        if (isAndroid13OrHigher()) {
            permissions = new String[]{Manifest.permission.POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(activity, permissions, NOTIFICATION_PERMISSION_REQUEST_CODE);
        }
    }

    private int checkSelfPermission(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                permissionListener.onNotificationPermissionGranted();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    permissionListener.onNotificationPermissionDenied();
                } else {
                    permissionListener.onOpenSettings();
                }
            }
        }
    }

    private boolean shouldShowRequestPermissionRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
