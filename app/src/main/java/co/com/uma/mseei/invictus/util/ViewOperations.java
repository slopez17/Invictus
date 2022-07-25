package co.com.uma.mseei.invictus.util;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.FOCUS_DOWN;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static co.com.uma.mseei.invictus.util.GeneralConstants.twoDigits;

import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ViewOperations {
    public static float getFloatFrom(TextView textView) {
        String value = textView.getText().toString();
        if (value.isEmpty()) {
            return 0f;
        } else {
            return Float.parseFloat(value);
        }
    }

    public static void setTextView(TextView textView, int value) {
        textView.setText(String.valueOf(value));
    }

    public static void setTextView(TextView textView, float value) {
        textView.setText(twoDigits.format(value));
    }

    public static void setHintTextView(TextView textView, float value) {
        textView.setHint(String.valueOf(value));
    }

    public static void changeEditor(int actionId, TextView textView) {
        switch (actionId){
            case IME_ACTION_DONE:
                hideKeyboard(textView);
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
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

}
