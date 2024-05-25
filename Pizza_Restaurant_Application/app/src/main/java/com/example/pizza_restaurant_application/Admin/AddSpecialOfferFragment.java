package com.example.pizza_restaurant_application.Admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddSpecialOfferFragment extends Fragment {

    private Spinner typeSpinner;
    private Spinner sizeSpinner;
    private EditText priceEditText;
    private Button periodButton; // Changed from EditText to Button
    private Button addOfferButton;
    private DataBaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_special_offer, container, false);

        typeSpinner = rootView.findViewById(R.id.typeSpinner);
        sizeSpinner = rootView.findViewById(R.id.sizeSpinner);
        priceEditText = rootView.findViewById(R.id.priceEditText);
        periodButton = rootView.findViewById(R.id.periodButton); // Changed from EditText to Button
        addOfferButton = rootView.findViewById(R.id.addOfferButton);

        dbHelper = new DataBaseHelper(requireContext());

        // Populate the type spinner with pizza types
        populateTypeSpinner();

        // Set click listener for the period button to open date picker
        periodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpecialOffer();
            }
        });

        return rootView;
    }

    private void populateTypeSpinner() {
        List<String> pizzaTypes = dbHelper.getAllPizzaTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pizzaTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Format the selected date as YYYY-MM-DD
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String selectedDate = sdf.format(calendar.getTime());

                        // Set the selected date to the period button text
                        periodButton.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    private void addSpecialOffer() {
        String type = typeSpinner.getSelectedItem().toString();
        String size = sizeSpinner.getSelectedItem().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());
        String period = periodButton.getText().toString(); // Get text from the period button

        if (dbHelper.insertSpecialOffer(type, size, price, period)) {
            Toast.makeText(requireContext(), "Special offer added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Failed to add special offer", Toast.LENGTH_SHORT).show();
        }
    }
}
