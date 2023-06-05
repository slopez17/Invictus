package co.com.uma.mseei.invictus.viewmodel.service;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ServiceChecker {
    private final Context context;

    public ServiceChecker(Context context) {
        this.context = context;
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = manager.getRunningAppProcesses();
            if (runningProcesses != null) {
                String packageName = context.getPackageName();
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ||
                            processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                        if (processInfo.processName.equals(packageName)) {
                            for (String service : processInfo.pkgList) {
                                if (service.equals(serviceClass.getName())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
