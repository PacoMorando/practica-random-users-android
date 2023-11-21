package sas.randomusers;

import com.squareup.moshi.Json;

import java.util.List;

public class UsersResults {
    @Json(name = "results")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
