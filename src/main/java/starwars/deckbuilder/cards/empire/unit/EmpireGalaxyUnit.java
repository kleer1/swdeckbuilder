package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.IsTargetable;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.cards.common.models.Unit;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.StaticEffect;

import java.util.List;

public abstract class EmpireGalaxyUnit extends Unit implements IsTargetable {
    protected EmpireGalaxyUnit(int id, int cost, int attack, int resources, int force, String title, List<Trait> traits, boolean isUnique, Game game) {
        super(id, cost, attack, resources, force, Faction.empire, title, traits, isUnique, null, CardLocation.GalaxyDeck, game.getGalaxyDeck(), game);
    }

    @Override
    public int getAttack() {
        int atk = super.getAttack();
        if (traits.contains(Trait.fighter)) {
            atk += game.getStaticEffects().stream().filter(se -> se == StaticEffect.CarrierEffect).count();
        }
        return atk;
    }
}
