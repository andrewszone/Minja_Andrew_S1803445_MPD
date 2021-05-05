//S1803445
package org.me.gcu;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.bon.earthquake.R;

import org.me.gcu.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UiTests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void click_item_at_specified_position() {
        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(3, click()));
    }

    @Test
    public void recyclerview_scrolls_to_specified_position(){
        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(10));
    }
}
