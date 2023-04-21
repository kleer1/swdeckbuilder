package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.cards.common.models.PlayableCard;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.ListIterator;

public class Rodia extends Base implements HasOnReveal {

    public Rodia(int id, Game game) {
        super(id, Faction.empire, "Rodia", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 16);
    }

    @Override
    public void applyOnReveal() {
        int numMatches = 0;
        ListIterator<PlayableCard> iterator = game.getGalaxyRow().listIterator();
        while (iterator.hasNext()) {
            PlayableCard card = iterator.next();
            if (card.getFaction() == Faction.rebellion) {
                iterator.remove();
                numMatches++;
                card.moveToGalaxyDiscard();
            }
        }
        game.getRebel().getCurrentBase().addDamage(numMatches);
        for (int i = 0; i < numMatches; i++) {
            game.drawGalaxyCard();
        }
    }
}
