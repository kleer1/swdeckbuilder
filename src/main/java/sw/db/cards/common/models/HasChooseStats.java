package sw.db.cards.common.models;

public interface HasChooseStats extends HasOnPlayAction {
    void applyChoice(Stats stat);
}
