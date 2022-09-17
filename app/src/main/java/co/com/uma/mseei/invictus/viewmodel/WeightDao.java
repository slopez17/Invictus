package co.com.uma.mseei.invictus.viewmodel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.uma.mseei.invictus.model.Weight;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface WeightDao {

    @Query("SELECT * FROM weights")
    Flowable<List<Weight>> getAllWeights();

    @Query("SELECT * FROM weights WHERE date LIKE :date LIMIT 1")
    Single<Weight> findWeightByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertWeights(Weight... weights);

}
