package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//////////////////////////////////////////////////////////////////////////////
//
// Creates the deck of cards and create deck functions
//
//////////////////////////////////////////////////////////////////////////////


public class CardDeck {
    public ArrayList<Card> cards;
    public float positionX; //0,0 draws to the center of the screen
    public float positionY;
    public boolean faceUp;
    public boolean yourTurn;

    //default constructor
    public CardDeck()
    {
        yourTurn = false;
        cards = new ArrayList<Card>();
    }

    public CardDeck(boolean faceup)
    {
        yourTurn = false;
        cards = new ArrayList<Card>();
        faceUp = faceup;
    }

    //initializes the entire deck
    public CardDeck(TextureAtlas atlas, int backIndex, boolean faceup) {
        yourTurn = false;
        cards = new ArrayList<Card>();
        faceUp = faceup;
        for (CardConstants.Suit suit : CardConstants.Suit.values()) {
            for (CardConstants.Pip pip : CardConstants.Pip.values()) {
                Sprite front = atlas.createSprite(suit.name, pip.value);
                Sprite back = atlas.createSprite("back", backIndex);
                cards.add(new Card(suit, pip, back, front));
            }
        }
    }

    //shuffle
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
    }

    public void setPosition(float px, float py){
        positionX = px;
        positionY = py;
    }

    //render
    public void render(SpriteBatch spriteBatch){
        if(cards.size() <= 0){
            return;
        }

        cards.get(0).setPosition(positionX, positionY);
        if(faceUp) {
            //top of the deck is head of the arraylist, draw top card only
            cards.get(0).front.draw(spriteBatch);
        }
        else {
            cards.get(0).back.draw(spriteBatch);
        }
    }

    public boolean isPressed(int screenX, int screenY, int pointer, int button, float originX, float originY, float cardWidthPixels, float cardHeightPixels) {
        if(!yourTurn){
            return false;
        }
        double lBound = originX + positionX * cardWidthPixels - 0.5 * cardWidthPixels;
        double rBound = originX + positionX * cardWidthPixels + 0.5 * cardWidthPixels;
        double uBound = originY - positionY * cardHeightPixels + 0.5 * cardHeightPixels;
        double dBound = originY - positionY * cardHeightPixels - 0.5 * cardHeightPixels;
        return (screenX > lBound &&
                screenX < rBound &&
                screenY > dBound &&
                screenY < uBound);
    }

    public Card drawCard(){
        //TODO delay drawing time for animations
        Card retCard;
        if(cards.size() > 0 ) {
            retCard = cards.get(0);
            cards.remove(0);
            return retCard;
        }
        else
            return null;

    }

    public void removeAllCards(){
        for(int i = 0 ; i < cards.size(); i++){
            cards.remove(0);
        }
    }

    //TODO: add customize card backs


}
