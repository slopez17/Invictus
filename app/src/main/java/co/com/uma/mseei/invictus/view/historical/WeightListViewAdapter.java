package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.util.UnitsAndConversions.KG_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.LBS_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.TWO_DIGITS;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.kg2lbs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import co.com.uma.mseei.invictus.R;
import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.database.Weight;

public class WeightListViewAdapter extends ArrayAdapter<Weight> {

    private final AppPreferences appPreferences;
    private final List<Weight> weightList;

    public WeightListViewAdapter(Activity activity, List<Weight> weightList) {
        super(activity.getApplicationContext(), R.layout.item_list_weight, weightList);
        this.weightList = weightList;
        this.appPreferences = new AppPreferences(activity.getApplication());
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_weight, parent, false);
        }

        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView weightTextView = convertView.findViewById(R.id.weightTextView);
        TextView weightUndTextView = convertView.findViewById(R.id.weightUndTextView);

        String date = weightList.get(position).getDate();
        float weight = weightList.get(position).getWeight();
        String weightUnd;
        if(appPreferences.isUnitSystemImperial()) {
            weight = kg2lbs(weight);
            weightUnd = LBS_UND;
        } else {
            weightUnd = KG_UND;
        }
        dateTextView.setText(date);
        weightTextView.setText(TWO_DIGITS.format(weight));
        weightUndTextView.setText(weightUnd);

        return convertView;
    }
}
