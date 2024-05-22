package com.example.pizza_restaurant_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PizzaMenuFragment extends Fragment {
    private RecyclerView recyclerView;
    private PizzaAdapter adapter;
    private DataBaseHelper databaseHelper;
    private List<Pizza> pizzaList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_menu, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DataBaseHelper(getContext());
        // Fetch pizzas only if the list is null
        if (pizzaList == null) {
            pizzaList = databaseHelper.getAllPizzas();
        }
        adapter = new PizzaAdapter(pizzaList, pizza -> {
            // Handle click event
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            PizzaDetailFragment detailFragment = PizzaDetailFragment.newInstance(pizza);
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}