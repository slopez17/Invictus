package co.com.uma.mseei.invictus.viewmodel.service;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.app.PendingIntent.getActivity;
import static co.com.uma.mseei.invictus.R.string.notification_title;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import co.com.uma.mseei.invictus.MainActivity;
import co.com.uma.mseei.invictus.model.AccelerometerServiceParameters;

public class ListenAccelerometerService
        extends Service
        implements SensorEventListener {

    public static final String  ACCELEROMETER_SERVICE_PARAMETERS = "accelerometerServiceParameters";
    private AccelerometerServiceParameters parameters;
    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "MyForegroundServiceChannel";
    private static final String CHANNEL_NAME = "My Foreground Service Channel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getAccelerometerServiceParameters(intent);
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void getAccelerometerServiceParameters(Intent intent) {
        parameters = (AccelerometerServiceParameters) intent.getSerializableExtra(ACCELEROMETER_SERVICE_PARAMETERS);
    }

    private void createNotificationChannel() {
        NotificationChannel channel =
                new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    @NonNull
    private Notification createNotification() {
        String contentTitle = getString(notification_title);
        String contentText = getString(parameters.getSportType().getSportName());
        int icon = parameters.getSportType().getSportIcon();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = getActivity(this, 0, notificationIntent, 0);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .build();
    }

}
