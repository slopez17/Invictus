package co.com.uma.mseei.invictus.model;

import static co.com.uma.mseei.invictus.R.string.all;
import static co.com.uma.mseei.invictus.R.string.day;
import static co.com.uma.mseei.invictus.R.string.month;
import static co.com.uma.mseei.invictus.R.string.no_implemented;
import static co.com.uma.mseei.invictus.R.string.rope_skipping;
import static co.com.uma.mseei.invictus.R.string.walk;
import static co.com.uma.mseei.invictus.R.string.week;
import static co.com.uma.mseei.invictus.R.string.weight;

import android.app.Application;

public class HistoricalOptions {
    public static final String SELECTED_OPTION = "selectedOption";
    public static final int NO_IMPLEMENTED = -1;
    public static final int WEIGHT = 0;
    public static final int WALK = 1;
    public static final int ROPE_SKIPPING = 2;

    public static int[] getTabTitlesFor(int selectedOption) {
        int[] TAB_TITLES;
        if (isWeightOption(selectedOption)) {
            TAB_TITLES = new int[]{week, month};
        } else {
            TAB_TITLES = new int[]{day, week, month, all};
        }
        return TAB_TITLES;
    }

    public static int getTitleFor(Application application, int selectedOption) {
        switch (selectedOption){
            case WEIGHT:
                return weight;
            case WALK:
                return walk;
            case ROPE_SKIPPING:
                return rope_skipping;
            default:
                return no_implemented;
        }
    }

    public static boolean isWeightOption(int selectedOption){
        return selectedOption == WEIGHT;
    }
}
