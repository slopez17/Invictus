package co.com.uma.mseei.invictus.view.home;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static co.com.uma.mseei.invictus.R.id.confirmButton;
import static co.com.uma.mseei.invictus.R.string.error_saved;
import static co.com.uma.mseei.invictus.R.string.successfully_saved;
import static co.com.uma.mseei.invictus.databinding.ActivityFeedbackBinding.inflate;
import static co.com.uma.mseei.invictus.util.Debug.getMethodName;
import static co.com.uma.mseei.invictus.util.Resource.getStringById;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import co.com.uma.mseei.invictus.databinding.ActivityFeedbackBinding;
import co.com.uma.mseei.invictus.viewmodel.home.FeedbackViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityFeedbackBinding binding;
    private CompositeDisposable compositeDisposable;
    private FeedbackViewModel feedbackViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        binding = inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        compositeDisposable = new CompositeDisposable();

        Button confirmButton = binding.confirmButton;
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == confirmButton) {
            saveFeedback();
            finish();
        }
    }

    private void saveFeedback() {
        TextView commentsTextView = binding.commentsEditText;
        String comments = commentsTextView.getText().toString();

        if(!comments.isEmpty()) {
            Disposable disposable = feedbackViewModel.saveFeedbackOnDatabase(comments)
                    .subscribeOn(io())
                    .observeOn(io())
                    .subscribe(() -> makeText(this, successfully_saved, LENGTH_SHORT).show(),
                            throwable -> Log.e(getMethodName(), getStringById(this, error_saved), throwable));
            compositeDisposable.add(disposable);
            //disposable.add(getFeedback(1));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }


    //    @NonNull
//    private Disposable getFeedback(int id) {
//        return feedbackViewModel.getFeedback(id)
//                .subscribeOn(io())
//                .observeOn(mainThread())
//                .subscribe(
//                        feedback -> {
//                            makeText(this, feedback.getComments(), LENGTH_SHORT).show();
//                        }
//                );
//    }
}
