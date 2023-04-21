package sw.db.cards;

import sw.db.cards.common.models.PlayableCard;
import sw.db.game.Player;

import java.util.List;

public interface HasMoveToInPlay {
    List<PlayableCard> moveToInPlay(Class<?> c, Player player, int amount);
}
