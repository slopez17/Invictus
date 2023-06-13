package co.com.uma.mseei.invictus.viewmodel.service;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.app.PendingIntent.getActivity;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.String.format;
import static co.com.uma.mseei.invictus.R.string.notification_title;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getMethodName;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import co.com.uma.mseei.invictus.MainActivity;
import co.com.uma.mseei.invictus.model.service.AccelerometerServiceParameters;

public class ListenAccelerometerService
        extends Service
        implements SensorEventListener {

    public static final String  ACCELEROMETER_SERVICE_PARAMETERS = "accelerometerServiceParameters";
    private AccelerometerServiceParameters parameters;
    private static final int NOTIFICATION_ID = 567;
    private static final String CHANNEL_ID = "ListenAccelerometerServiceChannel";
    private static final String CHANNEL_NAME = "ListenAccelerometerService Channel";
    private final String CLASS_NAME = this.getClass().getSimpleName();
    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new BinderAccess();
    }

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
        if (parameters.isDebugOn()){
            String message = format("%s -> %s", CLASS_NAME, getMethodName());
            makeText(this, message, LENGTH_SHORT).show();
        }
        return binder;
    }

    @Override
    public boolean stopService(Intent name) {
        if (parameters.isDebugOn()){
            String message = format("%s -> %s", CLASS_NAME, getMethodName());
            makeText(this, message, LENGTH_SHORT).show();
        }
        return super.stopService(name);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public boolean getSomething() {
        return true;
    }

    public class BinderAccess extends Binder {
        public ListenAccelerometerService getService() {
            return ListenAccelerometerService.this;
        }
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
        String contentText = getString(parameters.getSportType().getName());
        int icon = parameters.getSportType().getIcon();

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
