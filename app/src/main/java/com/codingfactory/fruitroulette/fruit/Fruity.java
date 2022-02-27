package com.codingfactory.fruitroulette.fruit;

import java.util.ArrayList;
import java.util.List;

public enum Fruity {
    STRAWBERRY ("ic_strawberry",false, false),
    BANANA   ("ic_banana",false, true),
    RASPBERRY ("ic_raspberry",false, false),
    KIWI ("ic_kiwi",false, true),
    ORANGE ("ic_orange",true, true),
    PLUM ("ic_plum",true, false),
    GRAPES ("ic_grapes",true, false),
    LEMON ("ic_lemon",true, true);

    private final String img;
    private final boolean seeds;
    private final boolean peeling;

    Fruity(String img, boolean seeds, boolean peeling) {
        this.img = img;
        this.seeds = seeds;
        this.peeling = peeling;
    }

    public boolean hasSeeds() {
        return this.seeds;
    }

    public boolean needsPeeling() {
        return this.peeling;
    }

    public String getImg() {
        return this.img;
    }
}