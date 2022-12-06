package com.uno.server;

import java.util.ArrayList;

public class Server {
    private ArrayList<Player> players = new ArrayList<>();
    private DrawPile drawPile;
    private PlayPile playPile;
    private static final int startCards = 7;

    public Server(){
        drawPile = new DrawPile();
        for (Player i : players){
            for (int i1 = 0; i1<startCards; i1++){
                i.getHand().addCard(drawPile.drawCard());
            }
        }
        playPile = new PlayPile(drawPile.drawCard());
    }
    public void chatBox(){}
    public Player showWinner(){
        return null;
    }
    public Player disconnectedPlayer(){
        return null;
    }
}
