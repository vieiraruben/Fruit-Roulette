package com.codingfactory.fruitroulette.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.codingfactory.fruitroulette.R;
import com.codingfactory.fruitroulette.logic.GameSequence;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //List of strings representing source of guessed fruit images or hints.
    private final ArrayList<String[]> guesses = new ArrayList<>();
    //List of strings representing correctly guessed fruit and misplaced fruit.
    private final ArrayList<String[]> positions = new ArrayList<>();
    private final Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guess_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //When a new row is added, this sets image source for each fruit/hint.
        holder.firstImg.setImageResource(context.getResources().getIdentifier(guesses.get(position)[0], "drawable", context.getPackageName()));
        holder.secondImg.setImageResource(context.getResources().getIdentifier(guesses.get(position)[1], "drawable", context.getPackageName()));
        holder.thirdImg.setImageResource(context.getResources().getIdentifier(guesses.get(position)[2], "drawable", context.getPackageName()));
        holder.fourthImg.setImageResource(context.getResources().getIdentifier(guesses.get(position)[3], "drawable", context.getPackageName()));
        //Displays correctly guessed fruit and misplaced fruit.
        holder.rightIndicator.setText(positions.get(position)[0]);
        holder.wrongIndicator.setText(positions.get(position)[1]);
    }

    //Returns number of lines in RecyclerView.
    @Override
    public int getItemCount() {
        return this.guesses.size();
    }

    //Converts number of right and wrong guesses to Strings and adds them to positions list.
    public void addPositions(int right, int wrong) {
        this.positions.add(new String[] {String.valueOf(right), String.valueOf(wrong)});
    }

    //Adds to RecyclerView and refreshes view.
    public void newLine(String[] guesses) {
        this.guesses.add(guesses);
        notifyDataSetChanged();
    }

    //Clear view when a new round or game is started.
    public void clear() {
        this.guesses.clear();
        this.positions.clear();
        notifyItemRangeRemoved(0, this.guesses.size());
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView firstImg;
        private final ImageView secondImg;
        private final ImageView thirdImg;
        private final ImageView fourthImg;
        private final TextView rightIndicator;
        private final TextView wrongIndicator;

        //Tells RecyclerView where to find its fields.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstImg = itemView.findViewById(R.id.firstGuess);
            secondImg = itemView.findViewById(R.id.secondGuess);
            thirdImg = itemView.findViewById(R.id.thirdGuess);
            fourthImg = itemView.findViewById(R.id.fourthGuess);
            rightIndicator = itemView.findViewById(R.id.rightIndicator);
            wrongIndicator = itemView.findViewById(R.id.wrongIndicator);

        }
    }
}
