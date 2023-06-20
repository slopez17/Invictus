package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.SELECTED_OPTION;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.getTabTitlesFor;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.getTitleFor;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.com.uma.mseei.invictus.databinding.ActivityHistoricalBinding;

public class HistoricalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHistoricalBinding binding = ActivityHistoricalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton backButton = binding.backButton;
        backButton.setOnClickListener(v -> this.finish());

        int selectedOption = this.getIntent().getExtras().getInt(SELECTED_OPTION);

        TextView title = binding.title;
        title.setText(getTitleFor(selectedOption));

        ViewPager2 viewPager = binding.viewPager;
        viewPager.setAdapter(new HistoricalSectionsAdapter(this));

        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, viewPager, ((tab, position) -> {
            int[] TAB_TITLES = getTabTitlesFor(selectedOption);
            tab.setText(TAB_TITLES[position]);
        })).attach();

    }
}