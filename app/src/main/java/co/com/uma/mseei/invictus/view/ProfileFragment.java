package co.com.uma.mseei.invictus.view;

import static java.lang.System.currentTimeMillis;
import static co.com.uma.mseei.invictus.R.id.birthdateEditText;
import static co.com.uma.mseei.invictus.R.id.genderSpinner;
import static co.com.uma.mseei.invictus.R.id.heightEditText;
import static co.com.uma.mseei.invictus.R.id.saveButton;
import static co.com.uma.mseei.invictus.R.id.weightEditText;
import static co.com.uma.mseei.invictus.R.layout.item_spinner;
import static co.com.uma.mseei.invictus.util.ViewOperations.changeEditor;
import static co.com.uma.mseei.invictus.util.ViewOperations.getFloatFrom;
import static co.com.uma.mseei.invictus.util.ViewOperations.setHintTextView;
import static co.com.uma.mseei.invictus.util.ViewOperations.setTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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

import co.com.uma.mseei.invictus.databinding.FragmentProfileBinding;
import co.com.uma.mseei.invictus.viewmodel.ProfileViewModel;

public class ProfileFragment
        extends Fragment
        implements OnItemSelectedListener, OnClickListener, OnDateSetListener, OnEditorActionListener, OnFocusChangeListener {

    private Activity activity;
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;

    LocalDate birthdate;
    TextView weightTextView;
    TextView heightTextView;

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
        initializeWeightEditText();
        initializeWeightUndTextView();
        initializeHeightEditText();
        initializeHeightUndTextView();
        initializeBmiTextView();
        initializeBmiClassificationTextView();
        initializeUpdateDate();
        initializeSaveButton();

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
    public void onClick(View view) {
        switch (view.getId()) {
            case birthdateEditText:
                initializeCalendar();
                break;
            case saveButton:
                profileViewModel.saveProfile();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        profileViewModel.setBirthdate(year, month+1, dayOfMonth);
        profileViewModel.setAge();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        changeEditor(actionId, textView);
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            float value = getFloatFrom((TextView) v);
            int id = v.getId();
            changeUserBodyData(id, value);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void changeUserBodyData(int id, float value) {
        switch (id) {
            case weightEditText:
                profileViewModel.setWeight(value);
                profileViewModel.setBmiValues();
                break;
            case heightEditText:
                profileViewModel.setHeight(value);
                profileViewModel.setBmiValues();
                break;
        }
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

    private void initializeCalendar() {
        int year =  birthdate.getYear();
        int month = birthdate.getMonthValue()-1;
        int day = birthdate.getDayOfMonth();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(activity, this, year, month, day);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMaxDate(currentTimeMillis());
    }

    private void initializeAgeTextView() {
        TextView ageTextView = binding.ageTextView;
        profileViewModel.getAge().observe(getViewLifecycleOwner(), x -> setTextView(ageTextView, x));
    }

    private void initializeWeightEditText(){
        weightTextView = binding.weightEditText;
        weightTextView.setOnEditorActionListener(this);
        weightTextView.setOnFocusChangeListener(this);
        profileViewModel.getWeightHint().observe(getViewLifecycleOwner(), x -> setHintTextView(weightTextView, x));
        profileViewModel.getWeight().observe(getViewLifecycleOwner(), x -> setTextView(weightTextView, x));
    }

    private void initializeWeightUndTextView(){
        TextView weightUndTextView = binding.weightUndTextView;
        profileViewModel.getWeightUnd().observe(getViewLifecycleOwner(), weightUndTextView::setText);
    }

    private void initializeHeightEditText(){
        heightTextView = binding.heightEditText;
        heightTextView.setOnEditorActionListener(this);
        heightTextView.setOnFocusChangeListener(this);
        profileViewModel.getHeightHint().observe(getViewLifecycleOwner(), x -> setHintTextView(heightTextView, x));
        profileViewModel.getHeight().observe(getViewLifecycleOwner(), x -> setTextView(heightTextView, x));
    }

    private void initializeHeightUndTextView(){
        TextView heightUndTextView = binding.heightUndTextView;
        profileViewModel.getHeightUnd().observe(getViewLifecycleOwner(), heightUndTextView::setText);
    }

    private  void  initializeBmiTextView(){
        TextView bmiTextView = binding.bmiTextView;
        profileViewModel.getBmi().observe(getViewLifecycleOwner(), x -> setTextView(bmiTextView, x));
    }

    private  void  initializeBmiClassificationTextView(){
        TextView bmiClassificationTextView = binding.bmiClassificationTextView;
        profileViewModel.getBmiClassification().observe(getViewLifecycleOwner(), bmiClassificationTextView::setText);
    }

    private void initializeUpdateDate() {
        TextView updateDate = binding.updateDateTextView;
        profileViewModel.getUpdateDate().observe(getViewLifecycleOwner(), updateDate::setText);
    }

    private void initializeSaveButton() {
        Button saveButton = binding.saveButton;
        saveButton.setOnClickListener(this);
    }
}