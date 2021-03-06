package com.example.go;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Check if the board is emptied after clicking “New Game”
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class GameActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void gameActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_VsCom), withText("One Player"),
                        childAtPosition(
                                allOf(withId(R.id.layout_Buttons),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction imageView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.boardGame),
                                6),
                        8),
                        isDisplayed()));
        imageView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.newGameButten), withText("New Game"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.topContent),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction imageView2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.boardGame),
                                6),
                        8),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
