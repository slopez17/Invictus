package co.com.uma.mseei.invictus.viewmodel.service;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.getActivity;
import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_STEP_COUNTER;
import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
import static co.com.uma.mseei.invictus.R.string.error_saved;
import static co.com.uma.mseei.invictus.R.string.notification_title;
import static co.com.uma.mseei.invictus.util.Constants.SEMICOLON;
import static co.com.uma.mseei.invictus.util.Debug.getMethodName;
import static co.com.uma.mseei.invictus.util.Debug.showExecutionPoint;
import static co.com.uma.mseei.invictus.util.Resource.getStringById;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.YYYY_MM_DD__HH_MM_SS;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.min2ms;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.s2ms;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.app.Application;
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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import co.com.uma.mseei.invictus.MainActivity;
import co.com.uma.mseei.invictus.model.database.Speed;
import co.com.uma.mseei.invictus.model.service.Acceleration;
import co.com.uma.mseei.invictus.model.service.AccelerometerServiceData;
import co.com.uma.mseei.invictus.model.service.AccelerometerServiceParameters;
import co.com.uma.mseei.invictus.viewmodel.database.SpeedRepository;
import co.com.uma.mseei.invictus.viewmodel.database.SportRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ListenAccelerometerService
        extends Service
        implements SensorEventListener {
    public static final String ACCELEROMETER_SERVICE_PARAMETERS = "accelerometerServiceParameters";
    private static final int NOTIFICATION_ID = 567;
    private static final String CHANNEL_ID = "ListenAccelerometerServiceChannel";
    private static final String CHANNEL_NAME = "ListenAccelerometerService Channel";

    private IBinder binder;
    private SensorManager sensorManager;
    private CompositeDisposable compositeDisposable;

    private AccelerometerServiceData data;
    private SpeedRepository speedRepository;
    private SportRepository sportRepository;
    private Timer speedTimer;
    private Timer sportTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new BinderAccess();
        setRepositories();
        setSensors();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = new AccelerometerServiceData(getAccelerometerServiceParameters(intent));
        setTimers();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        showExecutionPoint(this, data.parameters.isDebugOn(), getMethodName());
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        showExecutionPoint(this, data.parameters.isDebugOn(), getMethodName());
        stopTimers();
        return super.onUnbind(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        showExecutionPoint(this, data.parameters.isDebugOn(), getMethodName());
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showExecutionPoint(this, data.parameters.isDebugOn(), getMethodName());
        compositeDisposable.dispose();
        sensorManager.unregisterListener(this);
        stopForeground(true);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(data != null) {
            switch (sensorEvent.sensor.getType()) {
                case TYPE_ACCELEROMETER:
                    data.setTime();
                    data.setAcceleration(sensorEvent);
                    saveAccelerationSamplesOnFile();
                    data.setFalls();
                    break;
                case TYPE_STEP_COUNTER:
                    int steps = (int) sensorEvent.values[0];
                    data.setTime();
                    data.setSteps(steps);
                    data.setDistance();
                    data.setCalories();
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public String[] getData() {
        return data.getScreenData();
    }

    public class BinderAccess extends Binder {
        public ListenAccelerometerService getService() {
            return ListenAccelerometerService.this;
        }
    }

    private AccelerometerServiceParameters getAccelerometerServiceParameters(Intent intent) {
        return (AccelerometerServiceParameters) intent.getSerializableExtra(ACCELEROMETER_SERVICE_PARAMETERS);
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
        String contentText = getString(data.parameters.getSportType().getName());
        int icon = data.parameters.getSportType().getIcon();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void setRepositories() {
        Application application = getApplication();
        speedRepository = new SpeedRepository(application);
        sportRepository = new SportRepository(application);
        compositeDisposable = new CompositeDisposable();
    }

    private void setSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager != null){
            configureSensor(TYPE_ACCELEROMETER);
            configureSensor(TYPE_STEP_COUNTER);
        } else {
            showExecutionPoint(this, data.parameters.isDebugOn(), getMethodName(), "Sensor manager wasn't found");
            this.stopSelf();
        }
    }

    private void configureSensor(int sensorType) {
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(sensorType);
        if(sensorAccelerometer != null){
            sensorManager.registerListener(this, sensorAccelerometer, SENSOR_DELAY_GAME);
        }
    }

    private void setTimers() {
        speedTimer = new Timer();
        TimerTask speedTimerTask = new TimerTask() {
            @Override
            public void run() {
                saveSpeedOnDatabase();
            }
        };
        speedTimer.schedule(speedTimerTask,0, s2ms(data.parameters.getSpeedPeriod()));

        sportTimer = new Timer();
        TimerTask sportTimerTask = new TimerTask() {
            @Override
            public void run() {
                saveSportOnDatabase();
            }
        };
        sportTimer.schedule(sportTimerTask,0, min2ms(data.parameters.getSportPeriod()));
    }

    private void stopTimers() {
        if (speedTimer != null) {
            speedTimer.cancel();
            speedTimer = null;
        }

        if (sportTimer != null) {
            sportTimer.cancel();
            sportTimer = null;
        }
    }

    private void saveSpeedOnDatabase() {
        data.setSpeed();
        Speed speed = new Speed(data.parameters.getSportId(), data.getElapsedTime(), data.getSpeed());
        Disposable disposable = speedRepository.insertSpeed(speed)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(() -> showExecutionPoint(this, data.parameters.isDebugOn(), getMethodName()),
                        throwable -> Log.e(getMethodName(), getStringById(this, error_saved), throwable));
        compositeDisposable.add(disposable);
    }

    private void saveSportOnDatabase() {
//        AtomicReference<Float> maxSpeed = new AtomicReference<>((float) 0);
//        AtomicReference<Float> avgSpeed = new AtomicReference<>((float) 0);
//
//        Disposable disposable = speedRepository.getMaxSpeed(parameters.getSportId())
//                .subscribeOn(io())
//                .observeOn(mainThread())
//                .subscribe(maxSpeed::set,
//                        throwable -> Log.e(getMethodName(), getStringById(this, error_saved), throwable));
//        compositeDisposable.add(disposable);
//
//        disposable = speedRepository.getAvgSpeed(parameters.getSportId())
//                .subscribeOn(io())
//                .observeOn(io())
//                .subscribe(avgSpeed::set,
//                        throwable -> Log.e(getMethodName(), getStringById(this, error_saved), throwable));
//        compositeDisposable.add(disposable);

        Disposable disposable = sportRepository.insertSport(data.getSport())
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        () -> showExecutionPoint(this, data.parameters.isDebugOn(), getMethodName()),
                        throwable -> Log.e(getMethodName(), getStringById(this, error_saved), throwable));
        compositeDisposable.add(disposable);
    }

    private void saveAccelerationSamplesOnFile() {
        if (data.parameters.isSaveOnSdOn()) {
            Thread thread = new Thread(() -> writeFile(data.getAcceleration()));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void writeFile(Acceleration acceleration) {
        File downloadsDirectory = new File(getExternalFilesDir(null), "Downloads");

        if(downloadsDirectory.exists() || downloadsDirectory.mkdir()) {
            File file = new File(downloadsDirectory, data.parameters.getFileName());

            try {
                FileWriter writer = new FileWriter(file, true);
                String row =
                        acceleration.getTimestamp() + SEMICOLON +
                                acceleration.getDate(YYYY_MM_DD__HH_MM_SS) + SEMICOLON +
                                acceleration.getXAxis() + SEMICOLON +
                                acceleration.getyAxis() + SEMICOLON +
                                acceleration.getzAxis() + SEMICOLON +
                                acceleration.getMagnitude();
                writer.append(row).append("\n");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
