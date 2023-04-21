package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.*;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class ScoutTrooper extends EmpireGalaxyUnit implements HasAbility, IsTargetable {
    public ScoutTrooper(int id, Game game) {
        super(id, 2, 0, 2, 0, "Scout Trooper", List.of(Trait.trooper), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !game.getGalaxyDeck().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        PlayableCard card = game.getGalaxyDeck().get(0);
        game.revealTopCardOfDeck();
        if (card.getFaction() == Faction.empire) {
            owner.addForce(1);
        } else if (card.getFaction() == Faction.rebellion) {
            card.moveToGalaxyDiscard();
            game.forgetTopCardOfDeck();
        }
    }

    @Override
    public int getTargetValue() {
        return 2;
    }

    @Override
    public void applyReward() {
        addExilePendingAction(game.getRebel(), 1);
    }
}
