package sas.randomusers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sas.randomusers.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setDataBinding();
        setContentView(this.binding.getRoot());

        Call<UsersResults> call = UsersApiService.UsersApi.getInstance().getUsers(100, "name,location,picture");
        call.enqueue(new Callback<UsersResults>() {
            @Override
            public void onResponse(Call<UsersResults> call, Response<UsersResults> response) {
                if (response.isSuccessful()) {
                    UsersResults usersResults = response.body();
                    List<User> users = usersResults != null ? usersResults.getUsers() : null;
                    if (users != null) {
                        /*StringBuilder stringBuilder = new StringBuilder();
                        for (User user : users) {
                            stringBuilder.append(user.getName().getFirst())
                                    .append(" ").append(user.getName().getLast())
                                    .append(", ").append(user.getLocation().getCountry())
                                    .append("\n");
                        }
                        binding.textTest.setText(stringBuilder);*/
                        setUsersRecyclerView((ArrayList<User>) users);
                    }
                }

            }

            @Override
            public void onFailure(Call<UsersResults> call, Throwable t) {
                System.out.println("Fallo el pedo");
            }
        });
    }

    public void setUsersRecyclerView(ArrayList<User> users){
        this.binding.usersResView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.binding.usersResView.setAdapter(new UsersRecyclerAdapter(users));
    }


    private void setDataBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding.setMain(this);
    }
}