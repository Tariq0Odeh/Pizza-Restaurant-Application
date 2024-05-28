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
    private Button periodButton;
    private Button addOfferButton;
    private DataBaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_special_offer, container, false);

        // Initialize views
        typeSpinner = rootView.findViewById(R.id.typeSpinner);
        sizeSpinner = rootView.findViewById(R.id.sizeSpinner);
        priceEditText = rootView.findViewById(R.id.priceEditText);
        periodButton = rootView.findViewById(R.id.periodButton);
        addOfferButton = rootView.findViewById(R.id.addOfferButton);

        dbHelper = new DataBaseHelper(requireContext());

        populateTypeSpinner();

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

    // Method to populate the type spinner with pizza types
    private void populateTypeSpinner() {
        List<String> pizzaTypes = dbHelper.getAllPizzaTypes();
        pizzaTypes.add(0, "Pizza Type");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pizzaTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    // Method to show date picker dialog when period button is clicked
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(Calendar.YEAR, year);
                        selectedCalendar.set(Calendar.MONTH, month);
                        selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        if (selectedCalendar.after(calendar) || selectedCalendar.equals(calendar)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String selectedDate = sdf.format(selectedCalendar.getTime());

                            periodButton.setText(selectedDate);
                        } else {
                            Toast.makeText(requireContext(), "Please select a date after or equals today", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    // Method to add special offer
    private void addSpecialOffer() {
        String selectedType = typeSpinner.getSelectedItem().toString();

        if (selectedType.equals("Pizza Type")) {
            Toast.makeText(requireContext(), "Please select a valid pizza type", Toast.LENGTH_SHORT).show();
            return;
        }

        String size = sizeSpinner.getSelectedItem().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());
        String period = periodButton.getText().toString(); // Get text from the period button

        if (dbHelper.insertSpecialOffer(selectedType, size, price, period)) {
            Toast.makeText(requireContext(), "Special offer added!", Toast.LENGTH_SHORT).show();

            // Reset input fields to default values
            typeSpinner.setSelection(0);
            sizeSpinner.setSelection(0);
            priceEditText.setText("");
            periodButton.setText("");
        } else {
            Toast.makeText(requireContext(), "Failed to add special offer", Toast.LENGTH_SHORT).show();
        }
    }

}
