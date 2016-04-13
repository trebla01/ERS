package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//////////////////////////////////////////////////////////////////////////////
//
// Defines the member variables and functions for the cards
//
//////////////////////////////////////////////////////////////////////////////

public class Card {
    public CardConstants.Suit suit;
    public CardConstants.Pip pip;

    public final static float CARD_WIDTH = 1f;
    public final static float CARD_HEIGHT = CARD_WIDTH * 277f / 200f;

    public Sprite front;
    public Sprite back;

    public Card(CardConstants.Suit suit, CardConstants.Pip pip, Sprite back, Sprite front) {
        back.setSize(CARD_WIDTH, CARD_HEIGHT);
        front.setSize(CARD_WIDTH, CARD_HEIGHT);
        this.suit = suit;
        this.pip = pip;
        this.back = back;
        this.front = front;
    }

    //calculates position based off 0,0 being center of the screen
    public void setPosition(float x, float y) {
        front.setPosition(x - 0.5f * front.getWidth(), (y - 0.5f) * front.getHeight());
        back.setPosition(x - 0.5f * back.getWidth(), (y - 0.5f) * back.getHeight());
    }

    public void changeCard(int cardIndex, TextureAtlas atlas){
        int s = cardIndex / 13;
        int v = cardIndex % 13;

        if(s == 0){
            this.suit = CardConstants.Suit.Clubs;
        }else if (s == 1){
            this.suit = CardConstants.Suit.Diamonds;
        }else if (s == 2){
            this.suit = CardConstants.Suit.Hearts;
        }else{
            this.suit = CardConstants.Suit.Spades;
        }

        switch(v){
            case 0:
                this.pip = CardConstants.Pip.Ace;
                break;
            case 1:
                this.pip = CardConstants.Pip.Two;
                break;
            case 2:
                this.pip = CardConstants.Pip.Three;
                break;
            case 3:
                this.pip = CardConstants.Pip.Four;
                break;
            case 4:
                this.pip = CardConstants.Pip.Five;
                break;
            case 5:
                this.pip = CardConstants.Pip.Six;
                break;
            case 6:
                this.pip = CardConstants.Pip.Seven;
                break;
            case 7:
                this.pip = CardConstants.Pip.Eight;
                break;
            case 8:
                this.pip = CardConstants.Pip.Nine;
                break;
            case 9:
                this.pip = CardConstants.Pip.Ten;
                break;
            case 10:
                this.pip = CardConstants.Pip.Jack;
                break;
            case 11:
                this.pip = CardConstants.Pip.Queen;
                break;
            case 12:
                this.pip = CardConstants.Pip.King;
                break;
        }

        this.front = atlas.createSprite(this.suit.name, this.pip.value);
        this.back = atlas.createSprite("back", 1);

        this.back.setSize(CARD_WIDTH, CARD_HEIGHT);
        this.front.setSize(CARD_WIDTH, CARD_HEIGHT);

    }

}
