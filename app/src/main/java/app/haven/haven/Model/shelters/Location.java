package app.haven.haven.Model.shelters;

import com.google.android.gms.maps.model.LatLng;

/******************************************************************************
 *  Taken from some Princeton class, it's open source and will be useful
 ******************************************************************************/

public class Location {
    private double longitude;
    private double latitude;

    /**
     * Constructor method for Location class
     * @param latitude the latitude of the shelter
     * @param longitude the longitude of the shelter
     */
    public Location(double latitude, double longitude) {
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    /**
     * Calculates the location between this location and that location (in statute miles)
     * @param that that location
     * @return  distance between this location and that location
     * measured in statute miles
     */
    public double distanceTo(Location that) {
        if (this.latitude < -90 || that.latitude < -90) {
            throw new IllegalArgumentException("latitude can't be less than -90");
        }
        if (this.latitude > 90 || that.latitude > 90) {
            throw new IllegalArgumentException("latitude can't exceed 90");
        }
        if (this.longitude < -180 || that.longitude < -180) {
            throw new IllegalArgumentException("longitude can't be less than -180");
        }
        if (this.longitude > 180 || that.longitude > 180) {
            throw new IllegalArgumentException("longitude can't exceed 180");
        }
        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(that.latitude);
        double lon2 = Math.toRadians(that.longitude);

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
//        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
//        return statuteMiles;
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }

    /**
     *
     * @return a new LatLng object based on the class's latitude and longitude instance variables
     */
    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }

    /**
     * the class's toString method
     * @return string representation of this point
     */
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

}