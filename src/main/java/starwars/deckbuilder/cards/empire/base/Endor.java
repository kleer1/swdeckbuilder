package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.StaticEffect;
import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasAtStartOfTurn;
import starwars.deckbuilder.cards.common.models.HasOnReveal;

public class Endor extends Base implements HasAtStartOfTurn, HasOnReveal {

    public Endor(int id, Game game) {
        super(id, Faction.empire, "Endor", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 16);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.EndorBonus);
    }

    @Override
    public void applyOnReveal() {
        game.getStaticEffects().add(StaticEffect.EndorBonus);
    }
}
