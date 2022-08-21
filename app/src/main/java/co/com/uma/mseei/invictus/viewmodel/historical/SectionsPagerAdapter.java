package co.com.uma.mseei.invictus.viewmodel.historical;

import static co.com.uma.mseei.invictus.model.HistoricalOptions.isWeightOption;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.com.uma.mseei.invictus.view.historical.SportPlaceholderFragment;
import co.com.uma.mseei.invictus.view.historical.WeightPlaceholderFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    private final int selectedOption;

    public SectionsPagerAdapter(FragmentActivity fragmentActivity, int selectedOption) {
        super(fragmentActivity);
        this.selectedOption = selectedOption;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (isWeightOption(selectedOption)) {
            return WeightPlaceholderFragment.newInstance(position + 1);
        } else {
            return SportPlaceholderFragment.newInstance(position + 1, selectedOption);
        }
    }

    @Override
    public int getItemCount() {
        return  isWeightOption(selectedOption) ? 2 : 4;
    }

}