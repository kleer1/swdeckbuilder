package sw.db.cards.playablecard;

import sw.db.cards.HasGame;
import sw.db.cards.HasId;
import sw.db.cards.HasMoveToInPlay;
import sw.db.cards.HasPlayer;

public interface BasePlayableCard extends HasCard, HasGame, HasMoveToInPlay, HasId, HasPlayer {
}
