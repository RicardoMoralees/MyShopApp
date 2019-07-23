package myshop.com.myshop.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import myshop.com.myshop.utils.MyShopApp;

public class Carrito extends RealmObject implements Serializable {

    @SerializedName("idCarritoUsuario")
    private int idCarritoUsuario;

    @SerializedName("productoList")
    private RealmList<Producto> productoList;

    @SerializedName("total")
    private double total;

    public int getIdCarritoUsuario() {
        return idCarritoUsuario;
    }

    public void setIdCarritoUsuario(int idCarritoUsuario) {
        this.idCarritoUsuario = idCarritoUsuario;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public static Carrito getCarrito() {
        return MyShopApp.getRealmInstance()
                .where(Carrito.class)
                .findFirst();
    }
}
