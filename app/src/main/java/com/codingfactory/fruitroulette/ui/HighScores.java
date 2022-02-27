package com.codingfactory.fruitroulette.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingfactory.fruitroulette.MyDatabase.MyDatabaseHelper;
import com.codingfactory.fruitroulette.R;

import java.util.ArrayList;

public class HighScores extends AppCompatActivity {

    private TableLayout scoreTable;
    private static final String DATABASE_NAME = "score_manager";
    private ArrayList<String> highscoreTable;
    private String player;
    private int score;
    private int round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        System.out.println(player);
        System.out.println(score);

        //Get highscore from sqlite database

        setContentView(R.layout.high_scores);
        MyDatabaseHelper scoreDb = new MyDatabaseHelper(this, DATABASE_NAME, null,1);
        player = getIntent().getStringExtra("playerName");

        if (player != null) {
            score = getIntent().getIntExtra("finalScore", 0);
            round = getIntent().getIntExtra("finalRound",0);
            scoreDb.addHighscore(player,score,round);
        }

        //display scores

        highscoreTable = scoreDb.getAllScores();
        System.out.println(scoreDb.getAllScores());

        scoreTable = findViewById(R.id.ScoreTable);
        HighScoreDisplay(highscoreTable);
    }


    void HighScoreDisplay(ArrayList<String> highscoreTable) {

        //Function to create new rows for new score

        for (int i = 0; i<highscoreTable.size()-1; i+=3) {
            TableRow currentRow = new TableRow(getApplicationContext());
            TextView currentName = new TextView(getApplicationContext());
            TextView currentScore = new TextView(getApplicationContext());
            TextView currentRound = new TextView(getApplicationContext());

            scoreTable.addView(currentRow);
            currentName.setText(highscoreTable.get(i));
            currentScore.setText(highscoreTable.get(i+1));
            currentRound.setText(highscoreTable.get(i+2));

            currentRow.addView(currentName, 0);
            currentRow.addView(currentScore, 1);
            currentRow.addView(currentRound, 2);

            //Row format (textalignment, textsize)

            currentName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            currentScore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            currentRound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            currentName.setTextSize(20);
            currentRound.setTextSize(20);
            currentScore.setTextSize(20);

        }
    }
}
