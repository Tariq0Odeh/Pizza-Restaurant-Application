package com.example.pizza_restaurant_application.Customer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoritePizzaAdapter adapter;
    private DataBaseHelper databaseHelper;
    private EditText searchEditText;
    private Spinner priceSpinner;
    private Spinner sizeSpinner;
    private Spinner categorySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchEditText = view.findViewById(R.id.searchEditText);
        priceSpinner = view.findViewById(R.id.priceFilterSpinner);
        sizeSpinner = view.findViewById(R.id.sizeFilterSpinner);
        categorySpinner = view.findViewById(R.id.categoryFilterSpinner);

        setupSearch();
        setupSpinners();

        databaseHelper = new DataBaseHelper(getActivity());
        loadFavoritePizzas();

        return view;
    }

    private void loadFavoritePizzas() {
        List<Pizza> favoritePizzas = databaseHelper.getFavoritePizzas();
        adapter = new FavoritePizzaAdapter(favoritePizzas, pizza -> {
            databaseHelper.removeFavoritePizza(databaseHelper.getPizzaIdByName(pizza.getName()));
            loadFavoritePizzas();
            // Add your toast message here if needed
        });
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPizzas(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterPizzas(String query) {
        List<Pizza> favoritePizzas = databaseHelper.getFavoritePizzas();
        List<Pizza> filteredList = new ArrayList<>();
        for (Pizza pizza : favoritePizzas) {
            if (pizza.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(pizza);
            }
        }
        adapter.updateList(filteredList); // Update the adapter with the filtered list
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

        List<Pizza> favoritePizzas = databaseHelper.getFavoritePizzas();
        List<Pizza> filteredList = new ArrayList<>();
        for (Pizza pizza : favoritePizzas) {
            // Check if the pizza's name contains the search query
            boolean nameMatch = pizza.getName().toLowerCase().contains(searchEditText.getText().toString().toLowerCase());

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

            // If the pizza matches all conditions, add it to the filtered list
            if (nameMatch && categoryMatch && priceMatch) {
                filteredList.add(pizza);
            }
        }

        adapter.updateList(filteredList);
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
