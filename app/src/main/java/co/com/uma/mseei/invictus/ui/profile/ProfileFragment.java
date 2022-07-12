package co.com.uma.mseei.invictus.ui.profile;

import static co.com.uma.mseei.invictus.R.id.birthdateEditText;
import static co.com.uma.mseei.invictus.R.id.genderSpinner;
import static co.com.uma.mseei.invictus.R.id.heightEditText;
import static co.com.uma.mseei.invictus.R.id.saveButton;
import static co.com.uma.mseei.invictus.R.layout.item_spinner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

import co.com.uma.mseei.invictus.R;
import co.com.uma.mseei.invictus.databinding.FragmentProfileBinding;

public class ProfileFragment
        extends Fragment
        implements OnItemSelectedListener, OnClickListener, OnDateSetListener, OnEditorActionListener {

    private Activity activity;
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    LocalDate birthdate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory()).get(ProfileViewModel.class);


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = requireActivity();

        initializeGenderSpinner();
        initializeBirthdateEditText();
        initializeAgeTextView();

        Button saveButton = binding.saveButton;
        saveButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        profileViewModel.initializeValues();
    }

    private void initializeGenderSpinner() {
        Spinner genderSpinner = binding.genderSpinner;
        String[] genderOptions = profileViewModel.getGenderOptions();
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(activity, item_spinner, genderOptions);
        genderSpinner.setAdapter(arrayAdapter);
        genderSpinner.setOnItemSelectedListener(this);
        profileViewModel.getGender().observe(getViewLifecycleOwner(), genderSpinner::setSelection);
    }

    private void initializeBirthdateEditText() {
        EditText birthdateEditText = binding.birthdateEditText;
        birthdateEditText.setOnClickListener(this);
        profileViewModel.getBirthdate().observe(getViewLifecycleOwner(), x -> {
            birthdate = x;
            birthdateEditText.setText(birthdate.toString());
        });
    }

    private void initializeAgeTextView() {
        TextView ageTextView = binding.ageTextView;
        profileViewModel.getAge().observe(getViewLifecycleOwner(), x -> {
            String age = x.toString();
            ageTextView.setText(age);
        });
    }

    private void initializeWeightEditText(){
        EditText weightEditText = binding.weightEditText;
        weightEditText.setOnEditorActionListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == genderSpinner) {
            profileViewModel.setGender(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case birthdateEditText:
                int year =  birthdate.getYear();
                int month = birthdate.getMonthValue()-1;
                int day = birthdate.getDayOfMonth();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(activity, this, year, month, day);
                datePickerDialog.show();
                break;

            case saveButton:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        profileViewModel.setBirthdate(year, month+1, dayOfMonth);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()){
            case heightEditText:

                return true;
            case weightEditText:
                return true;
        }
        return false;
    }
}