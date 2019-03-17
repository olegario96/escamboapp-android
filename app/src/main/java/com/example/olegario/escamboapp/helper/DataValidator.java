package com.example.olegario.escamboapp.helper;

import android.util.Patterns;

public final class DataValidator {

    private static final DataValidator INSTANCE = new DataValidator();

    private DataValidator() {}

    public static DataValidator getInstance() {
        return INSTANCE;
    }

    public boolean validateEmail(String email) {
        if (email.length() > 0) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return false;
        }
    }

    public boolean validatePassword(String password) {
        if (password.length() > 0) {
            return password.length() >= 8;
        } else {
            return false;
        }
    }

    public boolean comparePasswords(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean validateFirstName(String firstName) {
        return firstName.length() > 0;
    }
}
