package starwars.deckbuilder.cards.common.models;

public interface HasReturnToHandAbility extends HasAbility {
    boolean isValidTarget(PlayableCard card);
}
