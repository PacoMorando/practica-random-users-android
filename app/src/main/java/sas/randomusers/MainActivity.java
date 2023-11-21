package sas.randomusers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import sas.randomusers.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setDataBinding();
        setContentView(this.binding.getRoot());

        this.binding.textTest.setText("Probando que el data binding este funkando como se debe");
    }

    private void setDataBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding.setMain(this);
    }
}