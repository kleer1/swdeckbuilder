package starwars.deckbuilder.cards;

import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Player;

import java.util.List;

public interface HasMoveToInPlay {
    List<PlayableCard> moveToInPlay(Class<?> c, Player player, int amount);
}
