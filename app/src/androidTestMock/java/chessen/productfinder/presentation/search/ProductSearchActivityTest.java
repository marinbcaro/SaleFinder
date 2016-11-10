package chessen.productfinder.presentation.search;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import chessen.productfinder.R;
import chessen.productfinder.data.remote.MockProductSearchServiceImpl;
import chessen.productfinder.presentation.searchProducts.ProductSearchActivity;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumentation Product, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ProductSearchActivityTest {
    @Rule
    public ActivityTestRule<ProductSearchActivity> testRule = new ActivityTestRule<>(ProductSearchActivity.class);

    @Test
    public void givenActivityIsLaunchedWhenUserDoesNotInteractWithTheViewThenTextShouldMatch(){
        onView(withText("Search Products")).check(matches(isDisplayed()));
    }

    @Test
    public void givenThatAUserSearchForAProductThenResultsShouldBeDisplayed() {
        //Arrange
        MockProductSearchServiceImpl.setDummySearchCallResult(null);
        //Act
        onView(allOf(withId(R.id.menu_search), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("white sweater"), pressKey(KeyEvent.KEYCODE_ENTER));

        //Assert
        onView(withText("Search Products")).check(matches(not(isDisplayed())));
        onView(withText("Moto jacket")).check(matches(isDisplayed()));
        onView(withText("Fall sweater")).check(matches(isDisplayed()));
    }

    @Test
    public void givenThereIsAServerErrorMessageShouldBrDisplayed(){
        //Act
        String errorMsg = "Network Error, Please try again later";
        MockProductSearchServiceImpl.setDummySearchCallResult(Observable.error(new Exception(errorMsg)));

        //Assert
        onView(allOf(withId(R.id.menu_search), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("white sweater"), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withText(errorMsg)).check(matches(isDisplayed()));

    }
}
