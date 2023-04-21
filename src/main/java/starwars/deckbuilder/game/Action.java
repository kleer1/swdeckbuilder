package starwars.deckbuilder.game;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Action {
    /*
     * Card Actions
     */
    PlayCard(Set.of(CardLocation.EmpireHand, CardLocation.RebelHand), Set.of()),
    PurchaseCard(Set.of(CardLocation.GalaxyRow, CardLocation.OuterRimPilots), Set.of(CardLocation.GalaxyRow, CardLocation.GalaxyDiscard)),
    UseCardAbility(Set.of(CardLocation.EmpireShipInPlay, CardLocation.EmpireUnitInPlay,
            CardLocation.RebelUnitInPlay, CardLocation.RebelShipInPlay, CardLocation.EmpireCurrentBase,
            CardLocation.RebelCurrentBase), Set.of()),
    AttackCenterRow(Set.of(CardLocation.GalaxyRow), Set.of(CardLocation.GalaxyRow), false),
    AttackBase(Set.of(CardLocation.EmpireCurrentBase, CardLocation.RebelCurrentBase), Set.of()),
    SelectAttacker(Set.of(), Set.of(CardLocation.EmpireShipInPlay, CardLocation.EmpireUnitInPlay,
            CardLocation.RebelUnitInPlay, CardLocation.RebelShipInPlay), false),
    DiscardFromHand(Set.of(), Set.of(CardLocation.EmpireHand, CardLocation.RebelHand), false),
    DiscardCardFromCenter(Set.of(), Set.of(CardLocation.GalaxyRow)),
    ExileCard(Set.of(), Set.of(CardLocation.EmpireHand, CardLocation.RebelHand, CardLocation.EmpireDiscard, CardLocation.RebelDiscard)),
    ReturnCardToHand(Set.of(), Set.of(CardLocation.EmpireDiscard, CardLocation.RebelDiscard)),
    ChooseNextBase(Set.of(), Set.of(CardLocation.EmpireAvailableBases, CardLocation.RebelAvailableBases), false),
    SwapTopCardOfDeck(Set.of(), Set.of(CardLocation.GalaxyRow)),
    FireWhenReady(Set.of(), Set.of(CardLocation.GalaxyRow, CardLocation.RebelShipInPlay)),
    GalacticRule(Set.of(), Set.of(CardLocation.GalaxyDeck), false),
    ANewHope1(Set.of(), Set.of(CardLocation.GalaxyDiscard)),
    ANewHope2(Set.of(), Set.of(CardLocation.GalaxyRow), false),
    DurosDiscard(Set.of(), Set.of(CardLocation.EmpireHand, CardLocation.RebelHand)),
    BWingDiscard(Set.of(), Set.of(CardLocation.EmpireHand, CardLocation.RebelHand)),
    JynErsoTopDeck(Set.of(), Set.of(CardLocation.EmpireHand), false),
    LukeDestroyShip(Set.of(), Set.of(CardLocation.EmpireShipInPlay), false),
    HammerHeadAway(Set.of(), Set.of(CardLocation.GalaxyRow, CardLocation.EmpireShipInPlay), false),
    JabbaExile(Set.of(), Set.of(CardLocation.EmpireHand, CardLocation.RebelHand), false),
    /*
     * Non-card Actions
     */
    PassTurn,
    DeclineAction,
    ChooseStatBoost,
    ChooseResourceOrRepair,
    AttackNeutralCard,
    ConfirmAttackers,
//    BuyGalaxyDiscard,
    ;

    private final Set<CardLocation> defaultLocations;
    private final Set<CardLocation> pendingLocations;
    private final boolean declinable;
    private static final Set<Action> values = Arrays.stream(values()).collect(Collectors.toSet());

    Action() {
        this.defaultLocations = Set.of();
        this.pendingLocations = Set.of();
        this.declinable = true;
    }

    Action(Set<CardLocation> defaultLocations, Set<CardLocation> pendingLocations) {
        this.defaultLocations = defaultLocations;
        this.pendingLocations = pendingLocations;
        this.declinable = true;
    }

    Action(Set<CardLocation> defaultLocations, Set<CardLocation> pendingLocations, boolean declinable) {
        this.defaultLocations = defaultLocations;
        this.pendingLocations = pendingLocations;
        this.declinable = declinable;
    }

    public static Set<Action> getDefaultActionsByLocation(CardLocation location) {
        return values.stream().filter(a -> a.defaultLocations.contains(location)).collect(Collectors.toSet());
    }

    public static Set<Action> getPendingActionsByLocation(CardLocation location) {
        return values.stream().filter(a -> a.pendingLocations.contains(location)).collect(Collectors.toSet());
    }
}
