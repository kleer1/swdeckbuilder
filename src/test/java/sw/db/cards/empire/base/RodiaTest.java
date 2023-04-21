package sw.db.cards.empire.base;

import sw.db.cards.BaseTest;
import sw.db.cards.base.HasOnRevealTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.game.CardLocation;

import java.util.ListIterator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class RodiaTest extends EmpireAvailableBaseTest implements HasOnRevealTest {

    private PlayableCard rebel1;
    private PlayableCard rebel2;
    private PlayableCard empire1;
    private PlayableCard empire2;
    private PlayableCard neutral1;
    private PlayableCard neutral2;

    @Override
    public int getId() {
        return 126;
    }

    @Override
    public void preChooseBaseSetup() {
        rebel1 = (PlayableCard) game.getCardMap().get(BaseTest.REBEL_GALAXY_CARD);
        rebel2 = (PlayableCard) game.getCardMap().get(BaseTest.REBEL_GALAXY_CARD + 1);
        empire1 = (PlayableCard) game.getCardMap().get(BaseTest.EMPIRE_GALAXY_CARD);
        empire2 = (PlayableCard) game.getCardMap().get(BaseTest.EMPIRE_GALAXY_CARD + 1);
        neutral1 = (PlayableCard) game.getCardMap().get(BaseTest.NEUTRAL_GALAXY_CARD);
        neutral2 = (PlayableCard) game.getCardMap().get(BaseTest.NEUTRAL_GALAXY_CARD + 1);

        ListIterator<PlayableCard> iterator = game.getGalaxyRow().listIterator();
        while (iterator.hasNext()) {
            PlayableCard card = iterator.next();
            iterator.remove();
            card.moveToTopOfGalaxyDeck();
        }

        assertThat(game.getGalaxyRow(), hasSize(0));

        rebel1.moveToGalaxyRow();
        rebel2.moveToGalaxyRow();
        empire1.moveToGalaxyRow();
        empire2.moveToGalaxyRow();
        neutral1.moveToGalaxyRow();
        neutral2.moveToGalaxyRow();
        assertThat(getPlayer().getOpponent().getCurrentBase().getCurrentDamage(), equalTo(0));
        assertThat(game.getGalaxyRow(), hasSize(6));
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(getPlayer().getOpponent().getCurrentBase().getCurrentDamage(), equalTo(2));
        assertThat(rebel1.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(rebel2.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire1.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(empire2.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(neutral1.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(neutral2.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(game.getGalaxyRow(), hasSize(6));
    }
}