package com.uno.client.Computers;

import com.uno.client.model.Card;

public interface AI {
    public void determineMove();

    public void determineDrawPlay(Card card);
}
