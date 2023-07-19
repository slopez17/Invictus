package co.com.uma.mseei.invictus.viewmodel.service;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.getActivity;
import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
import static java.lang.System.currentTimeMillis;
import static co.com.uma.mseei.invictus.R.string.notification_title;
import static co.com.uma.mseei.invictus.util.DebugOperations.getMethodName;
import static co.com.uma.mseei.invictus.util.DebugOperations.showExecutionPoint;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import co.com.uma.mseei.invictus.MainActivity;
import co.com.uma.mseei.invictus.model.service.Acceleration;
import co.com.uma.mseei.invictus.model.service.AccelerometerServiceParameters;

public class ListenAccelerometerService
        extends Service
        implements SensorEventListener {

    public static final String  ACCELEROMETER_SERVICE_PARAMETERS = "accelerometerServiceParameters";
    private AccelerometerServiceParameters parameters;
    private static final int NOTIFICATION_ID = 567;
    private static final String CHANNEL_ID = "ListenAccelerometerServiceChannel";
    private static final String CHANNEL_NAME = "ListenAccelerometerService Channel";
    private IBinder binder;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorSignificantMotion;
    private Sensor sensorStepCounter;

    private ArrayList<Acceleration> accelerationArrayList;

    @Override
    public void onCreate() {
        super.onCreate();

        binder = new BinderAccess();
        accelerationArrayList = new ArrayList<>();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager != null){
            sensorAccelerometer = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
            if(sensorAccelerometer != null){
                sensorManager.registerListener(this,sensorAccelerometer, SENSOR_DELAY_GAME);
            }
//            sensorSignificantMotion = sensorManager.getDefaultSensor(TYPE_SIGNIFICANT_MOTION);
//            sensorStepCounter = sensorManager.getDefaultSensor(TYPE_STEP_COUNTER);

        } else {
            showExecutionPoint(this, parameters.isDebugOn(), getMethodName(), "Sensor manager wasn't found");
            this.stopSelf();
        }
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
        showExecutionPoint(this, parameters.isDebugOn(), getMethodName());
        return binder;
    }



    @Override
    public boolean onUnbind(Intent intent) {
        showExecutionPoint(this, parameters.isDebugOn(), getMethodName());
        return super.onUnbind(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        showExecutionPoint(this, parameters.isDebugOn(), getMethodName());
        return super.stopService(name);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == TYPE_ACCELEROMETER) {
            getAcceleration(sensorEvent);
        }
    }

    private void getAcceleration(SensorEvent sensorEvent) {
        Acceleration acceleration = new Acceleration();
        acceleration.setxAxis(sensorEvent.values[0]);
        acceleration.setYAxis(sensorEvent.values[1]);
        acceleration.setZAxis(sensorEvent.values[2]);
        acceleration.setTimestamp(currentTimeMillis());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public String getData() {
        return "holiwi preciosura";
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
        PendingIntent pendingIntent = getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .build();
    }

}
