package com.example.olegario.escamboapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.olegario.escamboapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class SelectProfilePhotoActivity extends AppCompatActivity {

    private int RESULT_LOAD_IMAGE = 1;
    private ImageButton profileImageButton;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile_photo);

        profileImageButton = findViewById(R.id.selectProfileImageButton);
        finishButton = findViewById(R.id.finishCreateProfileButton);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void selectProfileImageButtonClicked(View view) {
        Intent photoProfileIntent = new Intent(Intent.ACTION_PICK);
        photoProfileIntent.setType("image/*");
        startActivityForResult(photoProfileIntent, this.RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
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
        // TODO
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
