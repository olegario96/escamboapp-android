package com.example.olegario.escamboapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.helper.DataValidator;

public class CreateUserActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEdiText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        this.firstNameEditText = findViewById(R.id.firstNameCreateEditText);
        this.lastNameEdiText = findViewById(R.id.lastNameCreateEditText);
        this.emailEditText = findViewById(R.id.emailCreateEditText);
        this.passwordEditText = findViewById(R.id.passwordCreateEditText);
        this.confirmPasswordEditText = findViewById(R.id.confirmPasswordCreateEditText);
    }

    public void cancel(View view) {
        // TODO
    }

    public void next(View view) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEdiText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        Log.i("DEBUG", ">>>>>>>");
        Log.i("DEBUG", password);
        Log.i("DEBUG", confirmPassword);
        final boolean dataIsValid = this.validateData(firstName, email, password, confirmPassword);
        if (dataIsValid) {
            // TODO
        }
    }

    private boolean validateData(String firstName, String email, String password, String confirmPassowrd) {

        final boolean nameIsValid = this.dataValidator.validateFirstName(firstName);
        final boolean emailIsValid = this.dataValidator.validateEmail(email);
        final boolean passwordIsValid = this.dataValidator.validatePassword(password);
        final boolean passwordsAreEquals = this.dataValidator.comparePasswords(password, confirmPassowrd);
        boolean result = false;

        if (!nameIsValid) {
            final String errMsg = getString(R.string.invalidName);
            this.firstNameEditText.setError(errMsg);
        }


        if (!emailIsValid) {
            final String errMsg = getString(R.string.invalidEmail);
            this.emailEditText.setError(errMsg);
        }


        if (!passwordIsValid) {
            final String errMsg = getString(R.string.shortPassword);
            this.passwordEditText.setError(errMsg);
        }

        if (!passwordsAreEquals) {
            final String errMsg = getString(R.string.differentPassword);
            this.passwordEditText.setError(errMsg);
            this.confirmPasswordEditText.setError(errMsg);
        } else {
            result = true;
        }

        return result;
    }
}
