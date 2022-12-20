package com.uno.server;

import java.util.ArrayList;
import com.uno.utils.TextIO;

public class Server {
    private ArrayList<Player> players;
    private DrawPile drawPile;
    private PlayPile playPile;
    private static final int startCards = 7;

    public Server(){
        drawPile = new DrawPile();
        playPile = null;
        players = new ArrayList<Player>();
    }

    public void start() {
        System.out.println("Game started!");
        addPlayers();
        drawPile.dealCards(players);
        playPile = new PlayPile(drawPile.drawCard());
    }

    public void addPlayers(){
        while(true) {
            System.out.println("Player name " + (players.size() + 1) + ": ");
            String name = TextIO.getln();
            if (name.equals("done") || players.size() >= 10) {
                if (players.size() < 2) {
                    System.out.println("You need at least 2 players to start the game!");
                    continue;
                }
                System.out.println("All players added, game started");
                return;
            }

            if (name.equals("")) {
                System.out.println("You need to enter a name!");
                continue;
            }

            players.add(new Player(name));
        }
    }

    public void chatBox(){}
    public Player showWinner(){
        return null;
    }
    public Player disconnectedPlayer(){
        return null;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
