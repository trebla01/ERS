package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//////////////////////////////////////////////////////////////////////////////
//
// Draws the texture and menu graphics
//
//////////////////////////////////////////////////////////////////////////////

public class Menu {
    public Texture playGameButton;
    public Texture localMultiplayerButton;
    public Texture onlineMultiplayerButton;
    public Texture singlePlayerButton;
    public Texture exitButton;
    public Texture menuButton;
    public Texture titleLogo;
    public Texture pauseLogo;
    public Texture resumeButton;
    public Texture tutorialButton;
    public Texture winner;
    public Texture winnerFlipped;
    public Texture loser;
    public Texture loserFlipped;
    public Texture[] tutorial;
    public Texture tutorialLogo;
    public Texture of;
    public Texture easy;
    public Texture medium;
    public Texture hard;
    public Menu(){
        titleLogo = new Texture("data/title.png");
        playGameButton = new Texture("data/play_button.png");
        localMultiplayerButton = new Texture("data/local_game_button.png");
        onlineMultiplayerButton = new Texture("data/multiplayer_button.png");
        singlePlayerButton = new Texture("data/singleplayer.png");
        exitButton = new Texture("data/quit_button.png");
        menuButton = new Texture("data/menu_button.png");
        pauseLogo = new Texture("data/pause.png");
        resumeButton = new Texture("data/resume_button.png");
        tutorialButton = new Texture("data/tutorial_button.png");
        winner = new Texture("data/winner.png");
        winnerFlipped = new Texture("data/winnerFlipped.png");
        loser = new Texture("data/loser.png");
        loserFlipped = new Texture("data/loserFlipped.png");
        easy = new Texture("data/easy.png");
        medium = new Texture("data/medium.png");
        hard = new Texture("data/hard.png");
        tutorial = new Texture[9];
        tutorial[0] = new Texture("data/tutorial1.png");
        tutorial[1] = new Texture("data/tutorial2.png");
        tutorial[2] = new Texture("data/tutorial3.png");
        tutorial[3] = new Texture("data/tutorial4.png");
        tutorial[4] = new Texture("data/tutorial5.png");
        tutorial[5] = new Texture("data/tutorial6.png");
        tutorial[6] = new Texture("data/tutorial7.png");
        tutorial[7] = new Texture("data/tutorial8.png");
        tutorial[8] = new Texture("data/tutorial9.png");
        tutorialLogo = new Texture("data/tutoriallabel.png");
        of = new Texture("data/of.png");

    }

    public void renderMainMenu(SpriteBatch spriteBatch){
        spriteBatch.draw(titleLogo, -1.5f, 2.25f, 3f, 1f);
        spriteBatch.draw(playGameButton, -1f, 1f, 2f, 1f);
        spriteBatch.draw(tutorialButton, -1f, -1f, 2f, 1f);
        spriteBatch.draw(exitButton, -1f, -3f, 2f, 1f);
    }

    public void renderPauseMenu(SpriteBatch spriteBatch){
        spriteBatch.draw(pauseLogo, -1.5f, 2.25f, 3f, 1f);
        spriteBatch.draw(resumeButton, -1f, 1f, 2f, 1f);
        spriteBatch.draw(menuButton, -1f, -1f, 2f, 1f);
        spriteBatch.draw(exitButton, -1f, -3f, 2f, 1f);
    }

    public void renderChooseModeMenu(SpriteBatch spriteBatch){
        spriteBatch.draw(singlePlayerButton, -1f, 1f, 2f, 1f);
        spriteBatch.draw(localMultiplayerButton, -1f, -1f, 2f, 1f);
        spriteBatch.draw(menuButton, -1f, -3f, 2f, 1f);
    }

    public void renderChooseDifficultyMenu(SpriteBatch spriteBatch){
        spriteBatch.draw(easy, -1f, 1f, 2f, 1f);
        spriteBatch.draw(medium, -1f, -1f, 2f, 1f);
        spriteBatch.draw(hard, -1f, -3f, 2f, 1f);
    }

}
