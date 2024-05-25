package com.example.pizza_restaurant_application.Customer;

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

import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.SpecialOffers.SpecialOfferOrder;

import java.util.ArrayList;
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
        List<SpecialOfferOrder> specialOrderList = dbHelper.getSpecialOfferOrders();

        // Merge both lists
        List<Object> mergedList = new ArrayList<>();
        mergedList.addAll(orderList);
        mergedList.addAll(specialOrderList);

        // Initialize and set the adapter
        orderAdapter = new OrderAdapter(mergedList, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Object order) {
                // Handle order click
                // For example, display order details in a dialog
                showOrderDetailsDialog(order);
            }
        });
        recyclerView.setAdapter(orderAdapter);

        return view;
    }

    // Method to show order details in a dialog
    private void showOrderDetailsDialog(Object order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String orderDetails = "";
        if(order instanceof Order) {
            Order obj = (Order) order;
            builder.setTitle("Order Details");
            orderDetails = String.format("Order ID: %d, Pizza Name: %s, Size: %s, Quantity: %d, Total Price: $%.2f, Date: %s, Time: %s", obj.getOrderId(), obj.getPizzaName(), obj.getSize(), obj.getQuantity(), obj.getTotalPrice(), obj.getDate(), obj.getTime());
        }
        else if(order instanceof SpecialOfferOrder) {
            builder.setTitle("Special Order Details");
            SpecialOfferOrder obj = (SpecialOfferOrder) order;
            orderDetails = String.format("Order ID: %d, Pizza Name: %s, Size: %s, Total Price: $%.2f, Date: %s, Time: %s", obj.getOrderId(), obj.getPizzaName(), obj.getSize(), obj.getTotalPrice(), obj.getOrderDate(), obj.getOrderTime());

        }

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
