package starwars.deckbuilder.cards.neutral.ship;

import starwars.deckbuilder.game.Game;

public class BlockadeRunner extends NeutralGalaxyShip {
    public BlockadeRunner(int id, Game game) {
        super(id, 4, 1, 1, "Blockade Runner", game, 4);
    }
}
