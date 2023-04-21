package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

public class Yavin4 extends Base implements HasAtStartOfTurn, HasOnReveal {
    public Yavin4(int id, Game game) {
        super(id, Faction.rebellion, "Yavin 4", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 16);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.Yavin4Effect);
    }

    @Override
    public void applyOnReveal() {
        game.getStaticEffects().add(StaticEffect.Yavin4Effect);
    }
}
