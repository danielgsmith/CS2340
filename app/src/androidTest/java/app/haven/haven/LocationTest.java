package app.haven.haven;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.haven.haven.Model.shelters.Location;

import static junit.framework.Assert.assertEquals;

/**
 * Created by bdavis18 on 4/9/2018.
 * JUnit tests for the distanceTo method in the Location class
 * M10
 */

@RunWith(AndroidJUnit4.class)
public class LocationTest {

    @Test(expected = IllegalArgumentException.class)
    public void thatLatLowTest() {
        Location dis = new Location(0, 0);
        Location that = new Location(-95, 0);
        dis.distanceTo(that);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatLongLowTest() {
        Location dis = new Location(0, 0);
        Location that = new Location(0, -200);
        dis.distanceTo(that);
    }

    public void thisLatLowTest() {
        Location that = new Location(0, 0);
        Location dis = new Location(-95, 0);
        dis.distanceTo(that);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thisLongLowTest() {
        Location that = new Location(0, 0);
        Location dis = new Location(0, -200);
        dis.distanceTo(that);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatLatHighTest() {
        Location dis = new Location(0, 0);
        Location that = new Location(95, 0);
        dis.distanceTo(that);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatLongHighTest() {
        Location dis = new Location(0, 0);
        Location that = new Location(0, 200);
        dis.distanceTo(that);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thisLatHighTest() {
        Location that = new Location(0, 0);
        Location dis = new Location(95, 0);
        dis.distanceTo(that);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thisLongHighTest() {
        Location that = new Location(0, 0);
        Location dis = new Location(0, 200);
        dis.distanceTo(that);
    }

    @Test
    public void zero() {
        Location dis = new Location(0, 0);
        Location that = new Location(0, 0);
        assertEquals(0.0, dis.distanceTo(that));
    }
    @Test
    public void nonZero() {
        Location dis = new Location(1.0, 5.0);
        Location that = new Location(-4.0, -1.0);
        assertEquals(539.0621514341841, dis.distanceTo(that));
    }
}

