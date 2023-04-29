package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.util.GeneralConstants.KG_UND;
import static co.com.uma.mseei.invictus.util.GeneralConstants.LBS_UND;
import static co.com.uma.mseei.invictus.util.ViewOperations.setTextView;

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
import co.com.uma.mseei.invictus.model.Weight;

public class WeightListViewAdapter extends ArrayAdapter<Weight> {

    private final AppPreferences appPreferences;
    private final List<Weight> weightList;

    public WeightListViewAdapter(Activity activity, List<Weight> weightList) {
        super(activity.getApplicationContext(), R.layout.item_list_weight, weightList);
        this.weightList = weightList;
        this.appPreferences = new AppPreferences(activity);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_weight, parent, false);
        }

        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        dateTextView.setText(weightList.get(position).getDate());

        boolean isUnitSystemImperial = appPreferences.isUnitSystemImperial();

        TextView weightTextView = convertView.findViewById(R.id.weightTextView);
        float weight = weightList.get(position).getValue(isUnitSystemImperial);
        setTextView(weightTextView, weight);

        TextView weightUndTextView = convertView.findViewById(R.id.weightUndTextView);
        weightUndTextView.setText(isUnitSystemImperial ? LBS_UND : KG_UND);

        return convertView;
    }
}
