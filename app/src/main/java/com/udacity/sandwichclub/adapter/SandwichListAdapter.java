package com.udacity.sandwichclub.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SandwichListAdapter extends ArrayAdapter<String> {

    private final Activity Context;
    private final String[] ListItemsName;
    private List<String> ImageName;

    public SandwichListAdapter(Activity context, String[] content) {

        super(context, R.layout.main_list_item, content);
        // TODO Auto-generated constructor stub

        this.Context = context;
        this.ListItemsName = content;
        this.ImageName = new ArrayList<>();

        getImagesFromJson();
    }

    private void getImagesFromJson() {
        List<String> details = Arrays.asList(Context.getResources().getStringArray(R.array.sandwich_details));
        for (int i = 0; i < details.size(); i++) {
            ImageName.add(JsonUtils.getSandwichImage(details.get(i)));
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = Context.getLayoutInflater();
        @SuppressLint("ViewHolder") View ListViewSingle = inflater.inflate(R.layout.main_list_item, null, true);

        TextView ListViewItems = ListViewSingle.findViewById(R.id.tv_item_name);
        ImageView ListViewImage = ListViewSingle.findViewById(R.id.iv_item);

        Picasso.with(Context)
                .load(ImageName.get(position))
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_error_placeholder)
                .into(ListViewImage);

        ListViewItems.setText(ListItemsName[position]);
        return ListViewSingle;
    }
}
