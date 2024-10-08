package com.example.login_mvvm.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.login_mvvm.model.Usuario;

public class ApiClient {

    private static SharedPreferences sp;

    private static SharedPreferences conectar(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("datos", 0);
        }
        return sp;
    }

    public static void guardar(Context context, Usuario usuario){

        SharedPreferences sp=conectar(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putLong("dni", Long.parseLong((usuario.getDni())));
        editor.putString("apellido",usuario.getApellido());
        editor.putString("nombre",usuario.getNombre());
        editor.putString("email",usuario.getEmail());
        editor.putString("contrasena",usuario.getContrasena());
        editor.commit();
    }

    public static Usuario leerDatos(Context context){
        SharedPreferences sp=conectar(context);
        Long dni= sp.getLong("dni",-1);
        String apellido=sp.getString("apellido","-1");
        String nombre=sp.getString("nombre","-1");
        String email=sp.getString("email","-1");
        String contrasena=sp.getString("contrasena","-1");
        Usuario usuario=new Usuario(dni.toString(),apellido,nombre,email,contrasena);
        return usuario;
    }

    public static Usuario login(Context context,String email,String contrasena){

        Usuario usuario=null;

        SharedPreferences sp=conectar(context);
        Long dni= sp.getLong("dni",-1);
        String apellido=sp.getString("apellido","-1");
        String nombre=sp.getString("nombre","-1");
        String mail=sp.getString("email","-1");
        String contra=sp.getString("contrasena","-1");

        if (email.equals(mail) && contrasena.equals(contra)){
            usuario=new Usuario(dni.toString(),apellido,nombre,mail,contra);
        }

        return usuario;
    }
}
