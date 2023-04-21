package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.*;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class TieInterceptor extends EmpireGalaxyUnit implements IsTargetable, HasAbility {
    public TieInterceptor(int id,Game game) {
        super(id, 3, 3, 0, 0, "Tie Interceptor", List.of(Trait.fighter), false, game);
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
            owner.drawCards(1);
        } else if (card.getFaction() == Faction.rebellion) {
            card.moveToGalaxyDiscard();
            game.forgetTopCardOfDeck();
        }
    }

    @Override
    public int getTargetValue() {
        return 3;
    }

    @Override
    public void applyReward() {
        game.getRebel().addResources(3);
    }
}
