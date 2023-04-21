package sw.db.matchers;

import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import sw.db.game.CardMapping;

@AllArgsConstructor
public class BetweenMatcher extends TypeSafeMatcher<Integer> {
    private final int min;
    private final int max;
    @Override
    protected boolean matchesSafely(Integer integer) {
        return integer >= min && integer < max;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("between [" + min +", " + max + ")");
    }

    public static Matcher<Integer> isBetween(CardMapping cardMapping) {
        return new BetweenMatcher(cardMapping.getMinRange(), cardMapping.getMaxRange());
    }
}
