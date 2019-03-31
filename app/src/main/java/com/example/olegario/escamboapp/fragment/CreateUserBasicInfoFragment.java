package com.example.olegario.escamboapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.helper.Formatter;
import com.example.olegario.escamboapp.model.User;

import java.nio.ByteBuffer;
import java.util.List;

public class CreateUserBasicInfoFragment extends Fragment {
    private static final String TAG = "CreateUserBasicInfoActivity";

    private boolean updatedName = false;

    TextView firstName;
    TextView lastName;
    TextView email;
    TextView phone;
    TextView cpf;
    TextView birthdate;
    ImageButton updateUser;
    ImageButton cancel;

    private Bundle bundle;
    private Formatter formatter = Formatter.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle = getArguments();
        final String title = getString(R.string.editUser);
        getSupportActionBar().setTitle(title);
        return inflater.inflate(R.layout.activity_create_user_basic_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        firstName = getView().findViewById(R.id.firstNameCreateEditText);
        lastName = getView().findViewById(R.id.lastNameCreateEditText);
        email = getView().findViewById(R.id.emailCreateEditText);
        phone = getView().findViewById(R.id.phoneCreateEditText);
        cpf = getView().findViewById(R.id.cpfCreateEditText);
        birthdate = getView().findViewById(R.id.birthdateCreateEditText);
        updateUser = getView().findViewById(R.id.nextCreateImageButton);
        cancel = getView().findViewById(R.id.cancelCreateImageButton);
        this.setFields();
        this.configButtons();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        setHasOptionsMenu(false);
        menu.findItem(R.id.search).setVisible(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setFields() {
        if (bundle != null) {
            User user = (User) bundle.getSerializable("user");
            final String cpfFormatted = formatter.formatCPF(user.getCPF());
            this.firstName.setText(user.getFirstName());
            this.lastName.setText(user.getLastName());
            this.email.setText(user.getEmail());
            this.cpf.setText(cpfFormatted);

            if (user.getPhone() != null) {
                final String formattedPhone = formatter.formatPhone(user.getPhone());
                this.phone.setText(formattedPhone);
            }

            if (user.getBirthdate() != null) {
                final String formattedBirthdate = formatter.formatBirthDate(user.getBirthdate());
                this.birthdate.setText(formattedBirthdate);
            }
        }
    }

    private void configButtons() {
        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private ActionBar getSupportActionBar() {
        return ((AppCompatActivity)getActivity()).getSupportActionBar();
    }
}
