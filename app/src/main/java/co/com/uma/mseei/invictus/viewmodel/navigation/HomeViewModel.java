package co.com.uma.mseei.invictus.viewmodel.navigation;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.R.string.sportype_error;
import static co.com.uma.mseei.invictus.R.string.start_traking;
import static co.com.uma.mseei.invictus.model.SportType.values;
import static co.com.uma.mseei.invictus.view.home.SportSelectionActivity.SELECTED_SPORT;
import static co.com.uma.mseei.invictus.view.home.StopTrackingConfirmationActivity.STOP_TRACKING;
import static co.com.uma.mseei.invictus.viewmodel.service.ListenAccelerometerService.ACCELEROMETER_SERVICE_PARAMETERS;

import android.app.Application;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import co.com.uma.mseei.invictus.model.AccelerometerServiceParameters;
import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.SportType;
import co.com.uma.mseei.invictus.viewmodel.service.ListenAccelerometerService;

public class HomeViewModel extends AndroidViewModel {

    private final AppPreferences appPreferences;
    private final MutableLiveData<Boolean> trackingActive;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appPreferences = new AppPreferences(application);
        trackingActive = new MutableLiveData<>();
        trackingActive.setValue(false);
    }

    public LiveData<Boolean> isTrackingActive(){
        return trackingActive;
    }

    public boolean isServiceOrTrackingActive() {
        boolean trackingState = requireNonNull(this.trackingActive.getValue());
        boolean serviceState = appPreferences.isServiceBound();
        return serviceState || trackingState;
    }

    public void getSportType(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            int selectedSport = requireNonNull(data).getExtras().getInt(SELECTED_SPORT);
            SportType sportType = values()[selectedSport];
            startService(sportType);
            changetrackingState();
            getSportTypeMessage(sportType);
        } else {
            getSportTypeMessage();
        }
    }

    public void getStopTrackingConfirmation(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            boolean stoptracking = requireNonNull(data).getExtras().getBoolean(STOP_TRACKING);
//                        homeViewModel.startService(sportType);
//                        homeViewModel.changetrackingState();
//                        homeViewModel.getSportTypeMessage(sportType);
        } else {
//                        homeViewModel.getSportTypeMessage();
        }
    }

    private void startService(SportType sportType) {
        Intent intent = new Intent(getApplication(), ListenAccelerometerService.class);

        AccelerometerServiceParameters parameters = new AccelerometerServiceParameters();
        parameters.setGender(appPreferences.getGender());
        parameters.setWeight(appPreferences.getWeight());
        parameters.setSportId(appPreferences.getSportId()+1);
        parameters.setSportType(sportType);
        parameters.setAutofinishOn(appPreferences.isAutoFinishOn());
        parameters.setSamplesLimit(appPreferences.getSamplesLimit());
        parameters.setSamplesOnMemory(appPreferences.getSamplesOnMemory());
        parameters.setSaveOnSdOn(appPreferences.isSaveOnSdOn());
        parameters.setFileName(appPreferences.getFileName(), appPreferences.isMultipleFilesOn());

        intent.putExtra(ACCELEROMETER_SERVICE_PARAMETERS, parameters);
        getApplication().startForegroundService(intent);
    }

    private void changetrackingState() {
        boolean state = requireNonNull(this.trackingActive.getValue());
        trackingActive.setValue(!state);
    }

    private void getSportTypeMessage(SportType... sportType) {
        if(sportType.length == 0) {
            makeText(getApplication(), sportype_error, LENGTH_LONG).show();
        } else {
            Application application = getApplication();
            String message = format("%s %s", application.getString(start_traking),
                    application.getString(sportType[0].getSportName()));
            makeText(getApplication(), message, LENGTH_LONG).show();
        }
    }
}