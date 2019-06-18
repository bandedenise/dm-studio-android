package com.example.dmstudio.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dmstudio.Clase;
import com.example.dmstudio.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HorariosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_horarios, container, false);

        Bundle args = getArguments();
        String dia = args.getString("dia");

        switch (dia)
        {
            case "L":

                List<Clase> clasesDefinitivas = new ArrayList<>();
                List<Clase> clases = new ArrayList<>();

                Clase c = new Clase();
                c.horaInicio = "13:30";
                c.horaFin = "14:30";
                c.nombre = "Clase 1";

                Clase c1 = new Clase();
                c1.horaInicio = "18:00";
                c1.horaFin = "19:30";
                c1.nombre = "Clase 2";

                Clase c2 = new Clase();
                c2.horaInicio = "20:00";
                c2.horaFin = "21:00";
                c2.nombre = "Clase 3";

                clases.add(c);
                clases.add(c1);
                clases.add(c2);

                Date d1 = null;
                Date d2 = null;
                Long diff = 0L;
                DateFormat sdf = new SimpleDateFormat("hh:mm");

                try {
                    d1 = sdf.parse(clases.get(0).horaInicio);
                    d2 = sdf.parse("10:00");

                    diff = d1.getTime() - d2.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                LinearLayout mainLayout = v.findViewById(R.id.containerClases);

                if (diff > 0) {
                    Clase cInicial = new Clase();

                    cInicial.horaInicio = "10:00";
                    cInicial.horaFin = clases.get(0).horaInicio;
                    cInicial.enBlanco = true;
                    clasesDefinitivas.add(cInicial);

                    Date dateInicial = null;
                    Date dateInicial2 = null;
                    try {
                        dateInicial = sdf.parse(cInicial.horaInicio);
                        dateInicial2 = sdf.parse(cInicial.horaFin);

                        long diffInMillies = dateInicial2.getTime() - dateInicial.getTime();

                        LinearLayout ll = new LinearLayout(getActivity());
                        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS), getResources().getDisplayMetrics());
                        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width));

                        TextView tv = new TextView(getActivity());

                        tv.setGravity(Gravity.CENTER);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        tv.setLayoutParams(params);
                        tv.setBackgroundResource(R.color.colorWhite);

                        ll.addView(tv);

                        mainLayout.addView(ll);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < clases.size() ; i ++) {
                    if (i == 0) {
                        clasesDefinitivas.add(clases.get(i));
                        Date firstDate = null;
                        Date firstDate2 = null;
                        try {
                            firstDate = sdf.parse(clases.get(i).horaInicio);
                            firstDate2 = sdf.parse(clases.get(i).horaFin);

                            long diffInMillies = firstDate2.getTime() - firstDate.getTime();

                            LinearLayout ll = new LinearLayout(getActivity());
                            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                    TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS), getResources().getDisplayMetrics());
                            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width));

                            TextView tv = new TextView(getActivity());

                            tv.setText(clases.get(i).nombre);
                            tv.setGravity(Gravity.CENTER);
                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            tv.setLayoutParams(params);
                            tv.setBackgroundResource(R.color.colorAccent);

                            ll.addView(tv);

                            mainLayout.addView(ll);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        if (clases.get(i).horaInicio == clases.get(i-1).horaFin) {
                            clasesDefinitivas.add(clases.get(i));

                            Date date = null;
                            Date date2 = null;
                            try {
                                date = sdf.parse(clases.get(i).horaInicio);
                                date2 = sdf.parse(clases.get(i).horaFin);

                                long diffInMillies = date2.getTime() - date.getTime();

                                LinearLayout ll = new LinearLayout(getActivity());
                                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                        TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS), getResources().getDisplayMetrics());
                                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width));

                                TextView tv = new TextView(getActivity());

                                tv.setText(clases.get(i).nombre);
                                tv.setGravity(Gravity.CENTER);
                                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                tv.setLayoutParams(params);
                                tv.setBackgroundResource(R.color.colorAccent);

                                ll.addView(tv);

                                mainLayout.addView(ll);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Clase cEnBlanco = new Clase();

                            cEnBlanco.horaInicio = clases.get(i-1).horaFin;
                            cEnBlanco.horaFin = clases.get(i).horaInicio;
                            clasesDefinitivas.add(cEnBlanco);

                            Date whiteDate = null;
                            Date whiteDate2 = null;
                            try {
                                whiteDate = sdf.parse(cEnBlanco.horaInicio);
                                whiteDate2 = sdf.parse(cEnBlanco.horaFin);

                                long diffInMillies = whiteDate2.getTime() - whiteDate.getTime();

                                LinearLayout ll = new LinearLayout(getActivity());
                                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                        TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS), getResources().getDisplayMetrics());
                                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width));

                                TextView tv = new TextView(getActivity());

                                tv.setGravity(Gravity.CENTER);
                                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                tv.setLayoutParams(params);
                                tv.setBackgroundResource(R.color.colorWhite);

                                ll.addView(tv);

                                mainLayout.addView(ll);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            clasesDefinitivas.add(clases.get(i));

                            Date da = null;
                            Date da2 = null;
                            try {
                                da = sdf.parse(clases.get(i).horaInicio);
                                da2 = sdf.parse(clases.get(i).horaFin);

                                long diffInMillies = da2.getTime() - da.getTime();

                                LinearLayout ll = new LinearLayout(getActivity());
                                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                        TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS), getResources().getDisplayMetrics());
                                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width));

                                TextView tv = new TextView(getActivity());

                                tv.setText(clases.get(i).nombre);
                                tv.setGravity(Gravity.CENTER);
                                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                tv.setLayoutParams(params);
                                tv.setBackgroundResource(R.color.colorAccent);

                                ll.addView(tv);

                                mainLayout.addView(ll);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                for (Clase clas : clasesDefinitivas) {
                    Log.d("clase", clas.horaInicio.toString() + " " + clas.horaFin.toString());
                }

                break;
            case "M":
                //textDia.setText("2");
                break;
            case "X":
                //textDia.setText("3");
                break;
            case "J":
                //textDia.setText("4");
                break;
            case "V":
                //textDia.setText("5");
                break;
            case "S":
                //textDia.setText("6");
                break;
            case "D":
                //textDia.setText("7");
                break;
            default:
                break;
        }

        return v;
    }
}
