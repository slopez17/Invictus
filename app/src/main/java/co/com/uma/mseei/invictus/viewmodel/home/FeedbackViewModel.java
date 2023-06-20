package co.com.uma.mseei.invictus.viewmodel.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.database.Feedback;
import co.com.uma.mseei.invictus.viewmodel.database.FeedbackRepository;
import io.reactivex.Completable;

public class FeedbackViewModel extends AndroidViewModel {
    private final FeedbackRepository feedbackRepository;

    public FeedbackViewModel(@NonNull Application application) {
        super(application);

        feedbackRepository = new FeedbackRepository(application);
    }

    public Completable saveFeedbackOnDatabase(String comments) {
        AppPreferences appPreferences = new AppPreferences(getApplication());
        int sportId = appPreferences.getSportId();
        Feedback feedback = new Feedback(sportId, comments);
        return feedbackRepository.insertFeedback(feedback);
    }

//    public Single<Feedback> getFeedback(int id) {
//        return feedbackRepository.getFeedback(id);
//    }
}
