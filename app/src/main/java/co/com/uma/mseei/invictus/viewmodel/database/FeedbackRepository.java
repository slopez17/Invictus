package co.com.uma.mseei.invictus.viewmodel.database;

import static co.com.uma.mseei.invictus.viewmodel.database.AppDatabase.getDatabase;

import android.app.Application;

import co.com.uma.mseei.invictus.model.database.Feedback;
import io.reactivex.Completable;

public class FeedbackRepository {
    private final FeedbackDao feedbackDao;

    public FeedbackRepository(Application application) {
        AppDatabase db = getDatabase(application);
        feedbackDao = db.feedbackDao();
    }

    public Completable insertFeedback(Feedback feedback){
        return feedbackDao.insertFeedback(feedback);
    }
}
