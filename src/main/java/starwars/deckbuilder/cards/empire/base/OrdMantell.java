package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.StaticEffect;
import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasAtStartOfTurn;
import starwars.deckbuilder.cards.common.models.HasOnReveal;

public class OrdMantell extends Base implements HasOnReveal, HasAtStartOfTurn {

    public OrdMantell(int id, Game game) {
        super(id, Faction.empire, "Ord Mantell", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 14);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.CanBountyOneNeutral);
    }

    @Override
    public void applyOnReveal() {
        game.getStaticEffects().add(StaticEffect.CanBountyOneNeutral);
    }
}
