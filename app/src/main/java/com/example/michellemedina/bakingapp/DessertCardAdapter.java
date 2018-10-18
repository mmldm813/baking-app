package com.example.michellemedina.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michellemedina.bakingapp.data.Dessert;

import java.util.List;

public class DessertCardAdapter extends RecyclerView.Adapter<DessertCardAdapter.DessertViewHolder> {

    private List<Dessert> desserts;

    public static class DessertViewHolder extends RecyclerView.ViewHolder {
        TextView dessertText;
        CardView dessertCard;

        DessertViewHolder(View itemView) {
            super(itemView);
            dessertCard = itemView.findViewById(R.id.dessert_card_view);
            dessertText = itemView.findViewById(R.id.dessert_name_text);
        }
    }

    public void setDesserts(List<Dessert> desserts) {
        this.desserts = desserts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DessertCardAdapter.DessertViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        DessertViewHolder viewHolder = new DessertViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DessertViewHolder holder, final int position) {
        final String dessertName = desserts.get(position).getDessertName();
        holder.dessertText.setText(dessertName);
    }

    @Override
    public int getItemCount() {
        if (desserts == null) {
            return 0;
        } else {
            return desserts.size();

        }
    }


}
