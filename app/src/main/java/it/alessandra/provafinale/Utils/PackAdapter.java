package it.alessandra.provafinale.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import it.alessandra.provafinale.AddPackActivity;
import it.alessandra.provafinale.Model.Corriere;
import it.alessandra.provafinale.Model.Pacco;
import it.alessandra.provafinale.R;

/**
 * Created by utente7.academy on 15/12/2017.
 */

public class PackAdapter  extends RecyclerView.Adapter<PackAdapter.ViewHolder>{
    private List<Pacco> pacchi;
    public Context context;

    public PackAdapter(List<Pacco> listaPacchi){
        pacchi = listaPacchi;
    }
    public PackAdapter(List<Pacco> listaPacchi, Context mcontext){
        pacchi = listaPacchi;
        context = mcontext;
    }

    @Override
    public PackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pack,parent,false);
        PackAdapter.ViewHolder vh = new PackAdapter.ViewHolder(v,parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final PackAdapter.ViewHolder holder, int position) {
        final Pacco tmp = pacchi.get(position);
        final String id = "ID PACCO: " + tmp.getId();
        final String stato = "STATO PACCO: " + tmp.getStato();
        final String corriere = "CORRIERE: " + tmp.getCorriereAssegnato();
        final Date data = tmp.getDataConsegna();
        final String dataString = "DATA CONSEGNA: " + SettingDate.formatToString(data);
        holder.id.setText(id);
        holder.stato.setText(stato);
        holder.corriere.setText(corriere);
        holder.data.setText(dataString);
    }

    @Override
    public int getItemCount() {
        return pacchi.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView id;
        public TextView stato;
        public TextView corriere;
        public TextView data;

        public ViewHolder(View v, final Context context){
            super(v);
            cardView = itemView.findViewById(R.id.cardpack);
            id = v.findViewById(R.id.idpack);
            stato = v.findViewById(R.id.stato);
            corriere = v.findViewById(R.id.corriere);
            data = v.findViewById(R.id.data);
        }
    }

    public Context getContext(){
        return context;
    }
}
