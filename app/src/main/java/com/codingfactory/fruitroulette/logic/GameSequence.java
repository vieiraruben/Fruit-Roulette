package com.codingfactory.fruitroulette.logic;

import android.util.Log;

import com.codingfactory.fruitroulette.fruit.Fruity;
import com.codingfactory.fruitroulette.ui.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameSequence {

    private final List<Fruity> possibleFruit;
    private int attempts, fruitDiscovered, cumulatedScore, round;
    private boolean firstHintGiven, seedHintGiven, peelHintGiven;
    private List<Integer> hiddenFruit;
    private RecyclerAdapter adapter;

    //Constructor that initializes all parameters for the game sequence.
    public GameSequence() {
        this.attempts = 10;
        this.fruitDiscovered = 0;
        this.cumulatedScore = 0;
        this.round = 0;
        this.seedHintGiven = false;
        this.peelHintGiven = false;
        this.possibleFruit = new ArrayList<>(Arrays.asList(
                Fruity.STRAWBERRY,
                Fruity.BANANA,
                Fruity.RASPBERRY,
                Fruity.KIWI,
                Fruity.ORANGE,
                Fruity.PLUM,
                Fruity.GRAPES,
                Fruity.LEMON
        ));
        this.hiddenFruit = fruitGenerator();
    }

    //Generates a new combination of four fruits.
    private List<Integer> fruitGenerator() {
        List<Integer> hiddenFruit = new ArrayList<>();
        Random random = new Random();
        while (hiddenFruit.size() < 4) {
            int fruit = random.nextInt(8);
            if (!hiddenFruit.contains(fruit)) hiddenFruit.add(fruit);
        }
        System.out.println("hidden 0: " + hiddenFruit.get(0) + "\nhidden 1: " + hiddenFruit.get(1) +
                "\nhidden 2: " + hiddenFruit.get(2) + "\nhidden 3: " + hiddenFruit.get(3));
        return hiddenFruit;
    }

    //Returns a list of images that represent hints (either seeds or peel based on argument).
    public void getHint(int whichHint) {
        String hintImg = "";
        if (whichHint == 1 && !seedHintGiven) {
            hintImg = "ic_seeds";
            seedHintGiven = true;
        }
        else if (!peelHintGiven) {
            hintImg = "ic_peel";
            peelHintGiven = true;
        }
        String[] seedImg = new String[4];
        for (int i = 0; i < 4; i++) {
            if (whichHint == 1 && this.getPossibleFruit().get(this.hiddenFruit.get(i)).hasSeeds()) {
                seedImg[i] = hintImg;
            } else if (whichHint == 2 && this.getPossibleFruit().get(this.hiddenFruit.get(i)).needsPeeling()) {
                seedImg[i] = hintImg;
            } else {
                seedImg[i] = "ic_blank_hint";
            }
        }
        if (!firstHintGiven) {
            firstHintGiven = true;
            attempts -= 2;
        } else attempts -= 3;
        adapter.addPositions(0, 0);
        adapter.newLine(seedImg);
    }

    //Checks if user has enough attempts to get a hint.
    public boolean canIGetAHint() {
        if (firstHintGiven) return this.attempts - 3 >= 0;
        else return this.attempts - 2 >= 0;
    }

    public boolean makeAGuess(int[] guessed) {
        // Each of the four fruits are checked against the generated hidden fruit list.
        this.attempts--;
        int rightPosition = 0;
        int wrongPosition = 0;
        this.fruitDiscovered = 0;
        String[] recyclerLine = new String[4];
        for (int i = 0; i < 4; i++) {
            if (guessed[i] == this.hiddenFruit.get(i)) {
                rightPosition++;
                fruitDiscovered++;
            } else if (this.hiddenFruit.contains(guessed[i])) {
                wrongPosition++;
            }
            recyclerLine[i] = getPossibleFruit().get(guessed[i]).getImg();
        }
        //Sends RecyclerAdapter images corresponding to the guessed fruit.
        adapter.newLine(recyclerLine);
        //Sends RecyclerAdapter number of correctly positioned fruit and number of misplaced fruit.
        adapter.addPositions(rightPosition, wrongPosition);
        if (fruitDiscovered == 4) {
            cumulatedScore += attempts + 1;
            round++;
        }
        return roundOver();
    }

    //Resets attempts, clears RecyclerAdapter and generates new fruit.
    public void newRound() {
        this.seedHintGiven = false;
        this.peelHintGiven = false;
        attempts = 10;
        adapter.clear();
        hiddenFruit = fruitGenerator();
    }

    //Receives RecyclerAdapter from NewGame so that guesses can be shown.
    public void setAdapter(RecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    //Returns number of attemps left for current round.
    public int getAttempts() {
        return this.attempts;
    }

    //Checks if user has won.
    public boolean isItAWin() {
        return fruitDiscovered == 4;
    }

    //Checks if the round is over (either user has won or no more attempts are left).
    public boolean roundOver() {
        return (attempts == 0 || isItAWin());
    }

    //Resets game and starts new round.
    public void reset() {
        fruitDiscovered = 0;
        cumulatedScore = 0;
        round = 0;
        newRound();
    }

    //Getter for current round number.
    public int getRound() {
        return this.round;
    }

    //Getter for total score.
    public int getCumulatedScore() {
        return this.cumulatedScore;
    }

    //Getter for all fruits.
    public List<Fruity> getPossibleFruit() {
        return this.possibleFruit;
    }

    //Returns if seed hint has been given.
    public boolean getSeedHintGiven() {
        return this.seedHintGiven;
    }

    //Returns if peel hint has been given.
    public boolean getPeelHintGiven() {
        return this.peelHintGiven;
    }
}
