package com.example.olegario.escamboapp.helper;

import android.util.Patterns;

import java.util.InputMismatchException;

public class DataValidator {

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

    public boolean validateCPF(String cpf) {
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public boolean validateTitleForAd(String title) {
        return !this.isEmptyString(title);
    }

    public boolean validatePrice(String price) {
        if (!this.isEmptyString(price)) {
            final int numCommas = this.countChar(price, ',');
            if (numCommas == 0 || numCommas == 1) {
                final String priceFormatted = price.replace(',', '.');
                try {
                    Double.parseDouble(priceFormatted);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateDescription(String description) {
        return !this.isEmptyString(description);
    }

    private boolean isEmptyString(String s) {
        return s.length() == 0;
    }

    private int countChar(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == c) {
                ++count;
            }
        }

        return count;
    }
}
