package com.mygdx.game;

//////////////////////////////////////////////////////////////////////////////
//
// Defines the Suit and Pip constants for each card
//
//////////////////////////////////////////////////////////////////////////////

public class CardConstants {
    public enum Suit {
        Clubs("clubs", 0), Diamonds("diamonds", 1), Hearts("hearts", 2), Spades("spades", 3);
        public final String name;
        public final int index;
        private Suit(String name, int index) {
            this.name = name;
            this.index = index;
        }
    }

    public enum Pip {
        Ace(1), Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Jack(11), Queen(12), King(13);
        public final int value;
        public final int index;
        private Pip(int value) {
            this.value = value;
            this.index = value - 1;
        }
    }
}
