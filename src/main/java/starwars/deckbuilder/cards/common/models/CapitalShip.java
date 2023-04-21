package starwars.deckbuilder.cards.common.models;

import lombok.Getter;
import lombok.Setter;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.Player;
import starwars.deckbuilder.game.StaticEffect;

import java.util.List;

@Getter
public class CapitalShip extends PlayableCard {
    protected final int hitPoints;
    @Setter
    protected int currentDamage;

    protected CapitalShip(int id, int cost, int attack, int resources, int force, Faction faction, String title, List<Trait> traits,
                       boolean isUnique, Player owner, CardLocation location, List<? extends Card> cardList, Game game, int hitPoints) {
        super(id, faction, title, isUnique, location, cardList, game, owner, cost, attack, resources, force, traits);
        this.hitPoints = hitPoints;
    }

    public void addDamage(int damage) {
        currentDamage += damage;
        if (currentDamage >= hitPoints) {
            moveToDiscard();
        }
    }

    @Override
    public void moveToInPlay() {
        super.moveToInPlay();
        owner.getShipsInPlay().add(this);
        location = owner.getFaction() == Faction.empire ? CardLocation.EmpireShipInPlay : CardLocation.RebelShipInPlay;
        cardList = owner.getShipsInPlay();
        currentDamage = 0;
    }

    @Override
    public void moveToDiscard() {
        super.moveToDiscard();
        currentDamage = 0;
    }

    public int getRemainingHealth() {
        return hitPoints - currentDamage;
    }

    @Override
    public int getAttack() {
        int attack =  super.getAttack();
        if (owner != null && owner.getFaction() == Faction.empire && game.getStaticEffects().contains(StaticEffect.AdmiralPiettBonus)) {
            attack++;
        }
        return attack;
    }

    public double getFlattenedHealth() {
        return  (hitPoints - currentDamage) / hitPoints;
    }
}
