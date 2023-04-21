package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasAtStartOfTurn;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

public class Coruscant extends Base implements HasAtStartOfTurn {
    public Coruscant(int id, Game game) {
        super(id, Faction.empire, "Coruscant", CardLocation.EmpireAvailableBases,
                game.getEmpire().getAvailableBases(), game, game.getEmpire(), 16);
    }

    @Override
    public void applyAtStartOfTurn() {
        if (game.getGalaxyDeck().size() >= 2) {
            game.getKnowsTopCardOfDeck().put(Faction.empire, 2);
            game.getPendingActions().add(PendingAction.of(Action.GalacticRule));
        }
    }
}
