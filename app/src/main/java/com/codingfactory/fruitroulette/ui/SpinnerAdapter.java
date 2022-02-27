package com.codingfactory.fruitroulette.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codingfactory.fruitroulette.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> fruit;

    public SpinnerAdapter(Context context, List<String> fruit) {
        super(context, R.layout.selected_fruit, fruit);
        this.context = context;
        this.fruit = fruit;
        this.fruit.add("placeholder");
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            return initialSelection(true);
        }
        return getFruitView(position-1, convertView, parent);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            return initialSelection(false);
        }
        return getFruitView(position-1, convertView, parent);
    }

    public View getFruitView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dropdown_fruit, parent, false);
        ImageView selectedFruitImg = row.findViewById(R.id.img);
        Resources res = context.getResources();
        String drawableImg =  fruit.get(position);
        int resId = res.getIdentifier(drawableImg, "drawable", context.getPackageName());
        Drawable drawable = res.getDrawable(resId);
        selectedFruitImg.setImageDrawable(drawable);

        return row;

    }

    private View initialSelection(boolean dropdown) {
        TextView view = new TextView(getContext());
        view.setText("Fruit");
        view.setPadding(0,0,0,0);
        view.setTextColor(Color.WHITE);
//        int spacing = getContext().getResources().getDimensionPixelSize(R.dimen.spacing_smaller);
//        view.setPadding(0, spacing, 0, spacing);

        if (dropdown) {
            view.setHeight(0);
        }
        return view;
    }
}
