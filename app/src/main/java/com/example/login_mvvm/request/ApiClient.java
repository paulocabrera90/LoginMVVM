package com.example.login_mvvm.request;

import android.content.Context;
import android.widget.Toast;

import com.example.login_mvvm.model.Usuario;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {

    private static final String FILE_NAME = "usuario.dat";

    public static void guardar(Context context, Usuario usuario){

        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(usuario);

            oos.close();
            fos.close();
            Toast.makeText(context, "Usuario guardado exitosamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al guardar el usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static Usuario leerDatos(Context context){
        Usuario usuario = null;
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuario = (Usuario) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al leer los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: Clase no encontrada: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return usuario;
    }

    public static Usuario login(Context context,String email,String contrasena){
        Usuario usuario = leerDatos(context);
        if (usuario != null && email.equals(usuario.getEmail()) && contrasena.equals(usuario.getContrasena())) {
            return usuario;
        } else {
            Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
