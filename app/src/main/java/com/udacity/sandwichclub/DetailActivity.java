package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_tv)
    TextView alsoKnownAs;

    @BindView(R.id.ingredients_tv)
    TextView ingredients;

    @BindView(R.id.origin_tv)
    TextView origin;

    @BindView(R.id.description_tv)
    TextView sandwichDescription;

    @BindView(R.id.image_iv)
    ImageView ingredientsIv;

    String dataNotAvailable;
    StringBuilder sbIngredients, sbAlsoKnownAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dataNotAvailable = getResources().getString(R.string.not_available);
        ButterKnife.bind(this);


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
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_error_placeholder)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        sbIngredients = new StringBuilder();
        sbAlsoKnownAs = new StringBuilder();

        sandwichDescription.setText(String.format("%s\n", sandwich.getDescription()));

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            origin.setText(String.format("%s\n", sandwich.getPlaceOfOrigin()));
        } else {
            origin.setText(String.format("%s\n", dataNotAvailable));
        }

        Iterator<String> itAlso = sandwich.getAlsoKnownAs().iterator();
        if (itAlso.hasNext()) {
            while (true) {
                sbAlsoKnownAs.append(itAlso.next() + "\n");
                if (!itAlso.hasNext())
                    break;
            }
        } else{
            sbAlsoKnownAs.append(dataNotAvailable + "\n");
        }
        alsoKnownAs.setText(sbAlsoKnownAs);

        Iterator<String> it = sandwich.getIngredients().iterator();
        if (it.hasNext()) {
            while (true) {
                sbIngredients.append(it.next() + "\n");
                if (!it.hasNext())
                    break;
            }
        }
        ingredients.setText(sbIngredients);
    }
}
