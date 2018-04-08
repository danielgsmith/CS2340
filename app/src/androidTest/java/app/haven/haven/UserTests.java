package app.haven.haven;

import org.junit.Assert;
import org.junit.Test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.haven.haven.Model.shelters.Capacity;

import static org.junit.Assert.*;

/**
 * Created by DanielSmith
 */

public class UserTests {

    @Test
    public void nullTest() {
        String testString = "";
        assertEquals(Capacity.CapacityType.UNLISTED,
                Capacity.parseFromString(testString).getCapacityType());
    }

    @Test
    public void spacesTest() {
        String testString = "10";
        assertEquals(Capacity.CapacityType.SPACES,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(10, Capacity.parseFromString(testString).getIndividualCapacity());
    }

    @Test
    public void apartmentsTest() {
        String testString = "apartments";
        assertEquals(Capacity.CapacityType.UNLISTED,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(-1, Capacity.parseFromString(testString).getIndividualCapacity());

        testString = "apartments10";
        assertEquals(Capacity.CapacityType.APARTMENTS,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(10, Capacity.parseFromString(testString).getGroupCapacity());
    }





}
