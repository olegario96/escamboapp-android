package com.example.olegario.escamboapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.helper.AuthHandler;
import com.example.olegario.escamboapp.helper.DataValidator;
import com.example.olegario.escamboapp.helper.Formatter;
import com.example.olegario.escamboapp.model.User;
import com.vicmikhailau.maskededittext.MaskedFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateUserBasicInfoActivity extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private User user;
    private EditText firstNameEditText;
    private EditText lastNameEdiText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText cpfEditText;
    private EditText birthdateEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private MaskedFormatter phoneFormatter = new MaskedFormatter("(##) #####-####");
    private MaskedFormatter cpfFormatter = new MaskedFormatter("###.###.###-##");
    private MaskedFormatter birhFormatter = new MaskedFormatter("##/##/####");

    private Formatter formatter = Formatter.getInstance();
    private DataValidator dataValidator = DataValidator.getInstance();
    private AuthHandler authHandler = AuthHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_basic_info);

        this.firstNameEditText = findViewById(R.id.firstNameCreateEditText);
        this.lastNameEdiText = findViewById(R.id.lastNameCreateEditText);
        this.emailEditText = findViewById(R.id.emailCreateEditText);
        this.phoneEditText = findViewById(R.id.phoneCreateEditText);
        this.cpfEditText = findViewById(R.id.cpfCreateEditText);
        this.birthdateEditText = findViewById(R.id.birthdateCreateEditText);
        this.passwordEditText = findViewById(R.id.passwordCreateEditText);
        this.confirmPasswordEditText = findViewById(R.id.confirmPasswordCreateEditText);

        if (getIntent().getStringExtra("user") != null) {
            this.user = (User) getIntent().getExtras().getSerializable("user");
            this.setFields();
        }

        this.setDatePicker();
    }

    public void cancel(View view) {
        // TODO
    }


    public void next(View view) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEdiText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = this.phoneFormatter.formatString(phoneEditText.getText().toString()).getUnMaskedString();
        String cpf = this.cpfFormatter.formatString(cpfEditText.getText().toString()).getUnMaskedString();
        String birthdate = this.birhFormatter.formatString(birthdateEditText.getText().toString()).getUnMaskedString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        final boolean dataIsValid = this.validateData(firstName, email, cpf, password, confirmPassword);
        if (dataIsValid) {
            this.user = new User();
            this.user.setAttributes(firstName, lastName, email, cpf, phone, birthdate, password);
            Intent intent = new Intent(getApplicationContext(), SelectProfilePhotoActivity.class);
            intent.putExtra("user", this.user);
            startActivity(intent);
        }
    }

    private void setFields() {
        this.firstNameEditText.setText(this.user.getFirstName());
        this.lastNameEdiText.setText(this.user.getLastName());
        this.emailEditText.setText(this.user.getEmail());
        final String phoneMasked = this.formatter.formatPhone(this.user.getPhone());
        this.phoneEditText.setText(phoneMasked);
        final String birthDateMasked = this.formatter.formatBirthDate(this.user.getBirthdate());
        this.birthdateEditText.setText(birthDateMasked);
        final String cpfMasked = this.formatter.formatCPF(this.user.getCPF());
        this.cpfEditText.setText(cpfMasked);
    }

    private void setDatePicker() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        this.birthdateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateUserBasicInfoActivity.this, date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String format = "dd/MM/YYYY";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(calendar.getTime());
        this.birthdateEditText.setText(date);
    }

    private boolean validateData(String firstName, String email, String cpf, String password, String confirmPassowrd) {

        final boolean nameIsValid = this.dataValidator.validateFirstName(firstName);
        final boolean emailIsValid = this.dataValidator.validateEmail(email);
        final boolean cpfIsValid = this.dataValidator.validateCPF(cpf);
        final boolean passwordIsValid = this.dataValidator.validatePassword(password);
        final boolean passwordsAreEquals = this.dataValidator.comparePasswords(password, confirmPassowrd);
        boolean result = true;

        if (!nameIsValid) {
            result = false;
            final String errMsg = getString(R.string.invalidName);
            this.firstNameEditText.setError(errMsg);
            Toast.makeText(getApplicationContext(), getString(R.string.invalidInfo), Toast.LENGTH_LONG).show();
        }


        if (!emailIsValid) {
            result = false;
            final String errMsg = getString(R.string.invalidEmail);
            this.emailEditText.setError(errMsg);
        }


        if (!passwordIsValid) {
            result = false;
            final String errMsg = getString(R.string.shortPassword);
            this.passwordEditText.setError(errMsg);
        }

        if (!passwordsAreEquals) {
            result = false;
            final String errMsg = getString(R.string.differentPassword);
            this.passwordEditText.setError(errMsg);
            this.confirmPasswordEditText.setError(errMsg);
        }

        if (!cpfIsValid) {
            result = false;
            final String errMsg = getString(R.string.invalidCPF);
            this.cpfEditText.setError(errMsg);
        }

        if (!result) {
            final String errMsg = getString(R.string.invalidInfo);
            Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
        }
        return result;
    }
}
