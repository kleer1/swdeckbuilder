package sw.db.game;

import sw.db.cards.common.models.Faction;

public enum CardLocation {
    EmpireHand,
    EmpireDeck,
    EmpireDiscard,
    EmpireUnitInPlay,
    EmpireShipInPlay,
    EmpireCurrentBase,
    EmpireAvailableBases,
    EmpireDestroyedBases,
    RebelHand,
    RebelDeck,
    RebelDiscard,
    RebelUnitInPlay,
    RebelShipInPlay,
    RebelCurrentBase,
    RebelAvailableBases,
    RebelDestroyedBases,
    GalaxyDeck,
    GalaxyDiscard,
    GalaxyRow,
    Exile,
    OuterRimPilots;

    public static CardLocation getDeck(Faction faction) {
        return faction == Faction.empire ? EmpireDeck : RebelDeck;
    }

    public static CardLocation getDiscard(Faction faction) {
        return faction == Faction.empire ? EmpireDiscard : RebelDiscard;
    }

    public static CardLocation getHand(Faction faction) {
        return faction == Faction.empire ? EmpireHand : RebelHand;
    }

    public static CardLocation getUnitsInPlay(Faction faction) {
        return faction == Faction.empire ? EmpireUnitInPlay : RebelUnitInPlay;
    }

    public static CardLocation getShipsInPlay(Faction faction) {
        return faction == Faction.empire ? EmpireShipInPlay : RebelShipInPlay;
    }

    public static CardLocation getCurrentBase(Faction faction) {
        return faction == Faction.empire ? EmpireCurrentBase : RebelCurrentBase;
    }

    public static CardLocation getAvailableBases(Faction faction) {
        return faction == Faction.empire ? EmpireAvailableBases : RebelAvailableBases;
    }

    public static CardLocation getDestroyedBases(Faction faction) {
        return faction == Faction.empire ? EmpireDestroyedBases : RebelDestroyedBases;
    }
}
