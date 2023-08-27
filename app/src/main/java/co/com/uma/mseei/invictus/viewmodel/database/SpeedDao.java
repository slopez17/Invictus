package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import co.com.uma.mseei.invictus.model.database.Speed;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface SpeedDao {

    @Query("SELECT MAX(speed) FROM speeds WHERE sport_id = :sportId")
    Single<Float> getMaxSpeed(int sportId);

    @Query("SELECT AVG(speed) FROM speeds WHERE sport_id = :sportId")
    Single<Float> getAvgSpeed(int sportId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSpeed(Speed speed);
}
