package sas.randomusers;

import com.squareup.moshi.Json;

public class User {
    @Json(name = "name")
    private Name name;
    @Json(name = "location")
    private Location location;
    @Json(name = "picture")
    private Picture picture;

    public Name getName() {
        return name;
    }

    public String getFirstName(){
        return this.name.getFirst();
    }

    public String getLastName(){
        return this.name.getLast();
    }

    public Location getLocation() {
        return location;
    }

    public String getCountry(){
        return this.location.getCountry();
    }

    public Picture getPicture() {
        return picture;
    }

    static class Name {
        @Json(name = "first")
        private String first;

        @Json(name = "last")
        private String last;

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }
    }

    static class Location {
        @Json(name = "country")
        private String country;

        public String getCountry() {
            return country;
        }
    }

    static class Picture {
        @Json(name = "thumbnail")
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }
    }
}
