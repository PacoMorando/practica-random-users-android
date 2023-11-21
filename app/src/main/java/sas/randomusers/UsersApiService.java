package sas.randomusers;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class UsersApiService {
    private static final String BASE_URL = "https://randomuser.me/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();

    public interface ApiServiceInterface {
        @GET("api")
        Call<UsersResults> getUsers(@Query("results") int size, @Query("inc") String fields);
    }

    public static class UsersApi {
        private static ApiServiceInterface retrofitService;

        public static ApiServiceInterface getInstance() {
            if (retrofitService == null) {
                retrofitService = retrofit.create(ApiServiceInterface.class);
            }
            return retrofitService;
        }
    }

}
