package cl.inacap.evaluacion4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class IndicadorAdapter extends RecyclerView.Adapter<IndicadorAdapter.ViewHolder> {

    private List<Indicador> listaIndicadores;

    public IndicadorAdapter(List<Indicador> listaIndicadores) {
        this.listaIndicadores = listaIndicadores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_indicador, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Indicador indicador = listaIndicadores.get(position);
        holder.tvFecha.setText(indicador.getFecha());
        holder.tvValor.setText(String.valueOf(indicador.getValor()));
        holder.tvTipo.setText(indicador.getTipo());
    }

    @Override
    public int getItemCount() {
        return listaIndicadores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvValor, tvTipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvTipo = itemView.findViewById(R.id.tvTipo);
        }
    }
}