package com.uno.test;

import com.uno.server.ClientHandler;
import com.uno.server.Server;
import com.uno.server.uno.Card;
import com.uno.server.uno.Game;
import com.uno.server.uno.Lobby;
import com.uno.server.uno.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class PlayerTest {

    static Player player;
    static ArrayList<Player> players;
    static Lobby lobby;

    static Server server;

    static Game game;

    /**
     * It creates a lobby, creates a server, creates two clients, adds the first client to the lobby, and sets the lobby
     * for the first client
     */
    @BeforeAll
    public static void beforeAll(){
        lobby = new Lobby();
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

        player = players.get(0);
        lobby.addPlayer(player);
        player.setLobby(lobby);
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
     * This function tests the calculatePoints() function in the Player class
     */
    @Test
    public void calculatePoints(){

        player.getHand().setHand(new ArrayList<>());
        //wild --> 50, RED 4 --> 4, skip --> 20, reverse --> 20: total 94
        player.getHand().getCards().add(new Card(Card.cardType.WILD, Card.cardColor.BLACK, 0));
        player.getHand().getCards().add(new Card(Card.cardType.NUMBER, Card.cardColor.RED, 4));
        player.getHand().getCards().add(new Card(Card.cardType.SKIP, Card.cardColor.BLUE, 0));
        player.getHand().getCards().add(new Card(Card.cardType.REVERSE, Card.cardColor.GREEN, 0));

        Assertions.assertEquals(94, game.calculatePoints());
    }

    /**
     * This function tests that the player draws two cards from the deck and that the player's hand has two more cards than
     * it did before.
     */
    @Test
    public void dealCards(){
        player.drawCardWithClientSync(2, game);
        Assertions.assertEquals(9, player.getHand().getCards().size());
    }

    /**
     * This function tests that when the draw pile is empty, the discard pile is shuffled and the cards are dealt to the
     * player
     */
    @Test
    public void dealCardsDrawPileEmpty(){
        for(int i =0; i <  game.getDrawPile().getDeck().size(); i++){
            Card cardDrawn = game.getDrawPile().drawCard();
            game.getPlayPile().addCardToDiscardPile(cardDrawn);
        }

        //All cards in discard pile.
        player.drawCardWithClientSync(2, game);
        Assertions.assertEquals(9, player.getHand().getCards().size());
    }


}
