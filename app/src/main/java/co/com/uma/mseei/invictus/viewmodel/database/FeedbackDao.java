package co.com.uma.mseei.invictus.viewmodel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import co.com.uma.mseei.invictus.model.database.Feedback;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface FeedbackDao {
    @Query("SELECT * FROM feedbacks WHERE id=:id")
    Single<Feedback> getFeedback(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFeedback(Feedback feedback);

}
