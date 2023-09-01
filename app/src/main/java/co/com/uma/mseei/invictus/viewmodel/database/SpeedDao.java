package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.uma.mseei.invictus.model.database.Feedback;
import co.com.uma.mseei.invictus.model.database.Speed;
import co.com.uma.mseei.invictus.model.database.Weight;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface SpeedDao {

    @Query("SELECT * FROM speeds WHERE sport_id=:sportId ORDER BY elapsed_time ASC")
    Single<List<Speed>> getSpeedsById(int sportId);

    @Query("SELECT MAX(speed) FROM speeds WHERE sport_id = :sportId")
    Single<Float> getMaxSpeed(int sportId);

    @Query("SELECT AVG(speed) FROM speeds WHERE sport_id = :sportId")
    Single<Float> getAvgSpeed(int sportId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSpeed(Speed speed);
}
