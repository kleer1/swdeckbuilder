package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

public class Lothal extends Base {

    public Lothal(int id, Game game) {
        super(id, Faction.empire, "Lothal", CardLocation.EmpireCurrentBase, null,
                game, game.getEmpire(), 8);
        game.getEmpire().setCurrentBase(this);
    }
}
