package com.uno.client.Computers;

import com.uno.client.model.Card;

public interface AI {
    void determineMove();

    void determineDrawPlay(Card card);
}
