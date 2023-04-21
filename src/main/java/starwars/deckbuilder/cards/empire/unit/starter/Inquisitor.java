package starwars.deckbuilder.cards.empire.unit.starter;

import starwars.deckbuilder.cards.common.TempleInquisitor;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class Inquisitor extends TempleInquisitor {

    public Inquisitor(int id, Game game) {
        super(id,  Faction.empire, "Inquisitor", List.of(),game.getEmpire(), CardLocation.EmpireDeck, game.getEmpire().getDeck(), game);
    }
}
