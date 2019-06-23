package com.example.dmstudio.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dmstudio.Model.Clase;
import com.example.dmstudio.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HorariosFragment extends Fragment {
    public static final String horarioComienzo = "10:00";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_horarios, container, false);

        Bundle args = getArguments();
        String dia = args.getString("dia");
        List<Clase> clasesDefinitivas = new ArrayList<>();
        List<Clase> clases = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        LinearLayout mainLayout = v.findViewById(R.id.containerClases);

        switch (dia) {
            case "L":
                getActivity().setTitle("Lunes");

                Clase c = new Clase();
                c.setHoraInicio("13:30");
                c.setHoraFin("14:30");
                c.setNombre("Clase 1");
                c.setProfesor("Denise");

                Clase c1 = new Clase();
                c1.setHoraInicio("18:00");
                c1.setHoraFin("19:30");
                c1.setNombre("Clase 2");
                c1.setProfesor("Denise");

                Clase c2 = new Clase();
                c2.setHoraInicio("20:00");
                c2.setHoraFin("21:00");
                c2.setNombre("Clase 3");
                c2.setProfesor("Denise");

                clases.add(c);
                clases.add(c1);
                clases.add(c2);
                break;
            case "M":
                getActivity().setTitle("Martes");

                Clase c3 = new Clase();
                c3.setHoraInicio("12:30");
                c3.setHoraFin("13:00");
                c3.setNombre("Clase 1");
                c3.setProfesor("Denise");

                Clase c4 = new Clase();
                c4.setHoraInicio("14:00");
                c4.setHoraFin("15:30");
                c4.setNombre("Clase 2");
                c4.setProfesor("Denise");

                Clase c5 = new Clase();
                c5.setHoraInicio("19:30");
                c5.setHoraFin("20:00");
                c5.setNombre("Clase 3");
                c5.setProfesor("Denise");

                clases.add(c3);
                clases.add(c4);
                clases.add(c5);
                break;
            case "X":
                getActivity().setTitle("Miercoles");
                break;
            case "J":
                getActivity().setTitle("Jueves");
                break;
            case "V":
                getActivity().setTitle("Viernes");
                break;
            case "S":
                getActivity().setTitle("Sabado");
                break;
            case "D":
                getActivity().setTitle("Domingo");
                break;
            default:
                break;
        }

        if (clases.size() > 0) {
            Long diff = 0L;

            try {
                Date d1 = sdf.parse(clases.get(0).getHoraInicio());
                Date d2 = sdf.parse(horarioComienzo);
                diff = d1.getTime() - d2.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (diff > 0) {
                Clase cInicial = new Clase();
                cInicial.setHoraInicio(horarioComienzo);
                cInicial.setHoraFin(clases.get(0).getHoraInicio());
                cInicial.setEnBlanco(true);
                clasesDefinitivas.add(cInicial);

                this.agregarLayout(cInicial, mainLayout, sdf, true);
            }

            for (int i = 0; i < clases.size(); i++) {
                if (i == 0) {
                    clasesDefinitivas.add(clases.get(i));
                    this.agregarLayout(clases.get(i), mainLayout, sdf, false);
                } else {
                    if (clases.get(i).getHoraInicio() == clases.get(i - 1).getHoraFin()) {
                        clasesDefinitivas.add(clases.get(i));

                        this.agregarLayout(clases.get(i), mainLayout, sdf, false);

                    } else {
                        // agrego la clase en blanco para completar el layout
                        Clase cEnBlanco = new Clase();

                        cEnBlanco.setHoraInicio(clases.get(i - 1).getHoraFin());
                        cEnBlanco.setHoraFin(clases.get(i).getHoraInicio());
                        cEnBlanco.setEnBlanco(true);
                        clasesDefinitivas.add(cEnBlanco);

                        this.agregarLayout(cEnBlanco,  mainLayout, sdf, true);

                        clasesDefinitivas.add(clases.get(i));

                        // agrego la clase
                        this.agregarLayout(clases.get(i), mainLayout, sdf, false);
                    }
                }
            }
        }

        return v;
    }

    public void agregarLayout(Clase c, LinearLayout mainLayout, DateFormat sdf, Boolean blanco) {
        Date dateInicial = null;
        Date dateInicial2 = null;
        try {
            dateInicial = sdf.parse(c.getHoraInicio());
            dateInicial2 = sdf.parse(c.getHoraFin());

            Long diffInMillies = dateInicial2.getTime() - dateInicial.getTime();

            LinearLayout ll = new LinearLayout(getActivity());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS), getResources().getDisplayMetrics());
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width));

            TextView tv = new TextView(getActivity());

            tv.setGravity(Gravity.CENTER);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(params);

            if (blanco) {
                tv.setBackgroundResource(R.color.colorWhite);
            } else {
                tv.setBackgroundResource(R.color.colorAccent);
                tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                tv.setText(c.getNombre());

                this.setOnClick(c, ll);
            }

            ll.addView(tv);


            mainLayout.addView(ll);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setOnClick(final Clase c, final LinearLayout ll) {

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

                StringBuilder mensaje = new StringBuilder(100);
                mensaje.append("Clase: ");
                mensaje.append(c.getNombre());
                mensaje.append("\nHorarios: ");
                mensaje.append(c.getHoraInicio());
                mensaje.append(" a ");
                mensaje.append(c.getHoraFin());
                mensaje.append("\nProfesor: ");
                mensaje.append(c.getProfesor());

                builder1.setMessage(mensaje);
                AlertDialog alert = builder1.create();
                alert.show();
            }
        });
    }
}
