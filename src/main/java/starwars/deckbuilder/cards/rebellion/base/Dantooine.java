package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

public class Dantooine extends Base {

    public Dantooine(int id, Game game) {
        super(id, Faction.rebellion, "Dantooine", CardLocation.RebelCurrentBase, null, game,
                game.getRebel(), 8);
        game.getRebel().setCurrentBase(this);
    }
}
