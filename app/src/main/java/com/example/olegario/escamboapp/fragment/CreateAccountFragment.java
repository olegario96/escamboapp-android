package com.example.olegario.escamboapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olegario.escamboapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_create_user_basic_info, container, false);
    }

}
