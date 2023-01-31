package com.uno.test;

import com.uno.server.ClientHandler;
import com.uno.server.Server;
import com.uno.server.uno.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GameTest {

    static Game game;
    static ArrayList<Player> players;
    static Lobby lobby;

    static Server server;



    /**
     * It creates a lobby, creates a server, creates two clients, and adds them to the lobby
     */
    @BeforeAll
    public static void beforeAll(){
        lobby = new Lobby();
        lobby.setSupportedFeatures("");
        players = new ArrayList<>();

        server = new Server();
        server.start();

        for(int i = 0; i < 2; i++) {
            try {
                Socket connection = new Socket("localhost", 3333);

                ClientHandler clientHandler = new ClientHandler(connection);
                clientHandler.setClientName("player" + i);
                clientHandler.getMessageHandler().setLobby(lobby);

                players.add(new Player(clientHandler));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * This function is called before each test case. It creates a new game object and starts the game
     */
    @BeforeEach
    public void beforeEach(){
        game = new Game(players, lobby);
        game.startGame();
    }

    /**
     * > This function tests that each player has 7 cards in their hand
     */
    @Test
    public void allPlayers7Cards(){
        for (Player player: game.getPlayers()) {
            Assertions.assertEquals(7, player.getHand().getCards().size());
        }

    }

    /**
     * "allCardsAreIn" checks if all the cards are in the draw pile
     */
    @Test
    public void allCardsAreIn(){
        //Uno exists of 108 cards, two hands (of 7 cards are dealt) and one is taken for the active card. so 93 cards should be in.
        Assertions.assertEquals(93, game.getDrawPile().getDeck().size());
    }

    /**
     * "Test if a player can play a regular card."
     *
     * The first thing we do is set the active card to a card with a number of 5 and a color of blue
     */
    @Test
    public void playRegularCards(){
        game.getPlayPile().setActiveCard(new Card(Card.cardType.NUMBER, Card.cardColor.BLUE, 5));

        Card wrongCard = new Card(Card.cardType.NUMBER, Card.cardColor.RED, 3);
        Card goodCardSameNumber = new Card(Card.cardType.NUMBER, Card.cardColor.GREEN, 5);
        Card goodCardSameColor = new Card(Card.cardType.NUMBER, Card.cardColor.GREEN,9);

        Player playerToPlay = game.getPlayers().get(0);
        playerToPlay.addCard(wrongCard);
        playerToPlay.addCard(goodCardSameNumber);
        playerToPlay.addCard(goodCardSameColor);

        //Play wrong card
        game.setActivePlayer(playerToPlay);
        game.playCard(wrongCard, playerToPlay.getClientHandler());
        //Card should not change
        Assertions.assertNotEquals(game.getPlayPile().getActiveCard().getColor(), wrongCard.getColor());
        Assertions.assertNotEquals(game.getPlayPile().getActiveCard().getNumber(), wrongCard.getNumber());

        //active player should change
        Assertions.assertNotEquals(playerToPlay.getClientHandler().getName(), game.getActivePlayer().getName());

        //Play correct card same number
        game.setActivePlayer(playerToPlay);
        game.playCard(goodCardSameNumber, playerToPlay.getClientHandler());

        //Card should change
        Assertions.assertEquals(game.getPlayPile().getActiveCard().getColor(), goodCardSameNumber.getColor());
        Assertions.assertEquals(game.getPlayPile().getActiveCard().getNumber(), goodCardSameNumber.getNumber());

        //Active player should change.
        Assertions.assertNotEquals(playerToPlay.getClientHandler().getName(), game.getActivePlayer().getName());

        //Play correct card same color
        game.setActivePlayer(playerToPlay);
        game.playCard(goodCardSameColor, playerToPlay.getClientHandler());

        //Card should change
        Assertions.assertEquals(game.getPlayPile().getActiveCard().getColor(), goodCardSameColor.getColor());
        Assertions.assertEquals(game.getPlayPile().getActiveCard().getNumber(), goodCardSameColor.getNumber());

        //Active player should change.
        Assertions.assertNotEquals(playerToPlay.getClientHandler().getName(), game.getActivePlayer().getName());
    }

    /**
     * Test that a player cannot play a wild card while having a card of the same color as the active card in their hand.
     */
    @Test
    public void testPlayingWildWhileHavingColorCardInYourHand(){
        Card wild = new Card(Card.cardType.WILD, Card.cardColor.GREEN, 50);
        Card greenCard = new Card(Card.cardType.NUMBER, Card.cardColor.GREEN, 4);
        Card activeCard = new Card(Card.cardType.NUMBER, Card.cardColor.GREEN, 5);

        game.getPlayPile().setActiveCard(activeCard);

        Player playerToPlay = game.getPlayers().get(0);

        //Wrong play, user has card of same color as active card in hand
        playerToPlay.addCard(greenCard);
        game.playCard(wild, playerToPlay.getClientHandler());
        Assertions.assertNotEquals(game.getPlayPile().getActiveCard().getType(), wild.getType());
    }

    /**
     * Test if the player has 8 cards after drawing a card.
     */
    @Test
    public void drawCard(){
        Player playerToPlay = game.getPlayers().get(0);
        game.setActivePlayer(playerToPlay);
        game.drawCard(playerToPlay.getClientHandler());

        Assertions.assertEquals(8, playerToPlay.getHand().getCards().size());
    }

    /**
     * This function tests the calculatePoints() function in the Game class
     */
    @Test
    public void countPoints(){
        Player playerOne = game.getPlayers().get(0);
        Player playerTwo = game.getPlayers().get(1);

        playerOne.getHand().setHand(new ArrayList<>());

        playerTwo.getHand().setHand(new ArrayList<>());
        //wild --> 50, RED 4 --> 4, skip --> 20: total 74
        playerTwo.getHand().getCards().add(new Card(Card.cardType.WILD, Card.cardColor.BLACK, 0));
        playerTwo.getHand().getCards().add(new Card(Card.cardType.NUMBER, Card.cardColor.RED, 4));
        playerTwo.getHand().getCards().add(new Card(Card.cardType.SKIP, Card.cardColor.BLUE, 0));

        Assertions.assertEquals(74, game.calculatePoints());
    }

    /**
     * > This function checks if the round is over by checking if the active player has no cards left in their hand
     */
    @Test
    public void checkRoundOver(){
        Player playerRoundWinner = game.getPlayers().get(0);
        game.setActivePlayer(playerRoundWinner);

        Assertions.assertFalse(game.checkRoundEnd());

        playerRoundWinner.getHand().setHand(new ArrayList<>());

        Assertions.assertTrue(game.checkRoundEnd());
    }

    /**
     * "If the active player has a score of 501, the game is over."
     *
     * The first line of the function creates a new Player object and assigns it to the variable playerGameWinner. The
     * second line sets the active player to the playerGameWinner. The third line checks that the game is not over. The
     * fourth line sets the score of the playerGameWinner to 501. The fifth line checks that the game is over
     */
    @Test
    public void checkGameOver(){
        Player playerGameWinner = game.getPlayers().get(0);
        game.setActivePlayer(playerGameWinner);

        Assertions.assertFalse(game.checkGameEnd());

        playerGameWinner.setScore(501);

        Assertions.assertTrue(game.checkGameEnd());
    }
}
