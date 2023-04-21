package sw.db.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PendingAction {
    @Getter
    private final Action action;
    private final Runnable callback;
    private final boolean shouldPassAction;

    public static PendingAction of(Action action) {
        return new PendingAction(action);
    }

    public static PendingAction of(Action action, Runnable callback) {
        return new PendingAction(action, callback);
    }

    public static PendingAction of(Action action, boolean shouldPassTurn) {
        return new PendingAction(action, shouldPassTurn);
    }

    public static PendingAction of(Action action, Runnable callback, boolean shouldPassTurn) {
        return new PendingAction(action, callback, shouldPassTurn);
    }

    public PendingAction(Action action) {
        this.action = action;
        this.callback = null;
        this.shouldPassAction = false;
    }

    public PendingAction(Action action, Runnable callback) {
        this.action = action;
        this.callback = callback;
        this.shouldPassAction = false;
    }

    public PendingAction(Action action, boolean shouldPassAction) {
        this.action = action;
        this.callback = null;
        this.shouldPassAction = shouldPassAction;
    }

    public void executeCallback() {
        if (callback != null) {
            callback.run();
        }
    }

    public boolean shouldPassAction() {
        return shouldPassAction;
    }
}
