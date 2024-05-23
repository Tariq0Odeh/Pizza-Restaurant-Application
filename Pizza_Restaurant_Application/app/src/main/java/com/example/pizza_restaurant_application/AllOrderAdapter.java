package com.example.pizza_restaurant_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AllOrderAdapter extends ArrayAdapter<OrderWithCustomerName> {

    private LayoutInflater inflater;

    public AllOrderAdapter(Context context, List<OrderWithCustomerName> orders) {
        super(context, 0, orders);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_all_order, parent, false);
            holder = new ViewHolder();
            holder.customerNameTextView = convertView.findViewById(R.id.customerNameTextView);
            holder.orderDetailsTextView = convertView.findViewById(R.id.orderDetailsTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OrderWithCustomerName order = getItem(position);
        if (order != null) {
            // Setting customer name and order details to TextViews
            holder.customerNameTextView.setText(order.getCustomerName());
            holder.orderDetailsTextView.setText(
                    "Order ID: " + order.getOrderId() +
                            ", Pizza Name: " + order.getPizzaName() +
                            ", Size: " + order.getSize() +
                            ", Quantity: " + order.getQuantity() +
                            ", Total Price: " + order.getTotalPrice() +
                            ", Date: " + order.getDate() +
                            ", Time: " + order.getTime());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView customerNameTextView;
        TextView orderDetailsTextView;
    }
}
