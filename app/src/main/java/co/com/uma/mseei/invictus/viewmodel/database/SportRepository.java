package co.com.uma.mseei.invictus.viewmodel.database;

import static co.com.uma.mseei.invictus.viewmodel.database.AppDatabase.getDatabase;

import android.app.Application;

import java.util.List;

import co.com.uma.mseei.invictus.model.database.Sport;
import co.com.uma.mseei.invictus.model.database.SportLimit;
import co.com.uma.mseei.invictus.model.database.Weight;
import co.com.uma.mseei.invictus.model.database.WeightLimit;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class SportRepository {
    private final SportDao sportDao;
    private final Flowable<List<Sport>> allSports;

    public SportRepository(Application application) {
        AppDatabase db = getDatabase(application);
        sportDao = db.sportDao();
        allSports = sportDao.getAllSports();
    }

    public Flowable<List<Sport>> getAllSports(){
        return allSports;
    }

    public Flowable<List<Sport>> getAllBySportType(String sportType){
        return sportDao.getAllBySportType(sportType);
    }

    public Single<List<Sport>> findSportsBySportTypeAndPeriod(String sportType, String dateFrom, String dateTo) {
        return sportDao.findSportsBySportTypeAndPeriod(sportType, dateFrom, dateTo);
    }

    public Single<SportLimit> getSportLimits(String dateFrom, String dateTo){
        return sportDao.getSportLimits(dateFrom, dateTo);
    }

    public Completable insertSport(Sport sport){
        return sportDao.insertSport(sport);
    }

}
