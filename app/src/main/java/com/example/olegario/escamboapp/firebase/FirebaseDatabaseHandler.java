package com.example.olegario.escamboapp.firebase;

import com.example.olegario.escamboapp.model.Ad;
import com.example.olegario.escamboapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public final class FirebaseDatabaseHandler {
    private static final FirebaseDatabase reference = FirebaseDatabase.getInstance();
    private static final FirebaseDatabaseHandler INSTANCE = new FirebaseDatabaseHandler();

    private FirebaseDatabaseHandler() {}

    public String saveUser(User user) {
        String userId = reference.getReference().child("users").push().getKey();
        reference.getReference().child("users").child(userId).setValue(user);
        return userId;
    }

    public Query getUserByEmail(String email) {
        return reference.getReference().child("users").orderByChild("email").equalTo(email);
    }

    public boolean saveAd(Ad ad) {
        return false;
    }

    public List<Ad> getAdsFromUser(String userId) {
        return null;
    }

    public static FirebaseDatabaseHandler getInstance() {
        return INSTANCE;
    }
}
