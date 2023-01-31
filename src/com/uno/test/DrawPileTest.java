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

public class DrawPileTest {


    static Game game;
    static ArrayList<Player> players;
    static Lobby lobby;

    static Server server;

    static DrawPile drawPile;



    /**
     * It creates a new lobby, a new server, and two new clients
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
    }

    /**
     * This function creates a new game object, and sets the draw pile to a new draw pile object
     */
    @BeforeEach
    public void beforeEach(){
        game = new Game(players, lobby);
        game.setDrawPile(new DrawPile());
        drawPile = game.getDrawPile();
    }

    /**
     * This function checks if the draw pile has all 108 cards
     */
    @Test
    public void checkIfDrawPileHasAllCards(){
        Assertions.assertEquals(108, drawPile.getDeck().size());
    }


    @Test
    public void checkDrawCard(){
        Card card = drawPile.drawCard();

        for(Card cardInDrawPile: drawPile.getDeck()){
            if(card.equals(cardInDrawPile)){
                Assertions.assertTrue(false);
            }
        }

        Assertions.assertTrue(true);
    }


    @Test
    public void dealCards(){
        drawPile.dealCards(players);

        Assertions.assertEquals(7, players.get(0).getHand().getCards().size());
        Assertions.assertEquals(7, players.get(1).getHand().getCards().size());

        Assertions.assertEquals(94, drawPile.getDeck().size());
    }
}
