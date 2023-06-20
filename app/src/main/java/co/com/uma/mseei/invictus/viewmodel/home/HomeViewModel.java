package co.com.uma.mseei.invictus.viewmodel.home;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_AUTO_CREATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.R.string.no_implemented;
import static co.com.uma.mseei.invictus.R.string.sportype_error;
import static co.com.uma.mseei.invictus.R.string.start_traking;
import static co.com.uma.mseei.invictus.model.SportType.values;
import static co.com.uma.mseei.invictus.view.home.SportSelectionActivity.SELECTED_SPORT;
import static co.com.uma.mseei.invictus.view.home.StopTrackingConfirmationActivity.STOP_TRACKING;
import static co.com.uma.mseei.invictus.viewmodel.service.ListenAccelerometerService.ACCELEROMETER_SERVICE_PARAMETERS;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Timer;
import java.util.TimerTask;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.SportType;
import co.com.uma.mseei.invictus.model.service.AccelerometerServiceParameters;
import co.com.uma.mseei.invictus.viewmodel.service.ListenAccelerometerService;
import co.com.uma.mseei.invictus.viewmodel.service.ServiceChecker;

public class HomeViewModel extends AndroidViewModel {

    private final long REFRESH_PERIOD;
    private IBinder binder;
    private Timer timer;
    private final AppPreferences appPreferences;
    private final MutableLiveData<Boolean> serviceBound;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appPreferences = new AppPreferences(application);
        serviceBound = new MutableLiveData<>();
        REFRESH_PERIOD = appPreferences.getRefreshPeriod();
        assureServiceState(application);
    }

    public LiveData<Boolean> isServiceBound() {
        return serviceBound;
    }

    public void startTrackingFor(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            int selectedSport = requireNonNull(data).getExtras().getInt(SELECTED_SPORT);
            SportType sportType = values()[selectedSport];
            startService(sportType);
            showStartTrackingMessage(sportType);
        } else {
            showStartTrackingMessage();
        }
    }

    public boolean stopTrackingFor(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            boolean stopTracking = requireNonNull(data).getExtras().getBoolean(STOP_TRACKING);
            if(stopTracking) {
                return stopService();
            }
        }
        return false;
    }

    private void startService(SportType sportType) {
        if(sportType.isImplemented()) {
            Application application = getApplication();
            Intent intent = new Intent(application, ListenAccelerometerService.class);
            AccelerometerServiceParameters parameters = new AccelerometerServiceParameters();
            parameters.setGender(appPreferences.getGender());
            parameters.setWeight(appPreferences.getWeight());
            int sportId = appPreferences.getSportId() + 1;
            appPreferences.setSportId(sportId);
            parameters.setSportId(sportId);
            parameters.setSportType(sportType);
            parameters.setAutofinish(appPreferences.isAutoFinishOn());
            parameters.setSamplesLimit(appPreferences.getSamplesLimit());
            parameters.setSamplesOnMemory(appPreferences.getSamplesOnMemory());
            parameters.setSaveOnSd(appPreferences.isSaveOnSdOn());
            parameters.setFileName(appPreferences.getFileName(), appPreferences.isMultipleFilesOn());
            parameters.setDebug(appPreferences.isDebugOn());

            intent.putExtra(ACCELEROMETER_SERVICE_PARAMETERS, parameters);
            application.startForegroundService(intent);
            application.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = iBinder;
            setServiceBound(true);
            setRefreshDataTimer(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void showStartTrackingMessage() {
        Application application = getApplication();
        String message = application.getString(sportype_error);
        makeText(application, message, LENGTH_LONG).show();
    }

    private void showStartTrackingMessage(SportType sportType) {
        Application application = getApplication();
        String message;
        if(sportType.isImplemented()) {
            message = format("%s %s", application.getString(start_traking),
                    application.getString(sportType.getName()));
        } else {
            message = application.getString(no_implemented);
        }
        makeText(application, message, LENGTH_LONG).show();
    }

    private boolean stopService() {
        if(appPreferences.isServiceBound()){
            getApplication().unbindService(serviceConnection);
            setServiceBound(false);
        }
        Application application = getApplication();
        Intent intent = new Intent(application,  ListenAccelerometerService.class);
        return application.stopService(intent);
    }

    private void assureServiceState(@NonNull Application application) {
        boolean isListenAccelerometerServiceRunning = new ServiceChecker(application).isServiceRunning(ListenAccelerometerService.class);
        boolean isServiceBound = appPreferences.isServiceBound();
        if (isServiceBound){
            if (!isListenAccelerometerServiceRunning){
                setServiceBound(false);
            }
        } else {
            serviceBound.setValue(false);
        }
    }

    private void setServiceBound(boolean state) {
        serviceBound.setValue(state);
        appPreferences.setServiceBound(state);
    }

    public void setRefreshDataTimer(boolean state) {
        boolean serviceBound = TRUE.equals(this.serviceBound.getValue());
        if(serviceBound) {
            if (state) {
                startRefreshDataTimer();
            } else {
                stopRefreshDataTimer();
            }
        }
    }

    private void startRefreshDataTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getListenAccelerometerServiceData();
            }
        };
        timer.schedule(timerTask,0, REFRESH_PERIOD * 1000L);
    }

    private void getListenAccelerometerServiceData() {
        ListenAccelerometerService.BinderAccess access = (ListenAccelerometerService.BinderAccess) binder;
        ListenAccelerometerService listenAccelerometerService = access.getService();
        String hey = listenAccelerometerService.getData();
        Log.i("juapa", hey);

    }

    private void stopRefreshDataTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }





}