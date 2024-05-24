package com.example.pizza_restaurant_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Object> orderList;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Object order);
    }

    public OrderAdapter(List<Object> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Object order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

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

        void bind(Object order) {
            if(order instanceof Order) {
                Order obj = (Order) order;
                orderTextView.setText(String.format("Order ID: %d, Pizza Name: %s, Date: %s, Time: %s",
                        obj.getOrderId(), obj.getPizzaName(), obj.getDate(), obj.getTime()));
            }
            else if(order instanceof SpecialOfferOrder) {
                SpecialOfferOrder obj = (SpecialOfferOrder) order;
                orderTextView.setText(String.format("Order ID: %d, Pizza Name: %s, Date: %s, Time: %s",
                        obj.getOrderId(), obj.getPizzaName(), obj.getOrderDate(), obj.getOrderTime()));
            }
        }
    }
}
