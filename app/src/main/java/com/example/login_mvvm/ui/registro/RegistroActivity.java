package com.example.login_mvvm.ui.registro;

import android.os.Bundle;
import android.view.View;

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
        viewModel.leerDatos(flag);
        binding.buttonRegister.setText(flag ? "Editar" : "Registrar");
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = binding.editTextDni.getText().toString();
                String nombre = binding.editTextNombre.getText().toString();
                String apellido = binding.editTextApellido.getText().toString();
                String correo = binding.editTextEmail.getText().toString();
                String password = binding.editTextContrasena.getText().toString();
                viewModel.guardar(new Usuario(dni, nombre, apellido, correo, password));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
