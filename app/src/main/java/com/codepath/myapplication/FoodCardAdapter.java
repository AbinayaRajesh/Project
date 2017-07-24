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

import org.parceler.Parcels;

import java.util.ArrayList;

// Provide the underlying view for an individual list item.
public class FoodCardAdapter extends RecyclerView.Adapter<FoodCardAdapter.VH> {
    private Activity mContext;
    private ArrayList<Food> mFood;

    public FoodCardAdapter(Activity context, ArrayList<Food> options) {
        mContext = context;
        if (options == null) {
            throw new IllegalArgumentException("options must not be null");
        }
        mFood = options;
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
        Food f = mFood.get(position);
        holder.rootView.setTag(f);
        holder.tvTitle.setText(f.getName());
        Glide.with(mContext).load(f.getImageUrl()).centerCrop().into(holder.ivProfile);
    }

    @Override
    public int getItemCount() {
        return mFood.size();
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
                    final Food food = (Food)v.getTag();
                    if (food != null) {
                        // Fire an intent when a option is selected
                        // Pass option object in the bundle and populate details activity.
                        // first parameter is the context, second is the class of the activity to launch

                        Intent i = new Intent(context, FoodDetail.class);
                        Food recipe = mFood.get(food.getId());

                        i.putExtra("recipe", Parcels.wrap(recipe));
                        context.startActivity(i); // brings up the second activity




                    }
                }
            });
        }
    }
}
