package com.example.pizza_restaurant_application.Customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pizza_restaurant_application.R;

public class CallFindUsFragment extends Fragment implements View.OnClickListener {

    public CallFindUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_find_us, container, false);

        Button btnCall = view.findViewById(R.id.btn_call);
        Button btnFind = view.findViewById(R.id.btn_find);
        Button btnEmail = view.findViewById(R.id.btn_email);

        btnCall.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        btnEmail.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call:
                // Call the restaurant
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0599000000"));
                startActivity(callIntent);
                break;
            case R.id.btn_find:
                // Open Google Maps to find the restaurant
                Uri gmmIntentUri = Uri.parse("geo:31.961013,35.190483?q=restaurant");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(requireContext(), "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_email:
                // Open Gmail to send an email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:AdvancePizza@Pizza.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Regarding your restaurant");
                if (emailIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}