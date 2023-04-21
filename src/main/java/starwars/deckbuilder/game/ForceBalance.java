package starwars.deckbuilder.game;

import lombok.Getter;

public class ForceBalance {
    @Getter
    private int position = 6;

    public void darkSideGainForce(int amount) {
        position -= amount;
        if (position < 0) position = 0;
    }

    public void lightSideGainForce(int amount) {
        position += amount;
        if (position > 6) position = 6;
    }

    public boolean lightSideHasTheForce() {
        return position > 3;
    }

    public boolean darkSideHasTheForce() {
        return position < 3;
    }

    public boolean lightSideFull() {
        return position == 6;
    }

    public boolean darkSideFull() {
        return position == 0;
    }
}
