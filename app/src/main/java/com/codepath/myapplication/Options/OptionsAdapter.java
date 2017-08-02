package com.codepath.myapplication.Options;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Event.EventActivity;
import com.codepath.myapplication.FoodFolder.FoodMainPage;
import com.codepath.myapplication.LanguageActivity;
import com.codepath.myapplication.R;
import com.codepath.myapplication.Tourism.TourismActivity;

import org.parceler.Parcels;

import java.util.List;

// Provide the underlying view for an individual list item.
public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.VH> {
    private Activity mContext;
    private List<Option> mOptions;
    private String longlat;
    public OptionsActivity optionsActivity;

    public OptionsAdapter(Activity context, List<Option> options) {
        mContext = context;
        if (options == null) {
            throw new IllegalArgumentException("options must not be null");
        }
        mOptions = options;
        longlat = "";
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_options, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(VH holder, int position) {
        Option option = mOptions.get(position);
        holder.rootView.setTag(option);
        Glide.with(mContext).load(option.getThumbnailDrawable()).centerCrop().into(holder.ivProfile);
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    // Provide a reference to the views for each contact item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;


        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);


            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Option option = (Option)v.getTag();
                    if (option != null) {
                        // Fire an intent when a option is selected
                        // Pass option object in the bundle and populate details activity.
                        // first parameter is the context, second is the class of the activity to launch
                        if (option.getmId()==2) {
                            Intent i = new Intent(context, FoodMainPage.class);
                            i.putExtra("country", Parcels.wrap(optionsActivity.country.getName()));
//                            i.putExtra("country", optionsActivity.country.getName());
                            // i.putExtra(FoodActivity.EXTRA_CONTACT, option);
                            i.putExtra("country", Parcels.wrap(optionsActivity.country));
                            i.putExtra("ll", longlat);
                            context.startActivity(i); // brings up the second activity
                        }
                        else if (option.getmId()==1) {
                            Intent i = new Intent(context, EventActivity.class);
                            i.putExtra("country", Parcels.wrap(optionsActivity.country));
                            // i.putExtra(FoodActivity.EXTRA_CONTACT, option);
                            i.putExtra("country", Parcels.wrap(optionsActivity.country));
                            i.putExtra("ll", longlat);
                            context.startActivity(i); // brings up the second activity
                        }
                        else if (option.getmId()==4) {
                            Intent i = new Intent(context, TourismActivity.class);
                            // i.putExtra(FoodActivity.EXTRA_CONTACT, option);
                            i.putExtra("country", Parcels.wrap(optionsActivity.country));
                            i.putExtra("ll", longlat);
                            context.startActivity(i); // brings up the second activity
                        }
                        else if (option.getmId()==3) {
                            Intent i = new Intent(context, LanguageActivity.class);
                            // i.putExtra(FoodActivity.EXTRA_CONTACT, option);
                            i.putExtra("country", Parcels.wrap(optionsActivity.country));
                            i.putExtra("ll", longlat);
                            context.startActivity(i); // brings up the second activity
                        }


                    }
                }
            });
        }
    }
    public void changeLL(String string){
        longlat = string;
    }
}
