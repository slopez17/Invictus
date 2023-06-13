package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import co.com.uma.mseei.invictus.model.database.Feedback;
import io.reactivex.Completable;

@Dao
public interface FeedbackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFeedback(Feedback feedback);
}
