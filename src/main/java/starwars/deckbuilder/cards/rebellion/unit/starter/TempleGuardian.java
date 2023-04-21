package starwars.deckbuilder.cards.rebellion.unit.starter;

import starwars.deckbuilder.cards.common.TempleInquisitor;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class TempleGuardian extends TempleInquisitor {

    public TempleGuardian(int id, Game game) {
        super(id, Faction.rebellion, "Temple Guardian", List.of(), game.getRebel(),
                CardLocation.RebelDeck, game.getRebel().getDeck(),  game);
    }
}
