package com.codepath.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Database.FoodContract.FoodEntry;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by emilylroth on 7/14/17.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{
    ArrayList<Food> recipes;
    ViewGroup pview;
    Context context;
    Food curE;
    int curI;

    public FoodAdapter(ArrayList<Food> recipes){this.recipes = recipes;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        pview = parent;
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView = inflater.inflate(R.layout.item_view_food, parent, false);

        return new ViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food recipe = recipes.get(position);
        String name = recipe.name;
        holder.tvRecipeName.setText(name);

        Glide.with(context)
                .load(recipe.imageUrl)
                .into(holder.ivRecipeImage);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // track view

        TextView tvRecipeName;
        ImageView ivRecipeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects by id
            tvRecipeName = (TextView) itemView.findViewById(R.id.tvRecipeName);
            ivRecipeImage = (ImageView) itemView.findViewById(R.id.ivRecipeImage);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    Food f = recipes.get(pos);
                    recipes.remove(pos);
                    deleteFood(f);
                    curE = f;
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
                recipes.add(curI, curE);
                insertRecipe(curE);
                notifyDataSetChanged();
            }
        };
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {

                // get the movie at the position, this won't work if the class is static
                Food recipe = recipes.get(position);
                // create intent for the new activity

                Intent intent = new Intent(context, FoodDetail.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra("recipe", Parcels.wrap(recipe));
                //intent.putExtra(Food.class.getSimpleName(), Parcels.wrap(recipe));
                // intent.putExtra(Country.class.getName(), Parcels.wrap(country));
                // show the activity
                context.startActivity(intent);
            }
        }
    }

    private void insertRecipe(Food recipe) {

        deleteFood(recipe);

        String nameString = recipe.getName();
        String urlString = recipe.getImageUrl();
        int ratingInt = recipe.getRating();
        int idInt = recipe.getId();


        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(context);

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
        EventDbHelper mDbHelper = new EventDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_FOOD_TABLE);

    }

}
