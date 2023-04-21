package starwars.deckbuilder.cards.base;

import starwars.deckbuilder.cards.HasGame;
import starwars.deckbuilder.cards.HasId;
import starwars.deckbuilder.cards.HasMoveToInPlay;
import starwars.deckbuilder.cards.HasPlayer;
import starwars.deckbuilder.cards.base.HasBase;

public interface AvailableBaseCard extends HasGame, HasId, HasPlayer, HasBase, HasMoveToInPlay {
}
