package starwars.deckbuilder.cards.common.models;

public interface HasAbility {
    boolean abilityActive();
    void applyAbility();
}
