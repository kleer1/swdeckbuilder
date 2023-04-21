package sw.db.cards.common.models;

public interface HasChooseResourceOrRepair extends HasAbility {
    void applyChoice(ResourceOrRepair choice);
}
