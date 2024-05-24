package com.example.pizza_restaurant_application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PizzaDetailFragment extends Fragment {
    private static final String ARG_PIZZA = "pizza";
    private Pizza pizza;
    private boolean isFavorite = false;
    private DataBaseHelper dbHelper;

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

        dbHelper = new DataBaseHelper(getContext());

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
        Button addToFavoritesButton = view.findViewById(R.id.addToFavoritesButton);
        Button orderButton = view.findViewById(R.id.orderButton);
        Button submitOrderButton = view.findViewById(R.id.submitOrderButton);

        if (pizza != null) {
            nameTextView.setText(pizza.getName());
            categoryTextView.setText(pizza.getCategory());
            descriptionTextView.setText(pizza.getDescription());
            smallPriceTextView.setText(String.format("Small: $%.2f", pizza.getSmallPrice()));
            mediumPriceTextView.setText(String.format("Medium: $%.2f", pizza.getMediumPrice()));
            largePriceTextView.setText(String.format("Large: $%.2f", pizza.getLargePrice()));

            loadImage(pizza.getName(), imageView);

            isFavorite = checkIfPizzaIsFavorite();
            if (isFavorite) {
                addToFavoritesButton.setBackgroundResource(R.drawable.heart_filled);
            } else {
                addToFavoritesButton.setBackgroundResource(R.drawable.heart_shape);
            }

            addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavorite) {
                        removePizzaFromFavorites();
                        showToast("Removed from favorites");
                    } else {
                        addPizzaToFavorites();
                        showToast("Added to favorites");
                    }
                    isFavorite = !isFavorite;
                    if (isFavorite) {
                        addToFavoritesButton.setBackgroundResource(R.drawable.heart_filled);
                    } else {
                        addToFavoritesButton.setBackgroundResource(R.drawable.heart_shape);
                    }
                }
            });



            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrderDialog();
                }
            });
        }

        return view;
    }

    private boolean checkIfPizzaIsFavorite() {
        List<Pizza> favoritePizzas = dbHelper.getFavoritePizzas();
        for (Pizza favPizza : favoritePizzas) {
            if (favPizza.getName().equals(pizza.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addPizzaToFavorites() {
        List<Pizza> favoritePizzas = dbHelper.getFavoritePizzas();
        if (!favoritePizzas.contains(pizza)) {
            boolean result = dbHelper.addFavoritePizza(dbHelper.getPizzaIdByName(pizza.getName()));
            if (!result) {
                showToast("Failed to add to favorites");
            }
        }
    }

    private void removePizzaFromFavorites() {
        List<Pizza> favoritePizzas = dbHelper.getFavoritePizzas();
        if (favoritePizzas.contains(pizza)) {
            boolean result = dbHelper.removeFavoritePizza(dbHelper.getPizzaIdByName(pizza.getName()));
            if (!result) {
                showToast("Failed to remove from favorites");
            }
        }
    }

    private void showToast(String message) {
        Context context = getContext();
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImage(String pizzaName, ImageView imageView) {
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

    private void showOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_order, null);
        builder.setView(dialogView);

        TextView orderPizzaNameTextView = dialogView.findViewById(R.id.orderPizzaNameTextView);
        Spinner sizeSpinner = dialogView.findViewById(R.id.sizeSpinner);
        TextView priceTextView = dialogView.findViewById(R.id.priceTextView);
        EditText quantityEditText = dialogView.findViewById(R.id.quantityEditText);
        Button submitOrderButton = dialogView.findViewById(R.id.submitOrderButton);

        orderPizzaNameTextView.setText(pizza.getName());

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePriceTextView(priceTextView, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();


        submitOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = sizeSpinner.getSelectedItem().toString();
                if (size.equals("Any Size")) {
                    showToast("Please choose the size");
                    return;
                }
                String quantityStr = quantityEditText.getText().toString();
                if (quantityStr.isEmpty()) {
                    showToast("Please enter quantity.");
                    return;
                }
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    showToast("Quantity should be greater than zero.");
                    return;
                }
                double price = getPriceForSize(size) * quantity;

                // Get current date and time
                String date = getCurrentDate();

                // Get current time
                long currentTimeMillis = System.currentTimeMillis();
                String time = formatTime(String.valueOf(currentTimeMillis));

                // Insert the order into the database
                boolean success = dbHelper.insertOrder(dbHelper.getPizzaIdByName(pizza.getName()), pizza.getName(), size, quantity, price, date, time);
                if (success) {
                    showToast(String.format("Ordered %d %s pizza(s) for $%.2f", quantity, size, price));
                } else {
                    showToast("Failed to submit order.");
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void updatePriceTextView(TextView priceTextView, int sizePosition) {
        double price;
        switch (sizePosition) {
            case 1:
                price = pizza.getSmallPrice();
                break;
            case 2:
                price = pizza.getMediumPrice();
                break;
            case 3:
                price = pizza.getLargePrice();
                break;
            default:
                price = 0.0;
                break;
        }
        priceTextView.setText(String.format("Price: $%.2f", price));
    }

    private double getPriceForSize(String size) {
        switch (size) {
            case "Small":
                return pizza.getSmallPrice();
            case "Medium":
                return pizza.getMediumPrice();
            case "Large":
                return pizza.getLargePrice();
            default:
                return 0.0;
        }
    }

    private String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String formatTime(String timestamp) {
        long millis = Long.parseLong(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(millis));
    }

}
