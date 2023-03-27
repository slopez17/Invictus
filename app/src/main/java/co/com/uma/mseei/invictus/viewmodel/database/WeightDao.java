package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.uma.mseei.invictus.model.Limits;
import co.com.uma.mseei.invictus.model.Weight;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface WeightDao {

    @Query("SELECT * FROM weights ORDER BY date DESC")
    Flowable<List<Weight>> getAllWeights();

    @Query("SELECT * FROM weights WHERE date BETWEEN :dateFrom AND :dateTo")
    Single<List<Weight>> findWeightsByPeriod(String dateFrom, String dateTo);

    @Query("SELECT * FROM weights WHERE date LIKE :date LIMIT 1")
    Single<Weight> findWeightByDate(String date);

    @Query("SELECT :dateFrom AS minX, :dateTo AS maxX, MIN(value) AS minY, MAX(value) AS maxY FROM weights WHERE date BETWEEN :dateFrom AND :dateTo")
    Single<Limits> getWeightLimits(String dateFrom, String dateTo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertWeights(Weight... weights);

}
