package com.example.pizza_restaurant_application.Customer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pizza_restaurant_application.DataAPI.SpecialOffer;
import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SpecialOfferDetailFragment extends Fragment {
    private static final String ARG_SPECIAL_OFFER = "special_offer";
    private SpecialOffer specialOffer;
    private DataBaseHelper dbHelper;

    public static SpecialOfferDetailFragment newInstance(SpecialOffer specialOffer) {
        SpecialOfferDetailFragment fragment = new SpecialOfferDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SPECIAL_OFFER, specialOffer);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_offer_detail, container, false);

        dbHelper = new DataBaseHelper(getContext());

        if (getArguments() != null) {
            specialOffer = (SpecialOffer) getArguments().getSerializable(ARG_SPECIAL_OFFER);
        }

        // Find views
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView categoryTextView = view.findViewById(R.id.categoryTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        TextView sizeView = view.findViewById(R.id.sizeTextView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);
        Button addToFavoritesButton = view.findViewById(R.id.addToFavoritesButton);
        Button orderButton = view.findViewById(R.id.orderButton);

        // Populate views
        if (specialOffer != null) {
            nameTextView.setText(specialOffer.getPizzaName());
            categoryTextView.setText(specialOffer.getPizzaCategory());
            sizeView.setText(specialOffer.getSize());
            descriptionTextView.setText(specialOffer.getPizzaDescription());
            priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", specialOffer.getPrice()));

            // Load image for special offer
            loadImage(specialOffer.getPizzaName(), imageView);

            // Check if special offer is already in favorites and adjust favorite button background
            if (isFavorite()) {
                addToFavoritesButton.setBackgroundResource(R.drawable.heart_red);
            } else {
                addToFavoritesButton.setBackgroundResource(R.drawable.heart);
            }

            // Add to favorites button click listener
            addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavorite();
                    // Update favorite button background
                    if (isFavorite()) {
                        addToFavoritesButton.setBackgroundResource(R.drawable.heart_red);
                    } else {
                        addToFavoritesButton.setBackgroundResource(R.drawable.heart);
                    }
                }
            });

            // Order button click listener
            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrderDialog();
                }
            });
        }

        return view;
    }

    // Method to load special offer photo
    private void loadImage(String pizzaName, ImageView imageView) {
        switch (pizzaName) {
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

    private void toggleFavorite() {
        if (isFavorite()) {
            removeFromFavorites();
        } else {
            addToFavorites();
        }
    }

    private boolean isFavorite() {
        return dbHelper.isFavoriteSpecialOffer(specialOffer.getSpecialOfferId());
    }

    private void addToFavorites() {
        boolean result = dbHelper.addFavoriteSpecialOffer(specialOffer.getSpecialOfferId());
        if (result) {
            showToast("Added to favorites");
        } else {
            showToast("Failed to add to favorites");
        }
    }

    private void removeFromFavorites() {
        boolean result = dbHelper.removeFavoriteSpecialOffer(specialOffer.getSpecialOfferId());
        if (result) {
            showToast("Removed from favorites");
        } else {
            showToast("Failed to remove from favorites");
        }
    }

    private void showOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_special_order, null);
        builder.setView(dialogView);

        TextView orderPizzaNameTextView = dialogView.findViewById(R.id.orderPizzaNameTextView);
        TextView sizeTextView = dialogView.findViewById(R.id.sizeTextView);
        TextView priceTextView = dialogView.findViewById(R.id.priceTextView);
        EditText quantityEditText = dialogView.findViewById(R.id.quantityEditText);
        Button submitOrderButton = dialogView.findViewById(R.id.submitOrderButton);

        orderPizzaNameTextView.setText(specialOffer.getPizzaName());
        sizeTextView.setText(specialOffer.getSize());
        priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", specialOffer.getPrice()));

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
                submitOrder(dialog, quantityEditText);
            }
        });

        dialog.show();
    }

    private void submitOrder(AlertDialog dialog, EditText quantityEditText) {
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
        double price = specialOffer.getPrice() * quantity;

        // Get current date and time
        String date = getCurrentDate();
        long currentTimeMillis = System.currentTimeMillis();
        String time = formatTime(String.valueOf(currentTimeMillis));

        // Insert the order into the database
        boolean success = dbHelper.insertSpecialOfferOrder(specialOffer.getSpecialOfferId(), specialOffer.getPizzaName(), specialOffer.getSize(), quantity, price, date, time);
        if (success) {
            showToast(String.format("Ordered %d %s pizzas for $%.2f", quantity, specialOffer.getSize(), price));
            dialog.dismiss(); // Dismiss dialog only on success
        } else {
            showToast("Failed to submit order.");
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String formatTime(String timestamp) {
        long millis = Long.parseLong(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(millis));
    }

    private void showToast(String message) {
        Context context = getContext();
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}

