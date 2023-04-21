package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class KelDorMysticTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));
        assertThat(card.getLocation(), equalTo(CardLocation.Exile));
    }

    @Override
    public int getId() {
        return 96;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 2);
    }
}