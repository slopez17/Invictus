package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.view.historical.HistoricalFragment.SELECTED_OPTION;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.com.uma.mseei.invictus.model.SportType;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class HistoricalSectionsAdapter extends FragmentStateAdapter {

    private final SportType selectedOption;

    public HistoricalSectionsAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.selectedOption = (SportType) fragmentActivity.getIntent().getSerializableExtra(SELECTED_OPTION);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getPlaceholderFragmentFor(selectedOption, position);
    }

    @Override
    public int getItemCount() {
        return getItemCountFor(selectedOption);
    }

    private Fragment getPlaceholderFragmentFor(SportType selectedOption, int position) {
        if(selectedOption != null) {
            return SportPlaceholderFragment.newInstance(position, selectedOption);
        }
        return WeightPlaceholderFragment.newInstance(position + 1);
    }

    private int getItemCountFor(SportType selectedOption) {
        if (selectedOption == null) {
            return 3;
        }
        return 4;
    }
}