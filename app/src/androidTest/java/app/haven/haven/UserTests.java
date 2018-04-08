package app.haven.haven;

import org.junit.Test;
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
        assertEquals(-1, Capacity.parseFromString(testString).getIndividualCapacity());
        assertEquals(-1, Capacity.parseFromString(testString).getGroupCapacity());
    }

    @Test
    public void spacesTest() {
        String testString = "10";
        assertEquals(Capacity.CapacityType.SPACES,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(10, Capacity.parseFromString(testString).getIndividualCapacity());
        assertEquals(0, Capacity.parseFromString(testString).getGroupCapacity());
    }

    @Test
    public void apartmentsTest() {
        String testString = "apartments";
        assertEquals(Capacity.CapacityType.UNLISTED,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(-1, Capacity.parseFromString(testString).getIndividualCapacity());
        assertEquals(-1, Capacity.parseFromString(testString).getGroupCapacity());

        testString = "apartments10";
        assertEquals(Capacity.CapacityType.APARTMENTS,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(0, Capacity.parseFromString(testString).getIndividualCapacity());
        assertEquals(10, Capacity.parseFromString(testString).getGroupCapacity());
    }

    @Test
    public void famAndSingTest() {
        String testString = "5family10single";
        assertEquals(Capacity.CapacityType.FAMILY_AND_SINGLE_ROOMS,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(10, Capacity.parseFromString(testString).getIndividualCapacity());
        assertEquals(5, Capacity.parseFromString(testString).getGroupCapacity());
    }

    @Test
    public void famTest() {
        String testString = "15fam";
        assertEquals(Capacity.CapacityType.FAMILY_ROOMS,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(15, Capacity.parseFromString(testString).getGroupCapacity());
    }

    @Test
    public void singTest() {
        String testString = "5sing";
        assertEquals(Capacity.CapacityType.SPACES,
                Capacity.parseFromString(testString).getCapacityType());
        assertEquals(5, Capacity.parseFromString(testString).getIndividualCapacity());
    }
}