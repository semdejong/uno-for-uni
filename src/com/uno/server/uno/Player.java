package com.uno.server.uno;

import com.uno.server.ClientHandler;
import com.uno.server.uno.Card;
import com.uno.server.uno.Hand;

public class Player {

    private Hand hand;
    private int score;
    private ClientHandler clientHandler;
    private Lobby lobby;
    private Card lastDrawnCard;
    

    // A constructor for the Player class. It takes a ClientHandler as a parameter and sets the clientHandler variable to
    // the parameter. It also creates a new Hand object and sets the hand variable to the new Hand object.
    public Player(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
        this.hand = new Hand();
    }


    /**
     * This function returns the hand of the player.
     *
     * @return The hand object.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * This function sets the hand of the player to the hand passed in.
     *
     * @param hand The hand that the player is holding.
     */
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    /**
     * This function adds a card to the hand.
     *
     * @param card The card to add to the hand.
     */
    public void addCard(Card card){
        hand.addCard(card);
    }

    /**
     * It draws a card from the draw pile and adds it to the player's hand
     *
     * @param amountOfCards the amount of cards the player wants to draw
     * @param game The game object
     */
    public void drawCardWithClientSync(int amountOfCards, Game game){
        for(int i =0; i < amountOfCards; i++){
            if(game.getDrawPile().getDeck().size() == 0){
                if (game.getPlayPile().getDiscardPile().size() == 0){
//                    game.nextPlayer();
//                    lobby.broadCastLobby("ActivePlayer|" + game.getActivePlayer().getName());

                    Player playerLeastCards = null;

                    for(Player player : lobby.getGame().getPlayers()){
                        if(playerLeastCards ==  null){
                            playerLeastCards = player;
                        }
                        if(player.getHand().getCards().size() < playerLeastCards.getHand().getCards().size()){
                            playerLeastCards = player;
                        }
                    }

                    lobby.broadCastLobby("RoundOver|"+calculatePoints()+"|"+playerLeastCards.getName());
                    playerLeastCards.setScore(score + calculatePoints());

                    if(lobby.getGame().checkGameEnd()){
                        return;
                    }

                    lobby.getGame().startGame();

                    return;
                }
                game.getDrawPile().setDeck(game.getPlayPile().getDiscardPile());
                game.getPlayPile().clearDiscardPile();
            }
            Card cardDrawn = game.getDrawPile().drawCard();
            this.getHand().addCardSyncWithClient(cardDrawn, clientHandler);
            this.lastDrawnCard = cardDrawn;
        }
    }

    public int calculatePoints(){
        int points = 0;
        for (Player player : lobby.getPlayers()){
            for (Card card : player.getHand().getCards()){
                switch (card.getType()){
                    case NUMBER:
                        points += card.getNumber();
                        break;
                    case SKIP:
                        points += 20;
                        break;
                    case REVERSE:
                        points += 20;
                        break;
                    case DRAW_TWO:
                        points += 20;
                        break;
                    case WILD:
                        points += 50;
                        break;
                    case WILD_DRAW_FOUR:
                        points += 50;
                        break;
                }
            }
        }
        return points;
    }

    /**
     * Remove a card from the hand.
     *
     * @param card The card to remove from the hand.
     */
    public void removeCard(Card card){
        hand.removeCard(card);
    }

    /**
     * This function returns the size of the hand
     *
     * @return The size of the hand.
     */
    public int getHandSize(){
        return hand.getHandSize();
    }

    /**
     * This function returns the clientHandler object.
     *
     * @return The clientHandler object.
     */
    public ClientHandler getClientHandler(){
        return clientHandler;
    }

    /**
     * This function returns a string that contains the name of the player and the cards in their hand.
     *
     * @return The player's name and the cards in their hand.
     */
    public String toString(){
        return "Player: " + this.clientHandler.getClientName() + " " + this.hand;
    }

    /**
     * This function returns the score.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }


    /**
     * This function sets the score of the player to the score passed in as a parameter.
     *
     * @param score The score of the player.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This function sets the lastDrawnCard variable to the value of the parameter lastDrawnCard
     *
     * @param lastDrawnCard The last card that was drawn from the deck.
     */
    public void setLastDrawnCard(Card lastDrawnCard){
        this.lastDrawnCard = lastDrawnCard;
    }

    /**
     * > This function returns the last card drawn from the deck
     *
     * @return The last card drawn from the deck.
     */
    public Card getLastDrawnCard(){
        return lastDrawnCard;
    }

    /**
     * This function returns the name of the client.
     *
     * @return The name of the client.
     */
    public String getName(){
        return clientHandler.getClientName();
    }

    /**
     * This function returns the lobby.
     *
     * @return The lobby object.
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * This function sets the lobby of the player to the lobby that is passed in.
     *
     * @param lobby The lobby that the player is in.
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
