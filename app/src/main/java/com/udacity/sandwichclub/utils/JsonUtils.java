package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        //creating string Java objects
        String frontImageJAVA = null;
        String mainNameJAVA = null;
        List<String> alsoKnownAsListJAVA = new ArrayList<>();
        String placeOfOriginJAVA = null;
        List<String> ingredientsListJAVA = new ArrayList<>();
        String descriptionJAVA = null;
        Sandwich sandwich = null;

        try {
            //JSON parser
            JSONObject jsonObject = new JSONObject(json);

            mainNameJAVA = jsonObject.getJSONObject("name").getString("mainName");
            placeOfOriginJAVA = jsonObject.getString("placeOfOrigin");
            descriptionJAVA = jsonObject.getString("description");
            frontImageJAVA = jsonObject.getString("image");

            JSONArray alsoKnownAsArrayJASON = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
            if (alsoKnownAsArrayJASON.length() > 0) {
                for (int i = 0; i < alsoKnownAsArrayJASON.length(); i++)
                {
                    alsoKnownAsListJAVA.add(alsoKnownAsArrayJASON.getString(i));
                }
            }
            JSONArray ingredientsArrayJSON = jsonObject.getJSONArray("ingredients");
            if (ingredientsArrayJSON.length() > 0 ) {
                for (int i = 0; i < ingredientsArrayJSON.length(); i++)
                {
                    ingredientsListJAVA.add(ingredientsArrayJSON.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sandwich = new Sandwich();
        sandwich.setMainName(mainNameJAVA);
        sandwich.setImage(frontImageJAVA);
        sandwich.setAlsoKnownAs(alsoKnownAsListJAVA);
        sandwich.setPlaceOfOrigin(placeOfOriginJAVA);
        sandwich.setIngredients(ingredientsListJAVA);
        sandwich.setDescription(descriptionJAVA);

        return sandwich;

    }
}
