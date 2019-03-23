package com.example.olegario.escamboapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.helper.DataValidator;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateAdActivity extends AppCompatActivity {

    private int RESULT_LOAD_IMAGE = 1;
    private ImageButton adImageButton;
    private EditText adTitleEditText;
    private EditText adPriceEditText;
    private EditText adDescriptionEditText;
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        this.adImageButton = findViewById(R.id.adImageButton);
        this.adTitleEditText = findViewById(R.id.adNameEditText);
        this.adPriceEditText = findViewById(R.id.adPriceEditText);
        this.adDescriptionEditText = findViewById(R.id.adDescriptionEditText);

//        NumberFormat format = NumberFormat.getCurrencyInstance();
//        findViewById(R.id.priceEditText).setText(format.format(res))
    }

    public void selectProductImage(View view) {
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
                this.adImageButton.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                final String errMsg = getString(R.string.photoNotExists);
                Toast.makeText(CreateAdActivity.this, errMsg, Toast.LENGTH_LONG).show();
            }
        } else {
            final String errMsg = getString(R.string.photoNotPicked);
            Toast.makeText(CreateAdActivity.this, errMsg, Toast.LENGTH_LONG).show();
        }
    }

    public void finishButtonClicked(View view) {
        String title = this.adTitleEditText.getText().toString();
        String price = this.adPriceEditText.getText().toString();
        String description = this.adDescriptionEditText.getText().toString();

        if(this.validateData(title, price, description)) {
            // TODO
        }
    }

    private boolean validateData(String title, String price, String description) {
        final boolean titleIsValid = dataValidator.validateTitleForAd(title);
        final boolean priceIsValid = dataValidator.validatePrice(price);
        final boolean descriptionIsValid = dataValidator.validateDescription(description);
        boolean dataIsValid = true;

        if (!titleIsValid) {
            dataIsValid = false;
            final String errMsg = getString(R.string.titleInvalid);
            this.adTitleEditText.setError(errMsg);
        }

        if (!priceIsValid) {
            dataIsValid = false;
            final String errMsg = getString(R.string.priceInvalid);
            this.adTitleEditText.setError(errMsg);
        }

        if (!descriptionIsValid) {
            dataIsValid = false;
            final String errMsg = getString(R.string.descriptionInvalid);
            this.adTitleEditText.setError(errMsg);
        }

        return dataIsValid;
    }
}
