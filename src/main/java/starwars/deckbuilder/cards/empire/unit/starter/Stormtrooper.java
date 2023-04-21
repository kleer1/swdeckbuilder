package starwars.deckbuilder.cards.empire.unit.starter;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.cards.common.models.Unit;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class Stormtrooper extends Unit {

    public Stormtrooper(int id, Game game) {
        super(id, 0, 2, 0, 0, Faction.empire, "Stormtrooper", List.of(Trait.trooper),
                false, game.getEmpire(), CardLocation.EmpireDeck, game.getEmpire().getDeck(), game);
    }
}
