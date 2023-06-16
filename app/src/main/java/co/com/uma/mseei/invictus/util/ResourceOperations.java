package co.com.uma.mseei.invictus.util;

import static androidx.core.content.ContextCompat.getColor;
import static java.lang.Integer.toHexString;
import static co.com.uma.mseei.invictus.util.GeneralConstants.HASH;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class ResourceOperations {

    public static String getStringById(Activity activity, int id) {
        return activity.getApplication().getResources().getString(id);
    }

    public static String[] getStringArrayById(Application application, int id) {
        return application.getResources().getStringArray(id);
    }

    public static String getStringFromArrayById(Application application, int id, int position) {
        String[] array = application.getResources().getStringArray(id);
        return array[position];
    }

    public static String getColorById(Activity activity, int id) {
        Context context = activity.getApplicationContext();
        return HASH + toHexString(getColor(context, id));
    }

}
