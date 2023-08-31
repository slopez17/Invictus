package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.util.UnitsAndConversions.floatToString;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import co.com.uma.mseei.invictus.R;
import co.com.uma.mseei.invictus.model.SportType;
import co.com.uma.mseei.invictus.model.database.Sport;
import co.com.uma.mseei.invictus.model.database.Weight;

public class SportListViewAdapter extends ArrayAdapter<Weight> {

    private final List<Sport> sportList;

    public SportListViewAdapter(Activity activity, List<Sport> sportList) {
        super(activity.getApplicationContext(), R.layout.item_list_sport);
        this.sportList = sportList;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_weight, parent, false);
        }

        TextView idTextView = convertView.findViewById(R.id.idTextView);
        TextView startTimeTextView = convertView.findViewById(R.id.startTimeTextView);
        TextView fallsTextView = convertView.findViewById(R.id.fallsTextView);
        TextView stepsJumpsTextView = convertView.findViewById(R.id.stepsJumpsTextView);
        TextView caloriesTextView = convertView.findViewById(R.id.caloriesTextView);
        ImageView sportImageView = convertView.findViewById(R.id.sportImageView);

        idTextView.setText(String.valueOf(sportList.get(position).getSportId()));
        startTimeTextView.setText(sportList.get(position).getStartDateTime());
        fallsTextView.setText(String.valueOf(sportList.get(position).getFalls()));
        stepsJumpsTextView.setText(String.valueOf(sportList.get(position).getSteps()));
        caloriesTextView.setText(floatToString(sportList.get(position).getCalories()));
        sportImageView.setImageResource(SportType.valueOf(sportList.get(position).getSportType()).getIcon());
        return convertView;
    }
}
