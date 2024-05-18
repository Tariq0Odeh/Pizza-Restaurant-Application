package com.example.pizza_restaurant_application;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAdminFragment extends Fragment {

    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Spinner genderSpinner;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_admin, container, false);
        dbHelper = new DataBaseHelper(getContext());

        emailEditText = rootView.findViewById(R.id.email_edit_text);
        phoneEditText = rootView.findViewById(R.id.phone_edit_text);
        firstNameEditText = rootView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = rootView.findViewById(R.id.last_name_edit_text);
        genderSpinner = rootView.findViewById(R.id.gender_spinner);
        passwordEditText = rootView.findViewById(R.id.password_edit_text);
        confirmPasswordEditText = rootView.findViewById(R.id.confirm_password_edit_text);

        Button signUpButton = rootView.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        return rootView;
    }

    private void signUp() {
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (validateInputs(email, phone, firstName, lastName, password, confirmPassword)) {
            if (dbHelper.checkEmailExists(email)) {
                Toast.makeText(getContext(), "Email already exists", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.checkPhoneExists(phone)) {
                Toast.makeText(getContext(), "Phone number already exists", Toast.LENGTH_SHORT).show();
            } else {
                // Encrypt password (if needed)
                String encryptedPassword = password;

                // Insert user into database
                User user = new User(email, phone, firstName, lastName, gender, encryptedPassword, null, true);
                boolean inserted = dbHelper.insertUser(user);

                if (inserted) {
                    Toast.makeText(getContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                    // Clear input fields after successful registration if needed
                    emailEditText.setText("");
                    phoneEditText.setText("");
                    firstNameEditText.setText("");
                    lastNameEditText.setText("");
                    passwordEditText.setText("");
                    confirmPasswordEditText.setText("");
                } else {
                    Toast.makeText(getContext(), "Failed to register user", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean validateInputs(String email, String phone, String firstName, String lastName, String password, String confirmPassword) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return false;
        }

        if (phone.isEmpty() || !phone.startsWith("05") || phone.length() != 10) {
            phoneEditText.setError("Enter a valid phone number starting with '05' and 10 digits");
            return false;
        }

        if (firstName.isEmpty() || firstName.length() < 3) {
            firstNameEditText.setError("Enter a valid first name (at least 3 characters)");
            return false;
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            lastNameEditText.setError("Enter a valid last name (at least 3 characters)");
            return false;
        }

        if (password.isEmpty() || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*")) {
            passwordEditText.setError("Password must be at least 8 characters long and contain at least one letter and one number");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}
