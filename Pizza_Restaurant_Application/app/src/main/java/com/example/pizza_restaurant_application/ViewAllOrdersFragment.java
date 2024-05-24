package com.example.pizza_restaurant_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ViewAllOrdersFragment extends Fragment {

    private ListView ordersListView;

    public ViewAllOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ordersListView = view.findViewById(R.id.ordersListView);

        // Fetch all orders with customer names from the database
        List<Object> allOrdersWithCustomerName = getAllOrdersWithCustomerName();

        // Create a custom adapter to display the orders in the ListView
        AllOrderAdapter adapter = new AllOrderAdapter(requireContext(), allOrdersWithCustomerName);

        // Set the adapter to the ListView
        ordersListView.setAdapter(adapter);
    }

    private List<Object> getAllOrdersWithCustomerName() {
        // Use the database helper to fetch all orders with customer names
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());
        List<Object> allOrders = new ArrayList<>();
        allOrders.addAll(dbHelper.getAllOrdersWithCustomerName());
        allOrders.addAll(dbHelper.getSpecialOfferOrdersWithName());
        return allOrders;
    }
}
