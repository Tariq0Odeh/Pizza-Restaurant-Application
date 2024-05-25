package com.example.pizza_restaurant_application.SpecialOffers;

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

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView categoryTextView = view.findViewById(R.id.categoryTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);
        ImageView imageView = view.findViewById(R.id.imageView);
        Button addToFavoritesButton = view.findViewById(R.id.addToFavoritesButton);
        Button orderButton = view.findViewById(R.id.orderButton);

        if (specialOffer != null) {
            nameTextView.setText(specialOffer.getPizzaName());
            categoryTextView.setText(specialOffer.getPizzaCategory());
            descriptionTextView.setText(specialOffer.getPizzaDescription());
            priceTextView.setText(String.format(Locale.getDefault(), "%.2f", specialOffer.getPrice()));

            loadImage(specialOffer.getPizzaName(), imageView);

            addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavorite();
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

    private void loadImage(String pizzaName, ImageView imageView) {
        // Load image based on pizza name
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
            showToast(String.format("Ordered %d %s pizza(s) for $%.2f", quantity, specialOffer.getSize(), price));
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
