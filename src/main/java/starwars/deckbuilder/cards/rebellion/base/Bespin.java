package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.StaticEffect;
import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasAtStartOfTurn;
import starwars.deckbuilder.cards.common.models.HasOnReveal;

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
