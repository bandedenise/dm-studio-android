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

import com.example.dmstudio.GetJsonClases;
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
    public static final String l = "Lunes";
    public static final String m = "Martes";
    public static final String x = "Miercoles";
    public static final String j = "Jueves";
    public static final String v = "Viernes";
    public static final String s = "Sabado";
    public static final String d = "Domingo";

    public View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_horarios, container, false);

        Bundle args = getArguments();
        String dia = args.getString("dia");

        switch (dia) {
            case "L":
                new GetJsonClases(this, l).execute();
                getActivity().setTitle(l);

                break;
            case "M":
                new GetJsonClases(this, m).execute();
                getActivity().setTitle(m);
                break;
            case "X":
                new GetJsonClases(this, x).execute();
                getActivity().setTitle(x);
                break;
            case "J":
                new GetJsonClases(this, j).execute();
                getActivity().setTitle(j);
                break;
            case "V":
                new GetJsonClases(this, v).execute();
                getActivity().setTitle(v);
                break;
            case "S":
                new GetJsonClases(this, s).execute();
                getActivity().setTitle(s);
                break;
            case "D":
                new GetJsonClases(this, d).execute();
                getActivity().setTitle(d);
                break;
            default:
                break;
        }

        return view;
    }

    public void UpdateClases(List<Clase> clases) {
        List<Clase> clasesDefinitivas = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        LinearLayout mainLayout = view.findViewById(R.id.containerClases);

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

            for (int i = 0; i < clases.size(); i++){
                if(i==0){
                    clasesDefinitivas.add(clases.get(i));
                    this.agregarLayout(clases.get(i),mainLayout,sdf,false);
                }else{
                    if(clases.get(i).getHoraInicio()==clases.get(i-1).getHoraFin()){
                    clasesDefinitivas.add(clases.get(i));

                    this.agregarLayout(clases.get(i),mainLayout,sdf,false);

                    }else{
                    // agrego la clase en blanco para completar el layout
                    Clase cEnBlanco=new Clase();

                    cEnBlanco.setHoraInicio(clases.get(i-1).getHoraFin());
                    cEnBlanco.setHoraFin(clases.get(i).getHoraInicio());
                    cEnBlanco.setEnBlanco(true);
                    clasesDefinitivas.add(cEnBlanco);

                    this.agregarLayout(cEnBlanco,mainLayout,sdf,true);

                    clasesDefinitivas.add(clases.get(i));

                    // agrego la clase
                    this.agregarLayout(clases.get(i),mainLayout,sdf,false);
                    }
                }
            }
        }
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

            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width);
            lparams.setMargins(0, 0, 0, 1);

            ll.setLayoutParams(lparams);

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
