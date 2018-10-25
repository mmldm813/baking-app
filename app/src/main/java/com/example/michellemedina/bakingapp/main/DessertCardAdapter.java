package com.example.michellemedina.bakingapp.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.detail.RecipeDetailActivity;

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
        final Dessert dessert = desserts.get(position);
        final String dessertName = dessert.getDessertName();
        holder.dessertText.setText(dessertName);
        holder.dessertCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                RecipeDetailActivity.startWith(context, dessert);
            }
        });

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
