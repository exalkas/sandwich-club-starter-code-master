package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /** Tag for log messages */
    private static final String LOG_TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject name= new JSONObject(json);
            JSONObject item=name.getJSONObject("name");

            //Get main name
            String mainName=item.getString("mainName");
            Log.d(LOG_TAG,"MainName= "+mainName);

            //Get array Also known
            List<String> alsoKnown=new ArrayList<>();

            JSONArray arrAlsoKnown = item.getJSONArray("alsoKnownAs");

            for (int i = 0; i < arrAlsoKnown.length(); i++) {
                alsoKnown.add(arrAlsoKnown.getString(i));
                Log.d(LOG_TAG,"alsoknown for: " +arrAlsoKnown.getString(i));
            }

            //Get place of origin
            String placeOfOrigin=name.getString("placeOfOrigin");
            Log.d(LOG_TAG,"PlaceofOrigin= "+placeOfOrigin);

            //Get description
            String description=name.getString("description");
            Log.d(LOG_TAG,"Desription= "+description);


            //Get image
            String image=name.getString("image");

            //Get array Ingredients
            List<String> ingredients=new ArrayList<>();

            JSONArray arrIngredients=name.getJSONArray("ingredients");

            for(int i=0;i<arrIngredients.length();i++)
                {
                    ingredients.add(arrIngredients.getString(i));
                    Log.d(LOG_TAG,"Ingredient: " +arrIngredients.getString(i));
                }

            Sandwich sandwich= new Sandwich(mainName,alsoKnown,placeOfOrigin,description,image,ingredients);
            return sandwich;
            }
         catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing Sandwich in JSON", e);
        }
        return null;
    }
}
