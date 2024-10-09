package com.example.login_mvvm.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.login_mvvm.model.Usuario;
import com.example.login_mvvm.request.ApiClient;
import com.example.login_mvvm.ui.registro.RegistroActivity;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<String> getAviso() {
        if (avisoMutable == null) {
            avisoMutable = new MutableLiveData<>();
        }
        return avisoMutable;
    }

    public LiveData<Integer> getAvisoVisibility() {
        if (avisoVisibilityMutable == null) {
            avisoVisibilityMutable = new MutableLiveData<>();
        }
        return avisoVisibilityMutable;
    }


    public void login(String email, String contrasena) {

        Usuario usuario = ApiClient.login(context, email, contrasena);
        if (usuario != null) {
            Intent intent = new Intent(context, RegistroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("isUser", true);
            context.startActivity(intent);
        } else {
            avisoMutable.setValue("Email o usuario incorrecto");
            avisoVisibilityMutable.setValue(View.VISIBLE);
        }
    }

    public void Registrarse() {
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
