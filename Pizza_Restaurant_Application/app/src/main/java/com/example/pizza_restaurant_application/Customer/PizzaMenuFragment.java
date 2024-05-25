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

import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;

import java.util.ArrayList;
import java.util.List;

public class PizzaMenuFragment extends Fragment {
    private RecyclerView recyclerView;
    private PizzaAdapter adapter;
    private DataBaseHelper databaseHelper;
    private List<Pizza> pizzaList;
    private Spinner priceSpinner;
    private Spinner sizeSpinner;
    private Spinner categorySpinner;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_menu, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        priceSpinner = view.findViewById(R.id.priceFilterSpinner);
        sizeSpinner = view.findViewById(R.id.sizeFilterSpinner);
        categorySpinner = view.findViewById(R.id.categoryFilterSpinner);
        searchEditText = view.findViewById(R.id.searchEditText);

        setupSpinners();

        databaseHelper = new DataBaseHelper(getContext());
        pizzaList = databaseHelper.getAllPizzas();
        adapter = new PizzaAdapter(pizzaList, pizza -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            PizzaDetailFragment detailFragment = PizzaDetailFragment.newInstance(pizza);
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        recyclerView.setAdapter(adapter);

        setupSearch();

        return view;
    }

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

    private void filterPizzas(String query) {
        List<Pizza> filteredList = new ArrayList<>();
        for (Pizza pizza : pizzaList) {
            if (pizza.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(pizza);
            }
        }
        adapter.filterList(filteredList);
    }

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

    private void filterPizzas() {
        String selectedPrice = priceSpinner.getSelectedItem().toString();
        String selectedSize = sizeSpinner.getSelectedItem().toString();
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        List<Pizza> filteredList = new ArrayList<>();
        for (Pizza pizza : pizzaList) {
            // Check if the pizza's category matches the selected category
            boolean categoryMatch = selectedCategory.equals("Any Category") || pizza.getCategory().equalsIgnoreCase(selectedCategory);

            // Assume that the selected size corresponds to a specific price range
            boolean priceMatch;
            if (selectedSize.equals("Small")) {
                priceMatch = selectedPrice.equals("Any Price") || isPizzaInPriceRange(pizza.getSmallPrice(), selectedPrice);
            } else if (selectedSize.equals("Medium")) {
                priceMatch = selectedPrice.equals("Any Price") || isPizzaInPriceRange(pizza.getMediumPrice(), selectedPrice);
            } else if (selectedSize.equals("Large")) {
                priceMatch = selectedPrice.equals("Any Price") || isPizzaInPriceRange(pizza.getLargePrice(), selectedPrice);
            } else {
                // For "Any Size", consider all pizza sizes
                priceMatch = selectedPrice.equals("Any Price") ||
                        (isPizzaInPriceRange(pizza.getSmallPrice(), selectedPrice) ||
                                isPizzaInPriceRange(pizza.getMediumPrice(), selectedPrice) ||
                                isPizzaInPriceRange(pizza.getLargePrice(), selectedPrice));
            }

            // If the pizza matches the category and price conditions, add it to the filtered list
            if (categoryMatch && priceMatch) {
                filteredList.add(pizza);
            }
        }

        adapter.filterList(filteredList);
    }

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
