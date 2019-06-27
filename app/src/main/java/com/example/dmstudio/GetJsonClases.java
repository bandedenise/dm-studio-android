package com.example.dmstudio;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dmstudio.Model.Clase;
import com.example.dmstudio.fragments.HorariosFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetJsonClases extends AsyncTask<Void, Void, Void> {
    List<Clase> clases = new ArrayList<>();
    String dia;
    HorariosFragment horariosFragment;

    public GetJsonClases(HorariosFragment horariosFragment, String dia) {
        this.dia = dia;
        this.horariosFragment = horariosFragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Descargando", "Comenzo descarga");
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        String url = "http://dm-studio.com.ar/dm/public/clases"+this.dia+".json";
        String jsonStr = sh.makeServiceCall(url);

       // Log.d("Clases", jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray clases = jsonObj.getJSONArray("clases");

                for (int i = 0; i < clases.length(); i++) {
                    JSONObject c = clases.getJSONObject(i);
                    Log.d("clase",c.getString("nombre") );
                    String nombreClase = c.getString("nombre");
                    String horarioDesde = c.getString("horario_desde");
                    String horarioHasta = c.getString("horario_hasta");
                    String nombreProfesor = c.getString("nombre_profesor");

                    Clase c1 = new Clase();
                    c1.setHoraInicio(horarioDesde);
                    c1.setHoraFin(horarioHasta);
                    c1.setNombre(nombreClase);
                    c1.setProfesor(nombreProfesor);

                    this.clases.add(c1);
                }
            } catch (final JSONException e) {
                Log.e("Error", e.getMessage());
            }

        } else {
            Log.e("Error", "Couldn't get json from server.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        this.horariosFragment.UpdateClases(this.clases);
    }
}
