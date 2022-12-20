package com.uno.server;

import java.util.ArrayList;
import java.util.Collections;

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
        runGame();
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

    public void runGame(){
        System.out.println("Game started!");
        boolean skip = false;
        boolean reverse = false;
        Player currentPlayer = players.get(0);
        while(true){
            for(Player player : players){
                if (skip){
                    skip = false;
                    continue;
                }

                if(reverse){
                    if(currentPlayer.equals(player)){
                        reverse = false;
                    }
                    continue;
                }

                currentPlayer = player;
                boolean validMove = false;
                while(!validMove){
                    clearConsole();
                    System.out.println("It's " + player.getName() + "'s turn");
                    System.out.println("Your hand: \n" + player.getHand().toString());
                    System.out.println("Active card: " + playPile.getActiveCard().toString());
                    System.out.println("What would you like to do? (play/draw)");
                    String input = TextIO.getln();
                    if (input.equals("play")) {
                        System.out.println("Which card would you like to play? (enter the number)");
                        int cardIndex = TextIO.getlnInt();
                        if (cardIndex < 1 || cardIndex > player.getHand().getHandSize()) {
                            System.out.println("Invalid card index!");
                            continue;
                        }
                        Card card = player.getHand().getCards().get(cardIndex - 1);
                        if (playPile.playCard(card)) {
                            if(card.getType() == Card.cardType.SKIP){
                                skip = true;
                            }

                            if(card.getType() == Card.cardType.REVERSE){
                                reverse = true;
                                Collections.reverse(players);
                            }
                            if(card.getType() == Card.cardType.DRAW_TWO){
                                forced2Draw(2, players.get(players.indexOf(player) + 1));
                                skip = true;
                            }
                            if(card.getType() == Card.cardType.WILD_DRAW_FOUR){
                                forced4Draw(4, players.get(players.indexOf(player) + 1));
                                skip = true;
                            }

                            validMove = true;
                            player.getHand().removeCard(card);
                            if (player.getHand().getHandSize() == 0) {
                                System.out.println("You won!");
                                return;
                            }
                        } else {
                            System.out.println("Invalid card!");
                        }
                    } else if (input.equals("draw")) {
                        validMove = true;
                        if(drawPile.getDeck().size() == 0){
                            System.out.println("No more cards in the draw pile!");
                            drawPile.setDeck(playPile.getDiscardPile());
                            playPile.clearDiscardPile();
                        }
                        player.getHand().addCard(drawPile.drawCard());
                    } else {
                        System.out.println("Invalid input!");
                    }
                }
            }
        }
    }
    public void forced2Draw(int amount, Player player){
        boolean progressiveUno = false;
        if (progressiveUno){
            for (Card card : player.getHand().getCards()){
                if (card.getType() == Card.cardType.WILD || card.getType() == Card.cardType.WILD_DRAW_FOUR){
                    player.getHand().removeCard(card);
                    playPile.playCard(card);
                    if (card.getType() == Card.cardType.DRAW_TWO){
                        forced2Draw(amount + 2, players.get(players.indexOf(player) + 1));
                    }
                }
            }
        }
        for(int i = 0; i < amount; i++){
            player.getHand().addCard(drawPile.drawCard());
        }
    }
    public void forced4Draw(int amount, Player player){
        boolean progressiveUno = false;
        if (progressiveUno){
            for (Card card : player.getHand().getCards()){
                if (card.getType() == Card.cardType.WILD || card.getType() == Card.cardType.WILD_DRAW_FOUR){
                    player.getHand().removeCard(card);
                    playPile.playCard(card);
                    if (card.getType() == Card.cardType.WILD_DRAW_FOUR){
                        forced4Draw(amount + 4, players.get(players.indexOf(player) + 1));
                    }
                }
            }
        }
        for(int i = 0; i < amount; i++){
            player.getHand().addCard(drawPile.drawCard());
        }
    }
    public void chatBox(){}
    public Player showWinner(){
        return null;
    }
    public Player disconnectedPlayer(){
        return null;
    }

    public final static void clearConsole()
    {
        for(int i = 0; i < 50; ++i) System.out.println();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
