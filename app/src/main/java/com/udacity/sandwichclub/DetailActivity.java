package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String SEPARATOR = ", ";

    private TextView alsoKnownAs_tv;
    private TextView ingredients_tv;
    private TextView placeOfOrigin_tv;
    private TextView description_tv;

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
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher) //local resource that app comes with. Loading image
                //takes a while. You need to make network request, wait for the server to respond,
                // Until image is load it will show placeholder image.
                .error(R.mipmap.ic_launcher_round) // will be displayed if the image cannot be loaded
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwichJAVA) {

        placeOfOrigin_tv = findViewById(R.id.origin_tv);
        if (sandwichJAVA.getPlaceOfOrigin().isEmpty())
        {
            placeOfOrigin_tv.setText(R.string.detail_error_message);
        } else {
            placeOfOrigin_tv.setText(sandwichJAVA.getPlaceOfOrigin());
        }

        alsoKnownAs_tv = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsListJAVA = sandwichJAVA.getAlsoKnownAs();
        StringBuilder stringBuilder = new StringBuilder();

        if (alsoKnownAsListJAVA.isEmpty()) {
            alsoKnownAs_tv.setText(R.string.detail_error_message);
            } else {
                for (String i : alsoKnownAsListJAVA)
                {
                    stringBuilder.append(i);
                    stringBuilder.append(SEPARATOR);
                }
                String sBuilderToString = stringBuilder.toString();
                //Remove last comma
                sBuilderToString = sBuilderToString.substring(0, sBuilderToString.length() - SEPARATOR.length());
                alsoKnownAs_tv.setText(sBuilderToString);
        }

        ingredients_tv = findViewById(R.id.ingredients_tv);
        List<String> ingredientsListJAVA = sandwichJAVA.getIngredients();
        StringBuilder ingredientStringBuilder = new StringBuilder();
        if (ingredientsListJAVA.size() == 0) {
            ingredients_tv.setText(R.string.detail_error_message);
        } else {
            for (String i : ingredientsListJAVA)
            {
                ingredientStringBuilder.append(i);
                ingredientStringBuilder.append(SEPARATOR);
            }
            String sIngredientStringBuilder = ingredientStringBuilder.toString();
            //Remove last comma
            sIngredientStringBuilder = sIngredientStringBuilder.substring(0, sIngredientStringBuilder.length() - SEPARATOR.length());
            ingredients_tv.setText(sIngredientStringBuilder);
        }


        description_tv = findViewById(R.id.description_tv);
        if (sandwichJAVA.getDescription().isEmpty())
        {
            description_tv.setText(R.string.detail_error_message);
        } else {
            description_tv.setText(sandwichJAVA.getDescription());
        }

    }
}