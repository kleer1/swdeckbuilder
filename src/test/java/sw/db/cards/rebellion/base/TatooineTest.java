package sw.db.cards.rebellion.base;

import sw.db.cards.base.HasOnRevealTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.rebellion.unit.LukeSkywalker;
import sw.db.game.Action;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class TatooineTest extends RebelAvailableBaseTest implements HasOnRevealTest {

    private PlayableCard rebel;

    @Override
    public int getId() {
        return 138;
    }

    @Override
    public void preChooseBaseSetup() {
        rebel = moveToGalaxyRow(LukeSkywalker.class).get(0);
        rebel.moveToGalaxyDiscard();
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ANewHope1));

        game.applyAction(rebel.getId());

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ANewHope2));

        PlayableCard rowCard = game.getGalaxyRow().get(0);
        game.applyAction(rowCard.getId());

        assertThat(rebel.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(rowCard.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(getPlayer().getResources(), equalTo(1));
    }
}