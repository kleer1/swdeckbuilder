package sw.db.cards.common.models;

public interface HasReturnToHandAbility extends HasAbility {
    boolean isValidTarget(PlayableCard card);
}
