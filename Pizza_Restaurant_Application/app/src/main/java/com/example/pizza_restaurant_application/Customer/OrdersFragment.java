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
import com.example.pizza_restaurant_application.DataAPI.Order;
import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.DataAPI.SpecialOfferOrder;
import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private DataBaseHelper dbHelper;

    // Method called to create the view hierarchy associated with the fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.ordersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DataBaseHelper(getContext());

        // Fetch user's orders from the database
        List<Order> orderList = dbHelper.getUserOrders();
        List<SpecialOfferOrder> specialOrderList = dbHelper.getSpecialOfferOrders();

        // Merge both lists into a single list
        List<Object> mergedList = new ArrayList<>();
        mergedList.addAll(orderList);
        mergedList.addAll(specialOrderList);

        // Initialize and set the adapter with merged list and click listener
        orderAdapter = new OrderAdapter(mergedList, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Object order) {
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
            // Format order details
            orderDetails = String.format("[Normal Order]\nOrder ID: %d\n\t- Pizza Name: %s\n\t- Size: %s\n\t- Quantity: %d\n\t- Total Price: $%.2f\n\t- Date: %s\n\t- Time: %s",
                    obj.getOrderId(), obj.getPizzaName(), obj.getSize(), obj.getQuantity(), obj.getTotalPrice(), obj.getDate(), obj.getTime());
        } else if(order instanceof SpecialOfferOrder) { // If the order is a special offer order
            builder.setTitle("Special Order Details");
            SpecialOfferOrder obj = (SpecialOfferOrder) order;
            // Format special offer order details
            orderDetails = String.format("[Special Order]\nOrder ID: %d\n\t- Pizza Name: %s\n\t- Size: %s\n\t- Total Price: $%.2f\n\t- Date: %s\n\t- Time: %s",
                    obj.getOrderId(), obj.getPizzaName(), obj.getSize(), obj.getTotalPrice(), obj.getOrderDate(), obj.getOrderTime());
        }

        // Set the dialog message and positive button
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
