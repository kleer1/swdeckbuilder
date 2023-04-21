package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class Z95Headhunter extends NeutralGalaxyUnit implements HasAbility {
    public Z95Headhunter(int id, Game game) {
        super(id, 1, 2, 0, 0, "Z-95 Headhunter", List.of(Trait.fighter), false, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && !owner.getOpponent().getShipsInPlay().isEmpty();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        owner.drawCards(1);
    }
}
