package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;

//////////////////////////////////////////////////////////////////////////////
//
// Entry point of the application, contains the game states, inputs, listener
//
//////////////////////////////////////////////////////////////////////////////

public class EgyptianRSGame implements ApplicationListener, InputProcessor {

    SpriteBatch spriteBatch;
    public TextureAtlas atlas;
    Game game;
    Menu menu;

    TextureAtlas numberAtlas;
    public Sprite p1Digit10;
    public Sprite p1Digit1;
    public Sprite p2Digit10;
    public Sprite p2Digit1;

    public Sprite tutorialCurrent;
    public Sprite tutorialTotal;

    OrthographicCamera cam;

    public final static float CARD_WIDTH = 1f;
    public final static float CARD_HEIGHT = CARD_WIDTH * 277f / 200f;
    public final static float MINIMUM_VIEWPORT_SIZE = 4f;
    public static float SCREEN_WIDTH;
    public static float SCREEN_HEIGHT;
    public static float SCREEN_ASPECT_RATIO;
    public static float ORIGIN_X;
    public static float ORIGIN_Y;
    public static float CARD_WIDTH_PIXELS;
    public static float CARD_HEIGHT_PIXELS;

    public final static int MAIN_MENU = 0;
    public final static int IN_GAME_LOCAL = 1;
    public final static int IN_GAME_SINGLPLAYER = 2;
    public final static int GAME_OVER = 3;
    public final static int PAUSED = 4;
    public final static int TUTORIAL = 5;
    public final static int CHOOSE_MODE = 6;
    public final static int CHOOSE_DIFFICULTY = 7;

    public int gameState = MAIN_MENU;
    public int previousState = MAIN_MENU;

    public final static int slapTimer = 500;
    public long timer1 = 0;
    public long timer2 = 0;
    public boolean collectFlag = false;
    public long collectTimer = 0;
    public int tutorialNumber = 0;
    public int tutorialPosX = 0;

    public int AiDrawDelay = 1000;
    public long AiDrawTimer = 0;
    public boolean AiDrawFlag = false;
    public int AiSlapDelay = 600;
    public long AiSlapTimer = 0;
    public boolean AiSlapFlag = false;
    public int postSlapDelay = 250;
    public long postSlapTimer = 0;
    public boolean postSlapFlag = false;


    public Texture animateCard;

    public int difficulty = 3; //easy = 1, medium = 2, hard = 3;
    public int easyAiMaxTimer = 1200;
    public int easyAiMinTimer = 700;
    public int normalAiMaxTimer = 700;
    public int normalAiMinTimer = 450;
    public int hardAiMaxTimer = 500;
    public int hardAiMinTimer = 300;

    Random rand;

    @Override
    public void create() {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        ORIGIN_X = SCREEN_WIDTH / 2;
        ORIGIN_Y = SCREEN_HEIGHT / 2;

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        spriteBatch = new SpriteBatch();
        atlas = new TextureAtlas("data/carddeck.atlas");
        numberAtlas = new TextureAtlas("data/number.atlas");

        menu = new Menu();
        game = new Game(atlas);

        cam = new OrthographicCamera();

        p1Digit10 = new Sprite();
        p1Digit1 = new Sprite();
        p2Digit10 = new Sprite();
        p2Digit1 = new Sprite();

        p1Digit10 = numberAtlas.createSprite("number", 2);
        p1Digit1 = numberAtlas.createSprite("number", 6);
        p1Digit10.setSize(0.5f, 0.5f);
        p1Digit1.setSize(0.5f, 0.5f);
        p1Digit1.setPosition(-0.05f, -3.5f);
        p1Digit10.setPosition(-.45f, -3.5f);

        p2Digit10 = numberAtlas.createSprite("number", 2);
        p2Digit1 = numberAtlas.createSprite("number", 6);
        p2Digit10.setSize(0.5f, 0.5f);
        p2Digit1.setSize(0.5f, 0.5f);
        p2Digit10.setPosition(-.05f, 3f);
        p2Digit1.setPosition(-0.45f, 3f);
        p2Digit10.flip(true, true);
        p2Digit1.flip(true, true);

        tutorialCurrent = new Sprite();
        tutorialTotal = new Sprite();

        tutorialCurrent = numberAtlas.createSprite("number", 1);
        tutorialCurrent.setSize(0.25f, 0.25f);
        tutorialCurrent.setPosition(-0.4f, -3.3f);
        tutorialTotal = numberAtlas.createSprite("number", 9);
        tutorialTotal.setSize(0.25f, 0.25f);
        tutorialTotal.setPosition(0.15f, -3.3f);

        animateCard = new Texture("data/cardBack.png");
        rand = new Random();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        atlas.dispose();
        numberAtlas.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0 / 255f, 180 / 255f, 0 / 255, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(cam.combined);

        switch (gameState) {
            case MAIN_MENU:
                spriteBatch.begin();
                menu.renderMainMenu(spriteBatch);
                spriteBatch.end();
                break;
            case TUTORIAL:
                tutorialCurrent = numberAtlas.createSprite("number", tutorialNumber + 1);
                tutorialCurrent.setSize(0.25f, 0.25f);
                tutorialCurrent.setPosition(-0.4f, -3.3f);
                spriteBatch.begin();
                spriteBatch.draw(menu.tutorialLogo, -1.5f, 2.8f, 3f, 1f);
                spriteBatch.draw(menu.tutorial[tutorialNumber], -1.8f, -3f, 3.6f, 6f);
                spriteBatch.draw(menu.of, -0.2f, -3.4f, 0.4f, 0.4f);
                tutorialCurrent.draw(spriteBatch);
                tutorialTotal.draw(spriteBatch);
                spriteBatch.end();
                break;
            case CHOOSE_MODE:
                spriteBatch.begin();
                menu.renderChooseModeMenu(spriteBatch);
                spriteBatch.end();
                break;
            case CHOOSE_DIFFICULTY:
                spriteBatch.begin();
                menu.renderChooseDifficultyMenu(spriteBatch);
                spriteBatch.end();
                break;
            case IN_GAME_LOCAL:
                if (Gdx.input.getInputProcessor() != this)
                    Gdx.input.setInputProcessor(this);
                spriteBatch.begin();
                game.render(spriteBatch);

                checkForCollect();

                changeNumbers();
                p1Digit10.draw(spriteBatch);
                p1Digit1.draw(spriteBatch);
                p2Digit10.draw(spriteBatch);
                p2Digit1.draw(spriteBatch);

                if(game.animateFlag == true) {
                    spriteBatch.draw(animateCard, (float) (-0.5 + game.animateCardX), (float) (-0.5 + game.animateCardY), 1, 277f / 200f);
                    if(game.animateWinner == 0)
                        game.animateCardY -= 0.15;
                    else
                        game.animateCardY += 0.15;

                    if(Math.abs(game.animateCardY) > 2) {
                        game.animateFlag = false;
                        game.animateCardY = 0;
                    }
                }

                spriteBatch.end();
                break;
            case IN_GAME_SINGLPLAYER:
                if (Gdx.input.getInputProcessor() != this)
                    Gdx.input.setInputProcessor(this);

                if(game.detectSlappables()) {
                    if(AiSlapFlag == false){
                        AiSlapFlag = true;
                        AiSlapTimer = System.currentTimeMillis();
                    }

                    if(System.currentTimeMillis() - AiSlapTimer > AiSlapDelay) {
                        System.out.println("SLAP DETECTED");
                        collectFlag = game.update(2, collectFlag);
                        AiSlapFlag = false;

                        if(postSlapFlag == false) {
                            postSlapFlag = true;
                            postSlapTimer = System.currentTimeMillis();
                        }
                    }
                }
                else if(game.playerTurn == 1){
                    if (collectFlag == false) {
                        if(AiDrawFlag == false)
                        {
                            AiDrawFlag = true;
                            AiDrawTimer = System.currentTimeMillis();
                        }
                        AiDrawDelay = rand.nextInt(1000 - 700 + 1) + 700; //randomize draw delay between 700 and 1000
                        if(System.currentTimeMillis() - AiDrawTimer > AiDrawDelay) {
                            if (game.players.get(1).cards.size() > 0) {
                                game.centerPile.cards.add(0, game.players.get(1).drawCard());
                                collectFlag = game.update(0, false);
                                collectTimer = System.currentTimeMillis();
                                AiDrawFlag = false;
                                AiSlapFlag = false;

                                if(difficulty == 1)
                                    AiSlapDelay = rand.nextInt(easyAiMaxTimer - easyAiMinTimer + 1) + easyAiMinTimer;
                                else if(difficulty == 2)
                                    AiSlapDelay = rand.nextInt(normalAiMaxTimer - normalAiMinTimer + 1) + normalAiMinTimer;
                                else if(difficulty == 3)
                                    AiSlapDelay = rand.nextInt(hardAiMaxTimer - hardAiMinTimer + 1) + hardAiMinTimer;
                            }
                        }
                    }
                }

                //code for checking game over
                if(collectFlag == false){
                    if(game.players.get(0).cards.size() == 0) {
                        game.collectPile(game.players.get(1));
                        gameState = GAME_OVER;
                    }
                    if(game.players.get(1).cards.size() == 0) {
                        game.collectPile(game.players.get(0));
                        gameState = GAME_OVER;
                    }
                }
                //code for checking game over + last slappable card
                if (game.numPlayers == 2) {
                    for (int j = 0; j < game.numPlayers; j++) {
                        if (game.players.get(j).cards.size() == 0) {
                            collectFlag = true;
                            collectTimer = System.currentTimeMillis();
                        }
                    }
                }

                //check for post slap timer
                if(System.currentTimeMillis() - postSlapTimer > postSlapDelay){
                    postSlapFlag = false;
                }

                spriteBatch.begin();
                game.render(spriteBatch);
                checkForCollect();
                changeNumbers();
                p1Digit10.draw(spriteBatch);
                p1Digit1.draw(spriteBatch);
                p2Digit10.draw(spriteBatch);
                p2Digit1.draw(spriteBatch);

                if(game.animateFlag == true) {
                    spriteBatch.draw(animateCard, (float) (-0.5 + game.animateCardX), (float) (-0.5 + game.animateCardY), 1, 277f / 200f);
                    if(game.animateWinner == 0)
                        game.animateCardY -= 0.15;
                    else
                        game.animateCardY += 0.15;

                    if(Math.abs(game.animateCardY) > 2) {
                        game.animateFlag = false;
                        game.animateCardY = 0;
                    }
                }

                spriteBatch.end();
                break;
            case GAME_OVER:
                int winner = 0;
                for (int i = 0; i < game.numPlayers; i++) {
                    if (game.players.get(i).cards.size() >= 52) {
                        winner = i;
                        break;
                    }
                }

                spriteBatch.begin();
                if (winner == 0) {
                    spriteBatch.draw(menu.winner, -1f, -1.5f, 2f, 1f);
                    spriteBatch.draw(menu.loserFlipped, -1f, 1.5f, 2f, 1f);
                }
                else {
                    spriteBatch.draw(menu.loser, -1f, -1.5f, 2f, 1f);
                    spriteBatch.draw(menu.winnerFlipped, -1f, 1.5f, 2f, 1f);
                }
                spriteBatch.draw(menu.menuButton, -1f, -3f, 2f, 1f);
                spriteBatch.end();
                break;
            case PAUSED:
                spriteBatch.begin();
                menu.renderPauseMenu(spriteBatch);
                spriteBatch.end();
                break;
        }
    }

    public void checkForCollect() {
        if (collectFlag == true) {
            if (System.currentTimeMillis() - collectTimer > 1200) {

                if(game.players.get(0).cards.size() == 0) {
                    game.collectPile(game.players.get(1));
                    gameState = GAME_OVER;
                    return;
                }
                else if(game.players.get(1).cards.size() == 0) {
                    game.collectPile(game.players.get(0));
                    gameState = GAME_OVER;
                    return;
                }

                game.players.get(game.playerTurn).yourTurn = false;
                game.playerTurn--;
                if (game.playerTurn < 0) {
                    game.playerTurn = game.numPlayers - 1;
                }
                game.collectPile(game.players.get(game.playerTurn));
                game.players.get(game.playerTurn).yourTurn = true;

                collectFlag = false;
                collectTimer = System.currentTimeMillis();
                //after one person collects the cards, check if the game is over
                for (int i = 0; i < game.numPlayers; i++) {
                    if (game.players.get(i).cards.size() >= 52) {
                        System.out.println("Player " + (i + 1) + " wins!");
                        gameState = GAME_OVER;
                    }
                }

            }
        }
    }


    public void changeNumbers() {
        p1Digit10 = numberAtlas.createSprite("number", game.players.get(0).cards.size() / 10);
        p1Digit1 = numberAtlas.createSprite("number", game.players.get(0).cards.size() % 10);
        p1Digit10.setSize(0.5f, 0.5f);
        p1Digit1.setSize(0.5f, 0.5f);
        p1Digit1.setPosition(-0.05f, -3.5f);
        p1Digit10.setPosition(-.45f, -3.5f);

        p2Digit10 = numberAtlas.createSprite("number", game.players.get(1).cards.size() / 10);
        p2Digit1 = numberAtlas.createSprite("number", game.players.get(1).cards.size() % 10);
        p2Digit10.setSize(0.5f, 0.5f);
        p2Digit1.setSize(0.5f, 0.5f);
        p2Digit10.setPosition(-.05f, 3f);
        p2Digit1.setPosition(-0.45f, 3f);
        p2Digit10.flip(true, true);
        p2Digit1.flip(true, true);
    }


    @Override
    public void resize(int width, int height) {

        if (width > height) {
            cam.viewportHeight = MINIMUM_VIEWPORT_SIZE;
            cam.viewportWidth = cam.viewportHeight * (float) width / (float) height;
            SCREEN_ASPECT_RATIO = (float) width / (float) height;
            CARD_HEIGHT_PIXELS = SCREEN_HEIGHT / MINIMUM_VIEWPORT_SIZE;
            CARD_WIDTH_PIXELS = CARD_HEIGHT_PIXELS * CARD_WIDTH / CARD_HEIGHT;
        } else {
            cam.viewportWidth = MINIMUM_VIEWPORT_SIZE;
            cam.viewportHeight = cam.viewportWidth * (float) height / (float) width;
            SCREEN_ASPECT_RATIO = (float) height / (float) width;
            CARD_WIDTH_PIXELS = SCREEN_WIDTH / MINIMUM_VIEWPORT_SIZE;
            CARD_HEIGHT_PIXELS = CARD_WIDTH_PIXELS * CARD_HEIGHT / CARD_WIDTH;
        }
        cam.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyDown(int keycode) {

        if (gameState == IN_GAME_LOCAL) {
            if (keycode == Input.Keys.BACK) {
                previousState = gameState;
                gameState = PAUSED;
                render();
            }
        }
        if (gameState == CHOOSE_MODE) {
            if (keycode == Input.Keys.BACK) {
                gameState = MAIN_MENU;
                render();
            }
        }
        if (gameState == CHOOSE_DIFFICULTY) {
            if (keycode == Input.Keys.BACK) {
                gameState = CHOOSE_MODE;
                render();
            }
        }
        if (gameState == IN_GAME_SINGLPLAYER) {
            if (keycode == Input.Keys.BACK) {
                previousState = gameState;
                gameState = PAUSED;
                render();
            }
        }
        if (gameState == TUTORIAL) {
            if (keycode == Input.Keys.BACK) {
                previousState = gameState;
                gameState = MAIN_MENU;
                render();
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        switch (gameState) {
            case MAIN_MENU:
                //if you touch top button (singleplayer)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y - 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y - CARD_WIDTH_PIXELS) {
                    gameState = CHOOSE_MODE;
                    render();
                }

                //if you touch middle button (tutorial)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y && screenY < ORIGIN_Y + CARD_WIDTH_PIXELS) {
                    gameState = TUTORIAL;
                    tutorialPosX = screenX;
                    render();
                }

                //if you touch bottom button (exit game)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y + 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y + 3 * CARD_WIDTH_PIXELS) {
                    Gdx.app.exit();
                }
                break;
            case CHOOSE_MODE:
                //if you touch top button (single player)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y - 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y - CARD_WIDTH_PIXELS) {
                    gameState = CHOOSE_DIFFICULTY;
                    game = new Game(atlas);
                    render();

                }

                //if you touch middle button (local multiplayer)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y && screenY < ORIGIN_Y + CARD_WIDTH_PIXELS) {
                    gameState = IN_GAME_LOCAL;
                    game = new Game(atlas);
                    render();
                }

                //if you touch bottom button (main menu)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y + 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y + 3 * CARD_WIDTH_PIXELS) {
                    gameState = MAIN_MENU;
                }
                break;
            case CHOOSE_DIFFICULTY:
                //if you touch top button (easy)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y - 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y - CARD_WIDTH_PIXELS) {
                    gameState = IN_GAME_SINGLPLAYER;
                    difficulty = 1;
                    game = new Game(atlas);
                    render();

                }

                //if you touch middle button (medium)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y && screenY < ORIGIN_Y + CARD_WIDTH_PIXELS) {
                    gameState = IN_GAME_SINGLPLAYER;
                    difficulty = 2;
                    game = new Game(atlas);
                    render();
                }

                //if you touch bottom button (hard)
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y + 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y + 3 * CARD_WIDTH_PIXELS) {
                    gameState = IN_GAME_SINGLPLAYER;
                    difficulty = 3;
                    game = new Game(atlas);
                    render();
                }
                break;
            case TUTORIAL:
                //goes to last seen page in the tutorial
                tutorialPosX = screenX;
                break;
            case IN_GAME_LOCAL:
                //if collect flag = true, do not let user play cards
                if (collectFlag == false) {
                    //if touching your own deck
                    for (int i = 0; i < game.players.size(); i++) {
                        if (game.players.get(i).isPressed(screenX, screenY, pointer, button, ORIGIN_X, ORIGIN_Y, CARD_WIDTH_PIXELS, CARD_HEIGHT_PIXELS)) {
                            if (game.players.get(i).cards.size() > 0) {
                                game.centerPile.cards.add(0, game.players.get(i).drawCard());
                                collectFlag = game.update(0, false);
                                collectTimer = System.currentTimeMillis();
                                render();
                            }
                        }
                    }
                }

                if (System.currentTimeMillis() - timer1 > slapTimer) {
                    timer1 = System.currentTimeMillis();
                    //if touching p1 slap icon, slap area slightly bigger than icon
                    if (screenX > ORIGIN_X - 1.8 * CARD_WIDTH_PIXELS && screenX < ORIGIN_X - .5 * CARD_WIDTH_PIXELS
                            && screenY > ORIGIN_Y + 1.2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y + 3.0 * CARD_WIDTH_PIXELS) {
                        System.out.println("P1: SLAP!");
                        collectFlag = game.update(1, collectFlag);
                        if(collectFlag == false){
                            if(game.players.get(0).cards.size() == 0) {
                                game.collectPile(game.players.get(1));
                                gameState = GAME_OVER;
                            }
                            if(game.players.get(1).cards.size() == 0) {
                                game.collectPile(game.players.get(0));
                                gameState = GAME_OVER;
                            }
                        }
                    }
                }

                if (System.currentTimeMillis() - timer2 > slapTimer) {
                    //if touching p2 slap icon, slap area slightly bigger than icon
                    timer2 = System.currentTimeMillis();
                    if (screenX > ORIGIN_X - 1.8 * CARD_WIDTH_PIXELS && screenX < ORIGIN_X - .5 * CARD_WIDTH_PIXELS
                            && screenY > ORIGIN_Y - 3.0 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y - 1.2 * CARD_WIDTH_PIXELS) {
                        System.out.println("P2: SLAP!");
                        collectFlag = game.update(2, collectFlag);
                        if(collectFlag == false){
                            if(game.players.get(0).cards.size() == 0) {
                                game.collectPile(game.players.get(1));
                                gameState = GAME_OVER;
                            }
                            if(game.players.get(1).cards.size() == 0) {
                                game.collectPile(game.players.get(0));
                                gameState = GAME_OVER;
                            }
                        }
                    }
                }

                if (game.numPlayers == 2) {
                    for (int j = 0; j < game.numPlayers; j++) {
                        if (game.players.get(j).cards.size() == 0) {
                            collectFlag = true;
                            collectTimer = System.currentTimeMillis();
                        }
                    }
                }
                break;

            case IN_GAME_SINGLPLAYER:
                // if it's your turn do standard local multiplayer things, single player take turn happens in render
                //if collect flag = true, do not let user play cards
                if (collectFlag == false) {
                    //if touching your own deck
                    if (game.players.get(0).isPressed(screenX, screenY, pointer, button, ORIGIN_X, ORIGIN_Y, CARD_WIDTH_PIXELS, CARD_HEIGHT_PIXELS)) {
                        if (game.players.get(0).cards.size() > 0) {
                            game.centerPile.cards.add(0, game.players.get(0).drawCard());
                            collectFlag = game.update(0, false);
                            collectTimer = System.currentTimeMillis();
                            AiSlapFlag = false;
                            render();
                        }
                    }
                }

                if(postSlapFlag == false) {
                    if (System.currentTimeMillis() - timer1 > slapTimer) {
                        timer1 = System.currentTimeMillis();
                        //if touching p1 slap icon, slap area slightly bigger than icon
                        if (screenX > ORIGIN_X - 1.8 * CARD_WIDTH_PIXELS && screenX < ORIGIN_X - .5 * CARD_WIDTH_PIXELS
                                && screenY > ORIGIN_Y + 1.2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y + 3.0 * CARD_WIDTH_PIXELS) {
                            System.out.println("P1: SLAP!");
                            collectFlag = game.update(1, collectFlag);
                            if (collectFlag)
                                AiSlapFlag = false;
                            if (collectFlag == false) {
                                if (game.players.get(0).cards.size() == 0) {
                                    game.collectPile(game.players.get(1));
                                    gameState = GAME_OVER;
                                }
                                if (game.players.get(1).cards.size() == 0) {
                                    game.collectPile(game.players.get(0));
                                    gameState = GAME_OVER;
                                }
                            }
                        }
                    }
                }

                if (game.numPlayers == 2) {
                    for (int j = 0; j < game.numPlayers; j++) {
                        if (game.players.get(j).cards.size() == 0) {
                            collectFlag = true;
                            collectTimer = System.currentTimeMillis();
                        }
                    }
                }
                break;

            case GAME_OVER:
                //if you touch bottom button (main_menu )
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y + 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y + 3 * CARD_WIDTH_PIXELS) {
                    gameState = MAIN_MENU;
                    resetGame();
                    game = new Game(atlas);
                    render();
                }
                break;

            case PAUSED:
                //return to tutorial
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y - 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y - CARD_WIDTH_PIXELS) {
                    if(previousState == TUTORIAL)
                        gameState = TUTORIAL;
                    else
                        gameState = IN_GAME_LOCAL;
                    render();
                }
                //go to main menu, reset tutorial count
                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y && screenY < ORIGIN_Y + CARD_WIDTH_PIXELS) {
                    gameState = MAIN_MENU;
                    if(previousState == TUTORIAL)
                        tutorialNumber = 0;
                    render();
                }

                if (screenX > ORIGIN_X - CARD_WIDTH_PIXELS && screenX < ORIGIN_X + CARD_WIDTH_PIXELS
                        && screenY > ORIGIN_Y + 2 * CARD_WIDTH_PIXELS && screenY < ORIGIN_Y + 3 * CARD_WIDTH_PIXELS) {
                    Gdx.app.exit();
                    render();
                }
                break;

        }

        return true;

    }

    public void resetGame(){
        game.animateFlag = false;
        game.animateCardY = 0;
        game.turn = 0;
        game.playerTurn = 0;
        game.royalCountDown = 0;
        collectFlag = false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(gameState == TUTORIAL){
            if(screenX - tutorialPosX > 100){
                if(tutorialNumber != 0)
                    tutorialNumber--;
            }
            else if (screenX - tutorialPosX < -100){

                if(tutorialNumber == 8){
                    tutorialNumber = 0;
                    gameState = MAIN_MENU;
                }
                else
                    tutorialNumber++;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

}