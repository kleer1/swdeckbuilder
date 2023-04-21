package starwars.deckbuilder.cards.common.models;

public interface HasChooseResourceOrRepair extends HasAbility {
    void applyChoice(ResourceOrRepair choice);
}
