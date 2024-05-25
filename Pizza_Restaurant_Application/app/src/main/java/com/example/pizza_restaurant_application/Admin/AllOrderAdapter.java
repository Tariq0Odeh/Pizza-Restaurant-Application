package com.example.pizza_restaurant_application.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pizza_restaurant_application.R;

import java.util.List;

public class AllOrderAdapter extends ArrayAdapter<Object> {

    private LayoutInflater inflater;

    public AllOrderAdapter(Context context, List<Object> orders) {
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

        Object order = getItem(position);

        if (order instanceof OrderWithCustomerName) {
            OrderWithCustomerName orderWithCustomerName = (OrderWithCustomerName) order;
            if (orderWithCustomerName != null) {
                // Setting customer name and order details to TextViews
                holder.customerNameTextView.setText(orderWithCustomerName.getCustomerName());
                holder.orderDetailsTextView.setText(
                        "Order ID: " + orderWithCustomerName.getOrderId() +
                                ", Pizza Name: " + orderWithCustomerName.getPizzaName() +
                                ", Size: " + orderWithCustomerName.getSize() +
                                ", Quantity: " + orderWithCustomerName.getQuantity() +
                                ", Total Price: " + orderWithCustomerName.getTotalPrice() +
                                ", Date: " + orderWithCustomerName.getDate() +
                                ", Time: " + orderWithCustomerName.getTime());
            }
        } else if (order instanceof SpecialOrderWithCustomerName) {
            SpecialOrderWithCustomerName specialOrder = (SpecialOrderWithCustomerName) order;
            if (specialOrder != null) {
                // Setting customer name and order details to TextViews
                holder.customerNameTextView.setText(specialOrder.getCustomerName());
                holder.orderDetailsTextView.setText(
                        "Order ID: " + specialOrder.getOrderId() +
                                ", Pizza Name: " + specialOrder.getPizzaName() +
                                ", Size: " + specialOrder.getSize() +
                                ", Total Price: " + specialOrder.getTotalPrice() +
                                ", Date: " + specialOrder.getOrderDate() +
                                ", Time: " + specialOrder.getOrderTime());
            }
        }
        return convertView;
    }

    static class ViewHolder {
        TextView customerNameTextView;
        TextView orderDetailsTextView;
    }
}
