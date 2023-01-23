package com.uno.server;

import java.util.ArrayList;
import java.util.Collections;

import com.uno.server.uno.Card;
import com.uno.server.uno.DrawPile;
import com.uno.server.uno.PlayPile;
import com.uno.server.uno.Player;
import com.uno.utils.TextIO;

public class ServerOld {
    private ArrayList<Player> players;
    private DrawPile drawPile;
    private PlayPile playPile;

    public ServerOld(){
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

            //players.add(new Player(name));
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
                    if (input.contains("play")) {

                        int cardIndex = tryParseInt(input, -1);

                        if (cardIndex < 1 || cardIndex > player.getHand().getHandSize()) {
                            System.out.println("Invalid card index!");
                            continue;
                        }
                        Card card = player.getHand().getCards().get(cardIndex - 1);
                        if (playPile.playCard(card, player.getHand())) {
                            if(card.getType() == Card.cardType.SKIP){
                                skip = true;
                            }

                            if(card.getType() == Card.cardType.REVERSE){
                                reverse = true;
                                Collections.reverse(players);
                            }
                            if(card.getType() == Card.cardType.DRAW_TWO){
                                if(players.indexOf(player) + 1 == players.size()) {
                                    forced2Draw(2, players.get(0));
                                }else {
                                    forced2Draw(2, players.get(players.indexOf(player) + 1));
                                }
                                skip = true;
                            }
                            if(card.getType() == Card.cardType.WILD_DRAW_FOUR){
                                if(players.indexOf(player) + 1 == players.size()) {
                                    forced4Draw(4, players.get(0));
                                }else {
                                    forced4Draw(4, players.get(players.indexOf(player) + 1));
                                }
                                skip = true;
                            }

                            validMove = true;
                            player.getHand().removeCard(card);
                            if (player.getHand().getHandSize() == 0) {
                                player.setScore(calculateScore(player.getScore()));
                                restart();
                                if (player.getScore() >= 500) {
                                    System.out.println(player.getName() + " won the game!");
                                    return;
                                }
                            }
                        } else {
                            System.out.println("Invalid card!");
                        }
                    } else if (input.equals("draw")) {
                        validMove = true;
                        drawCardPlayer(player);
                    } else {
                        System.out.println("Invalid input!");
                    }
                }
            }
        }
    }

    public void drawCardPlayer(Player player){
        if(drawPile.getDeck().size() == 0){
            System.out.println("No more cards in the draw pile!");
            drawPile.setDeck(playPile.getDiscardPile());
            playPile.clearDiscardPile();
        }
        player.getHand().addCard(drawPile.drawCard());
    }

    public void forced2Draw(int amount, Player player){
        boolean progressiveUno = false;
        if (progressiveUno){
            for (Card card : player.getHand().getCards()){
                if (card.getType() == Card.cardType.WILD || card.getType() == Card.cardType.WILD_DRAW_FOUR){
                    player.getHand().removeCard(card);
                    playPile.playCard(card, null);
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
                    playPile.playCard(card, null);
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
    public int calculateScore(int score){
        for (Player player : players) {
            for (Card card : player.getHand().getCards()) {
                if (card.getType() == Card.cardType.NUMBER) {
                    score += card.getNumber();
                }
                if (card.getType() == Card.cardType.DRAW_TWO) {
                    score += 20;
                }
                if (card.getType() == Card.cardType.REVERSE) {
                    score += 20;
                }
                if (card.getType() == Card.cardType.SKIP) {
                    score += 20;
                }
                if (card.getType() == Card.cardType.WILD) {
                    score += 50;
                }
                if (card.getType() == Card.cardType.WILD_DRAW_FOUR) {
                    score += 50;
                }
            }
        }
        return score;
    }
    public void chatBox(){}
    public Player showWinner(){
        return null;
    }
    public Player disconnectedPlayer(){
        return null;
    }

    public void restart(){
        drawPile = new DrawPile();

        for (Player player : players){
            player.getHand().clearHand();
        }

        drawPile.dealCards(players);

        playPile = new PlayPile(drawPile.drawCard());
    }

    public final static void clearConsole()
    {
        for(int i = 0; i < 50; ++i) System.out.println();
    }

    public int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value.substring(5));
        } catch (Exception e) {
            return defaultVal;
        }
    }


    public ArrayList<Player> getPlayers(){
        return players;
    }

    public DrawPile getDrawPile(){
        return drawPile;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    public void setPlayPile(PlayPile playPile) {
        this.playPile = playPile;
    }


}
