package co.com.uma.mseei.invictus.viewmodel;

import static co.com.uma.mseei.invictus.util.AppDatabase.getDatabase;

import android.app.Application;

import java.util.List;

import co.com.uma.mseei.invictus.model.Weight;
import co.com.uma.mseei.invictus.util.AppDatabase;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class WeightRepository {
    private final WeightDao weightDao;

    public WeightRepository(Application application) {
        AppDatabase db = getDatabase(application);
        weightDao = db.weightDao();
    }

    Flowable<List<Weight>> getAllWeights(){
        return weightDao.getAllWeights();
    }

    public Single<Weight> findWeightByDate(String date){
        return weightDao.findWeightByDate(date);
    }

    public Completable insertWeights(Weight... weights){
        return weightDao.insertWeights(weights);
    }
}
