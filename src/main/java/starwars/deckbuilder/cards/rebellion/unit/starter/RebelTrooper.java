package starwars.deckbuilder.cards.rebellion.unit.starter;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.cards.common.models.Unit;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class RebelTrooper extends Unit {

    public RebelTrooper(int id, Game game) {
        super(id, 0, 2, 0, 0, Faction.rebellion, "Rebel Trooper", List.of(Trait.trooper),
                false, game.getRebel(), CardLocation.RebelDeck, game.getRebel().getDeck(), game);
    }
}
