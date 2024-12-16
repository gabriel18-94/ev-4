package cl.inacap.evaluacion4;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerIndicadores;
    private Button btnVerIndicador;
    private String indicadorSeleccionado;

    private RecyclerView recyclerView;
    private IndicadorAdapter adapter;
    private List<Indicador> listaIndicadores = new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar elementos de la interfaz
        spinnerIndicadores = findViewById(R.id.spinnerIndicadores);
        btnVerIndicador = findViewById(R.id.btnVerIndicador);
        recyclerView = findViewById(R.id.recyclerView);

        // Configurar Spinner
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(
                this, R.array.indicadores, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIndicadores.setAdapter(adapterSpinner);
        spinnerIndicadores.setOnItemSelectedListener(this);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IndicadorAdapter(listaIndicadores);
        recyclerView.setAdapter(adapter);

        // Inicializar RequestQueue
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        indicadorSeleccionado = parent.getItemAtPosition(position).toString();
        obtenerDatos(indicadorSeleccionado);
    }

    private void obtenerDatos(String indicador) {
        String url = "https://mindicador.cl/api/" + indicador;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray serie = response.getJSONArray("serie");
                        actualizarRecyclerView(serie, indicador);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }

    private void actualizarRecyclerView(JSONArray jsonArray, String tipoIndicador) {
        listaIndicadores.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fecha = jsonObject.getString("fecha");
                double valor = jsonObject.getDouble("valor");
                listaIndicadores.add(new Indicador(fecha, valor, tipoIndicador));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}