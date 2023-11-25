package sas.randomusers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sas.randomusers.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UsersRecyclerAdapter usersRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setDataBinding();
        setContentView(this.binding.getRoot());

        this.getUsers();
        this.setFilterListener();
    }

    private void getUsers() {
        Call<UsersResults> call = UsersApiService.UsersApi.getInstance().getUsers(10, "name,location,picture");
        call.enqueue(new Callback<UsersResults>() {
            @Override
            public void onResponse(Call<UsersResults> call, Response<UsersResults> response) {
                if (response.isSuccessful()) {
                    UsersResults usersResults = response.body();
                    List<User> users = usersResults != null ? usersResults.getUsers() : null;
                    if (users != null) {
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

    private void setFilterListener() {
        this.binding.filterByCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usersRecyclerAdapter.setInputFilter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setButtonText(boolean sortByCountryState) {
        if (sortByCountryState) {
            this.binding.byCountryButton.setText(R.string.un_sort_country_button);
        } else {
            this.binding.byCountryButton.setText(R.string.sort_country_button);
        }
    }

    public void sortUsersByCountry() {
        this.usersRecyclerAdapter.sortUsersByCountry();
        this.setButtonText(this.usersRecyclerAdapter.getSortByCountryState());
    }

    public void sortUsersByFirstName() {
        this.usersRecyclerAdapter.sortUsersByFirstName();
    }

    public void sortUsersByLastName() {
        this.usersRecyclerAdapter.sortUsersByLastName();
    }

    public void resetUsers() {
        this.usersRecyclerAdapter.resetUsers();
    }

    private void setUsersRecyclerView(ArrayList<User> users) {
        this.usersRecyclerAdapter = new UsersRecyclerAdapter(users);
        this.binding.usersResView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.binding.usersResView.setAdapter(this.usersRecyclerAdapter);
    }


    private void setDataBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding.setMain(this);
    }
}