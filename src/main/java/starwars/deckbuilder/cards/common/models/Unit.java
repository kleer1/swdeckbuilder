package starwars.deckbuilder.cards.common.models;

import lombok.Getter;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.Player;
import starwars.deckbuilder.game.StaticEffect;

import java.util.List;

@Getter
public class Unit extends PlayableCard {


    protected Unit(int id, int cost, int attack, int resources, int force, Faction faction, String title, List<Trait> traits,
                boolean isUnique, Player owner, CardLocation location, List<? extends Card> cardList, Game game) {
        super(id, faction, title, isUnique, location, cardList, game, owner, cost, attack, resources, force, traits);
    }

    @Override
    public void moveToInPlay() {
        super.moveToInPlay();
        owner.getUnitsInPlay().add(this);
        location = owner.getFaction() == Faction.empire ? CardLocation.EmpireUnitInPlay : CardLocation.RebelUnitInPlay;
        cardList = owner.getUnitsInPlay();
    }

    @Override
    public int getAttack() {
        int base = super.getAttack();
        if (faction == Faction.empire && game.getStaticEffects().contains(StaticEffect.EndorBonus) &&
                (traits.contains(Trait.trooper) || traits.contains(Trait.vehicle))) {
            base++;
        }
        return base;
    }
}
