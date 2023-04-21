package starwars.deckbuilder.cards.common;

import starwars.deckbuilder.cards.common.models.*;
import starwars.deckbuilder.game.*;

import java.util.List;

public class TempleInquisitor extends Unit implements HasOnPlayAction, HasChooseStats {
    Stats choice = null;

    public TempleInquisitor(int id, Faction faction, String title, List<Trait> traits, Player owner,
                            CardLocation location, List<? extends Card> cardList, Game game) {
        super(id, 0, 0, 0, 0, faction, title, traits, false, owner, location, cardList, game);
    }

    @Override
    public List<PendingAction> getActions() {
        return List.of(PendingAction.of(Action.ChooseStatBoost));
    }

    @Override
    public int getAttack() {
        if (choice != null && Stats.Attack == choice) return attack + 1;
        return super.getAttack();
    }

    @Override
    public int getResources() {
        if (choice != null && Stats.Resources == choice) return resources + 1;
        return super.getAttack();
    }

    @Override
    public int getForce() {
        if (choice != null && Stats.Force == choice) return force + 1;
        return super.getForce();
    }

    @Override
    public void moveToDiscard() {
        super.moveToDiscard();
        choice = null;
    }

    public void applyChoice(Stats statChoice) {
        choice = statChoice;
        if (choice == Stats.Resources) {
            owner.addResources(1);

        } else if (choice == Stats.Force) {
            owner.addForce(1);
        }
    }
}
