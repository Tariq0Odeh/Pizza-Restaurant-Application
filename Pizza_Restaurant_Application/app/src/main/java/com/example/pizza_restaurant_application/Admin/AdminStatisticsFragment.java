package com.example.pizza_restaurant_application.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;

import java.util.Map;

public class AdminStatisticsFragment extends Fragment {

    private TextView ordersPerPizzaTextView;
    private TextView totalIncomePerPizzaTextView;
    private TextView totalIncomeTextView;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_statistics, container, false);

        // Initialize views
        ordersPerPizzaTextView = view.findViewById(R.id.orders_per_pizza_text_view);
        totalIncomePerPizzaTextView = view.findViewById(R.id.total_income_per_pizza_text_view);
        totalIncomeTextView = view.findViewById(R.id.total_income_text_view);

        // Initialize database helper
        dbHelper = new DataBaseHelper(getContext());

        // Update statistics
        updateStatistics();

        return view;
    }

    private void updateStatistics() {
        // Number of orders for each pizza type
        Map<String, Integer> ordersPerType = dbHelper.getOrdersPerPizzaType();
        StringBuilder ordersPerPizzaText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : ordersPerType.entrySet()) {
            ordersPerPizzaText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        ordersPerPizzaTextView.setText(ordersPerPizzaText.toString());

        // Total income for each type
        Map<String, Double> incomePerType = dbHelper.getTotalIncomePerPizzaType();
        StringBuilder totalIncomePerPizzaText = new StringBuilder();
        for (Map.Entry<String, Double> entry : incomePerType.entrySet()) {
            totalIncomePerPizzaText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        totalIncomePerPizzaTextView.setText(totalIncomePerPizzaText.toString());

        // Total income for all types together
        double totalIncome = dbHelper.getTotalIncomeForAllTypes();
        totalIncomeTextView.setText("Total Income: " + totalIncome);
    }
}
