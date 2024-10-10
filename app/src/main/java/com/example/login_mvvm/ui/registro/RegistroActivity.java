package com.example.login_mvvm.ui.registro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.login_mvvm.R;
import com.example.login_mvvm.databinding.ActivityRegistroBinding;
import com.example.login_mvvm.model.Usuario;

import java.io.FileNotFoundException;

public class RegistroActivity extends AppCompatActivity {

    private RegistroActivityViewModel viewModel;
    private ActivityRegistroBinding binding;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;
    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        abrirGaleria();

        boolean flag = getIntent().getBooleanExtra("isUser", false);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(RegistroActivityViewModel.class);

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
        viewModel.getUsuario().observe(this, new Observer<Usuario>() {
                @Override
                public void onChanged(Usuario usr) {
                    binding.editTextDni.setText(String.valueOf(usr.getDni()));
                    binding.editTextApellido.setText(usr.getApellido());
                    binding.editTextNombre.setText(usr.getNombre());
                    binding.editTextEmail.setText(usr.getEmail());
                    binding.editTextContrasena.setText(usr.getContrasena());
                    binding.imageViewProfile.setImageURI(viewModel.getImageUri(usr.getImage()));
                }
            });
        viewModel.getUriMutable().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.imageViewProfile.setImageURI(uri);
            }
        });

        binding.buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
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

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
