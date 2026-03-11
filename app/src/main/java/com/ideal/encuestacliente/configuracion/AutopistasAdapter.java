package com.ideal.encuestacliente.configuracion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ideal.encuestacliente.R;
import com.ideal.encuestacliente.model.Esatis_Autopistas;

import java.util.List;

public class AutopistasAdapter extends  RecyclerView.Adapter<AutopistasAdapter.NewItemViewHolder> implements  View.OnClickListener{

    private View.OnClickListener listener;
    private List<Esatis_Autopistas> autopistas;

    public AutopistasAdapter(List<Esatis_Autopistas> autopistas) {
        this.autopistas = autopistas;
    }

    @NonNull
    @Override
    public NewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_autopista,
                parent,
                false
        );
        view.setOnClickListener(this);
        return new NewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewItemViewHolder holder, int position) {
        holder.textTitle.setText(autopistas.get(position).getNombreAutopista());
        holder.textDescription.setText("Acronimo ".concat(autopistas.get(position).getAcronimoAutopista()));
    }

    @Override
    public int getItemCount() {
        return autopistas.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    class NewItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private TextView textDescription;

        public NewItemViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle =  itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
        }
    }
}
