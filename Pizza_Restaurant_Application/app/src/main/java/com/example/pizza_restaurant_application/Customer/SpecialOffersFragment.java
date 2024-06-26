package com.example.pizza_restaurant_application.Customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza_restaurant_application.DataAPI.SpecialOffer;
import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpecialOffersFragment extends Fragment {
    private RecyclerView recyclerView;
    private SpecialOfferAdapter adapter;
    private DataBaseHelper databaseHelper;
    private List<SpecialOffer> specialOffersList;
    private Spinner priceSpinner;
    private Spinner sizeSpinner;
    private Spinner categorySpinner;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_offer, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Spinners and EditText
        priceSpinner = view.findViewById(R.id.priceFilterSpinner);
        sizeSpinner = view.findViewById(R.id.sizeFilterSpinner);
        categorySpinner = view.findViewById(R.id.categoryFilterSpinner);
        searchEditText = view.findViewById(R.id.searchEditText);

        setupSpinners();

        databaseHelper = new DataBaseHelper(getContext());
        specialOffersList = databaseHelper.getAllSpecialOffers();

        // Initialize and set adapter for RecyclerView
        adapter = new SpecialOfferAdapter(specialOffersList, specialOffer -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            SpecialOfferDetailFragment detailFragment = SpecialOfferDetailFragment.newInstance(specialOffer);
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        recyclerView.setAdapter(adapter);

        setupSearch();

        return view;
    }

    // Method to set up search functionality
    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterPizzas(s.toString());
            }
        });
    }

    // Method to filter special offers based on search query
    private void filterPizzas(String query) {
        List<SpecialOffer> filteredList = new ArrayList<>();
        for (SpecialOffer pizza : specialOffersList) {
            if (pizza.getPizzaName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(pizza);
            }
        }
        adapter.filterList(filteredList);
    }

    // Method to set up spinners
    private void setupSpinners() {
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.price_ranges, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceSpinner.setAdapter(priceAdapter);
        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterPizzas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Size spinner setup
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sizes, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterPizzas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Category spinner setup
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterPizzas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // Method to filter special offers based on selected filters
    private void filterPizzas() {
        String query = searchEditText.getText().toString();
        String selectedPrice = priceSpinner.getSelectedItem().toString();
        String selectedSize = sizeSpinner.getSelectedItem().toString();
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        List<SpecialOffer> filteredList = new ArrayList<>();
        for (SpecialOffer pizza : specialOffersList) {
            boolean categoryMatch = selectedCategory.equals("Any Category") || pizza.getPizzaCategory().equalsIgnoreCase(selectedCategory);
            boolean sizeMatch = selectedSize.equals("Any Size") || pizza.getSize().equalsIgnoreCase(selectedSize);
            boolean priceMatch = isPizzaInPriceRange(pizza.getPrice(), selectedPrice);
            boolean searchMatch = query.isEmpty() || pizza.getPizzaName().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()));

            if (categoryMatch && sizeMatch && priceMatch && searchMatch) {
                filteredList.add(pizza);
            }
        }
        adapter.filterList(filteredList);
    }

    // Method to check if pizza price is within selected price range
    private boolean isPizzaInPriceRange(double pizzaPrice, String selectedPrice) {
        switch (selectedPrice) {
            case "Under $10":
                return pizzaPrice < 10.0;
            case "$10 - $15":
                return pizzaPrice >= 10.0 && pizzaPrice <= 15.0;
            case "Above $15":
                return pizzaPrice > 15.0;
            case "Any Price":
            default:
                return true;
        }
    }
}
