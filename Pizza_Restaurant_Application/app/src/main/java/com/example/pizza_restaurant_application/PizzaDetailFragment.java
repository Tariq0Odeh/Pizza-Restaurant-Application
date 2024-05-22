package com.example.pizza_restaurant_application;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PizzaDetailFragment extends Fragment {
    private static final String ARG_PIZZA = "pizza";
    private Pizza pizza;

    public static PizzaDetailFragment newInstance(Pizza pizza) {
        PizzaDetailFragment fragment = new PizzaDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PIZZA, pizza);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_detail, container, false);

        if (getArguments() != null) {
            pizza = (Pizza) getArguments().getSerializable(ARG_PIZZA);
        }

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView categoryTextView = view.findViewById(R.id.categoryTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        TextView smallPriceTextView = view.findViewById(R.id.smallPriceTextView);
        TextView mediumPriceTextView = view.findViewById(R.id.mediumPriceTextView);
        TextView largePriceTextView = view.findViewById(R.id.largePriceTextView);
        ImageView imageView = view.findViewById(R.id.imageView);

        if (pizza != null) {
            nameTextView.setText(pizza.getName());
            categoryTextView.setText(pizza.getCategory());
            descriptionTextView.setText(pizza.getDescription());
            smallPriceTextView.setText(String.format("Small: $%.2f", pizza.getSmallPrice()));
            mediumPriceTextView.setText(String.format("Medium: $%.2f", pizza.getMediumPrice()));
            largePriceTextView.setText(String.format("Large: $%.2f", pizza.getLargePrice()));

            switch (pizza.getName()) {
                case "Margherita Pizza":
                    imageView.setImageResource(R.drawable.margarita);
                    break;
                case "Neapolitan Pizza":
                    imageView.setImageResource(R.drawable.neapolitan);
                    break;
                case "Hawaiian Pizza":
                    imageView.setImageResource(R.drawable.hawaiian);
                    break;
                case "Pepperoni Pizza":
                    imageView.setImageResource(R.drawable.pepperoni);
                    break;
                case "New York Style Pizza":
                    imageView.setImageResource(R.drawable.new_york_style);
                    break;
                case "Calzone":
                    imageView.setImageResource(R.drawable.calzone);
                    break;
                case "Tandoori Chicken Pizza":
                    imageView.setImageResource(R.drawable.tandoori_chicken);
                    break;
                case "BBQ Chicken Pizza":
                    imageView.setImageResource(R.drawable.bbq_chicken);
                    break;
                case "Seafood Pizza":
                    imageView.setImageResource(R.drawable.seafood);
                    break;
                case "Vegetarian Pizza":
                    imageView.setImageResource(R.drawable.vegetarian);
                    break;
                case "Buffalo Chicken Pizza":
                    imageView.setImageResource(R.drawable.buffalo_chicken);
                    break;
                case "Mushroom Truffle Pizza":
                    imageView.setImageResource(R.drawable.mushroom_truffle);
                    break;
                case "Pesto Chicken Pizza":
                    imageView.setImageResource(R.drawable.pesto_chicken);
                    break;
                default:
                    imageView.setImageResource(R.drawable.pizza_icon);
                    break;
            }
        }

        return view;
    }
}

