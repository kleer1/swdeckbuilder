package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

public class Bespin extends Base implements HasOnReveal, HasAtStartOfTurn {
    public Bespin(int id, Game game) {
        super(id, Faction.rebellion, "Bespin", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 14);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.DrawOnFirstNeutralCard);
    }

    @Override
    public void applyOnReveal() {
        game.getStaticEffects().add(StaticEffect.DrawOnFirstNeutralCard);
    }
}
