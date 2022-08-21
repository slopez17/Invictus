package co.com.uma.mseei.invictus.util;

import android.app.Application;

public class ResourceOperations {

    public static String[] getStringArrayById(Application application, int id) {
        return application.getResources().getStringArray(id);
    }

    public static String getStringFromArrayById(Application application, int id, int position) {
        String[] array = application.getResources().getStringArray(id);
        return array[position];
    }
}
