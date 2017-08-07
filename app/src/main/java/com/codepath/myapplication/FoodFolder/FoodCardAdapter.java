package com.codepath.myapplication.FoodFolder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Database.FoodContract.FoodEntry;
import com.codepath.myapplication.Food;
import com.codepath.myapplication.FoodDetail;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

// Provide the underlying view for an individual list item.
//card adapter for food, displays food as card on food page, in addition it lets you save
//recipies you like for later
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(VH holder, int position) {
        Food f = mFood.get(position);
        holder.rootView.setTag(f);
        holder.tvTitle.setText(f.getName());

        Glide.with(mContext).load(f.getImageUrl()).centerCrop().into(holder.ivProfile);

        if (f.isFavourite()==1) {
            Glide.with(mContext) .load("") .error(R.drawable.ic_remove) .into(holder.add);
        }
        else {
            Glide.with(mContext) .load("") .error(R.drawable.ic_add) .into(holder.add);
        }
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
        final ImageButton add;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            vPalette = itemView.findViewById(R.id.vPalette);
            add = (ImageButton) itemView.findViewById(R.id.add);

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
                        mContext.startActivityForResult(i, REQUEST_CODE); // brings up the second activity


                    }
                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Food f = (Food) mFood.get(position);
                    if (f.isFavourite()==0) {
                        add.setImageResource(R.drawable.ic_remove);
                        Byte i = 1;
                        f.setFavourite(i);
                        insertRecipe(f);
                    }
                    else {
                        add.setImageResource(R.drawable.ic_add);
                        Byte i = 0;
                        f.setFavourite(i);
                        deleteFood(f);
                    }
                }
            });
        }
    }

    private void insertRecipe(Food recipe) {

        deleteFood(recipe);

        String nameString = recipe.getName();
        String urlString = recipe.getImageUrl();
        int ratingInt = recipe.getRating();
        int idInt = recipe.getId();


        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(mContext);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_FOOD_NAME, nameString);
        values.put(FoodEntry.COLUMN_FOOD_URL, urlString);
        values.put(FoodEntry.COLUMN_FOOD_RATING, ratingInt);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(FoodEntry.TABLE_NAME, null, values);



    }

    private void deleteFood(Food recipe) {


        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_FOOD_TABLE =  "DELETE FROM " + FoodEntry.TABLE_NAME +
                " WHERE " + FoodEntry.COLUMN_FOOD_NAME + " = \"" + recipe.getName() + "\";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(mContext);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_FOOD_TABLE);

    }

}
