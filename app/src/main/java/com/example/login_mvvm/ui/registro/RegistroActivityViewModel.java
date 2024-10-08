package com.example.login_mvvm.ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.login_mvvm.model.Usuario;
import com.example.login_mvvm.request.ApiClient;
import com.example.login_mvvm.ui.login.LoginActivity;

public class RegistroActivityViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;
    private MutableLiveData<Usuario> usuarioMutable;

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<String> getAvisoMutable() {
        if (avisoMutable == null) {
            avisoMutable = new MutableLiveData<>();
        }
        return avisoMutable;
    }

    public LiveData<Integer> getAvisoVisibilityMutable() {
        if (avisoVisibilityMutable == null) {
            avisoVisibilityMutable = new MutableLiveData<>();
        }
        return avisoVisibilityMutable;
    }

    public LiveData<Usuario> getUsuario() {
        if (usuarioMutable == null) {
            usuarioMutable = new MutableLiveData<>();
        }
        return usuarioMutable;
    }

    public void setUsuario() {
        usuarioMutable.setValue(ApiClient.leerDatos(context));
    }

    public void Guardar(Usuario usr) {
        Usuario usuario = ApiClient.leerDatos(context);
        if (usuario == null) {
            ApiClient.guardar(context, usr);

            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            avisoMutable.setValue("Usuario registrado");
            avisoVisibilityMutable.setValue(View.VISIBLE);
            Toast.makeText(context.getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
        } else {
            avisoMutable.setValue("El usuario ya existe");
            avisoVisibilityMutable.setValue(View.VISIBLE);
        }
    }

    public void Editar(Usuario usr) {
        ApiClient.guardar(context, usr);

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(context.getApplicationContext(), "Usuario Editado", Toast.LENGTH_SHORT).show();
        context.startActivity(intent);

        avisoMutable.setValue("Usuario editado");
        avisoVisibilityMutable.setValue(View.VISIBLE);
    }

}
