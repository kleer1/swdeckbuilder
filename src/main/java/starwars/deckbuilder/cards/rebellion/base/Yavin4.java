package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.StaticEffect;
import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasAtStartOfTurn;
import starwars.deckbuilder.cards.common.models.HasOnReveal;

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
