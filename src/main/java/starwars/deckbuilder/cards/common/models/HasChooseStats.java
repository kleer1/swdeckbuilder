package starwars.deckbuilder.cards.common.models;

public interface HasChooseStats extends HasOnPlayAction {
    void applyChoice(Stats stat);
}
