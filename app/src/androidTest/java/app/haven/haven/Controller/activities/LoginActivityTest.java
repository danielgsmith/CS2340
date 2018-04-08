package app.haven.haven.Controller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import app.haven.haven.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    public String email;
    public String password;
    public String badEmail;
    public String badPassword;
    public String lockedOutUser;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp(){
        email = "test@test.com";
        password = "password";
        badPassword = "notapassword";
        badEmail = "notaemail@nothing.com";
    }

    @Test()
    public void testSignInCorrect(){
        onView(withId(R.id.email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
        mActivityRule.getActivity().signIn(email, password);
        assertTrue(MainPageActivity.getUser() != null);
        FirebaseAuth.getInstance().signOut();
        MainPageActivity.setUser(null);
    }

    @Test()
    public void testSignInIncorrectPassword(){
        onView(withId(R.id.email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(badPassword), closeSoftKeyboard());
        mActivityRule.getActivity().signIn(email, badPassword);
        assertTrue(MainPageActivity.getUser() == null);
        FirebaseAuth.getInstance().signOut();
    }

    @Test()
    public void testSignInIncorrectEmail(){
        onView(withId(R.id.email)).perform(typeText(badEmail), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
        mActivityRule.getActivity().signIn(badEmail, password);
        assertTrue(MainPageActivity.getUser() == null);
        FirebaseAuth.getInstance().signOut();
    }


    @Test()
    public void testSignInBad(){
        onView(withId(R.id.email)).perform(typeText(badEmail), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(badPassword), closeSoftKeyboard());
        mActivityRule.getActivity().signIn(badEmail, badPassword);
        assertTrue(MainPageActivity.getUser() == null);
        FirebaseAuth.getInstance().signOut();
    }
}

