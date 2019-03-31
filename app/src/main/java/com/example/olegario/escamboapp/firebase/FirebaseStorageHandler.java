package com.example.olegario.escamboapp.firebase;

import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public final class FirebaseStorageHandler {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private static FirebaseStorageHandler INSTANCE = new FirebaseStorageHandler();

    private FirebaseStorageHandler() {}

    public static FirebaseStorageHandler getInstance() {
        return INSTANCE;
    }

    public boolean saveUserImage(String userId, String filePath) {
        final StorageReference ref = storageReference.child("users/" + userId);

        Uri uri = Uri.fromFile(new File(filePath));
        UploadTask uploadTask = ref.putFile(uri);

        return true;
    }

    public Task<Uri> getUserImage(String userId) {
        final StorageReference ref = storageReference.child("users/" + userId);
        return ref.getDownloadUrl();
    }
}
