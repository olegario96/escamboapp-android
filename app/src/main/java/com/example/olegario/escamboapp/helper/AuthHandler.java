package com.example.olegario.escamboapp.helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public final class AuthHandler {

    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final AuthHandler INSTANCE = new AuthHandler();

    public AuthHandler() {}

    public boolean createUser(String email, String password) {
        Task authenticated = auth.createUserWithEmailAndPassword(email, password);
        return authenticated.isSuccessful();
    }

    public boolean authenticateUser(String email, String password) {
        Task authenticated = auth.signInWithEmailAndPassword(email, password);
        return authenticated.isSuccessful();
    }

    public void logoutUser() {
        auth.signOut();
    }

    public boolean isUserAuthenticated() {
        return (auth.getCurrentUser() != null);
    }

    public static AuthHandler getInstance() {
        return INSTANCE;
    }
}
