package com.example.olegario.escamboapp.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class FirebaseAuthHandler {

    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final FirebaseAuthHandler INSTANCE = new FirebaseAuthHandler();

    public FirebaseAuthHandler() {}

    public Task createUser(String email, String password) {
        Task authenticated = auth.createUserWithEmailAndPassword(email, password);
        return authenticated;
    }

    public Task authenticateUser(String email, String password) {
        String passwordHash = this.encryptPassword(password);
        return auth.signInWithEmailAndPassword(email, passwordHash);
    }

    public String getEmailFromCurrentUser() {
        return auth.getCurrentUser().getEmail();
    }

    public void logoutUser() {
        auth.signOut();
    }

    public boolean isUserAuthenticated() {
        return (auth.getCurrentUser() != null);
    }

    public static FirebaseAuthHandler getInstance() {
        return INSTANCE;
    }

    public String encryptPassword(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder(2*hash.length);
            for (byte b: hash) {
                sb.append(String.format("%02x", b&0xff));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
