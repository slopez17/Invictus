package co.com.uma.mseei.invictus.viewmodel.database;

import static co.com.uma.mseei.invictus.viewmodel.database.AppDatabase.getDatabase;

import android.app.Application;

import co.com.uma.mseei.invictus.model.database.Speed;
import io.reactivex.Completable;
import io.reactivex.Single;

public class SpeedRepository {
    private final SpeedDao speedDao;

    public SpeedRepository(Application application) {
        AppDatabase db = getDatabase(application);
        speedDao = db.speedDao();
    }

    public Single<Float> getMaxSpeed(int sportId){
        return speedDao.getMaxSpeed(sportId);
    }

    public Single<Float> getAvgSpeed(int sportId){
        return speedDao.getAvgSpeed(sportId);
    }

    public Completable insertSpeed(Speed speed){
        return speedDao.insertSpeed(speed);
    }
}
