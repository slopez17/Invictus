package co.com.uma.mseei.invictus.viewmodel.home;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_AUTO_CREATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.R.string.no_implemented;
import static co.com.uma.mseei.invictus.R.string.permission;
import static co.com.uma.mseei.invictus.R.string.sportype_error;
import static co.com.uma.mseei.invictus.R.string.start_traking;
import static co.com.uma.mseei.invictus.model.SportType.values;
import static co.com.uma.mseei.invictus.util.Constants.CLOSE_PARENTHESIS;
import static co.com.uma.mseei.invictus.util.Constants.OPEN_PARENTHESIS;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.KM_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.s2ms;
import static co.com.uma.mseei.invictus.view.home.SportSelectionActivity.SELECTED_SPORT;
import static co.com.uma.mseei.invictus.view.home.StopTrackingConfirmationActivity.STOP_TRACKING;
import static co.com.uma.mseei.invictus.viewmodel.service.ListenAccelerometerService.ACCELEROMETER_SERVICE_PARAMETERS;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

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

    private final AppPreferences appPreferences;
    private final MutableLiveData<Boolean> serviceBound;
    private final MutableLiveData<SportType> sportType;
    private final MutableLiveData<String> falls;
    private final MutableLiveData<String> steps;
    private final MutableLiveData<String> calories;
    private final MutableLiveData<String> distance;
    private final MutableLiveData<String> distanceUnd;
    private final MutableLiveData<String> speed;
    private final MutableLiveData<String> elapsedTime;
    private IBinder binder;
    private Timer timer;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        serviceBound = new MutableLiveData<>();
        sportType = new MutableLiveData<>();
        falls = new MutableLiveData<>();
        steps = new MutableLiveData<>();
        calories = new MutableLiveData<>();
        speed = new MutableLiveData<>();
        elapsedTime = new MutableLiveData<>();
        distance = new MutableLiveData<>();
        distanceUnd = new MutableLiveData<>();
        distanceUnd.setValue(format(" %s%s%s", OPEN_PARENTHESIS, KM_UND, CLOSE_PARENTHESIS));

        appPreferences = new AppPreferences(application);
        assureServiceState(application);
    }

    public LiveData<Boolean> isServiceBound() {
        return serviceBound;
    }

    public LiveData<SportType> getSportType() {
        return sportType;
    }

    public LiveData<String> getFalls() {
        return falls;
    }

    public LiveData<String> getSteps() {
        return steps;
    }

    public LiveData<String> getCalories() {
        return calories;
    }

    public LiveData<String> getDistance() {
        return distance;
    }

    public LiveData<String> getDistanceUnd() {
        return distanceUnd;
    }

    public LiveData<String> getSpeed() {
        return speed;
    }

    public LiveData<String> getElapsedTime() {
        return elapsedTime;
    }

    public void startTrackingFor(ActivityResult result) {
        if (appPreferences.isPermissionGranted()) {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                int selectedSport = requireNonNull(data).getExtras().getInt(SELECTED_SPORT);
                SportType sportType = values()[selectedSport];
                startService(sportType);
                showStartTrackingMessage(sportType);
            } else {
                showStartTrackingMessage();
            }
        } else {
            Application application = getApplication();
            makeText(application, application.getString(permission), LENGTH_LONG).show();
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

    private void startService(SportType sportType) {
        if(sportType.isImplemented()) {
            this.sportType.setValue(sportType);
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
            parameters.setSpeedPeriod(appPreferences.getSpeedPeriod());
            parameters.setSportPeriod(appPreferences.getSportPeriod());

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

    private void startRefreshDataTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getListenAccelerometerServiceData();
            }
        };
        timer.schedule(timerTask,0, s2ms(appPreferences.getRefreshPeriod()));
    }

    private void getListenAccelerometerServiceData() {
        ListenAccelerometerService.BinderAccess access = (ListenAccelerometerService.BinderAccess) binder;
        ListenAccelerometerService service = access.getService();
        String[] data = service.getData();
        falls.postValue(data[0]);
        steps.postValue(data[1]);
        calories.postValue(data[2]);
        distance.postValue(data[3]);
        speed.postValue(data[4]);
        elapsedTime.postValue(data[5]);
    }

    private void stopRefreshDataTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
