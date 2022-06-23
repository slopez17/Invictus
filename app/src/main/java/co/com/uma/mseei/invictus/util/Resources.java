package co.com.uma.mseei.invictus.util;

import android.content.Context;

public class Resources {
    public static String getStringById(Context context, int id) {
        return context.getResources().getString(id);
    }
}
