package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.uma.mseei.invictus.model.database.Sport;
import co.com.uma.mseei.invictus.model.database.SportLimit;
import co.com.uma.mseei.invictus.model.database.Weight;
import co.com.uma.mseei.invictus.model.database.WeightLimit;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface SportDao {

    @Query("SELECT * FROM sports WHERE sport_type = :sportType ORDER BY start_date_time DESC")
    Flowable<List<Sport>> getAllBySportType(String sportType);

    @Query("SELECT * FROM sports WHERE sport_type = :sportType AND SUBSTR(start_date_time,1,10) BETWEEN :dateFrom AND :dateTo ORDER BY start_date_time DESC")
    Single<List<Sport>> findSportsBySportTypeAndPeriod(String sportType, String dateFrom, String dateTo);

    @Query("SELECT :dateFrom AS minX, :dateTo AS maxX, MIN(calories) AS minY, MAX(calories) AS maxY FROM sports WHERE SUBSTR(start_date_time,1,10) BETWEEN :dateFrom AND :dateTo")
    Single<SportLimit> getSportLimits(String dateFrom, String dateTo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSport(Sport sport);

}
