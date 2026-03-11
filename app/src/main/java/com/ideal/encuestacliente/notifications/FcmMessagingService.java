package com.ideal.encuestacliente.notifications;

import static com.ideal.encuestacliente.configuracion.Utils.isAndroid12OrHigher;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ideal.encuestacliente.R;

public class FcmMessagingService extends FirebaseMessagingService {

    private final String ADMIN_CHANNEL_ID ="admin_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null &&
                remoteMessage.getData().size() > 0 && remoteMessage.getNotification() != null){
            sendNotification(remoteMessage);
        } else {
            sendEmptyNotification();
        }
    }

    private void sendEmptyNotification() {
        float desc = 0.15f;

        // Obtener el paquete de la aplicación
        final String appPackageName = getPackageName();

        Intent intent = null;
        try {
            // Intent para abrir la aplicación en Google Play Store
            Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Agregar la bandera FLAG_ACTIVITY_NEW_TASK
            startActivity(intentWeb);
            PendingIntent pendingIntent = getPendingIntent(intentWeb);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification.Builder notificationBuilder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Por favor, sincronice su información y actualice la app*.")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .addAction(R.mipmap.ic_launcher_foreground, "RECHAZAR", null)
                    .addAction(R.mipmap.ic_launcher_foreground, "CONTESTAR", pendingIntent)
                    .setContentIntent(pendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                notificationBuilder.setPriority(Notification.PRIORITY_MAX);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = getString(R.string.normal_channel_id);
                String channelName = getString(R.string.normal_channel_name);
                NotificationChannel channel = new NotificationChannel(channelId, channelName,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 200, 50});
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
                notificationBuilder.setChannelId(channelId);
            }

            if (notificationManager != null) {
                notificationManager.notify("", 0, notificationBuilder.build());
            }
        } catch (android.content.ActivityNotFoundException anfe) {
            // Si no se puede abrir Google Play Store, abrir la página de la aplicación en el navegador web
            Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Agregar la bandera FLAG_ACTIVITY_NEW_TASK
            PendingIntent pendingIntentWeb = getPendingIntent(intentWeb);

            // Crear la notificación con el PendingIntent para abrir la página de la aplicación en el navegador web
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification.Builder notificationBuilder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Por favor, sincronice su información y actualice la app.")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntentWeb);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                notificationBuilder.setPriority(Notification.PRIORITY_MAX);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = getString(R.string.normal_channel_id);
                String channelName = getString(R.string.normal_channel_name);
                NotificationChannel channel = new NotificationChannel(channelId, channelName,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 200, 50});
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
                notificationBuilder.setChannelId(channelId);
            }

            if (notificationManager != null) {
                notificationManager.notify("", 0, notificationBuilder.build());
            }
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String descStr = remoteMessage.getData().get(ADMIN_CHANNEL_ID);
        float desc = Float.valueOf(descStr != null? descStr : "0");

        // Obtener el paquete de la aplicación
        final String appPackageName = getPackageName();

        try {
            Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Agregar la bandera FLAG_ACTIVITY_NEW_TASK
            startActivity(intentWeb);
            PendingIntent pendingIntent = getPendingIntent(intentWeb);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            RemoteMessage.Notification notification = remoteMessage.getNotification();
            if (notification != null) {
                Notification.Builder notificationBuilder = new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_foreground)
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .addAction(R.mipmap.ic_launcher_foreground, "RECHAZAR", null)
                        .addAction(R.mipmap.ic_launcher_foreground, "CONTESTAR", pendingIntent)
                        .setContentIntent(pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notificationBuilder.setColor(desc > .4 ?
                            ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) :
                            ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    notificationBuilder.setPriority(Notification.PRIORITY_MAX);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = desc < .10 ? getString(R.string.low_channel_id) :
                            getString(R.string.normal_channel_id);
                    String channelName = desc < .10 ? getString(R.string.low_channel_name) :
                            getString(R.string.normal_channel_name);
                    NotificationChannel channel = new NotificationChannel(channelId, channelName,
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[]{100, 200, 200, 50});
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }

                    notificationBuilder.setChannelId(channelId);
                }

                if (notificationManager != null) {
                    notificationManager.notify("", 0, notificationBuilder.build());
                }
            }
        } catch (android.content.ActivityNotFoundException anfe) {
            // Si no se puede abrir Google Play Store, abrir la página de la aplicación en el navegador web
            Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Agregar la bandera FLAG_ACTIVITY_NEW_TASK
            PendingIntent pendingIntentWeb = getPendingIntent(intentWeb);

            // Crear la notificación con el PendingIntent para abrir la página de la aplicación en el navegador web
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            if (notification != null) {
                Notification.Builder notificationBuilder = new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_foreground)
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntentWeb);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notificationBuilder.setColor(desc > .4 ?
                            ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) :
                            ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    notificationBuilder.setPriority(Notification.PRIORITY_MAX);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = desc < .10 ? getString(R.string.low_channel_id) :
                            getString(R.string.normal_channel_id);
                    String channelName = desc < .10 ? getString(R.string.low_channel_name) :
                            getString(R.string.normal_channel_name);
                    NotificationChannel channel = new NotificationChannel(channelId, channelName,
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[]{100, 200, 200, 50});
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }

                    notificationBuilder.setChannelId(channelId);
                }

                if (notificationManager != null) {
                    notificationManager.notify("", 0, notificationBuilder.build());
                }
            }
        }
    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.d("newToken", token);
    }

    private PendingIntent getPendingIntent(Intent intent) {
        int flags = isAndroid12OrHigher() ? PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;
        return PendingIntent.getActivity(this, 0, intent, flags);
    }
}
