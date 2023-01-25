//package com.uno.test;
//
//import com.uno.server.*;
//import com.uno.server.uno.Card;
//import com.uno.server.uno.DrawPile;
//import com.uno.server.uno.PlayPile;
//import com.uno.server.uno.Player;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ServerTest {
//    ServerOld server;
//    ArrayList<Player> players;
//
//    DrawPile drawPile;
//
//    PlayPile playPile;
//
//    Card cardStart;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        server = new ServerOld();
//
//        players = new ArrayList<Player>();
//
//        //players.add(new Player("Player 1"));
//        //players.add(new Player("Player 2"));
//        //players.add(new Player("Player 3"));
//        //players.add(new Player("Player 4"));
//
//        server.setPlayers(players);
//
//        drawPile = new DrawPile();
//
//        server.setDrawPile(drawPile);
//
//        drawPile.dealCards(players);
//
//        cardStart = drawPile.drawCard();
//
//        playPile = new PlayPile(cardStart);
//
//        server.setPlayPile(playPile);
//    }
//
//    @Test
//    public void testGameStart(){
//        assertTrue(server.getPlayers().size() == 4);
//        assertTrue(server.getDrawPile().getDeck().size() == 79);
//    }
//
//    @Test
//    public void drawCard(){
//        assertTrue(server.getDrawPile().getDeck().size() == 79);
//        server.drawCardPlayer(players.get(0));
//        assertTrue(server.getDrawPile().getDeck().size() == 78);
//        assertTrue(server.getPlayers().get(0).getHand().getHandSize() == 8);
//    }
//
////    @Test
////    public void playCard(){
////        boolean sameColor = false;
////        int indexOfCard = 0;
////        while(sameColor){
////            for (Card card: server.getPlayers().get(0).getHand().getCards()){
////                if (card.getColor() == cardStart.getColor()){
////                    indexOfCard = server.getPlayers().get(0).getHand().getCards().indexOf(card);
////                    sameColor = true;
////                    break;
////                }
////            }
////
////            if (!sameColor){
////                server.drawCardPlayer(players.get(0));
////            }
////        }
////
////        server.playCardPlayer(players.get(0), indexOfCard);
////    }
//}
