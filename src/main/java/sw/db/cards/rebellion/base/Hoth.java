package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.game.CardLocation;
import sw.db.game.Game;

public class Hoth extends Base implements HasAtStartOfTurn {

    private int damageTakenThisTurn;
    public Hoth(int id, Game game) {
        super(id, Faction.rebellion, "Hoth", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 14);
        damageTakenThisTurn = 0;
    }

    @Override
    public void applyAtStartOfTurn() {
        damageTakenThisTurn = 0;
    }

    @Override
    public void addDamage(int damage) {
        int dam = damage;
        if (damageTakenThisTurn < 2) {
            dam -= (2 - damageTakenThisTurn);
            if (dam < 0) {
                dam = 0;
            }
            damageTakenThisTurn += damage;
        }
        super.addDamage(dam);
    }
}
