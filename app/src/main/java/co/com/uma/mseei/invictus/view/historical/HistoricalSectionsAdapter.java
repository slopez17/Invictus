package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.SELECTED_OPTION;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.getItemCountFor;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.getPlaceholderFragmentFor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class HistoricalSectionsAdapter extends FragmentStateAdapter {

    private final int selectedOption;

    public HistoricalSectionsAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.selectedOption = fragmentActivity.getIntent().getExtras().getInt(SELECTED_OPTION);
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
}