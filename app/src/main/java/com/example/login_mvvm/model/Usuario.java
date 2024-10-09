package com.example.login_mvvm.model;

import android.net.Uri;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String dni;
    private String email;
    private String contrasena;
    private String apellido;
    private String nombre;
    private Uri image;

    // Constructor
    public Usuario(String dni, String apellido, String nombre, String email, String contrasena, Uri image) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.image = image;
    }
    // Getters
    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contrasena + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
