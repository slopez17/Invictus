package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import co.com.uma.mseei.invictus.model.database.Sport;
import io.reactivex.Completable;

@Dao
public interface SportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSport(Sport sport);

}
