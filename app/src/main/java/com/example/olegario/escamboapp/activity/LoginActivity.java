package com.example.olegario.escamboapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.firebase.FirebaseAuthHandler;
import com.example.olegario.escamboapp.helper.DataValidator;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private FirebaseAuthHandler firebaseAuthHandler = FirebaseAuthHandler.getInstance();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailEditText = findViewById(R.id.emailSignInEditText);
        this.passwordEditText = findViewById(R.id.passwordLoginEditText);
        this.signInButton = findViewById(R.id.signInButton);
    }

    public void authenticateUser(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (this.validateData(email, password)) {
            boolean authenticated = firebaseAuthHandler.authenticateUser(email, password);
            if (authenticated) {
                // TODO
            } else {
                final String errMsg = getString(R.string.signInError);
                Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateData(String email, String password) {
        final boolean isEmailValid = this.dataValidator.validateEmail(email);
        final boolean isPasswordValid = this.dataValidator.validatePassword(password);
        boolean result = false;

        if (!isEmailValid) {
            final String errMsg = getString(R.string.invalidEmail);
            this.emailEditText.setError(errMsg);
        }

        if (!isPasswordValid) {
            final String errMsg = getString(R.string.shortPassword);
            this.passwordEditText.setError(errMsg);
        } else {
            result = true;
        }

        return result;
    }
}
