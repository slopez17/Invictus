package co.com.uma.mseei.invictus.viewmodel.home;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_AUTO_CREATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
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

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import co.com.uma.mseei.invictus.model.service.AccelerometerServiceParameters;
import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.SportType;
import co.com.uma.mseei.invictus.viewmodel.service.ListenAccelerometerService;
import co.com.uma.mseei.invictus.viewmodel.service.ServiceChecker;

public class HomeViewModel extends AndroidViewModel {

    private IBinder binder;
    private final AppPreferences appPreferences;
    private final MutableLiveData<Boolean> serviceBound;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appPreferences = new AppPreferences(application);
        serviceBound = new MutableLiveData<>();
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
//            ListenAccelerometerService.BinderAccess access = (ListenAccelerometerService.BinderAccess) iBinder;
//            ListenAccelerometerService listenAccelerometerService = access.getService();
            setServiceBound(true);
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

    private boolean stopService() {
        if(appPreferences.isServiceBound()){
            getApplication().unbindService(serviceConnection);
            setServiceBound(false);
        }
        Application application = getApplication();
        Intent intent = new Intent(application,  ListenAccelerometerService.class);
        return application.stopService(intent);
    }
}