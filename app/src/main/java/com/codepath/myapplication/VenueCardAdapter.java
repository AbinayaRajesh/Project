package com.codepath.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Models.Venue;

import java.util.ArrayList;

// Provide the underlying view for an individual list item.
public class VenueCardAdapter extends RecyclerView.Adapter<VenueCardAdapter.VH> {
    private Activity mContext;
    private ArrayList<Venue> mVenues;
    // public tempFOOD optionsActivity;

    public VenueCardAdapter(Activity context, ArrayList<Venue> venues) {
        mContext = context;
        if (venues == null) {
            throw new IllegalArgumentException("options must not be null");
        }
        mVenues = venues;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(VH holder, int position) {
        Venue venue = mVenues.get(position);
        holder.rootView.setTag(venue);
        holder.tvTitle.setText(venue.getTitle());
        if (venue.getImageUrl()=="") {
            int i = (int) ((Math. random() * 50 + 1) % 5);
            switch (i) {
                case 0: {holder.ivProfile.setImageResource(R.mipmap.red);break;}
                case 1: {holder.ivProfile.setImageResource(R.mipmap.blue); break;}
                case 2: {holder.ivProfile.setImageResource(R.mipmap.green); break;}
                case 3: {holder.ivProfile.setImageResource(R.mipmap.violet); break;}
                case 4: {holder.ivProfile.setImageResource(R.mipmap.pink); break;}
            }
            // holder.ivProfile.setImageResource(R.drawable.ic_music);
        }
        else {
            Glide.with(mContext).load(venue.getImageUrl()).centerCrop().into(holder.ivProfile);
        }
    }

    @Override
    public int getItemCount() {
        return mVenues.size();
    }

    // Provide a reference to the views for each contact item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvTitle;
        final View vPalette;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            vPalette = itemView.findViewById(R.id.vPalette);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Venue ven = (Venue) v.getTag();
                    if (v != null) {
                        // Fire an intent when a option is selected
                        // Pass option object in the bundle and populate details activity.
                        // first parameter is the context, second is the class of the activity to launch

                        Intent i = new Intent(context, DetailPlaceActivity.class);
                        Venue venue = mVenues.get(v.getId());
                        i.putExtra("venue", venue);
                        context.startActivity(i); // brings up the second activity


                    }
                }
            });
        }
    }
}
