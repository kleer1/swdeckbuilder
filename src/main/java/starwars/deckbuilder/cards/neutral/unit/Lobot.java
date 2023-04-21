package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.HasChooseStats;
import starwars.deckbuilder.cards.common.models.Stats;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public class Lobot extends NeutralGalaxyUnit implements HasChooseStats {
    private Stats choice;
    public Lobot(int id, Game game) {
        super(id, 3, 0, 0, 0, "Lobot", List.of(Trait.officer), true, game);
        choice = null;
    }

    @Override
    public int getAttack() {
        return super.getAttack() + (choice == Stats.Attack ? 2 : 0);
    }

    @Override
    public int getResources() {
        return super.getResources() + (choice == Stats.Resources ? 2 : 0);
    }

    @Override
    public int getForce() {
        return super.getForce() + (choice == Stats.Force ? 2 : 0);
    }

    @Override
    public void moveToDiscard() {
        super.moveToDiscard();
        choice = null;
    }

    @Override
    public void applyChoice(Stats stat) {
        choice = stat;
        if (choice == Stats.Resources) {
            owner.addResources(2);
        } else if (choice == Stats.Force) {
            owner.addForce(2);
        }
    }

    @Override
    public List<PendingAction> getActions() {
        return List.of(PendingAction.of(Action.ChooseStatBoost));
    }
}
