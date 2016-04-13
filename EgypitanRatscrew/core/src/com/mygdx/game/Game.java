package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

//////////////////////////////////////////////////////////////////////////////
//
// Game logic for Egyptian Ratscrew
//
//////////////////////////////////////////////////////////////////////////////

public class Game {
    //turn variable
    //detect slaps
    //game needs to know all the decks
    //p1deck, p2deck, centerdeck
    public static int numPlayers = 2;

    public ArrayList<CardDeck> players;
    CardDeck centerPile;
    public Texture turnArrow;
    public Texture hand1;
    public Texture hand2;
    public Sound slapSound;
    public Sound drawSound;
    public Sound collectSound;
    public Sound errorSound;

    float handSize = 0.9f;

    //game logic variables
    int turn; // turn statistic: every press is 1 turn
    int playerTurn; //who's turn it is
    int royalCountDown;
    int burnAmount = 2;

    public float animateCardX;
    public float animateCardY;
    public boolean animateFlag = false;
    public int animateWinner = 0;

    public Game(TextureAtlas atlas){
        turn = 0;
        playerTurn = 0;
        royalCountDown = 0;
        players = new ArrayList<CardDeck>();

        turnArrow = new Texture("data/arrow.png");
        hand1 = new Texture("data/hand1.png");
        hand2 = new Texture("data/hand2.png");

        collectSound = Gdx.audio.newSound(Gdx.files.internal("data/cardFan.wav"));
        slapSound = Gdx.audio.newSound(Gdx.files.internal("data/slap.mp3"));
        drawSound = Gdx.audio.newSound(Gdx.files.internal("data/draw.wav"));
        errorSound = Gdx.audio.newSound(Gdx.files.internal("data/error.wav"));

        for(int i = 0; i < numPlayers; i++)
            players.add(new CardDeck(false));

        centerPile = new CardDeck(atlas, 1, true);
        centerPile.shuffle();
        centerPile.setPosition(0,0);

        int cardsDistributed = 0;
        while(centerPile.cards.size() > 0)
        {
            players.get(cardsDistributed % numPlayers).cards.add(centerPile.drawCard());
            cardsDistributed++;
        }

        //TODO:make set position dynamic
        players.get(0).setPosition(0, -1.5f);
        players.get(1).setPosition(0, 1.5f);

        //TODO: make set card back work
        //players.get(0).setCardBack(atlas, 1);
        //players.get(1).setCardBack(atlas, 2);

        //initially, p1 goes first
        players.get(0).yourTurn = true;
        players.get(1).yourTurn = false;

        animateCardX = 0;
        animateCardY = 0;
    }

    public void render(SpriteBatch spriteBatch){
        //draw cards
        centerPile.render(spriteBatch);
        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).render(spriteBatch);
        }

        //draw turn arrow
        if(playerTurn == 0){
            spriteBatch.draw(turnArrow, 1f,-2.5f, 0.25f, 0.25f);
        }
        if(playerTurn == 1){
            spriteBatch.draw(turnArrow, 1f,2f, 0.25f, 0.25f);
        }

        //draw slap area
        spriteBatch.draw(hand1, -1.5f, -2.7f, handSize, handSize);
        spriteBatch.draw(hand2, -1.5f, 2.0f, handSize, handSize);

    }

    //return whether we should collect center pile or not
    public boolean update(int playerSlap, boolean doCollect){
        boolean retCollectFlag = false;
        if(playerSlap != 0) {
            //if slap on a slappable
            slapSound.play(0.5f);
            if (detectSlappables()) {
                royalCountDown = 0;
                players.get(playerTurn).yourTurn = false;
                playerTurn = playerSlap - 1;
                players.get(playerTurn).yourTurn = true;
                collectPile(players.get(playerSlap - 1));

                return false;
            }
            //if it's slap error, burn cards
            else {
                errorSound.play(0.3f);
                for (int i = 0; i < burnAmount; i++) {
                    //maybe pause the game to render burn animation?
                    if(players.get(playerSlap - 1).cards.size() > 0) {
                        centerPile.cards.add(players.get(playerSlap - 1).cards.get(0));
                        players.get(playerSlap - 1).cards.remove(0);
                        drawSound.play();
                    }
                }
            }

            return doCollect;
        }
        drawSound.play();
        //if someone presses centerpile and slappable = true
        //make it that player's turn
        //collect pile for that player
        if(royalCountDown == 0) {
            incrementTurn();
        }
        else if(centerPile.cards.get(0).pip.value >= 11 || centerPile.cards.get(0).pip.value == 1){
            incrementTurn();
        }
        else{
            royalCountDown--;

            //TODO
            //render last card played in royal countdown
            //render an empty center pile
            //give the cards to the person who played the royal
            //verify collect pile works
            //here's where we check if pile belongs to someone

            //only works for two players so far
            //TODO: (maybe?) make it work for more than 2 players
            if (royalCountDown == 0){
                retCollectFlag = true;
            }

        }
        if(centerPile.cards.size() > 0 ) {
            switch (centerPile.cards.get(0).pip.value) {
                case 11:
                    royalCountDown = 1;
                    break;
                case 12:
                    royalCountDown = 2;
                    break;
                case 13:
                    royalCountDown = 3;
                    break;
                case 1:
                    royalCountDown = 4;
                    break;
            }
        }

        if(retCollectFlag == false)
            turn++; //increment turn statistic
        return retCollectFlag;
    }

    public void incrementTurn(){
        players.get(playerTurn).yourTurn = false; //remove previous player's turn
        playerTurn++;
        if (playerTurn >= numPlayers) {
            playerTurn = 0;
        }
        players.get(playerTurn).yourTurn = true; //set current player turn
    }

    public void collectPile(CardDeck player){
        for(int i = centerPile.cards.size() - 1; i >= 0; i--){
            player.cards.add(centerPile.cards.get(i));
            centerPile.cards.remove(i);
        }

        animateFlag = true;
        animateWinner = playerTurn;

        collectSound.play();
    }

    public boolean detectSlappables(){
        if(centerPile.cards.size() >= 2){
            if(centerPile.cards.get(0).pip == centerPile.cards.get(1).pip){
                System.out.println("double!");
                return true;
            }
        }
        if(centerPile.cards.size() >= 3){
            if(centerPile.cards.get(0).pip == centerPile.cards.get(2).pip){
                System.out.println("sandwich!");
                return true;
            }
        }
        return false;

    }

}
