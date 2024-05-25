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
import com.example.pizza_restaurant_application.SpecialOffers.SpecialOffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FavoritesOffersFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteOffersAdapter adapter;
    private DataBaseHelper databaseHelper;
    private EditText searchEditText;
    private Spinner priceSpinner;
    private Spinner sizeSpinner;
    private Spinner categorySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_offers, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchEditText = view.findViewById(R.id.searchEditText);
        priceSpinner = view.findViewById(R.id.priceFilterSpinner);
        sizeSpinner = view.findViewById(R.id.sizeFilterSpinner);
        categorySpinner = view.findViewById(R.id.categoryFilterSpinner);

        setupSearch();
        setupSpinners();

        databaseHelper = new DataBaseHelper(getActivity());
        loadFavoritePizzas(); // Load favorite pizzas initially

        return view;
    }

    private void loadFavoritePizzas() {
        List<SpecialOffer> favoritePizzas = databaseHelper.getFavoriteSpecialOffers();
        adapter = new FavoriteOffersAdapter(favoritePizzas, specialOffer -> {
            databaseHelper.removeFavoriteSpecialOffer(specialOffer.getSpecialOfferId());
            adapter.removeSpecialOffer(specialOffer); // Remove from adapter's list
            // You can also add a toast message here if needed
        });

        // Set the adapter to the RecyclerView after initialization
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Notify adapter of dataset change
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
        List<SpecialOffer> favoritePizzas = databaseHelper.getFavoriteSpecialOffers();
        List<SpecialOffer> filteredList = new ArrayList<>();
        for (SpecialOffer pizza : favoritePizzas) {
            if (pizza.getPizzaName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(pizza);
            }
        }
        adapter.updateListOffers(filteredList); // Update the adapter with the filtered list
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
        String query = searchEditText.getText().toString();
        String selectedPrice = priceSpinner.getSelectedItem().toString();
        String selectedSize = sizeSpinner.getSelectedItem().toString();
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        List<SpecialOffer> favoritePizzas = databaseHelper.getFavoriteSpecialOffers();
        List<SpecialOffer> filteredList = new ArrayList<>();
        for (SpecialOffer pizza : favoritePizzas) {
            boolean categoryMatch = selectedCategory.equals("Any Category") || pizza.getPizzaCategory().equalsIgnoreCase(selectedCategory);
            boolean sizeMatch = selectedSize.equals("Any Size") || pizza.getSize().equalsIgnoreCase(selectedSize);
            boolean priceMatch = isPizzaInPriceRange(pizza.getPrice(), selectedPrice);
            boolean searchMatch = query.isEmpty() || pizza.getPizzaName().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()));

            if (categoryMatch && sizeMatch && priceMatch && searchMatch) {
                filteredList.add(pizza);
            }
        }

        adapter.updateListOffers(filteredList);
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
