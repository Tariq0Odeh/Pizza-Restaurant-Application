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

    private TextView ordersPerPizzaContentTextView;
    private TextView totalIncomePerPizzaContentTextView;
    private TextView totalIncomeContentTextView;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_statistics, container, false);

        // Initialize TextViews
        ordersPerPizzaContentTextView = view.findViewById(R.id.orders_per_pizza_content);
        totalIncomePerPizzaContentTextView = view.findViewById(R.id.total_income_per_pizza_content);
        totalIncomeContentTextView = view.findViewById(R.id.total_income_content);
        dbHelper = new DataBaseHelper(getContext());

        updateStatistics();
        return view;
    }

    // Method to update the statistics data displayed in the fragment
    private void updateStatistics() {
        Map<String, Integer> ordersPerType = dbHelper.getOrdersPerPizzaType();
        StringBuilder ordersPerPizzaText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : ordersPerType.entrySet()) {
            ordersPerPizzaText.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Set the text for orders per pizza type
        ordersPerPizzaContentTextView.setText(ordersPerPizzaText.toString());

        // Get the total income for each pizza type
        Map<String, Double> incomePerType = dbHelper.getTotalIncomePerPizzaType();
        StringBuilder totalIncomePerPizzaText = new StringBuilder();
        for (Map.Entry<String, Double> entry : incomePerType.entrySet()) {
            totalIncomePerPizzaText.append("- ").append(entry.getKey()).append(": $").append(entry.getValue()).append("\n");
        }
        // Set the text for total income per pizza type
        totalIncomePerPizzaContentTextView.setText(totalIncomePerPizzaText.toString());
        double totalIncome = dbHelper.getTotalIncomeForAllTypes();
        totalIncomeContentTextView.setText("- Total Income: $" + totalIncome);
    }
}
