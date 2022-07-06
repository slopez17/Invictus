package co.com.uma.mseei.invictus.model;


public class AppPreferences {

    public static final String PREF_FILE = "data";

    /**
     * Settings constants
     * If you need to modify some value, you must change corresponding key/default value on
     * fragment_settings
     **/
    public static final String PREF_AUTO_FINISH = "autoFinish";
    public static final String PREF_SAMPLES_LIMIT = "samplesLimit";
    public static final String PREF_SAMPLES_ON_MEMORY = "samplesOnMemory";
    public static final String PREF_SAVE_ON_SD = "saveOnSD";
    public static final String PREF_FILE_NAME = "fileName";
    public static final String PREF_MULTIPLE_FILES = "multipleFiles";
    public static final String PREF_REFRESH_TIME = "refreshTime";
    public static final String PREF_UNIT_SYSTEM = "unitSystem";

    public static final boolean DEFAULT_AUTO_FINISH = false;
    public static final String DEFAULT_SAMPLES_LIMIT = "2000";
    private static final String DEFAULT_SAMPLES_ON_MEMORY = "200";
    private static final boolean DEFAULT_SAVE_ON_SD = false;
    private static final String DEFAULT_FILE_NAME = "Invictus";
    private static final boolean DEFAULT_MULTIPLE_FILES = false;
    private static final String DEFAULT_REFRESH_TIME = "5";
    public static final String DEFAULT_UNIT_SYSTEM = "0";




}
