package app.haven.haven;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.haven.haven.Controller.fragments.UserAccoundEditingFragment;
import app.haven.haven.Model.shelters.Capacity;


import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

/**
 * Created by Michael Sherman
 */


public class EmailCorrectnessTest {

    UserAccoundEditingFragment testFragment = new UserAccoundEditingFragment();

    @Test
    public void nullTest() {
        String test = null;
        assertNull(test);
        try {
            testFragment.isEmailCorrect(test);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void noPeriodTest() {
        String email = "abc@gmailcom";
        assertFalse(email.isEmpty());
        assertTrue(email.contains("@"));
        assertFalse(email.contains("."));
        assertFalse(testFragment.isEmailCorrect(email));

    }

    @Test
    public void noAtTest() {
        String email = "abcgmail.com";
        assertFalse(email.isEmpty());
        assertFalse(email.contains("@"));
        assertTrue(email.contains("."));
        assertFalse(testFragment.isEmailCorrect(email));
    }

    @Test
    public void noAtOrPeriodTest() {
        String email = "abcgmailcom";
        assertFalse(email.contains("@"));
        assertFalse(email.contains("."));
        assertFalse(testFragment.isEmailCorrect(email));
    }

    @Test
    public void emptyTest() {
        String email = "";
        assertFalse(email.contains("@"));
        assertFalse(email.contains("."));
        assertEquals(0, email.length());
        assertTrue(email.isEmpty());
        assertFalse(testFragment.isEmailCorrect(email));
    }

    @Test
    public void correctTest() {
        String email = "abc@gmail.com";
        assertFalse(email.isEmpty());
        assertTrue(email.contains("@"));
        assertTrue(email.contains("."));
        assertTrue(testFragment.isEmailCorrect(email));
    }
}

