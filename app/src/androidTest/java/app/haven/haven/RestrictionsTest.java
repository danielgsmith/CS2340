package app.haven.haven;

/**
 * Created by Matt on 4/9/2018.
 */

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.haven.haven.Model.shelters.Restrictions;

import static junit.framework.Assert.assertEquals;

/**
 * @Author by Matan Diamond
 *
 * JUnit tests for the parseFromString method in the Capacity class
 * M10
 */

@RunWith(AndroidJUnit4.class)
public class RestrictionsTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullTest() {
        Restrictions.parseFrom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyTest() {
        Restrictions.parseFrom("");
    }

    @Test
    public void testWomenChildren() {
        assertEquals(Restrictions.WOMEN_CHILDREN, Restrictions.parseFrom("Women/Children "));
    }

    @Test
    public void testMen() {
        assertEquals(Restrictions.MEN, Restrictions.parseFrom("Men "));
    }

    @Test
    public void testFamilies() {
        assertEquals(Restrictions.FAMILIES, Restrictions.parseFrom("Families "));
    }

    @Test
    public void testFamiliesChildrenUnderFive() {
        assertEquals(Restrictions.FAMILIES_CHILDREN_UNDER_5, Restrictions.parseFrom("Families w/ Children Under 5 "));
    }

    @Test
    public void testFamiliesNewborns() {
        assertEquals(Restrictions.FAMILIES_NEWBORNS, Restrictions.parseFrom("Families w/ Newborns "));
    }

    @Test
    public void testChildrenYoungAdults() {
        assertEquals(Restrictions.CHILDRENS_YOUNG_ADULTS, Restrictions.parseFrom("Childrens/Young Adults "));
    }

    @Test
    public void testAnyone() {
        assertEquals(Restrictions.ANYONE, Restrictions.parseFrom("Anyone "));
    }

    @Test
    public void testYoungAdults() {
        assertEquals(Restrictions.YOUNG_ADULTS, Restrictions.parseFrom("Young Adults"));
    }

    @Test
    public void testVeterans() {
        assertEquals(Restrictions.VETERANS, Restrictions.parseFrom("Veterans "));
    }

    @Test
    public void testWomen() {
        assertEquals(Restrictions.WOMEN, Restrictions.parseFrom("Women "));
    }

}
