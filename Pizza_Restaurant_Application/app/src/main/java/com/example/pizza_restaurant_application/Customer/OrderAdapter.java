package com.example.pizza_restaurant_application.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pizza_restaurant_application.DataAPI.Order;
import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.DataAPI.SpecialOfferOrder;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Object> orderList;
    private OnOrderClickListener listener;

    // Interface for click events on orders
    public interface OnOrderClickListener {
        void onOrderClick(Object order);
    }

    // Constructor to initialize order list and click listener
    public OrderAdapter(List<Object> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Object order = orderList.get(position);
        holder.bind(order);
    }

    // Return the size of the order list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder class to hold and manage the view for each order
    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderTextView;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderTextView = itemView.findViewById(R.id.orderTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Object order = orderList.get(position);
                        if (listener != null) {
                            listener.onOrderClick(order);
                        }
                    }
                }
            });
        }

        // Bind the order object to the view holder
        void bind(Object order) {
            if (order instanceof Order) {
                Order obj = (Order) order;
                // Set the text of the TextView with order details
                orderTextView.setText(String.format("\n\t- Pizza Name: %s\n\t- Date: %s\n\t- Time: %s",
                        obj.getPizzaName(), obj.getDate(), obj.getTime()));
            } else if (order instanceof SpecialOfferOrder) { // Check if the object is a SpecialOfferOrder
                SpecialOfferOrder obj = (SpecialOfferOrder) order;
                // Set the text of the TextView with special offer order details
                orderTextView.setText(String.format("\n\t- Pizza Name: %s\n\t- Date: %s\n\t- Time: %s",
                       obj.getPizzaName(), obj.getOrderDate(), obj.getOrderTime()));
            }
        }
    }
}
