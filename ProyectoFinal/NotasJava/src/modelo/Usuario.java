package modelo;

import java.sql.Timestamp;

public class Usuario {
    private int id;
    private String apodo;
    private String contrasena;
    private Timestamp fechaRegistro;

    public Usuario() {}

    public Usuario(String apodo, String contrasena) {
        this.apodo = apodo;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}