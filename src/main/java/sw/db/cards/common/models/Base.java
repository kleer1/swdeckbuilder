package sw.db.cards.common.models;

import lombok.Getter;
import lombok.Setter;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.Player;

import java.util.List;

@Getter
public class Base extends Card {
    protected int hitPoints;
    @Setter
    protected int currentDamage;

    protected Base(int id, Faction faction, String title, CardLocation location,
                List<? extends Card> cardList, Game game, Player owner, int hitPoints) {
        super(id, faction, title, true, location, cardList, game, owner);
        this.hitPoints = hitPoints;
    }

    public int getRemainingHealth() {
        return hitPoints - currentDamage;
    }

    public void addDamage(int damage) {
        currentDamage += damage;
        if (currentDamage >= hitPoints) {
           destroyBase();
        } else if (currentDamage < 0) {
            currentDamage = 0;
        }
    }

    protected void destroyBase() {
        owner.setCurrentBase(null);
        owner.getOpponent().getDestroyedBases().add(this);
        location = CardLocation.getDestroyedBases(owner.getOpponent().getFaction());
        cardList = owner.getOpponent().getDestroyedBases();
        owner = owner.getOpponent();
    }

    public void makeCurrentBase() {
        if (owner.getCurrentBase() != null) {
            throw new RuntimeException("Setting new current base while there is still a current base");
        }
        if (location != CardLocation.getAvailableBases(owner.getFaction())) {
            throw new RuntimeException("Setting new current base for an unavailable base");
        }
        owner.setCurrentBase(this);
        cardList.remove(this);
        location = CardLocation.getCurrentBase(owner.getFaction());
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && location == CardLocation.getCurrentBase(owner.getFaction());
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", hitPoints=" + hitPoints +
                ", currentDamage=" + currentDamage +
                '}';
    }
}
