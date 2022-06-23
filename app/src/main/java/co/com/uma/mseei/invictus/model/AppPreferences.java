package co.com.uma.mseei.invictus.model;


public class AppPreferences {

    public static final String PREF_FILE = "data";

    /**
     * Settings constants
     * If you need to modify some value, you must change corresponding key value on
     * fragment_settings
     **/
    public static final String PREF_AUTO_FINISH = "autoFinish";
    public static final String PREF_MAX_SAMPLES_LIMIT = "maxSamplesLimit";
    public static final String PREF_MAX_MEASURES_ON_MEMORY = "maxMeasuresMem";
    public static final String PREF_SAVE_ON_SD = "saveOnSD";
    public static final String PREF_FILE_NAME = "fileName";
    public static final String PREF_MULTIPLE_FILES = "multipleFiles";
    public static final String PREF_REFRESH_TIME = "refreshTimeInSeconds";

    public static final boolean DEFAULT_AUTO_FINISH = false;
    public static final String DEFAULT_MAX_MEASURES_TO_ACQUIRE = "200";
    private static final String DEFAULT_MAX_MEASURES_ON_MEMORY = "200";
    private static final boolean DEFAULT_SAVE_ON_SD = false;
    private static final String DEFAULT_FILENAME = "Sporting";
    private static final boolean DEFAULT_MULTIPLE_FILES = false;
    private static final String DEFAULT_REFRESH_TIME = "5";




}
