package com.example.login_mvvm.ui.login;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.login_mvvm.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LoginActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);

        initViews();

        viewModel.getAviso().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvAviso.setText(s);
            }
        });

        viewModel.getAvisoVisibility().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                binding.tvAviso.setVisibility(visibility);
            }
        });
    }

    private void initViews() {
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.login(binding.email.getText().toString(), binding.contrasena.getText().toString());
            }
        });

        binding.registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.Registrarse();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
