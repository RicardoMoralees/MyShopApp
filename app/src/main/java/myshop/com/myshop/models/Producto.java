package myshop.com.myshop.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import myshop.com.myshop.utils.MyShopApp;

public class Producto extends RealmObject implements Serializable {

    @SerializedName("idProducto")
    private int idProducto;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("precio")
    private double precio;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("imagen")
    private String imagen;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public static Producto getProducto() {
        return MyShopApp.getRealmInstance()
                .where(Producto.class)
                .findFirst();
    }

    public static List<Producto> getProductos() {
        Realm realm = MyShopApp.getRealmInstance();
        List<Producto> productos;
        realm.beginTransaction();
        productos = realm.copyFromRealm(realm.where(Producto.class).findAll());
        realm.commitTransaction();
        return productos;
    }
}
