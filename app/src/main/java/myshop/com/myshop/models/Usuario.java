package myshop.com.myshop.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import myshop.com.myshop.utils.MyShopApp;

public class Usuario extends RealmObject implements Serializable {


    @SerializedName("correo")
    private String correo;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("contrasena")
    private String contrasena;

    public Usuario(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Usuario(String correo, String nombre, String contrasena) {
        this.correo = correo;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public static Usuario getUsuario() {
        return MyShopApp.getRealmInstance()
                .where(Usuario.class)
                .findFirst();
    }

}
