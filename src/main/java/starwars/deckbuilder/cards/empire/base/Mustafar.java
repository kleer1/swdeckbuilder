package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasOnReveal;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

public class Mustafar extends Base implements HasOnReveal {

    public Mustafar(int id, Game game) {
        super(id, Faction.empire, "Mustafar", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 14);
    }

    @Override
    public void applyOnReveal() {
        game.getEmpire().addForce(4);
    }
}
