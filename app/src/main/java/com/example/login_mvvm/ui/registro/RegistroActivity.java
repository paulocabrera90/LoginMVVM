package com.example.login_mvvm.ui.registro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.login_mvvm.databinding.ActivityRegistroBinding;
import com.example.login_mvvm.model.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private RegistroActivityViewModel viewModel;
    private ActivityRegistroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean flag = getIntent().getBooleanExtra("isUser", false);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(RegistroActivityViewModel.class);

        initViews(flag);

        if(!flag){
            viewModel.getAvisoMutable().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    binding.tvAviso.setText(s);
                }
            });

            viewModel.getAvisoVisibilityMutable().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer visibility) {
                    binding.tvAviso.setVisibility(visibility);
                }
            });
        } else {
            viewModel.getUsuario().observe(this, new Observer<Usuario>() {
                @Override
                public void onChanged(Usuario usr) {
                    binding.editTextDni.setText(String.valueOf(usr.getDni()));
                    binding.editTextApellido.setText(usr.getApellido());
                    binding.editTextNombre.setText(usr.getNombre());
                    binding.editTextEmail.setText(usr.getEmail());
                    binding.editTextContrasena.setText(usr.getContrasena());
                }
            });

            viewModel.getAvisoMutable().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    binding.tvAviso.setText(s);
                }
            });

            viewModel.getAvisoVisibilityMutable().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer visibility) {
                    binding.tvAviso.setVisibility(visibility);
                }
            });

            viewModel.setUsuario();
        }

    }

    private void initViews(boolean flag) {
        binding.buttonRegister.setText(flag ? "Editar" : "Registrar");

        binding.buttonRegister.setOnClickListener(view -> {
            Usuario usuario = crearUsuarioDesdeCampos();
            if (flag) {
                viewModel.Editar(usuario);
            } else {
                viewModel.Guardar(usuario);
            }
        });
    }

    private Usuario crearUsuarioDesdeCampos() {
        return new Usuario(
                binding.editTextDni.getText().toString(),
                binding.editTextNombre.getText().toString(),
                binding.editTextApellido.getText().toString(),
                binding.editTextEmail.getText().toString(),
                binding.editTextContrasena.getText().toString()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
