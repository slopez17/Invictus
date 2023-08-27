package co.com.uma.mseei.invictus.util;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.FOCUS_DOWN;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static co.com.uma.mseei.invictus.util.Constants.CLEAN;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ViewOperations {

    @NonNull
    public static String getStringFromTextView(TextView textView) {
        String text = textView.getText().toString();
        return text.isEmpty() ? CLEAN : text;
    }

    public static void changeEditor(int actionId, View textView) {
        switch (actionId){
            case IME_ACTION_DONE:
                hideKeyboard((TextView) textView);
                textView.clearFocus();
                break;
            case IME_ACTION_NEXT:
                textView.focusSearch(FOCUS_DOWN).requestFocus();
                break;
        }
    }

    public static void hideKeyboard(TextView textView) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) textView.getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

}
