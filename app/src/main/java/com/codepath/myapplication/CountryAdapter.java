package com.codepath.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // track view

        ImageView ivFlag;
        TextView tvName;
        TextView tvTransName;
        TextView tvContinent;

        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects by id
          //  ivFlag = (ImageView) itemView.findViewById(R.id.ivFlag);
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
            //    Country countries = countries.get(position);
                // create intent for the new activity
               // Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
             //   intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
           //     context.startActivity(intent);
            }
        }
    }
}
