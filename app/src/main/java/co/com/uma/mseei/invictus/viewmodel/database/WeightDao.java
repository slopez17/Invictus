package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.uma.mseei.invictus.model.chart.limit.WeightLimit;
import co.com.uma.mseei.invictus.model.database.Weight;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface WeightDao {

    @Query("SELECT * FROM weights ORDER BY date DESC")
    Flowable<List<Weight>> getAllWeights();

    @Query("SELECT * FROM weights WHERE date BETWEEN :dateFrom AND :dateTo ORDER BY date DESC")
    Single<List<Weight>> findWeightsByPeriod(String dateFrom, String dateTo);

    @Query("SELECT :dateFrom AS minX, :dateTo AS maxX, MIN(value) AS minY, MAX(value) AS maxY FROM weights WHERE date BETWEEN :dateFrom AND :dateTo")
    Single<WeightLimit> getWeightLimits(String dateFrom, String dateTo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertWeights(Weight... weights);

}
