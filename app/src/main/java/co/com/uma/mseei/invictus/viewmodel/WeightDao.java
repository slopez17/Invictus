package co.com.uma.mseei.invictus.viewmodel;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.uma.mseei.invictus.model.Weight;

public interface WeightDao {

    @Query("SELECT * FROM weights")
    List<Weight> getAll();

    @Query("SELECT * FROM weights WHERE date LIKE :date LIMIT 1")
    Weight findByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWeights(Weight... weights);

    @Delete
    void delete(Weight weight);

}
