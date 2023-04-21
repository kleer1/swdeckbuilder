package starwars.deckbuilder.cards.empire.ship;

import starwars.deckbuilder.game.Game;

public class StarDestroyer extends EmpireGalaxyShip {
    public StarDestroyer(int id, Game game) {
        super(id, 7, 4, 0, 0, "StarDestroyer", game, 7);
    }
}
