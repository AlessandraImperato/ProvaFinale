package it.alessandra.provafinale.Utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import it.alessandra.provafinale.Model.Pacco;
import it.alessandra.provafinale.R;

/**
 * Created by utente7.academy on 15/12/2017.
 */

public class PackCourierAdapter extends RecyclerView.Adapter<PackCourierAdapter.ViewHolder>{
    private List<Pacco> pacchi;
    public Context context;

    public PackCourierAdapter(List<Pacco> listaPacchi){
        pacchi = listaPacchi;
    }
    public PackCourierAdapter(List<Pacco> listaPacchi, Context mcontext){
        pacchi = listaPacchi;
        context = mcontext;
    }

    @Override
    public PackCourierAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cour_pack,parent,false);
        PackCourierAdapter.ViewHolder vh = new PackCourierAdapter.ViewHolder(v,parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final PackCourierAdapter.ViewHolder holder, int position) {
        final Pacco tmp = pacchi.get(position);
        final String id = "ID PACCO: " + tmp.getId();
        final String destinatario = "DESTINATARIO: " + tmp.getDestinatario();
        final Date data = tmp.getDataConsegna();
        final String dataString = "DATA CONSEGNA: " + SettingDate.formatToString(data);
        holder.id.setText(id);
        holder.destinatario.setText(destinatario);
        holder.data.setText(dataString);
    }

    @Override
    public int getItemCount() {
        return pacchi.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView id;
        public TextView destinatario;
        public TextView data;

        public ViewHolder(View v, final Context context){
            super(v);
            cardView = itemView.findViewById(R.id.cardpack);
            id = v.findViewById(R.id.idpack);
            destinatario = v.findViewById(R.id.destinatario);
            data = v.findViewById(R.id.data);
        }
    }

    public Context getContext(){
        return context;
    }
}
