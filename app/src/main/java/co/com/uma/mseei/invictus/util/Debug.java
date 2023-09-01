package co.com.uma.mseei.invictus.util;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.String.format;
import static java.lang.String.join;
import static co.com.uma.mseei.invictus.util.Constants.CLEAN;

import android.content.Context;

public class Debug {
    public static String getClassName(Context context) {
        return context.getClass().getSimpleName();
    }

    public static String getMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 0) {
            return stackTrace[3].getMethodName();
        }
        return CLEAN;
    }

    public static void showExecutionPoint(Context context, boolean isDebugOn, String methodName, String... extraInformation) {
        if (isDebugOn){
            String className = getClassName(context);
            String message = format("%s -> %s. %s", className, methodName, join(" ", extraInformation));
            makeText(context, message, LENGTH_SHORT).show();
        }
    }
}
