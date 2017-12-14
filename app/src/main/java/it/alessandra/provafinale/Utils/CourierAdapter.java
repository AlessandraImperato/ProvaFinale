package it.alessandra.provafinale.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.alessandra.provafinale.AddPackActivity;
import it.alessandra.provafinale.Model.Corriere;
import it.alessandra.provafinale.R;

/**
 * Created by User on 14/12/2017.
 */

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {

    private List<Corriere> corrieri;
    public Context context;

    public CourierAdapter(List<Corriere> listaCorrieri){

        corrieri = listaCorrieri;
    }
    public CourierAdapter(List<Corriere> listaCorrieri, Context mcontext){
        corrieri = listaCorrieri;
        context = mcontext;
    }

    @Override
    public CourierAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_courier,parent,false);
        ViewHolder vh = new ViewHolder(v,parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final CourierAdapter.ViewHolder holder, int position) {
        final Corriere tmp = corrieri.get(position);
        holder.nome.setText(tmp.getNome());
        holder.cognome.setText(tmp.getCognome());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddPackActivity.class);
                (v.getContext()).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return corrieri.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView nome;
        public TextView cognome;

        public ViewHolder(View v, final Context context){
            super(v);
            cardView = (CardView)itemView.findViewById(R.id.cardcour);
            nome = (TextView) v.findViewById(R.id.nomeC);
            cognome = (TextView) v.findViewById(R.id.cognomeC);
        }
    }

    public Context getContext(){
        return context;
    }
}
