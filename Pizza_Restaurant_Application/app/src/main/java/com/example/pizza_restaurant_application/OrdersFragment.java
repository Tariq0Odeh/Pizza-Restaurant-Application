package com.example.pizza_restaurant_application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrdersFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private DataBaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = view.findViewById(R.id.ordersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper = new DataBaseHelper(getContext());

        // Fetch user's orders from the database
        List<Order> orderList = dbHelper.getUserOrders();

        // Initialize and set the adapter
        orderAdapter = new OrderAdapter(orderList, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                // Handle order click
                // For example, display order details in a dialog
                showOrderDetailsDialog(order);
            }
        });
        recyclerView.setAdapter(orderAdapter);

        return view;
    }

    // Method to show order details in a dialog
    private void showOrderDetailsDialog(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Order Details");
        String orderDetails = String.format("Order ID: %d, Pizza Name: %s, Size: %s, Quantity: %d, Total Price: $%.2f, Date: %s, Time: %s",
                order.getOrderId(), order.getPizzaName(), order.getSize(), order.getQuantity(), order.getTotalPrice(), order.getDate(), order.getTime());
        builder.setMessage(orderDetails);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
