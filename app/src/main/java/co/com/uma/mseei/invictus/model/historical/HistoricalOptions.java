package co.com.uma.mseei.invictus.model.historical;

import static co.com.uma.mseei.invictus.R.string.all;
import static co.com.uma.mseei.invictus.R.string.day;
import static co.com.uma.mseei.invictus.R.string.month;
import static co.com.uma.mseei.invictus.R.string.no_implemented;
import static co.com.uma.mseei.invictus.R.string.rope_skipping;
import static co.com.uma.mseei.invictus.R.string.walk;
import static co.com.uma.mseei.invictus.R.string.week;
import static co.com.uma.mseei.invictus.R.string.weight;

import androidx.fragment.app.Fragment;

import co.com.uma.mseei.invictus.view.historical.RopeSkippingPlaceholderFragment;
import co.com.uma.mseei.invictus.view.historical.WalkPlaceholderFragment;
import co.com.uma.mseei.invictus.view.historical.WeightPlaceholderFragment;

public class HistoricalOptions {
    public static final String SELECTED_OPTION = "selectedOption";
    public static final int NO_IMPLEMENTED = -1;
    public static final int WEIGHT = 0;
    public static final int WALK = 1;
    public static final int ROPE_SKIPPING = 2;

    public static final String SECTION_NUMBER = "sectionNumber";
    public static final int ALL = 3;

    public static int[] getTabTitlesFor(int selectedOption) {
        int[] TAB_TITLES;
        if (isWeightOption(selectedOption)) {
            TAB_TITLES = new int[]{week, month, all};
        } else {
            TAB_TITLES = new int[]{day, week, month, all};
        }
        return TAB_TITLES;
    }

    public static int getTitleFor(int selectedOption) {
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

    public static Fragment getPlaceholderFragmentFor(int selectedOption, int position) {
        switch (selectedOption){
            case WALK:
                return WalkPlaceholderFragment.newInstance(position);
            case ROPE_SKIPPING:
                return RopeSkippingPlaceholderFragment.newInstance(position);
            default:
                return WeightPlaceholderFragment.newInstance(position + 1);
        }
    }

    public static int getItemCountFor(int selectedOption) {
        if (isWeightOption(selectedOption)) {
            return 3;
        }
        return 4;
    }

    private static boolean isWeightOption(int selectedOption) {
        return selectedOption == WEIGHT;
    }
}
