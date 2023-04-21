package sw.db.cards.base;

import sw.db.cards.HasGame;
import sw.db.cards.HasId;
import sw.db.cards.HasMoveToInPlay;
import sw.db.cards.HasPlayer;

public interface AvailableBaseCard extends HasGame, HasId, HasPlayer, HasBase, HasMoveToInPlay {
}
