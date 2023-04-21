package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

public class Sullust extends Base implements HasOnReveal, HasAtStartOfTurn {
    public Sullust(int id, Game game) {
        super(id, Faction.rebellion, "Sullust", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 16);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.BuyNextToTopOfDeck);
    }

    @Override
    public void applyOnReveal() {
        game.getStaticEffects().add(StaticEffect.BuyNextToTopOfDeck);
    }
}
