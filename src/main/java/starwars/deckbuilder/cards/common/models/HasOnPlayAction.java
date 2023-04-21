package starwars.deckbuilder.cards.common.models;

import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public interface HasOnPlayAction {
    List<PendingAction> getActions();
}
