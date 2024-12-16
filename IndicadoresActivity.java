package cl.inacap.evaluacion4;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndicadoresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IndicadorAdapter adapter;
    private List<IndicadorItem> indicadorItems;
    private Spinner spinnerIndicadores;
    private Map<String, String> indicadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicadores);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        indicadorItems = new ArrayList<>();
        adapter = new IndicadorAdapter(indicadorItems);
        recyclerView.setAdapter(adapter);

        spinnerIndicadores = findViewById(R.id.spinnerIndicadores);
        indicadores = new HashMap<>();
        indicadores.put("Dólar", "dolar");
        indicadores.put("Euro", "euro");
        indicadores.put("UF", "uf");
        indicadores.put("IPC", "ipc");
        // Agrega más indicadores según sea necesario

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(indicadores.keySet()));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIndicadores.setAdapter(spinnerAdapter);

        spinnerIndicadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIndicador = parent.getItemAtPosition(position).toString();
                fetchIndicadorData(indicadores.get(selectedIndicador));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchIndicadorData(String indicador) {
        String url = "https://mindicador.cl/api/" + indicador;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            indicadorItems.clear();
                            JSONArray serieArray = response.getJSONArray("serie");
                            for (int i = 0; i < serieArray.length(); i++) {
                                JSONObject indicadorObject = serieArray.getJSONObject(i);
                                String fecha = indicadorObject.getString("fecha");
                                double valor = indicadorObject.getDouble("valor");
                                indicadorItems.add(new IndicadorItem(fecha, valor));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
