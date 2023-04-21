package starwars.deckbuilder.cards.common.models;

import starwars.deckbuilder.game.Game;

public interface IsTargetable {
    int getTargetValue();
    void applyReward();
}
