package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasOnReveal;
import starwars.deckbuilder.game.*;

public class Dagobah extends Base implements HasOnReveal {
    public Dagobah(int id, Game game) {
        super(id, Faction.rebellion, "Dagobah", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 12);
    }

    @Override
    public void applyOnReveal() {
        addExilePendingAction(owner, 3);
    }
}
