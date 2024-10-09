package com.example.login_mvvm.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.login_mvvm.R;
import com.example.login_mvvm.model.Usuario;
import com.example.login_mvvm.request.ApiClient;
import com.example.login_mvvm.ui.login.LoginActivity;

public class RegistroActivityViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;
    private MutableLiveData<Usuario> usuarioMutable;
    private MutableLiveData<Uri> uriMutableLiveData;
    private Uri uri;

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();

        uriMutableLiveData = new MutableLiveData<>();
        uriMutableLiveData.setValue(Uri.parse("android.resource://" + getApplication().getPackageName() + "/" + R.drawable.upload));

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

    public LiveData<Uri> getUriMutable(){

        if(uriMutableLiveData==null){
            uriMutableLiveData=new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public LiveData<Uri> getImageUri() {
        return uriMutableLiveData;
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
        if (validarUsuario(usr)) {
            ApiClient.guardar(context, usr);
            Toast.makeText(context, "Usuario imagen URL Guardar" +  usr.getImage().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            avisoMutable.setValue("Usuario registrado");
            avisoVisibilityMutable.setValue(View.VISIBLE);
            Toast.makeText(context.getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
        } else {
            avisoMutable.setValue("Debe completar todos los datos");
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

    public void recibirFoto(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK){
            Intent data=result.getData();
            uri=data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                );
            }
            uriMutableLiveData.setValue(uri);
            Log.d("SALIDA-RECIBIR-FOTO", uri.toString());
        }
    }



    private boolean validarUsuario(Usuario usr) {
        return usr.getDni() != null && !usr.getDni().isEmpty() &&
                usr.getEmail() != null && !usr.getEmail().isEmpty() &&
                usr.getContrasena() != null && !usr.getContrasena().isEmpty() &&
                usr.getApellido() != null && !usr.getApellido().isEmpty() &&
                usr.getNombre() != null && !usr.getNombre().isEmpty();
    }

}
