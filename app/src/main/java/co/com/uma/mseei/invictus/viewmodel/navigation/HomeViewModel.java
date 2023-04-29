package co.com.uma.mseei.invictus.viewmodel.navigation;

import static java.util.Objects.requireNonNull;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> monitoringState;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        monitoringState = new MutableLiveData<>();
        monitoringState.setValue(false);
    }

    public LiveData<Boolean> getMonitoringState(){
        return monitoringState;
    }

    public void changeMonitoringState() {
        boolean state = requireNonNull(this.monitoringState.getValue());
        monitoringState.setValue(!state);
    }

}