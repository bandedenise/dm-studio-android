package com.example.dmstudio.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dmstudio.R;

public class DiasFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dias, container, false);
        v.findViewById(R.id.lunes).setOnClickListener(this);
        v.findViewById(R.id.martes).setOnClickListener(this);
        v.findViewById(R.id.miercoles).setOnClickListener(this);
        v.findViewById(R.id.jueves).setOnClickListener(this);
        v.findViewById(R.id.viernes).setOnClickListener(this);
        v.findViewById(R.id.sabado).setOnClickListener(this);
        v.findViewById(R.id.domingo).setOnClickListener(this);
        getActivity().setTitle("Grilla de clases");

        return v;
    }

    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        Button b = (Button)v;

        String buttonText = b.getText().toString();

        bundle.putString("dia", buttonText);

        HorariosFragment secondFragment = new HorariosFragment();
        secondFragment.setArguments(bundle);

        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
        ft2.replace(R.id.flHorarios, secondFragment);
        ft2.commit();
    }
}
