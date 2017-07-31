package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Models.Location;
import com.codepath.myapplication.Models.Venue;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by arajesh on 6/22/17.
 */

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder>{


    ArrayList<Venue> venues;
    ViewGroup pview;
    Context context;
    Venue curV;
    int curI;
    public VenueAdapter(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    // creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        pview = parent;
        context = parent.getContext();


        LayoutInflater inflater = LayoutInflater.from(context);
        // create the view using the item_movie layout
        View venueView = inflater.inflate(R.layout.item_view_place, parent, false);
        // return a new ViewHolder
        return new ViewHolder(venueView);

    }

    // binds an inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the movie data at the specified position
        Venue venue = venues.get(position);
        // populate the view with the movie data
        holder.tvTitle.setText(venue.getTitle());
        Location location = venue.getLocation();
        holder.tvDistance.setText(String.valueOf(location.getDistance()));
        holder.tvCity.setText(location.getCity());
        holder.tvState.setText(location.getState());
        // holder.tvAddress.setText(location.getState());


        if(venue.getImageUrl()!="" && venue.getImageUrl()!=null) {
            Glide.with(context)
                    .load(venue.getImageUrl())
                    .into(holder.ivImg);
        }
        else {
            Glide.with(context) .load("") .error(R.drawable.ic_image_black_24dp) .into(holder.ivImg);
//            Glide.with(context)
//                    .load(ic_image_black_24dp.xml)
//                    .into(holder.ivImg);
        }

    }

    // returns total number of items in a list
    @Override
    public int getItemCount() {
        return venues.size();
    }

    // create the viewholder as a static inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // track view objects
        //@BindView(R.id.ivPosterImage) ImageView getIvPosterImage;
        ImageView ivImg;
        TextView tvTitle;
        TextView tvCity;
        TextView tvState;
        TextView tvDistance;
        TextView tvAddress;



        // when the user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View v) {

            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Venue venue = venues.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, DetailPlaceActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra("venue", Parcels.wrap(venue));

                // show the activity
                context.startActivity(intent);
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            //lookup view objects by id


            ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            tvState = (TextView) itemView.findViewById(R.id.tvState);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            tvAddress = (TextView) itemView.findViewById(R.id.textView) ;

            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    Venue venue = venues.get(pos);
                    venues.remove(pos);
                    curV = venue;
                    curI = pos;
                    notifyDataSetChanged();
                    //Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
                    Snackbar.make(pview, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                            .setAction(R.string.snackbar_action, myOnClickListener)
                            .show(); // Don’t forget to show!

                    return true;
                }
            });



        }
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venues.add(curI, curV);
                notifyDataSetChanged();
            }
        };
    }
}

