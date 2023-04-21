package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasOnReveal;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

public class Kessel extends Base implements HasOnReveal {

    public Kessel(int id,Game game) {
        super(id, Faction.empire, "Kessel", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 12);
    }

    @Override
    public void applyOnReveal() {
        addExilePendingAction(owner, 3);
    }
}
