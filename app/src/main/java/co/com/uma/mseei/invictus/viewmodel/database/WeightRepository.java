package co.com.uma.mseei.invictus.viewmodel.database;

import static co.com.uma.mseei.invictus.viewmodel.database.AppDatabase.getDatabase;

import android.app.Application;

import java.util.List;

import co.com.uma.mseei.invictus.model.Limits;
import co.com.uma.mseei.invictus.model.Weight;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class WeightRepository {
    private final WeightDao weightDao;
    private final Flowable<List<Weight>> allWeights;

    public WeightRepository(Application application) {
        AppDatabase db = getDatabase(application);
        weightDao = db.weightDao();
        allWeights = weightDao.getAllWeights();
    }

    public Flowable<List<Weight>> getAllWeights(){
        return allWeights;
    }

    public Single<List<Weight>> findWeightsByPeriod(String dateFrom, String dateTo){
        return weightDao.findWeightsByPeriod(dateFrom, dateTo);
    }

    public Single<Limits> getWeightLimits(String dateFrom, String dateTo){
        return weightDao.getWeightLimits(dateFrom, dateTo);
    }

    public Completable insertWeights(Weight... weights){
        return weightDao.insertWeights(weights);
    }
}
