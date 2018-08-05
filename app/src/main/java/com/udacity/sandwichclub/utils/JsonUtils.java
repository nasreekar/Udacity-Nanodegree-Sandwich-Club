package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    static final String KEY_NAME = "name";
    static final String KEY_MAIN_NAME = "mainName";
    static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_IMAGE = "image";
    static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject obj = new JSONObject(json);
            JSONObject nameObj = obj.getJSONObject(KEY_NAME);
            sandwich.setMainName(nameObj.getString(KEY_MAIN_NAME));
            sandwich.setAlsoKnownAs(getJsonArrayAsList(nameObj.getJSONArray(KEY_ALSO_KNOW_AS)));
            sandwich.setPlaceOfOrigin(obj.getString(KEY_PLACE_OF_ORIGIN));
            sandwich.setDescription(obj.getString(KEY_DESCRIPTION));
            sandwich.setImage(obj.getString(KEY_IMAGE));
            sandwich.setIngredients(getJsonArrayAsList(obj.getJSONArray(KEY_INGREDIENTS)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    public static String parseSandwichImages(String json){
        String imagesURL = null;
        try {
            JSONObject obj = new JSONObject(json);
            imagesURL = (obj.getString(KEY_IMAGE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imagesURL;
    }


    public static ArrayList<String> getJsonArrayAsList(JSONArray array) {
        ArrayList<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
