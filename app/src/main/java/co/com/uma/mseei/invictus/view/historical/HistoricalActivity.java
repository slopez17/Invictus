package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.R.string.all;
import static co.com.uma.mseei.invictus.R.string.day;
import static co.com.uma.mseei.invictus.R.string.month;
import static co.com.uma.mseei.invictus.R.string.week;
import static co.com.uma.mseei.invictus.R.string.weight;
import static co.com.uma.mseei.invictus.view.historical.HistoricalFragment.SELECTED_OPTION;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.com.uma.mseei.invictus.databinding.ActivityHistoricalBinding;
import co.com.uma.mseei.invictus.model.SportType;

public class HistoricalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHistoricalBinding binding = ActivityHistoricalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton backButton = binding.backButton;
        backButton.setOnClickListener(v -> this.finish());

        SportType selectedOption = (SportType) this.getIntent().getSerializableExtra(SELECTED_OPTION);

        TextView title = binding.title;
        title.setText(selectedOption == null ? weight : selectedOption.getName());

        ViewPager2 viewPager = binding.viewPager;
        viewPager.setAdapter(new HistoricalSectionsAdapter(this));

        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, viewPager, ((tab, position) -> {
            int[] TAB_TITLES = getTabTitlesFor(selectedOption);
            tab.setText(TAB_TITLES[position]);
        })).attach();

    }

    private int[] getTabTitlesFor(SportType selectedOption) {
        int[] TAB_TITLES;
        if (selectedOption == null) {
            TAB_TITLES = new int[]{week, month, all};
        } else {
            TAB_TITLES = new int[]{day, week, month, all};
        }
        return TAB_TITLES;
    }
}