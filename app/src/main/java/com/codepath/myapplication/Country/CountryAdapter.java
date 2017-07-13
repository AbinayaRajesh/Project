package com.codepath.myapplication.Country;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.OptionsAvailable;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by emilylroth on 7/11/17.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{
   ArrayList<Country> countries;
    Context context;

    public CountryAdapter(ArrayList<Country> countries){this.countries = countries;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View countryView = inflater.inflate(R.layout.item_view_country, parent, false);

        return new ViewHolder(countryView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = countries.get(position);
        String name = country.getName();
        holder.tvName.setText(name);
        holder.tvContinent.setText(country.getContinent());
        holder.tvTransName.setText(country.getNativeName());
        Glide.with(context)
                .load(country.getFlagUrl())
                .into(holder.ivFlag);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // track view

        TextView tvName;
        TextView tvTransName;
        TextView tvContinent;
        ImageView ivFlag;
        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects by id
            ivFlag = (ImageView) itemView.findViewById(R.id.ivFlag);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvTransName = (TextView) itemView.findViewById(R.id.tvTransName);
            tvContinent = (TextView) itemView.findViewById(R.id.tvContinent);
            itemView.setOnClickListener(this);
        }
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {

                // get the movie at the position, this won't work if the class is static
                Country country = countries.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, OptionsAvailable.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Country.class.getName(), Parcels.wrap(country));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
