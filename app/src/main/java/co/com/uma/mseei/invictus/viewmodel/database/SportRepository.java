package co.com.uma.mseei.invictus.viewmodel.database;

import static co.com.uma.mseei.invictus.viewmodel.database.AppDatabase.getDatabase;

import android.app.Application;

import co.com.uma.mseei.invictus.model.database.Sport;
import io.reactivex.Completable;

public class SportRepository {
    private final SportDao sportDao;

    public SportRepository(Application application) {
        AppDatabase db = getDatabase(application);
        sportDao = db.sportDao();
    }

    public Completable insertSport(Sport sport){
        return sportDao.insertSport(sport);
    }
}
