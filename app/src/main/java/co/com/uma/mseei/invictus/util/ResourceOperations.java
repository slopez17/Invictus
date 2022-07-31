package co.com.uma.mseei.invictus.util;

import android.app.Activity;
import android.app.Application;

public class ResourceOperations {
    public static String getStringById(Activity activity, int id) {
        return activity.getResources().getString(id);
    }

    public static String getStringById(Application application, int id) {
        return application.getResources().getString(id);
    }

    public static String[] getStringArrayById(Application application, int id) {
        return application.getResources().getStringArray(id);
    }
}
