package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    /** Tag for log messages */
    private static final String LOG_TAG = DetailActivity.class.getName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    public TextView alsoKnown_Tv;
    public TextView placeOfOrigin_Tv;
    public TextView description_Tv;
    public TextView ingredients_Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d(LOG_TAG,"Oncreate "+ String.valueOf(position));
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.d(LOG_TAG,"No array data ");
            closeOnError();
            return;
        }

        placeOfOrigin_Tv=findViewById(R.id.origin_tv);
        alsoKnown_Tv=findViewById(R.id.origin_tv);
        description_Tv=findViewById(R.id.description_tv);
        ingredients_Tv=findViewById(R.id.ingredients_tv);

        List<String> alsoKnownAs=sandwich.getAlsoKnownAs();
        List<String> ingredients=sandwich.getIngredients();

        String origin=sandwich.getPlaceOfOrigin();
        String description=sandwich.getDescription();

        Log.d(LOG_TAG,"origin= " +origin);

        populateUI(origin,alsoKnownAs, description,ingredients);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(String origin,List<String> alsoKnownAs, String description, List<String> ingredients) {

            for (int i = 0; i < alsoKnownAs.size(); i++) {
                alsoKnown_Tv.append(alsoKnownAs.get(i));
                Log.d(LOG_TAG,alsoKnownAs.get(i));
            }


        placeOfOrigin_Tv.setText(origin);
        description_Tv.setText(description);
        for (int i=0;i<ingredients.size();i++){
            ingredients_Tv.append(ingredients.get(i)+"\n");
        }
    }
}
