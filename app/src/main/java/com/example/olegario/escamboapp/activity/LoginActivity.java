package com.example.olegario.escamboapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.helper.AuthHandler;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private AuthHandler authHandler = AuthHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailEditText = findViewById(R.id.emailEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
        this.signInButton = findViewById(R.id.signInButton);
    }

    public void authenticateUser(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (this.validateData(email, password)) {
            boolean authenticated = authHandler.authenticateUser(email, password);
            if (authenticated) {
            } else {
                final String errMsg = getString(R.string.signInError);
                Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateData(String email, String password) {
        return this.validateEmail(email) && this.validatePassword(password);
    }

    private boolean validateEmail(String email) {
        if (email.length() > 0) {
            final boolean validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
            if (validEmail) {
                return true;
            } else {
                final String errMsg = getString(R.string.invalidEmail);
                this.emailEditText.setError(errMsg);
                return false;
            }
        } else {
            final String errMsg = getString(R.string.emptyEmail);
            this.emailEditText.setError(errMsg);
            return false;
        }
    }

    private boolean validatePassword(String password) {
        if (password.length() > 0) {
            if (password.length() >= 8) {
                return true;
            } else {
                final String errMsg = getString(R.string.shortPassword);
                this.passwordEditText.setError(errMsg);
                return false;
            }
        } else {
            final String errMsg = getString(R.string.emptyPassword);
            this.passwordEditText.setError(errMsg);
            return false;
        }
    }
}
