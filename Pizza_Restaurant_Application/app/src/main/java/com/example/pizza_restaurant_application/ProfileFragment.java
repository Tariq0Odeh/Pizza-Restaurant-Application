package com.example.pizza_restaurant_application;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private ImageView tvImage;
    private EditText tvFirstName;
    private EditText tvLastName;
    private EditText tvPhone;
    private EditText tvEmail;
    private EditText tvGender;
    private EditText etNewPassword;
    private EditText etConfNewPassword;
    private Button btnUpdate;
    private Button btnNewPassword;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        tvImage = view.findViewById(R.id.imageView);
        tvFirstName = view.findViewById(R.id.editText_first_name);
        tvLastName = view.findViewById(R.id.editText_last_name);
        tvPhone = view.findViewById(R.id.editText_phone);
        tvEmail = view.findViewById(R.id.editText_email);
        tvGender = view.findViewById(R.id.editText_gender);
        etNewPassword = view.findViewById(R.id.editText_newPassword);
        etConfNewPassword = view.findViewById(R.id.editText_confNewPassword);
        btnUpdate = view.findViewById(R.id.button_update);
        btnNewPassword = view.findViewById(R.id.button_newPassword);

        // Initialize database helper
        dbHelper = new DataBaseHelper(getActivity());

        // Fetch user information and update TextViews
        displayUserInfo();

        // Set onClickListener for the update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values from EditText fields
                String firstName = tvFirstName.getText().toString().trim();
                String lastName = tvLastName.getText().toString().trim();
                String phone = tvPhone.getText().toString().trim();
                String email = tvEmail.getText().toString().trim();

                // Validate inputs
                if (!validateInputs(email, phone, firstName, lastName)) {
                    return;
                }

                // Update user information
                boolean updated = dbHelper.updateUserInfo(email, firstName, lastName, phone);

                // Display a toast message indicating success or failure
                if (updated) {
                    // User information updated successfully
                    Toast.makeText(getActivity(), "User information updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Failed to update user information
                    Toast.makeText(getActivity(), "Failed to update user information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClickListener for the new password button
        btnNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new password and confirm new password
                String newPassword = etNewPassword.getText().toString().trim();
                String confNewPassword = etConfNewPassword.getText().toString().trim();

                // Validate inputs
                if (!validatePassword(newPassword, confNewPassword)) {
                    return;
                }

                // Update user password
                boolean updated = dbHelper.updateUserPassword(tvEmail.getText().toString().trim(), newPassword);

                // Display a toast message indicating success or failure
                if (updated) {
                    // Password updated successfully
                    Toast.makeText(getActivity(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                    // Clear EditText fields
                    etNewPassword.setText("");
                    etConfNewPassword.setText("");
                } else {
                    // Failed to update password
                    Toast.makeText(getActivity(), "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void displayUserInfo() {
        // Retrieve user information from the database
        User user = dbHelper.getUserByEmail();

        // Check if user exists
        if (user != null) {
            // Set user information in TextViews
            tvFirstName.setText(user.getFirstName());
            tvLastName.setText(user.getLastName());
            tvPhone.setText(user.getPhone());
            tvEmail.setText(user.getEmail());
            tvEmail.setEnabled(false);
            tvGender.setText(user.getGender());
            tvGender.setEnabled(false);
        } else {
            // Handle the case where user does not exist
        }
    }

    private boolean validateInputs(String email, String phone, String firstName, String lastName) {
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tvEmail.setError("Enter a valid email address");
            return false;
        }

        if (TextUtils.isEmpty(phone) || !phone.startsWith("05") || phone.length() != 10) {
            tvPhone.setError("Enter a valid phone number starting with '05' and 10 digits");
            return false;
        }

        if (TextUtils.isEmpty(firstName) || firstName.length() < 3) {
            tvFirstName.setError("Enter a valid first name (at least 3 characters)");
            return false;
        }

        if (TextUtils.isEmpty(lastName) || lastName.length() < 3) {
            tvLastName.setError("Enter a valid last name (at least 3 characters)");
            return false;
        }

        return true;
    }

    private boolean validatePassword(String newPassword, String confNewPassword) {
        if (TextUtils.isEmpty(newPassword) || newPassword.length() < 8 || !newPassword.matches(".*\\d.*") || !newPassword.matches(".*[a-zA-Z].*")) {
            etNewPassword.setError("Password must be at least 8 characters long and contain at least one letter and one number");
            return false;
        }

        if (!TextUtils.equals(newPassword, confNewPassword)) {
            etConfNewPassword.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}
