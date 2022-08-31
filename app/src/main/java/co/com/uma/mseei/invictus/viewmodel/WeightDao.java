package co.com.uma.mseei.invictus.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.uma.mseei.invictus.model.Weight;

@Dao
public interface WeightDao {

    @Query("SELECT * FROM weights")
    List<Weight> getAll();

    @Query("SELECT * FROM weights WHERE date LIKE :date LIMIT 1")
    Weight findByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeights(Weight... weights);

    @Delete
    void delete(Weight weight);

}
