package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasOnReveal;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

public class Alderaan extends Base implements HasOnReveal {
    public Alderaan(int id, Game game) {
        super(id, Faction.rebellion, "Alderaan", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 14);
    }

    @Override
    public void applyOnReveal() {
        owner.addForce(4);
    }
}
