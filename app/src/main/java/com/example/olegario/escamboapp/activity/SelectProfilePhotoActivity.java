package com.example.olegario.escamboapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.firebase.FirebaseAuthHandler;
import com.example.olegario.escamboapp.firebase.FirebaseDatabaseHandler;
import com.example.olegario.escamboapp.firebase.FirebaseStorageHandler;
import com.example.olegario.escamboapp.helper.Database;
import com.example.olegario.escamboapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class SelectProfilePhotoActivity extends AppCompatActivity {

    private int RESULT_LOAD_IMAGE = 1;
    private final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private ImageButton profileImageButton;
    private User user;
    private Uri filePath;
    private FirebaseAuthHandler authHandler = FirebaseAuthHandler.getInstance();
    private FirebaseStorageHandler storageHandler = FirebaseStorageHandler.getInstance();
    private FirebaseDatabaseHandler databaseHandler = FirebaseDatabaseHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile_photo);

        profileImageButton = findViewById(R.id.selectProfileImageButton);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getSerializableExtra("user") != null) {
            this.user = (User) getIntent().getExtras().getSerializable("user");
        }
    }

    public void getPhotoCheckingPermissions(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    this.PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {
            this.pickPhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.pickPhoto();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                this.filePath = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(filePath);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                this.profileImageButton.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                final String errMsg = getString(R.string.photoNotExists);
                Toast.makeText(SelectProfilePhotoActivity.this, errMsg, Toast.LENGTH_LONG).show();
            }
        } else {
            final String errMsg = getString(R.string.photoNotPicked);
            Toast.makeText(SelectProfilePhotoActivity.this, errMsg, Toast.LENGTH_LONG).show();
        }
    }

    public void finishButtonClicked(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.creatingUser));
        progressDialog.show();

        final String userId = databaseHandler.saveUser(this.user);
        if (this.filePath != null) {
            String filePath = this.getRealPathFromUri(this.filePath);
            storageHandler.saveUserImage(userId, filePath);
        }

        authHandler.createUser(this.user.getEmail(), this.user.getPasswordHash())
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Intent data = new Intent();
                            data.putExtra("userId", userId);
                            setResult(RESULT_OK, data);
                            finish();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent data = new Intent();
        data.putExtra("userId", "");
        setResult(RESULT_CANCELED, data);
        finish();
        return true;
    }

    private void pickPhoto() {
        Intent photoProfileIntent = new Intent(Intent.ACTION_PICK);
        photoProfileIntent.setType("image/*");
        startActivityForResult(photoProfileIntent, this.RESULT_LOAD_IMAGE);
    }

    private String getRealPathFromUri(Uri contentUri) {
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int dx =  cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(dx);
            cursor.close();
        }
        return result;
    }
}
