package com.example.pizza_restaurant_application.Admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.DataAPI.User;
import com.example.pizza_restaurant_application.R;

public class ProfileFragment extends Fragment {

    // Declare UI elements and database helper
    private ImageView tvImage;
    private Button btnImage;
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
    private final int GALLERY_REQ_CODE = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvImage = view.findViewById(R.id.imageView_user);
        btnImage = view.findViewById(R.id.button_change_picture);
        tvFirstName = view.findViewById(R.id.editText_first_name);
        tvLastName = view.findViewById(R.id.editText_last_name);
        tvPhone = view.findViewById(R.id.editText_phone);
        tvEmail = view.findViewById(R.id.editText_email);
        tvGender = view.findViewById(R.id.editText_gender);
        etNewPassword = view.findViewById(R.id.editText_newPassword);
        etConfNewPassword = view.findViewById(R.id.editText_confNewPassword);
        btnUpdate = view.findViewById(R.id.button_update);
        btnNewPassword = view.findViewById(R.id.button_newPassword);

        dbHelper = new DataBaseHelper(getActivity());
        displayUserInfo();

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select a new profile picture
                Intent iGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        // Set click listener for updating user information
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String firstName = tvFirstName.getText().toString().trim();
                String lastName = tvLastName.getText().toString().trim();
                String phone = tvPhone.getText().toString().trim();
                String email = tvEmail.getText().toString().trim();

                if (!validateInputs(email, phone, firstName, lastName)) {
                    return;
                }

                boolean updated = dbHelper.updateUserInfo(email, firstName, lastName, phone);

                if (updated) {
                    Toast.makeText(getActivity(), "User information updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to update user information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for updating user password
        btnNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = etNewPassword.getText().toString().trim();
                String confNewPassword = etConfNewPassword.getText().toString().trim();

                if (!validatePassword(newPassword, confNewPassword)) {
                    return;
                }

                boolean updated = dbHelper.updateUserPassword(tvEmail.getText().toString().trim(), newPassword);

                if (updated) {
                    Toast.makeText(getActivity(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                    etNewPassword.setText("");
                    etConfNewPassword.setText("");
                } else {
                    Toast.makeText(getActivity(), "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // Handle the result of the gallery intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE && data != null) {
                // Set the selected image to the ImageView
                tvImage.setImageURI(data.getData());
                // Update profile picture in the database
                boolean updated = dbHelper.updateUserProfilePicture(tvEmail.getText().toString().trim(), data.getData().toString());
                if (updated) {
                    Toast.makeText(getActivity(), "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to update profile picture", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Fetch user information from the database and display it
    private void displayUserInfo() {
        User user = dbHelper.getUserByEmail();

        if (user != null) {
            tvFirstName.setText(user.getFirstName());
            tvLastName.setText(user.getLastName());
            tvPhone.setText(user.getPhone());
            tvEmail.setText(user.getEmail());
            tvEmail.setEnabled(false);  // Email should not be editable
            tvGender.setText(user.getGender());
            tvGender.setEnabled(false); // Gender should not be editable
            if (user.getProfilePicture() == null) {
                // Set a default image if profile picture is null
                tvImage.setImageResource(R.drawable.user_icon);
            } else {
                // Set the user's profile picture
                tvImage.setImageURI(user.getProfilePicture());
            }
        } else {
            Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    // Validate input fields for user information
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

    // Validate password fields
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
