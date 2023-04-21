package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.HasAbility;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.PendingAction;

import java.util.List;

public class BWing extends RebelGalaxyUnit implements HasAbility {

    private boolean hasAttackBoost;
    public BWing(int id, Game game) {
        super(id, 5, 5, 0, 0, "B-Wing", List.of(Trait.fighter), false, game);
        hasAttackBoost = false;
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive();
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        hasAttackBoost = true;
        if (!owner.getOpponent().getHand().isEmpty()) {
            game.getPendingActions().add(PendingAction.of(Action.BWingDiscard, () -> this.hasAttackBoost = false, true));
        }
    }

    @Override
    public int getTargetValue() {
        return 5;
    }

    @Override
    public void applyReward() {
        addExilePendingAction(game.getEmpire(), 2);
    }

    @Override
    public void moveToDiscard() {
        super.moveToDiscard();
        hasAttackBoost = false;
    }

    @Override
    public void moveToInPlay() {
        super.moveToInPlay();
        hasAttackBoost = false;
    }

    @Override
    public int getAttack() {
        int atk = super.getAttack();
        if (hasAttackBoost) {
            atk += 2;
        }
        return atk;
    }
}
