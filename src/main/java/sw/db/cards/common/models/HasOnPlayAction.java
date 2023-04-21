package sw.db.cards.common.models;

import sw.db.game.PendingAction;

import java.util.List;

public interface HasOnPlayAction {
    List<PendingAction> getActions();
}
